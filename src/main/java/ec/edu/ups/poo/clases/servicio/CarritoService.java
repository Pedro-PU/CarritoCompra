package ec.edu.ups.poo.clases.servicio;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.modelo.ItemCarrito;
import java.util.List;
public interface CarritoService {
    void agregarProducto(Producto producto, int cantidad);

    void eliminarProducto(int codigoProducto);

    void vaciarCarrito();

    double calcularTotal();

    List<ItemCarrito> obtenerItems();

    boolean estaVacio();

}
