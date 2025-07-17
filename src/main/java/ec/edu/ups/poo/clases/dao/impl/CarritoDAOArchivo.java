package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.modelo.Carrito;
import ec.edu.ups.poo.clases.modelo.ItemCarrito;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoDAOArchivo implements CarritoDAO {
    private List<Carrito> carritos;
    private int contCodigo = 1;
    private File archivo;

    public CarritoDAOArchivo(File archivo) {
        this.archivo = archivo;
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

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(contCodigo++);
        Carrito copia = carrito.copiar();
        carritos.add(copia);
        guardarCarritos(carritos);
        System.out.println("Guardado carrito con código " + copia.getCodigo() +
                " con " + copia.obtenerItems().size() + " items.");
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for(Carrito carrito : carritos){
            if(carrito.getCodigo() == codigo){
                return carrito;
            }
        }
        return null;
    }

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

    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarCarritos(carritos);
    }

    @Override
    public List<Carrito> listarTodos() {
        for (Carrito c : carritos) {
            System.out.println("Listado - Carrito " + c.getCodigo() + " con " + c.obtenerItems().size() + " productos.");
        }
        return carritos;
    }

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
                writer.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Carrito> cargarCarritos() {
        List<Carrito> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", 4);
                if (partes.length < 4) continue;

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
                usuario.setUsername(username);
                carrito.setUsuario(usuario);
                carrito.setFechaCreacion(fecha);

                String itemsStr = partes[3];
                String[] itemsArr = itemsStr.split(";");
                for (String itemStr : itemsArr) {
                    if (itemStr.isEmpty()) continue;
                    String[] itemPartes = itemStr.split(":");
                    if (itemPartes.length != 2) continue;
                    int prodCodigo = Integer.parseInt(itemPartes[0]);
                    int cantidad = Integer.parseInt(itemPartes[1]);

                    // Producto placeholder con código
                    Producto producto = new Producto(prodCodigo, "Desconocido", 0.0);
                    carrito.agregarProducto(producto, cantidad);
                }

                lista.add(carrito);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
}


