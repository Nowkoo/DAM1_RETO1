import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidades {

    //Para pedir input númerico sin que el programa se rompa cuando el usuario
    //introduce una letra en lugar de un número.
    public static int inputNumerico() {
        Scanner scanner = new Scanner(System.in);
        int input;
        try {
            input = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
        } catch (InputMismatchException e) {
            input = -10;
        }
        return input;
    }

    //Para convertir los booleanos almacenados como string en los csv de vuelta a booleanos.
    public static boolean stringToBoolean(String s) {
        return s.equals("true");
    }

    //Meramente decorativo.
    public static void imprimirSeparador() {
        System.out.println("------------------------------------------------------------------------------------");
    }

    //Pide la contraseña al usuario.
    public static String pedirPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su contraseña");
        return scanner.nextLine();
    }

    //Comprueba que la contraseña introducida coincida con la contraseña de la base de datos.
    public static boolean validarPassword(String passwordIngresada, String passwordCorrecta) {
        return passwordIngresada.equals(passwordCorrecta);
    }

    //FUNCIONES QUE PERMITEN BUSCAR OBJETOS DE DISTINTAS CLASES DENTRO DE UN ARRAY DE
    //OBJETOS DE ESE TIPO MEDIANTE DE SU ID.
    public static Usuario buscarUsuarioPorId(int id, ArrayList<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public static Tecnico buscarTecnicoPorId(int id, ArrayList<Tecnico> tecnicos) {
        for (Tecnico tecnico :  tecnicos) {
            if (tecnico.getId() == id) {
                return tecnico;
            }
        }
        return null;
    }

    public static DispositivoInventario buscarDispositivoPorId(int id, ArrayList<DispositivoInventario> dispositivos) {
        for (DispositivoInventario dispositivo : dispositivos) {
            if (dispositivo.getId() == id) {
                return dispositivo;
            }
        }
        return null;
    }

    public static Categoria buscarCategoriaPorId(int id, ArrayList<Categoria> categorias) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                return categoria;
            }
        }
        return null;
    }

    public static Admin buscarAdminPorId(int id, ArrayList<Admin> admins) {
        for (Admin admin : admins) {
            if (admin.getId() == id) {
                return admin;
            }
        }
        return null;
    }

    public static Ticket buscarTicketPorId(int id, ArrayList<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    public static Peticion buscarPeticionPorId(int id, ArrayList<Peticion> peticiones) {
        for (Peticion peticion : peticiones) {
            if (peticion.getId() == id) {
                return peticion;
            }
        }
        return null;
    }

    //Imprime las peticiones del arraylist de peticiones que pasamos como parámetro.
    public static void imprimirPeticiones(ArrayList<Peticion> p, ArrayList<Usuario> u, ArrayList<Categoria> c) {
        for (Peticion peticion : p) {
            imprimirSeparador();
            Usuario usuarioActual = Utilidades.buscarUsuarioPorId(peticion.getIdUsuario(), u);
            Categoria categoria = Utilidades.buscarCategoriaPorId(peticion.getIdCategoria(), c);

            System.out.println("ID: " + peticion.getId() + "\t|\tFecha: " + peticion.getFecha() + "\t|\tEstado: " + estado(peticion.getResuelta()) + "\t|\tPor: " + usuarioActual.getNombre());
            System.out.println("Categoría: " + categoria.getCategoria());
            System.out.println("Descripción: " + peticion.getDescripcion());
            imprimirSeparador();
            System.out.println();
        }
    }

    //Para mostrar resuelto o pendiente en lugar de true o false cuando se imprime el estado
    //de un ticket.
    public static String estado(boolean resuelta) {
        if (resuelta)
            return "Resuelto";
        return "Pendiente";
    }

    //Imprime los tickets del arraylist de tickets que pasamos como parámetro.
    public static void imprimirTickets(ArrayList<Ticket> t, ArrayList<Tecnico> tec, ArrayList<DispositivoInventario> d) {
        for (Ticket ticket : t) {
            imprimirSeparador();
            Tecnico tecnico = Utilidades.buscarTecnicoPorId(ticket.getIdTecnico(), tec);
            DispositivoInventario dispositivo = Utilidades.buscarDispositivoPorId(ticket.getIdDispositivos(), d);

            System.out.println("ID: " + ticket.getId() + "\t|\tUrgencia: " + ticket.getUrgencia() + "\t|\tEstado: " + estado(ticket.getResuelto()) + "\t|\tTécnico asignado: " + tecnico.getNombre());
            System.out.println("Dispositivo afectado: " + dispositivo.getNombre() + " ubicado en " + dispositivo.getSala() + " con IP " + dispositivo.getIp());
            System.out.println("Tarea a realizar: " + ticket.getDescripcion());
            imprimirSeparador();
            System.out.println();
        }
    }

    //Imprime las categorías del arraylist de categorías que pasamos como parámetro.
    public static void imprimirCategorias(ArrayList<Categoria> c) {
        for (Categoria categoria : c) {
            System.out.println(categoria.getId() + " " + categoria.getCategoria());
        }
    }

    //Imprime los técnicos del arraylist de técnicos que pasamos como parámetro.

    public static void imprimirTecnicos(ArrayList<Tecnico> t) {
        for (Tecnico tecnico : t) {
            System.out.println(tecnico.getId() + " " + tecnico.getNombre());
        }
    }

    //Imprime los dispositivos del arraylist de dispositivos que pasamos como parámetro.

    public static void imprimirDispositivos(ArrayList<DispositivoInventario> d) {
        for (DispositivoInventario dispositivo : d) {
            System.out.println(dispositivo.getId() + " " + dispositivo.getNombre() + " ubicado en " + dispositivo.getSala() + " con IP " + dispositivo.getIp());
        }
    }

    //Devuelve un arraylist de peticiones que tengan de id usuario el id que se pasa como parámetro
    //a partir del arraylist de peticiones que se pasa también como parámetro.
    public static ArrayList<Peticion> filtrarPeticionesPorUsuario(int idUsuario, ArrayList<Peticion> peticiones) {
        ArrayList<Peticion> peticionesUsuario = new ArrayList<>();

        for (int i = 0; i < peticiones.size(); i++) {
            if (idUsuario == peticiones.get(i).getIdUsuario()) {
                peticionesUsuario.add(peticiones.get(i));
            }
        }
        return peticionesUsuario;
    }

    //Devuelve un arraylist de peticiones que tengan de id categoría el id que se pasa como parámetro
    //a partir del arraylist de peticiones que se pasa también como parámetro.
    public static ArrayList<Peticion> filtrarPeticionesPorCategoria(int idCategoria, ArrayList<Peticion> peticiones) {
        ArrayList<Peticion> peticionesCategoria = new ArrayList<>();

        for (int i = 0; i < peticiones.size(); i++) {
            if (idCategoria == peticiones.get(i).getIdCategoria()) {
                peticionesCategoria.add(peticiones.get(i));
            }
        }
        return peticionesCategoria;
    }

    //Devuelve un arraylist de peticiones que tengan de estado el estado que se pasa como parámetro
    //a partir del arraylist de peticiones que se pasa también como parámetro.
    //true=resuelto, false=pendiente o sin resolver.
    public static ArrayList<Peticion> filtrarPeticionesPorEstado(boolean resuelta, ArrayList<Peticion> peticiones) {
        ArrayList<Peticion> peticionesEstado = new ArrayList<>();

        for (int i = 0; i < peticiones.size(); i++) {
            if (resuelta == peticiones.get(i).getResuelta()) {
                peticionesEstado.add(peticiones.get(i));
            }
        }
        return peticionesEstado;
    }

    //Devuelve un arraylist de tickets que tengan de estado el estado que se pasa como parámetro
    //a partir del arraylist de tickets que se pasa también como parámetro.
    //true=resuelto, false=pendiente o sin resolver.
    public static ArrayList<Ticket> filtrarTicketsPorEstado(boolean resuelto, ArrayList<Ticket> tickets) {
        ArrayList<Ticket> ticketsEstado = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            if (resuelto == tickets.get(i).getResuelto()) {
                ticketsEstado.add(tickets.get(i));
            }
        }
        return ticketsEstado;
    }

    //Devuelve un arraylist de tickets que tengan de id técnico el id que se pasa como parámetro
    //a partir del arraylist de tickets que se pasa también como parámetro.
    public static ArrayList<Ticket> filtrarTicketsPorTecnico(int idTecnico, ArrayList<Ticket> tickets) {
        ArrayList<Ticket> ticketsPorTecnico = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            if (idTecnico == tickets.get(i).getIdTecnico()) {
                ticketsPorTecnico.add(tickets.get(i));
            }
        }
        return ticketsPorTecnico;
    }

    //Devuelve un arraylist de tickets que tengan de id dispositivo el id que se pasa como parámetro
    //a partir del arraylist de tickets que se pasa también como parámetro.
    public static ArrayList<Ticket> filtrarTicketsPorDispositivo(int idDispositivo, ArrayList<Ticket> tickets) {
        ArrayList<Ticket> ticketsPorDispositivo = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            if (idDispositivo == tickets.get(i).getIdDispositivos()) {
                ticketsPorDispositivo.add(tickets.get(i));
            }
        }
        return ticketsPorDispositivo;
    }
}
