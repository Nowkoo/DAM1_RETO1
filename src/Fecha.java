import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//PARA OBTENER LA FECHA ACTUAL DEL ORDENADOR Y PODER IMPRIMIRLA EN LAS PETICIONES.
public class Fecha {

    public static String ObtenerFechaActual () {
        LocalDateTime fechaActual = LocalDateTime.now();


        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd.MM.yy");
        String fechaFormateada = fechaActual.format(formato);

        return fechaFormateada;
    }
}