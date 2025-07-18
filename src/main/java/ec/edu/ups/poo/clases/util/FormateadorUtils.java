package ec.edu.ups.poo.clases.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase utilitaria para formatear valores como fechas y montos monetarios según una configuración regional.
 * Facilita la conversión de datos numéricos y temporales a cadenas legibles por el usuario.
 */
public class FormateadorUtils {

    /**
     * Formatea un valor numérico como moneda de acuerdo al locale especificado.
     * @param cantidad Valor numérico a formatear.
     * @param locale Configuración regional que determina el formato de moneda.
     * @return Cadena formateada como moneda.
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea una fecha en estilo medio (ej. 17-jul-2025) según el locale especificado.
     * @param fecha Fecha a formatear.
     * @param locale Configuración regional que determina el formato de fecha.
     * @return Cadena formateada como fecha.
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}

