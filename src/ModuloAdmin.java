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

    //Permite iniciar sesión al administrador.
    public static void ingresarComoAdmin() {
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
                System.out.println("La opción seleccionada no existe, pruebe otra vez:\n");
            }
            if (eleccionMenu == 0) {
                GestorDatos.guardarDatosPeticiones(peticiones);
                GestorDatos.guardarDatosTickets(tickets);
            }

            switch (eleccionMenu) {
                case 1:
                    menuPeticiones();
                    break;
                case 2:
                    Utilidades.imprimirPeticiones(peticiones, usuarios, categorias);
                    break;
                case 3:
                    consultarTickets();
                    break;
            }
        } while (eleccionMenu != 0);

    }

    //Carga los datos de los csv.
    public static void cargarDatos() {
        usuarios = GestorDatos.cargarDatosUsuario(usuarios);
        peticiones = GestorDatos.cargarDatosPeticiones(peticiones);
        categorias = GestorDatos.cargarDatosCategorias(categorias);
        admins = GestorDatos.cargarDatosAdmins(admins);
        tickets = GestorDatos.cargarDatosTickets(tickets);
        tecnicos = GestorDatos.cargarDatosTecnicos(tecnicos);
        dispositivos = GestorDatos.cargarDatosDispositivos(dispositivos);
    }

    //Texto del menú.
    public static void mostrarMenu() {
        System.out.println("0-Cerrar sesión.");
        System.out.println("1-Gestionar peticiones.");
        System.out.println("2-Ver todas las peticiones.");
        System.out.println("3-Gestionar tickets.");
    }

    //Permite iniciar sesión al administrador.
    public static void loginAdmin() {
        System.out.println("Ingresa tu ID de admin:");
        idIngresada = Utilidades.inputNumerico();
        usuarioEncontrado = Utilidades.buscarAdminPorId(idIngresada, admins);

        if(usuarioEncontrado == null) {
            System.out.println("Usuario no encontrado.\n");
            return;
        }

        String passwordIngresada = Utilidades.pedirPassword();
        boolean esCorrecta = Utilidades.validarPassword(passwordIngresada, usuarioEncontrado.getPassword());

        if (esCorrecta) {
            System.out.println("\n¡Bienvenido/a, " + usuarioEncontrado.getNombre() + "! :) \n");
            loginExitoso = true;
        } else
            System.out.println("Contraseña incorrecta.\n");
    }

    //Muestra las credenciales de la cuenta de un administrador para facilitar las pruebas
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

    //El administrador elige si quiere generar un ticket o cambiar una petición de categoría.
    static void menuPeticiones() {
        int option = 1;

        while (option != 0) {
            Utilidades.imprimirPeticiones(peticionesDelAdmin, usuarios, categorias);
            System.out.println("0: Atrás\t1: Generar ticket\t2: Cambiar petición de categoría");
            System.out.println("¿Qué acción desea realizar? ");
            option = Utilidades.inputNumerico();

            if (option == 1) {
                generarTicketMenu();

            } else if (option == 2) {
                cambiarCategoria();

            } else if (option == 0) {
                return;

            } else {
                System.out.println("La opción seleccionada no existe.\n");
            }
        }
    }

    //Pregunta al administrador de qué ticket quiere generar una petición y comprueba
    //que el valor introducido sea válido.
    public static void generarTicketMenu() {
        System.out.println("Introduzca el ID de la petición a partir de la que quiere generar el ticket: ");
        Peticion peticion = Utilidades.buscarPeticionPorId(Utilidades.inputNumerico(), peticionesDelAdmin);
        if (peticion == null)
            System.out.println("El ID de petición indicado no es válido: no se ha podido generar el nuevo ticket.\n");
        else if (peticion.getResuelta()) {
            System.out.println("No se puede generar tickets a partir de una petición que ya ha sido resuelta.");
        } else
            generarTicket(peticion.getId());
    }

    //Se genera el ticket a partir del técnico, dispositivo y descripción ingresadas por el administrador.
    //Si cualquiera de los valores introducidos no es válido, se cancela la operación.
    public static void generarTicket(int idPeticion) {
        int id = generarIdTicket();

        Tecnico tecnico = elegirTecnico();
        if (tecnico == null) {
            System.out.println("El técnico seleccionado no existe: no se ha podido generar el nuevo ticket.\n");
            return;
        }

        DispositivoInventario dispositivo = elegirDispositivo();
        if (dispositivo == null) {
            System.out.println("El dispositivo seleccionado no existe: no se ha podido generar el nuevo ticket.\n");
            return;
        }

        int urgencia = elegirUrgencia();
        if (urgencia < 1) {
            System.out.println("Valor inválido: no se ha podido generar el nuevo ticket.\n");
            return;
        }

        System.out.println("Ingrese la descripción de la tarea que tiene que realizar el técnico: ");
        String descripcion = scanner.nextLine();
        System.out.println();

        Ticket nuevoTicket = new Ticket(id, idPeticion, idIngresada, tecnico.getId(), dispositivo.getId(), urgencia, false, descripcion);
        tickets.add(nuevoTicket);
        System.out.println("El ticket ha sido generado con éxito.\n");
    }

    //Genera el id del ticket creado para que no coincida con ninguno ya existente.
    public static int generarIdTicket() {
        return tickets.get(tickets.size() - 1).getId() + 1;
    }

    //Permite cambiar una petición de categoría. Si la categoría introducida por el
    //administrador no es válida, se cancela la operación.
    public static void cambiarCategoria() {
        System.out.println("Introduzca el ID de la petición que quiere cambiar de categoría: ");
        Peticion peticion = Utilidades.buscarPeticionPorId(Utilidades.inputNumerico(), peticionesDelAdmin);
        if (peticion == null) {
            System.out.println("La petición seleccionada no existe.");
        } else if (peticion.getResuelta()) {
            System.out.println("No se puede modificar una petición que ya ha sido resuelta.");
        } else {
            Categoria categoria = ModuloUsuario.elegirCategoria(categorias);
            if (categoria == null) {
                System.out.println("La categoría seleccionada no existe.\n");
            } else {
                peticion.setIdCategoria(categoria.getId());
                System.out.println("La petición " + peticion.getId() + " ha sido movida a " + categoria.getCategoria() + ".\n");
            }
        }
    }

    //Muestra los tickets existentes y llama al menú de gestión de tickets.
    public static void consultarTickets() {
        Utilidades.imprimirTickets(tickets, tecnicos, dispositivos);
        menuTickets();
    }


    //Getiona lo que puede hacerse con los tickets: modificarlos,
    //filtrarlos por técnico o filtrarlos por dispositivo afectado.
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
                System.out.println("La opción seleccionada no existe.\n");
            }
        }
    }

    //Pide input al administrador para escoger uno de los técnicos que se muestran y devuelve
    //el técnico seleccionada, o nulo si no hay técnicos para el input introducido.
    public static Tecnico elegirTecnico() {
        Tecnico tecnico;
        System.out.println("TÉCNICOS:");
        Utilidades.imprimirTecnicos(tecnicos);
        System.out.println("Introduzca el número del técnico que quiere asignar al ticket: ");
        return tecnico = Utilidades.buscarTecnicoPorId(Utilidades.inputNumerico(), tecnicos);
    }

    //Pide input al administrador para escoger uno de los dispositivos que se muestran y devuelve
    //el dispositivo seleccionada, o nulo si no hay dispositivos para el input introducido.
    public static DispositivoInventario elegirDispositivo() {
        DispositivoInventario dispositivo;
        System.out.println("DISPOSITIVOS:");
        Utilidades.imprimirDispositivos(dispositivos);
        System.out.println("Introduzca el número de dispositivo afectado: ");
        return dispositivo = Utilidades.buscarDispositivoPorId(Utilidades.inputNumerico(), dispositivos);
    }

    //Para introducir la urgencia de un ticket cuando se crea.
    public static int elegirUrgencia() {
        System.out.println("Seleccione urgencia (1-5: 1 más alta, 5 más baja): ");
        int urgencia = Utilidades.inputNumerico();
        if(urgencia < 1 || urgencia > 5)
            return -1;
        return  urgencia;
    }

    //Pregunta al administrador qué ticket quiere modificar, comprueba que no sea nulo y gestiona
    //lo que puede hacerse con él: cambiar de técnico o modificar la descripción.
    public static void modificarTicketMenu() {
        System.out.println("Introduzca el ID del ticket que quiere modificar: ");
        int idTicket = Utilidades.inputNumerico();
        Ticket ticket = Utilidades.buscarTicketPorId(idTicket, tickets);
        if (ticket == null) {
            System.out.println("El ID introducido no es válido.\n");
            return;
        } else if (ticket.getResuelto()) {
            System.out.println("No se puede modificar un ticket que ya ha sido resuelto.");
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
                System.out.println("La opción seleccionada no existe.\n");
            }
        }
    }

    //Para cambiar el técnico de un ticket. Comprueba que el técnico seleccionado por el administrador
    //no sea nulo y lo asigna al ticket.
    public static void cambiarTecnico(Ticket ticket) {
        Tecnico tecnico = elegirTecnico();
        if (tecnico == null) {
            System.out.println("El técnico seleccionado no existe: no se ha podido modificar el técnico del ticket.\n");
            return;
        }
        ticket.setIdTecnico(tecnico.getId());
        System.out.println("El técnico del ticket " + ticket.getId() + " es ahora " + tecnico.getNombre() + ".\n");
    }

    //Cambia la descripción de un ticket por aquella introducida por el administrador.
    public static void cambiarTarea(Ticket ticket){
        System.out.println("Introduzca la descripción de la nueva tarea: ");
        String nuevaTarea = scanner.nextLine();
        System.out.println();
        ticket.setDescripcion(nuevaTarea);
        System.out.println("La descripción ha sido modificada con éxito.\n");
    }

    //Filtra los tickets del técnico indicado por el administrador y los imprime en pantalla.
    public static void filtrarTicketPorTecnico() {
        System.out.println("Lista de Técnicos:");
        Utilidades.imprimirTecnicos(tecnicos);

        System.out.println("Introduzca el número del técnico para filtrar los tickets: ");
        Tecnico tecnico = Utilidades.buscarTecnicoPorId(Utilidades.inputNumerico(), tecnicos);

        if (tecnico == null) {
            System.out.println("El técnico seleccionado no existe.\n");
            return;
        }

        ArrayList<Ticket> ticketsFiltrados = Utilidades.filtrarTicketsPorTecnico(tecnico.getId(), tickets);

        if (ticketsFiltrados.isEmpty()) {
            System.out.println(tecnico.getNombre() + " no tiene tickets asignados\n");
        } else {
            System.out.println("Tickets del técnico " + tecnico.getNombre() + ":");
            Utilidades.imprimirTickets(ticketsFiltrados, tecnicos, dispositivos);
        }
    }

    //Filtra los tickets del dispositivo indicado por el administrador y los imprime en pantalla.

    public static void filtrarTicketPorInventario() {
        System.out.println("Lista de Dispositivos:");
        Utilidades.imprimirDispositivos(dispositivos);

        System.out.println("Introduzca el número del dispositivo para filtrar los tickets: ");
        DispositivoInventario dispositivo = Utilidades.buscarDispositivoPorId(Utilidades.inputNumerico(), dispositivos);

        if (dispositivo == null) {
            System.out.println("El dispositivo seleccionado no existe.\n");
            return;
        }

        ArrayList<Ticket> ticketsFiltrados = Utilidades.filtrarTicketsPorDispositivo(dispositivo.getId(), tickets);

        if (ticketsFiltrados.isEmpty()) {
            System.out.println("No hay tickets asociados a " + dispositivo.getNombre() + ".\n");
        } else {
            System.out.println("Tickets relacionados con " + dispositivo.getNombre() + ":");
            Utilidades.imprimirTickets(ticketsFiltrados, tecnicos, dispositivos);
        }
    }
}
