import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class ModuloAdmin {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Peticion> peticiones = new ArrayList<>();
    private static ArrayList<Peticion> peticionesDelAdmin = new ArrayList<>();
    private static ArrayList<Categoria> categorias = new ArrayList<>();
    private static ArrayList<Admin> admins = new ArrayList<>();
    private static ArrayList<Ticket> tickets = new ArrayList<>();
    private static ArrayList<Tecnico> tecnicos = new ArrayList<>();
    private static ArrayList<DispositivoInventario> dispositivos = new ArrayList<>();


    static Admin usuarioEncontrado;
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
                loginAdmin();
            }

            peticionesDelAdmin = Utilidades.filtrarPeticionesPorCategoria(usuarioEncontrado.getIdCategoria(), peticiones);

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
        tecnicos = GestorDatos.cargarDatosTecnicos(tecnicos);
        dispositivos = GestorDatos.cargarDatosDispositivos(dispositivos);
    }

    public static void mostrarMenu() {
        System.out.println("0-Salir del programa");
        System.out.println("1-Consultar peticiones");
        System.out.println("2-Consultar tickets");
    }

    public static void loginAdmin() {
        System.out.println("Ingresa tu ID de admin");
        idIngresada = Utilidades.inputNumerico();
        usuarioEncontrado = Utilidades.buscarAdminPorId(idIngresada, admins);

        if(usuarioEncontrado == null) {
            System.out.println("Usuario no encontrado");
            return;
        }

        String passwordIngresada = Utilidades.pedirPassword();
        boolean esCorrecta = Utilidades.validarPassword(passwordIngresada, usuarioEncontrado.getPassword());

        if (esCorrecta) {
            System.out.println("\n¡Bienvenido, " + usuarioEncontrado.getNombre() + "! \uD83D\uDE00 \n");
            loginExitoso = true;
        } else
            System.out.println("Contraseña incorrecta.");
    }

    public static void mostrarUsuarios() {
        Categoria categoria;
        System.out.println("//Cuentas de administradores: ");
        for (Admin admin : admins) {
            categoria = Utilidades.buscarCategoriaPorId(admin.getIdCategoria(), categorias);
            System.out.println("ID: " + admin.getId() + ", PW: " + admin.getPassword() + ", GESTIONA: " + categoria.getCategoria());
        }
        //System.out.println("//Iniciar sesión con ID: " + admins.get(0).getId() + ", CONTRASEÑA: " + admins.get(0).getPassword());
        System.out.println();
    }

    public static void consultarPeticiones() {
        Utilidades.imprimirPeticiones(peticionesDelAdmin, usuarios, categorias);
        menuPeticiones();
    }

    static void menuPeticiones() {
        int option = 1;

        while (option != 0) {
            System.out.println("0: Atrás\t1: Generar ticket\t2: Cambiar petición de categoría");
            System.out.println("¿Qué acción desea realizar? ");
            option = Utilidades.inputNumerico();
            scanner.nextLine();

            if (option == 1) {
                System.out.println("Introduzca el ID de la petición a partir de la que quiere generar el ticket: ");
                int idPeticion = Utilidades.inputNumerico();
                Peticion peticion = Utilidades.buscarPeticionPorId(idPeticion, peticionesDelAdmin);
                if (peticion == null)
                    System.out.println("El ID de petición indicado no es válido: no se ha podido generar el nuevo ticket.");
                else
                    generarTicket(idPeticion);

            } else if (option == 2) {
                System.out.println("Introduzca el ID de la petición que quiere cambiar de categoría: ");
                int idPeticion = scanner.nextInt();
                scanner.nextLine();

                Utilidades.imprimirCategorias(categorias);
                System.out.println("Introduzca el número de la categoría a la que quiere que pertenezca la petición: ");
                int idNuevaCategoria = scanner.nextInt();
                System.out.println(idPeticion);
                System.out.println(idNuevaCategoria);
                boolean modificada = modificarCategoria(idPeticion, idNuevaCategoria);
                if(!modificada)
                    System.out.println("No tiene ninguna solicitud abierta con el ID de petición proporcionado: no se ha podido cambiar la categoría.");

            } else if (option == 0) {
                return;

            } else {
                System.out.println("La opción seleccionada no existe.");
            }
        }
    }

    public static boolean modificarCategoria(int idPeticion, int idNuevaCategoria) {
        boolean categoriaCambiada = false;
        for (int i = 0; i < peticiones.size(); i++) {
            if (peticiones.get(i).getId() == idPeticion) {
                peticiones.get(i).setIdCategoria(idNuevaCategoria);
                categoriaCambiada = true;
            }
        }
        return categoriaCambiada;
    }

    public static void consultarTickets() {
        Utilidades.imprimirTickets(tickets, tecnicos, dispositivos);
        menuTickets();
    }

    public static void menuTickets() {
        int option = 1;

        while (option != 0) {
            System.out.println("0: Atrás\t1: Modificar un ticket\t2: Filtrar por técnicos asignados\t3: Filtrar por dispositivo afectado");
            System.out.println("¿Qué acción desea realizar? ");
            option = Utilidades.inputNumerico();

            if (option == 1) {
                modificarTicketMenu();
            } else if (option == 2) {
                filtrarTicketPorTecnico();
            } else if (option == 3) {
                filtrarTicketPorInventario();
            } else if (option == 0) {
                return;
            } else {
                System.out.println("La opción seleccionada no existe.");
            }
        }
    }

    public static void generarTicket(int idPeticion) {
        int id = generarIdTicket();

        Tecnico tecnico = elegirTecnico();
        if (tecnico == null) {
            System.out.println("El técnico seleccionado no existe: no se ha podido generar el nuevo ticket.");
            return;
        }

        DispositivoInventario dispositivo = elegirDispositivo();
        if (dispositivo == null) {
            System.out.println("El dispositivo seleccionado no existe: no se ha podido generar el nuevo ticket.");
            return;
        }

        int urgencia = elegirUrgencia();
        if (urgencia < 1) {
            System.out.println("Valor inválido: no se ha podido generar el nuevo ticket.");
            return;
        }

        System.out.println("Ingrese la descripción de la tarea que tiene que realizar el técnico: ");
        String descripcion = scanner.nextLine();

        Ticket nuevoTicket = new Ticket(id, idPeticion, idIngresada, tecnico.getId(), dispositivo.getId(), urgencia, false, descripcion);
        tickets.add(nuevoTicket);
        System.out.println("El ticket ha sido generado con éxito.");
    }

    public static int generarIdTicket() {
        return tickets.get(tickets.size() - 1).getId() + 1;
    }

    public static Tecnico elegirTecnico() {
        Tecnico tecnico;
        System.out.println("TÉCNICOS:");
        Utilidades.imprimirTecnicos(tecnicos);
        System.out.println("Introduzca el número del técnico que quiere asignar al ticket: ");
        return tecnico = Utilidades.buscarTecnicoPorId(Utilidades.inputNumerico(), tecnicos);
    }

    public static DispositivoInventario elegirDispositivo() {
        DispositivoInventario dispositivo;
        System.out.println("DISPOSITIVOS:");
        Utilidades.imprimirDispositivos(dispositivos);
        System.out.println("Introduzca el número de dispositivo afectado: ");
        return dispositivo = Utilidades.buscarDispositivoPorId(Utilidades.inputNumerico(), dispositivos);
    }

    public static int elegirUrgencia() {
        System.out.println("Seleccione urgencia (1-5: 1 más alta, 5 más baja): ");
        int urgencia = Utilidades.inputNumerico();
        if(urgencia < 1 || urgencia > 5)
            return -1;
        return  urgencia;
    }

    public static void modificarTicketMenu() {
        System.out.println("Introduzca el ID del ticket que quiere modificar: ");
        int idTicket = Utilidades.inputNumerico();
        Ticket ticket = Utilidades.buscarTicketPorId(idTicket, tickets);
        if (ticket == null) {
            System.out.println("El ID introducido no es válido.");
            return;
        }

        int option = 1;
        while (option != 0) {
            System.out.println("0: Atrás\t 1: Cambiar de técnico\t2: Modificar descripción de la tarea");
            System.out.println("¿Qué acción desea realizar? ");
            option = Utilidades.inputNumerico();

            if (option == 1) {
                cambiarTecnico(ticket);
            } else if (option == 2) {
                cambiarTarea(ticket);
            } else if (option == 0){
                return;
            } else {
                System.out.println("La opción seleccionada no existe.");
            }
        }
    }

    public static void cambiarTecnico(Ticket ticket) {
        Tecnico tecnico = elegirTecnico();
        if (tecnico == null) {
            System.out.println("El técnico seleccionado no existe: no se ha podido modificar el técnico del ticket.");
            return;
        }
        ticket.setIdTecnico(tecnico.getId());
        System.out.println("El técnico del ticket " + ticket.getId() + " es ahora " + tecnico.getNombre() + ".");
    }

    public static void cambiarTarea(Ticket ticket){
        System.out.println("Introduzca la descripción de la nueva tarea: ");
        String nuevaTarea = scanner.nextLine();
        ticket.setDescripcion(nuevaTarea);
        System.out.println("La descripción ha sido cambiada con éxito");
    }

    public static void filtrarTicketPorTecnico() {
        System.out.println("Lista de Técnicos:");
        Utilidades.imprimirTecnicos(tecnicos);

        System.out.println("Introduzca el número del técnico para filtrar los tickets: ");
        int idTecnico = Utilidades.inputNumerico();

        ArrayList<Ticket> ticketsFiltrados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getIdTecnico() == idTecnico) {
                ticketsFiltrados.add(ticket);
            }
        }

        if (ticketsFiltrados.isEmpty()) {
            System.out.println("No hay tickets asignados al técnico con ID: " + idTecnico);
        } else {
            Utilidades.imprimirTickets(ticketsFiltrados, tecnicos, dispositivos);
        }
    }

    public static void filtrarTicketPorInventario() {
        System.out.println("Lista de Dispositivos:");
        Utilidades.imprimirDispositivos(dispositivos);

        System.out.println("Introduzca el número del dispositivo para filtrar los tickets: ");
        int idDispositivo = Utilidades.inputNumerico();

        ArrayList<Ticket> ticketsFiltrados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getIdDispositivos() == idDispositivo) {
                ticketsFiltrados.add(ticket);
            }
        }

        if (ticketsFiltrados.isEmpty()) {
            System.out.println("No hay tickets asociados al dispositivo con ID: " + idDispositivo);
        } else {
            Utilidades.imprimirTickets(ticketsFiltrados, tecnicos, dispositivos);
        }
    }
}
