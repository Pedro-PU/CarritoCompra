package ec.edu.ups.poo.clases.modelo;

/**
 * Enumeración que representa los posibles roles de los usuarios en el sistema.
 * Se utiliza para definir permisos, accesos y comportamientos específicos según el tipo de usuario.
 */
public enum Rol {

    /**
     * Rol con privilegios administrativos: puede gestionar usuarios, productos, carritos, y configuraciones.
     */
    ADMINISTRADOR,

    /**
     * Rol de usuario regular: puede realizar compras, consultar productos y utilizar funcionalidades estándar.
     */
    USUARIO
}
