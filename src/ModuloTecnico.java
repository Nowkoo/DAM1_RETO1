import java.util.ArrayList;

public class ModuloTecnico {
    private static ArrayList<Ticket> tickets = new ArrayList<>();
    private static ArrayList<Peticion> peticiones = new ArrayList<>();
    private static ArrayList<Tecnico> tecnicos = new ArrayList<>();
    private static ArrayList<DispositivoInventario> dispositivos = new ArrayList<>();
    private static ArrayList<Ticket> ticketsDelTecnico = new ArrayList<>();

    static Tecnico usuarioActual;
    static boolean loginExitoso = false;
    static int idIngresada;

    public static void ingresarComoTecnico() {
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
                System.out.println("La opción seleccionada no existe, pruebe otra vez:\n");
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

    //Carga los datos de los archivos csv que utiliza este módulo en arrays.
    public static void cargarDatos() {
        peticiones = GestorDatos.cargarDatosPeticiones(peticiones);
        tickets = GestorDatos.cargarDatosTickets(tickets);
        tecnicos = GestorDatos.cargarDatosTecnicos(tecnicos);
        dispositivos = GestorDatos.cargarDatosDispositivos(dispositivos);
    }

    //Texto del menú.
    public static void mostrarMenu() {
        System.out.println("0-Cerrar sesión.");
        System.out.println("1-Gestionar mis tickets.");
        System.out.println("2-Ver todos los tickets.");
    }

    //Permite iniciar sesión al técnico.
    public static void loginTecnico() {
        System.out.println("Ingresa tu ID de técnico:\n");
        idIngresada = Utilidades.inputNumerico();
        usuarioActual = Utilidades.buscarTecnicoPorId(idIngresada, tecnicos);

        if(usuarioActual == null) {
            System.out.println("Usuario no encontrado.\n");
            return;
        }

        String passwordIngresada = Utilidades.pedirPassword();
        boolean esCorrecta = Utilidades.validarPassword(passwordIngresada, usuarioActual.getPassword());

        if (esCorrecta) {
            System.out.println("\n¡Bienvenido/a, " + usuarioActual.getNombre() + "! :) \n");
            loginExitoso = true;
        } else
            System.out.println("Contraseña incorrecta.\n");
    }

    //Muestra las credenciales de un técnico para facilitar pruebas en el programa.
    public static void mostrarUsuarios() {
        System.out.println("//Iniciar sesión con ID: " + tecnicos.get(0).getId() + ", CONTRASEÑA: " + tecnicos.get(0).getPassword());
        System.out.println();
    }

    //Imprime los tickets asignados al técnico que ha iniciado sesión y gestiona el menú
    //en el que se decide que hacer con ellos: modificar o filtrar por estado.
    public static void consultarTickets() {
        if (ticketsDelTecnico.isEmpty()) {
            System.out.println("No hay tickets asignados al técnico actual.\n");
            return;
        }
        Utilidades.imprimirTickets(ticketsDelTecnico, tecnicos, dispositivos);

        int opcion = 1;
        while (opcion != 0) {
            System.out.println("0: Atrás\n1: Modificar ticket\n2: Filtrar por tickets resueltos\n3: Filtrar por tickets sin resolver");
            System.out.println("¿Qué quiere hacer?");
            opcion = Utilidades.inputNumerico();
            switch (opcion) {
                case 0:
                    break;
                case 1:
                    modificarTickets();
                    break;
                case 2:
                    filtrarTicketsResueltos();
                    break;
                case 3:
                    filtrarTicketsSinResolver();
                    break;
                default:
                    System.out.println("Opción no válida.\n");
            }
        }
    }

    //Pregunta al técnico qué ticket quiere modificar, comprueba que no sea nulo y gestiona
    //varias opciones: modificar el dispositivo asignado al ticket o cambiar el estado del ticket.
    public static void modificarTickets() {
        System.out.println("Ingrese el ID del ticket que desea modificar (0 para regresar): ");
        int idTicket = Utilidades.inputNumerico();

        if (idTicket == 0) {
            return;
        }

        Ticket ticketSeleccionado = Utilidades.buscarTicketPorId(idTicket, ticketsDelTecnico);

        if (ticketSeleccionado == null) {
            System.out.println("ID de ticket no válido.\n");
            return;
        } else if (ticketSeleccionado.getResuelto()) {
            System.out.println("No se puede modificar un ticket que ya ha sido resuelto.\n");
            return;
        }

        System.out.println("Seleccione una opción:");
        System.out.println("0: Cancelar operación");
        System.out.println("1: Modificar dispositivo");
        System.out.println("2: Modificar estado");

        int opcion = Utilidades.inputNumerico();

        switch (opcion) {
            case 0:
                break;
            case 1:
                modificarDispositivo(ticketSeleccionado);
                break;
            case 2:
                modificarEstado(ticketSeleccionado);
                break;
            default:
                System.out.println("Opción no válida.\n");
        }
    }

    //Da a elegir al técnico cuál quiere que sea el nuevo dispositivo asignado al ticket,
    //comprueba que no sea nulo y lo asigna.
    public static void modificarDispositivo(Ticket ticket) {
        System.out.println("Seleccione el nuevo dispositivo (ID): ");
        Utilidades.imprimirDispositivos(dispositivos);

        int idDispositivo = Utilidades.inputNumerico();
        DispositivoInventario nuevoDispositivo = Utilidades.buscarDispositivoPorId(idDispositivo, dispositivos);

        if (nuevoDispositivo == null) {
            System.out.println("ID de dispositivo no válido.\n");
            return;
        }

        ticket.setIdDispositivos(nuevoDispositivo.getId());
        System.out.println("Dispositivo modificado con éxito.\n");
    }

    //El usuario puede decidir si marcar o no un ticket como resuelto.
    public static void modificarEstado(Ticket ticket) {
        System.out.println("0: Atrás\t 1: Marcar ticket como resuelto");
        int nuevoEstado = Utilidades.inputNumerico();

        if (nuevoEstado == 1) {
            ticket.setResuelto(true);
            System.out.println("El ticket ha sido marcado como resuelto: ya no podrá ser modificado.\n");
            actualizarEstadoPeticiones(ticket);
        } else if (nuevoEstado == 0) {
            return;
        } else {
            System.out.println("Opción no válida.\n");
        }
    }

    //Imprime todos los tickets sin resolver
    public static void filtrarTicketsSinResolver() {
        ArrayList<Ticket> ticketsFiltrados = Utilidades.filtrarTicketsPorEstado(false, ticketsDelTecnico);
        if (ticketsFiltrados.isEmpty()) {
            System.out.println("Todos los tickets han sido resueltos.\n");
            return;
        }
        System.out.println("Tickets sin resolver: ");
        Utilidades.imprimirTickets(ticketsFiltrados, tecnicos, dispositivos);
    }

    //Imprime todos los tickets resueltos.
    public static void filtrarTicketsResueltos() {
        ArrayList<Ticket> ticketsFiltrados = Utilidades.filtrarTicketsPorEstado(true, ticketsDelTecnico);
        if (ticketsFiltrados.isEmpty()) {
            System.out.println("No hay tickets resueltos.\n");
        }
        System.out.println("Tickets resueltos:");
        Utilidades.imprimirTickets(ticketsFiltrados, tecnicos, dispositivos);
    }

    //Cuando un ticket se resuelve, comprueba el estado del resto de tickets asociados
    //a la misma petición que el ticket que ha sido resuelto. Si todos los tickets asociados
    //a la petición han sido resueltos, marca la petición como resuelta también.
    public static void actualizarEstadoPeticiones(Ticket ticket) {
        boolean hayTicketsPendientes = false;
        for (Ticket t : tickets) {
            if (t.getIdPeticion() == ticket.getIdPeticion()) {
                if (!t.getResuelto()) {
                    hayTicketsPendientes = true;
                }
            }
        }
        if (!hayTicketsPendientes) {
            Peticion peticion = Utilidades.buscarPeticionPorId(ticket.getIdPeticion(), peticiones);
            peticion.setResuelta(true);
            System.out.println("Todos los tickets asociados a esta petición han sido resueltos.");
            System.out.println("La petición ha sido marcada también como resuelta.\n");
        }
    }
}
