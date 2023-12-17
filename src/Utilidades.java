import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidades {
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

    public static boolean stringToBoolean(String s) {
        return s.equals("true");
    }

    public static void imprimirSeparador() {
        System.out.println("------------------------------------------------------------------------------------");
    }

    public static String pedirPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su contraseña");
        return scanner.nextLine();
    }

    public static boolean validarPassword(String passwordIngresada, String passwordCorrecta) {
        return passwordIngresada.equals(passwordCorrecta);
    }

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

    public static String estado(boolean resuelta) {
        if (resuelta)
            return "Resuelto";
        return "Pendiente";
    }

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

    public static void imprimirCategorias(ArrayList<Categoria> c) {
        for (Categoria categoria : c) {
            System.out.println(categoria.getId() + " " + categoria.getCategoria());
        }
    }

    public static void imprimirTecnicos(ArrayList<Tecnico> t) {
        for (Tecnico tecnico : t) {
            System.out.println(tecnico.getId() + " " + tecnico.getNombre());
        }
    }

    public static void imprimirDispositivos(ArrayList<DispositivoInventario> d) {
        for (DispositivoInventario dispositivo : d) {
            System.out.println(dispositivo.getId() + " " + dispositivo.getNombre() + " ubicado en " + dispositivo.getSala() + " con IP " + dispositivo.getIp());
        }
    }

    public static ArrayList<Peticion> filtrarPeticionesPorUsuario(int idUsuario, ArrayList<Peticion> peticiones) {
        ArrayList<Peticion> peticionesUsuario = new ArrayList<>();

        for (int i = 0; i < peticiones.size(); i++) {
            if (idUsuario == peticiones.get(i).getIdUsuario()) {
                peticionesUsuario.add(peticiones.get(i));
            }
        }
        return peticionesUsuario;
    }

    public static ArrayList<Peticion> filtrarPeticionesPorCategoria(int idCategoria, ArrayList<Peticion> peticiones) {
        ArrayList<Peticion> peticionesCategoria = new ArrayList<>();

        for (int i = 0; i < peticiones.size(); i++) {
            if (idCategoria == peticiones.get(i).getIdCategoria()) {
                peticionesCategoria.add(peticiones.get(i));
            }
        }
        return peticionesCategoria;
    }

    public static ArrayList<Peticion> filtrarPeticionesPorEstado(boolean resuelta, ArrayList<Peticion> peticiones) {
        ArrayList<Peticion> peticionesEstado = new ArrayList<>();

        for (int i = 0; i < peticiones.size(); i++) {
            if (resuelta == peticiones.get(i).getResuelta()) {
                peticionesEstado.add(peticiones.get(i));
            }
        }
        return peticionesEstado;
    }

    public static ArrayList<Ticket> filtrarTicketsPorEstado(boolean resuelto, ArrayList<Ticket> tickets) {
        ArrayList<Ticket> ticketsEstado = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            if (resuelto == tickets.get(i).getResuelto()) {
                ticketsEstado.add(tickets.get(i));
            }
        }
        return ticketsEstado;
    }

    public static ArrayList<Ticket> filtrarTicketsPorTecnico(int idTecnico, ArrayList<Ticket> tickets) {
        ArrayList<Ticket> ticketsPorTecnico = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            if (idTecnico == tickets.get(i).getIdTecnico()) {
                ticketsPorTecnico.add(tickets.get(i));
            }
        }
        return ticketsPorTecnico;
    }

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
