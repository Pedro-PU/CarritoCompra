package ec.edu.ups.poo.clases.modelo;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase que representa un carrito de compras.
 * Almacena productos seleccionados por el usuario junto con sus cantidades, fecha de creación y métodos para cálculos de total.
 */
public class Carrito {

    private final double IVA = 0.12;
    //private static int contador = 1;
    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario usuario;

    /**
     * Constructor del carrito.
     * Inicializa la lista de productos y cantidades como vacía.
     */
    public Carrito() {
        //codigo = contador++;
        items = new ArrayList<>();
    }

    /**
     * Obtiene el usuario asociado al carrito.
     * @return Usuario que posee el carrito.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado al carrito.
     * @param usuario Usuario que realiza la compra.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el código identificador del carrito.
     * @return Código del carrito.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código identificador del carrito.
     * @param codigo Código único del carrito.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha de creación del carrito.
     * @return Fecha en formato GregorianCalendar.
     */
    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del carrito.
     * @param fechaCreacion Fecha en formato GregorianCalendar.
     */
    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Agrega un producto al carrito. Si ya existe, suma la cantidad.
     * @param producto Producto a agregar.
     * @param cantidad Cantidad a agregar.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito según su código.
     * @param codigoProducto Código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Vacía completamente el carrito.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Devuelve la lista de productos con sus cantidades.
     * @return Lista de ItemCarrito.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito está vacío.
     * @return true si no hay productos; false en caso contrario.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Calcula el subtotal sumando precios por cantidad de todos los productos.
     * @return Valor total sin impuestos.
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
            System.out.println("Producto: " + item.getProducto().getNombre() +
                    ", Precio: " + item.getProducto().getPrecio() + ", Cantidad: " + item.getCantidad());
        }
        return subtotal;
    }

    /**
     * Calcula el valor del IVA a partir del subtotal.
     * @return Valor del IVA.
     */
    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    /**
     * Calcula el total de la compra incluyendo IVA.
     * @return Total final a pagar.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Genera una copia del carrito actual incluyendo productos, cantidades, usuario y fecha.
     * @return Nuevo objeto Carrito duplicado.
     */
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

