import java.util.ArrayList;
import java.util.Scanner;

public class ModuloAdmin {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Peticion> peticiones = new ArrayList<>();
    private static ArrayList<Categoria> categorias = new ArrayList<>();
    private static ArrayList<Admin> admins = new ArrayList<>();
    private static ArrayList<Ticket> tickets = new ArrayList<>();
    private static ArrayList<Tecnico> tecnicos = new ArrayList<>();

    static Admin usuarioEncontrado;
    static boolean loginExitoso = false;
    static int idIngresada;
    static String passwordIngresada;
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        cargarDatos();

        int eleccionMenu;

        do {
            mostrarUsuarios();
            while (!loginExitoso) {
                identificarse();
            }

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

    public static void cargarDatos() {
        usuarios = GestorDatos.cargarDatosUsuario(usuarios);
        peticiones = GestorDatos.cargarDatosPeticiones(peticiones);
        categorias = GestorDatos.cargarDatosCategorias(categorias);
        admins = GestorDatos.cargarDatosAdmins(admins);
        tickets = GestorDatos.cargarDatosTickets(tickets);
        //tecnicos = GestorDatos.cargarDatosTecnicos(tecnicos);
    }

    public static void mostrarMenu() {
        System.out.println("0-Salir del programa");
        System.out.println("1-Consultar peticiones");
        System.out.println("2-Consultar tickets");
    }

    public static void identificarse() {
        System.out.println("Ingresa tu ID de administrador");
        idIngresada = Utilidades.inputNumerico();
        usuarioEncontrado = Utilidades.buscarAdminisPorId(idIngresada, admins);
        pedirPassword();
        validarPassword();
    }

    public static void pedirPassword() {
        if (usuarioEncontrado != null) {
            System.out.println("Ingresa tu contraseña");
            passwordIngresada = scanner.next();
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    public static void validarPassword() {
        boolean usuarioNoEsNulo = usuarioEncontrado != null;

        if (usuarioNoEsNulo) {
            boolean passwordCoincide = passwordIngresada.equals(usuarioEncontrado.getPassword());
            if (passwordCoincide) {
                System.out.println("\n¡Bienvenido, " + usuarioEncontrado.getNombre() + "! \uD83D\uDE00 \n");
                loginExitoso = true;
            } else {
                System.out.println("Contraseña incorrecta.");
            }
        }
    }

    public static void mostrarUsuarios() {
        System.out.println("//Iniciar sesión con ID: " + admins.get(0).getId() + ", CONTRASEÑA: " + admins.get(0).getPassword());
        System.out.println();
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
