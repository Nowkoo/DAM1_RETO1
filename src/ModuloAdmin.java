import java.util.ArrayList;
import java.util.Scanner;

public class ModuloAdmin {

    static Scanner scanner = new Scanner(System.in);

    private ArrayList usuarios;
    private ArrayList peticiones;
    private ArrayList categoria;
    private static ArrayList tickets;
    private static ArrayList tecnicos;
    private static ArrayList Administradores;


    private Usuario usuarioActual;

    public static void main(String[] args) {

    }

    public static void loginAdmin(String rol) {
        System.out.println("Ingresa tu ID de " + rol);

    }

    public static void consultarPeticiones() {

    }

    public static void consultarTickets() {

    }

    public static void generarTicket() {
        scanner.nextLine();

        System.out.println("Ingrese la descripción de la tarea");
        String descripcion = scanner.nextLine();


        System.out.println("Ingrese la ID de la categoria de la peticion:");
        int idCategoria = scanner.nextInt();

        Ticket nuevoTicket = new Ticket(
                1,
                1,
                1,
                1,
                1,
                "1",
                "1");
        tickets.add(nuevoTicket);
        System.out.println("El ticket ha sido generado con éxito.");
      /*

        Peticion nuevaPeticion = new Peticion(
                obtenerNuevoIdPeticion(),
                idIngresada,
                descripcion,
                fecha,
                idCategoria,
                2, //harcodeado de forma provisional
                2, //harcodeado de forma provisional
                true
        );

        // Agrega la nueva petición al ArrayList de peticiones
        peticiones.add(nuevaPeticion);

        System.out.println("La petición ha sido generada con éxito.");
*/
    }

    public static void modificarTicket() {

    }

    public static void modificarCategoriaPeticion() {

    }

    public static void filtrarTicketPorTiempo() {

    }

    public static void filtrarTicketPorInventario() {

    }

    public static void cargarDatosUsuario() {

    }

    public static void cargarDatosPeticiones() {

    }

    public static void cargarDatosCategoria() {

    }

    public static void cargarDatosTecnicos() {

    }

    public static void cargarDatosAdmins() {

    }

    public static void cargarDatosTickets() {

    }

    public static void guardarDatosPeticiones() {

    }

    public static void guardarDatosTickets() {

    }
}
