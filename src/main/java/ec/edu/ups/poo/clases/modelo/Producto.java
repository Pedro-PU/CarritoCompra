package ec.edu.ups.poo.clases.modelo;

/**
 * Clase que representa un producto disponible en el sistema.
 * Contiene atributos básicos como código, nombre y precio.
 */
public class Producto {

    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor que inicializa un producto con sus atributos principales.
     * @param codigo Código único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     * @return Entero que representa el código.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del producto.
     * @param codigo Nuevo valor para el código.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre del producto.
     * @return Cadena con el nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     * @return Valor numérico del precio.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio Nuevo valor para el precio.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Retorna una representación en texto del producto, útil para depuración o visualización.
     * @return Cadena con los atributos del producto.
     */
    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}

