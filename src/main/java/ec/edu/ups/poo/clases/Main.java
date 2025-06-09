package ec.edu.ups.poo.clases;

public class Main {
    public static void main(String[] args) {
        Producto p1 = new Producto("P001", "Galleta",2);
        Producto p2 = new Producto("P002", "Pan",5);
        Producto p3 = new Producto("P003", "Tomate",10);
        CarritoCompra cc = new CarritoCompra();
        cc.agregarItem(5,p1);
        cc.agregarItem(10, p2);
        cc.agregarItem(2,p3);
        cc.calcularTotalCompra();

        System.out.println("Carrito:");
        for (ItemCarrito item : cc.getItems()) {
            System.out.println("\t" + item);
        }
        System.out.println("\tTotal: " + cc.calcularTotalCompra());
    }
}
