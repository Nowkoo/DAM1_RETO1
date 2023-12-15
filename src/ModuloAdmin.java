import java.util.ArrayList;
import java.util.Scanner;

public class ModuloAdmin {
    private static ArrayList<Usuario> usuarios;
    private static ArrayList<Peticion> peticiones;
    private static ArrayList<Categoria> categorias;
    private static ArrayList<Admin> admins;
    private static ArrayList<Ticket> tickets;
    private static ArrayList<Tecnico> tecnicos;

    private Usuario usuarioActual;
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        usuarios = GestorDatos.cargarDatosUsuario(usuarios);
        peticiones = GestorDatos.cargarDatosPeticiones(peticiones);
        categorias = GestorDatos.cargarDatosCategorias(categorias);

        int eleccionMenu;
        String rol = "usuario";

        do {

            mostrarMenu();
            eleccionMenu = Utilidades.inputNumerico();

            if (eleccionMenu < 0 || eleccionMenu > 3) {
                System.out.println("El número introducido no es válido, por favor introduce otro número");
            }
            if (eleccionMenu == 0) {
                guardarDatosPeticiones();
            }

            switch (eleccionMenu) {
                case 1:
                    consultarPeticiones();
                    break;
                case 2:
                    consultarTickets();
                    break;
            }
        } while (eleccionMenu != 0);

    }

    public static void mostrarMenu() {
        System.out.println("0-Salir del programa");
        System.out.println("1-Consultar peticiones");
        System.out.println("2-Consultar tickets");
    }

    public static void loginAdmin(String rol) {

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
