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
        if (ticketsDelTecnico.isEmpty()) {
            System.out.println("No hay tickets asignados al técnico actual.");
            return;
        }

        Utilidades.imprimirTickets(ticketsDelTecnico, tecnicos, dispositivos);

        System.out.println("Ingrese el ID del ticket que desea modificar (0 para regresar): ");
        int idTicket = Utilidades.inputNumerico();

        if (idTicket == 0) {
            return;
        }

        Ticket ticketSeleccionado = Utilidades.buscarTicketPorId(idTicket, ticketsDelTecnico);

        if (ticketSeleccionado == null) {
            System.out.println("ID de ticket no válido.");
            return;
        }

        System.out.println("Seleccione una opción:");
        System.out.println("1. Modificar dispositivo");
        System.out.println("2. Modificar estado");

        int opcion = Utilidades.inputNumerico();

        switch (opcion) {
            case 1:
                modificarDispositivo(ticketSeleccionado);
                break;
            case 2:
                modificarEstado(ticketSeleccionado);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    public static void modificarDispositivo(Ticket ticket) {
        System.out.println("Seleccione el nuevo dispositivo (ID): ");
        Utilidades.imprimirDispositivos(dispositivos);

        int idDispositivo = Utilidades.inputNumerico();
        DispositivoInventario nuevoDispositivo = Utilidades.buscarDispositivoPorId(idDispositivo, dispositivos);

        if (nuevoDispositivo == null) {
            System.out.println("ID de dispositivo no válido.");
            return;
        }

        ticket.setIdDispositivos(nuevoDispositivo.getId());
        System.out.println("Dispositivo modificado con éxito.");
    }

    public static void modificarEstado(Ticket ticket) {
        System.out.println("Seleccione el nuevo estado (1: Resuelto, 0: Pendiente): ");
        int nuevoEstado = Utilidades.inputNumerico();

        if (nuevoEstado == 0 || nuevoEstado == 1) {
            ticket.setResuelto(nuevoEstado == 1);
            System.out.println("Estado modificado con éxito.");
        } else {
            System.out.println("Opción no válida.");
        }
    }
}
