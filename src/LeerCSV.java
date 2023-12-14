import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeerCSV {

    //Recibe la ruta del fichero CSV y devuelve un arraylist con los datos del Fichero.
    // Funciona independientemente del número de filas o columnas

    public static List<String[]> LectorDeCSV (String RutaCSV) {

        List<String[]> DatosDelCSV = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(RutaCSV))) {
            while (scanner.hasNextLine()) {
                DatosDelCSV.add(scanner.nextLine().split(","));
            }

            //Captura excepciones si la RutaCSV no es válida o no se tienen los permisos necesarios
        } catch (Exception e) {
            e.printStackTrace();

        }
        return DatosDelCSV;

    }

    public static void main(String[] args){

    List<String[]> PruebaLectorCSV = LectorDeCSV("CSV/admin.csv");

        for (String[] fila : PruebaLectorCSV) {
            for (String columna : fila) {
                System.out.print(columna + " ");
            }
            System.out.println();
        }
    }
}
