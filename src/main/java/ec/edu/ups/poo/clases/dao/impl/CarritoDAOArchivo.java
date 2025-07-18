package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.*;
import ec.edu.ups.poo.clases.util.Cedula;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
/**
 * Implementación del DAO de Carrito que persiste los datos en un archivo de texto plano.
 * Se encarga de realizar operaciones CRUD, cargar y guardar carritos, y mantener la integridad del contador de códigos.
 */
public class CarritoDAOArchivo implements CarritoDAO {
    private List<Carrito> carritos;
    private int contCodigo = 1;
    private File archivo;
    private ProductoDAO productoDAO;

    /**
     * Constructor que inicializa el DAO con acceso a un archivo y un DAO de productos.
     * Si el archivo ya existe, carga los carritos y ajusta el contador; caso contrario, crea uno nuevo.
     * @param archivo Archivo de persistencia.
     * @param productoDAO DAO para resolver referencias de productos.
     */
    public CarritoDAOArchivo(File archivo, ProductoDAO productoDAO) {
        this.archivo = archivo;
        this.productoDAO = productoDAO;
        if (archivo.exists()) {
            carritos = cargarCarritos();
            // Ajustar contCodigo al máximo código + 1 para evitar duplicados
            contCodigo = carritos.stream()
                    .mapToInt(Carrito::getCodigo)
                    .max()
                    .orElse(0) + 1;
        } else {
            carritos = new ArrayList<>();
            guardarCarritos(carritos);
        }
    }
    /**
     * Asigna un nuevo código único al carrito, lo copia para preservar encapsulamiento,
     * lo agrega a la lista y lo persiste en el archivo.
     * @param carrito Carrito a registrar.
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(contCodigo++);
        Carrito copia = carrito.copiar();
        carritos.add(copia);
        guardarCarritos(carritos);
        System.out.println("Guardado carrito con código " + copia.getCodigo() +
                " con " + copia.obtenerItems().size() + " items.");
    }

    /**
     * Busca un carrito por su código en la lista cargada desde archivo.
     * @param codigo Código único del carrito.
     * @return Carrito encontrado; null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for(Carrito carrito : carritos){
            if(carrito.getCodigo() == codigo){
                return carrito;
            }
        }
        return null;
    }

    /**
     * Actualiza el carrito existente en la lista y guarda los cambios en el archivo.
     * @param carrito Carrito con datos modificados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                guardarCarritos(carritos);
                return;
            }
        }
    }

    /**
     * Elimina el carrito por su código de la lista y actualiza el archivo.
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarCarritos(carritos);
    }

    /**
     * Lista todos los carritos, mostrando información básica de cada uno en consola.
     * @return Lista de carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        for (Carrito c : carritos) {
            System.out.println("Listado - Carrito " + c.getCodigo() + " con " + c.obtenerItems().size() + " productos.");
        }
        return carritos;
    }

    /**
     * Devuelve una lista de carritos asociados a un usuario por su nombre de usuario.
     * @param usuario Usuario cuyos carritos se desean buscar.
     * @return Lista de carritos del usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario() != null &&
                    carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                resultado.add(carrito);
            }
        }
        return resultado;
    }
    /**
     * Guarda todos los carritos en el archivo especificado utilizando formato CSV extendido.
     * Incluye código, usuario, fecha, items y totales.
     * @param carritos Lista de carritos a guardar.
     */
    private void guardarCarritos(List<Carrito> carritos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            for (Carrito c : carritos) {
                StringBuilder sb = new StringBuilder();
                sb.append(c.getCodigo()).append(",");
                sb.append(c.getUsuario() != null ? c.getUsuario().getUsername() : "").append(",");
                GregorianCalendar fecha = c.getFechaCreacion();
                if (fecha != null) {
                    sb.append(fecha.get(Calendar.YEAR)).append("-")
                            .append(fecha.get(Calendar.MONTH)).append("-")
                            .append(fecha.get(Calendar.DAY_OF_MONTH)).append(",");
                } else {
                    sb.append("0-0-0,");
                }

                for (ItemCarrito item : c.obtenerItems()) {
                    sb.append(item.getProducto().getCodigo())
                            .append(":")
                            .append(item.getCantidad())
                            .append(";");
                }
                sb.append(",");
                sb.append(c.calcularSubtotal()).append(",");
                sb.append(c.calcularIVA()).append(",");
                sb.append(c.calcularTotal());

                writer.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Carga los carritos desde el archivo al iniciar el sistema.
     * Reconstruye objetos incluyendo fecha, usuario, productos y cantidades.
     * @return Lista de carritos recuperados.
     */
    private List<Carrito> cargarCarritos() {
        List<Carrito> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", 7); // código, usuario, fecha, items, subtotal, iva, total
                if (partes.length < 7) continue;

                int codigo = Integer.parseInt(partes[0]);
                String username = partes[1];

                String[] fechaPartes = partes[2].split("-");
                GregorianCalendar fecha = new GregorianCalendar(
                        Integer.parseInt(fechaPartes[0]),
                        Integer.parseInt(fechaPartes[1]),
                        Integer.parseInt(fechaPartes[2])
                );

                Carrito carrito = new Carrito();
                carrito.setCodigo(codigo);
                Usuario usuario = new Usuario();
                try {
                    usuario.setUsername(username);
                    carrito.setUsuario(usuario);
                    carrito.setFechaCreacion(fecha);
                } catch (Cedula e) {
                    System.err.println("Advertencia: Cédula inválida '" + username + "' al cargar carrito " + codigo + ". Se asignará usuario nulo.");
                    carrito.setUsuario(null);
                }

                String itemsStr = partes[3];
                String[] itemsArr = itemsStr.split(";");
                for (String itemStr : itemsArr) {
                    if (itemStr.isEmpty()) continue;
                    String[] itemPartes = itemStr.split(":");
                    if (itemPartes.length != 2) continue;

                    int prodCodigo = Integer.parseInt(itemPartes[0]);
                    int cantidad = Integer.parseInt(itemPartes[1]);
                    Producto producto = productoDAO.buscarPorCodigo(prodCodigo);
                    if (producto == null) {
                        producto = new Producto(prodCodigo, "Desconocido", 0.0);
                    }
                    carrito.agregarProducto(producto, cantidad);
                }

                try {
                    double subtotal = Double.parseDouble(partes[4]);
                    double iva = Double.parseDouble(partes[5]);
                    double total = Double.parseDouble(partes[6]);

                    System.out.printf("Carrito %d: Subtotal=%.2f, IVA=%.2f, Total=%.2f%n",
                            codigo, subtotal, iva, total);
                } catch (NumberFormatException e) {
                    System.err.println("Error parseando montos del carrito " + codigo);
                }

                lista.add(carrito);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}


