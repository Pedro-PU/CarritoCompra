package ec.edu.ups.poo.clases.modelo;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Carrito {
    private final double IVA = 0.12;
    //private static int contador = 1;
    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario usuario;

    public Carrito() {
        //codigo = contador++;
        items = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }


    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
            System.out.println("Producto: " + item.getProducto().getNombre() +
                    ", Precio: " + item.getProducto().getPrecio() + ", Cantidad: " + item.getCantidad());
        }

        return subtotal;
    }

    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    public Carrito copiar() {
        Carrito copia = new Carrito();
        copia.setFechaCreacion(this.fechaCreacion);
        copia.setUsuario(this.usuario);
        copia.setCodigo(this.codigo);

        for (ItemCarrito item : this.items) {
            Producto producto = item.getProducto();
            int cantidad = item.getCantidad();
            copia.agregarProducto(producto, cantidad);
        }

        return copia;
    }
}
