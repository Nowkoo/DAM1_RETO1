import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fecha {

    public static String ObtenerFechaActual () {
        LocalDateTime fechaActual = LocalDateTime.now();


        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd.MM.yy");
        String fechaFormateada = fechaActual.format(formato);

        return fechaFormateada;
    }

    public static void main(String[] args) {
        System.out.println(ObtenerFechaActual());
    }
}