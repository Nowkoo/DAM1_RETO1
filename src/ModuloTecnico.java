import java.util.ArrayList;
import java.util.Scanner;

public class ModuloTecnico {
    private static ArrayList<Ticket> tickets = new ArrayList<>();
    private static ArrayList<Peticion> peticiones = new ArrayList<>();
    private static ArrayList<Tecnico> tecnicos = new ArrayList<>();
    private static ArrayList<DispositivoInventario> dispositivos = new ArrayList<>();
    private static ArrayList<Ticket> ticketsDelTecnico = new ArrayList<>();

    static Tecnico usuarioActual;
    static boolean loginExitoso = false;
    static int idIngresada;
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        cargarDatos();

        int eleccionMenu;

        do {
            while (!loginExitoso) {
                mostrarUsuarios();
                loginTecnico();
            }

            ticketsDelTecnico = Utilidades.filtrarTicketsPorTecnico(usuarioActual.getId(), tickets);

            mostrarMenu();
            eleccionMenu = Utilidades.inputNumerico();

            if (eleccionMenu < 0 || eleccionMenu > 3) {
                System.out.println("El número introducido no es válido, por favor introduce otro número");
            }
            if (eleccionMenu == 0) {
                GestorDatos.guardarDatosPeticiones(peticiones);
                GestorDatos.guardarDatosTickets(tickets);
            }

            switch (eleccionMenu) {
                case 1:
                    consultarTickets();
                    break;
                case 2:
                    Utilidades.imprimirTickets(tickets, tecnicos, dispositivos);
            }

        } while (eleccionMenu != 0);
    }

    public static void cargarDatos() {
        peticiones = GestorDatos.cargarDatosPeticiones(peticiones);
        tickets = GestorDatos.cargarDatosTickets(tickets);
        tecnicos = GestorDatos.cargarDatosTecnicos(tecnicos);
        dispositivos = GestorDatos.cargarDatosDispositivos(dispositivos);
    }

    public static void mostrarMenu() {
        System.out.println("0-Salir del programa");
        System.out.println("1-Consultar mis tickets");
        System.out.println("2-Consultar todos los tickets");
    }

    public static void loginTecnico() {
        System.out.println("Ingresa tu ID de técnico");
        idIngresada = Utilidades.inputNumerico();
        usuarioActual = Utilidades.buscarTecnicoPorId(idIngresada, tecnicos);

        if(usuarioActual == null) {
            System.out.println("Usuario no encontrado");
            return;
        }

        String passwordIngresada = Utilidades.pedirPassword();
        boolean esCorrecta = Utilidades.validarPassword(passwordIngresada, usuarioActual.getPassword());

        if (esCorrecta) {
            System.out.println("\n¡Bienvenido, " + usuarioActual.getNombre() + "! \uD83D\uDE00 \n");
            loginExitoso = true;
        } else
            System.out.println("Contraseña incorrecta.");
    }

    public static void mostrarUsuarios() {
        System.out.println("//Iniciar sesión con ID: " + tecnicos.get(0).getId() + ", CONTRASEÑA: " + tecnicos.get(0).getPassword());
        System.out.println();
    }

    public static void consultarTickets() {
        //Imprime los tickets asignados al técnico que ha iniciado sesión.
        //Mini menú que da la opción de modificar el dispositivo o el estado de un ticket
        //seleccionado en la lista.
    }

    public static void modificarDispositivo() {
        //Permite al técnico cambiar el dispositivo asociado a un ticket.
    }

    public static void modificarEstado() {
        //Permite al técnico cambiar el estado de un ticket de false (pendiente) a true (resuelto).
    }
}
