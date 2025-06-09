package ec.edu.ups.poo.clases;
import java.util.List;
import java.util.ArrayList;
public class CarritoCompra implements CarritoCalculable {
    private List<ItemCarrito> items;
    public CarritoCompra() {
        items = new ArrayList<>();
    }
    public void agregarItem(int cantidad, Producto producto) {
        items.add(new ItemCarrito(cantidad, producto));
    }
    public void removerItem(int cantidad, Producto producto) {
        items.remove(new ItemCarrito(cantidad, producto));
    }
    public List<ItemCarrito> getItems() {
        return items;
    }
    @Override
    public double calcularTotalCompra() {
        double total = 0;
        for (ItemCarrito item : items) {
            total = item.getCantidad() * item.getProducto().getPrecio();
        }
        return total;
    }

}
