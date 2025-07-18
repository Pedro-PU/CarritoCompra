package ec.edu.ups.poo.clases.modelo;

/**
 * Clase que representa un ítem dentro del carrito de compras.
 * Asocia un producto con la cantidad seleccionada por el usuario.
 */
public class ItemCarrito {

    private Producto producto;
    private int cantidad;

    /**
     * Constructor que crea un nuevo ítem de carrito con el producto y la cantidad indicada.
     * @param producto Producto seleccionado.
     * @param cantidad Cantidad del producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    /**
     * Obtiene el producto asociado a este ítem.
     * @return Objeto Producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado a este ítem.
     * @param producto Objeto Producto.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Obtiene la cantidad del producto en este ítem.
     * @return Cantidad seleccionada.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto en este ítem.
     * @param cantidad Valor de cantidad.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve una representación textual del ítem con información de producto y cantidad.
     * @return Cadena representativa del objeto.
     */
    @Override
    public String toString() {
        return "ItemCarrito{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }
}

