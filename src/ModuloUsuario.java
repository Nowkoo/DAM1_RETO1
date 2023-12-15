import java.io.*;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ModuloUsuario {
    static Scanner scanner = new Scanner(System.in);
    static Usuario usuarioEncontrado;
    static int idIngresada;
    static String passwordIngresada;
    static boolean loginExitoso = false;

    static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    static ArrayList<Categoria> categorias = new ArrayList<>();
    static ArrayList<Peticion> peticiones = new ArrayList<>();

    //Método que he usado para recorrer el arraylist usuarios
    //No es necesario pero podría mantenerse como una opción a la que puede acceder el admin
    public static void mostrarUsuarios() {
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Contraseña: " + usuario.getPassword());
        }
    }

    public static void main(String[] args) {
        usuarios = GestorDatos.cargarDatosUsuario(usuarios);
        peticiones = GestorDatos.cargarDatosPeticiones(peticiones);
        categorias = GestorDatos.cargarDatosCategorias(categorias);

        int eleccionMenu;
        String rol = "usuario";

        do {
            //Borrar luego
            mostrarUsuarios();
            //Borrar luego
            while (!loginExitoso) {
                identificarse(rol);
            }

            mostrarMenu();
            eleccionMenu = inputNumerico();

            if (eleccionMenu < 0 || eleccionMenu > 3) {
                System.out.println("El número introducido no es válido, por favor introduce otro número");
            }
            if (eleccionMenu == 0) {
                System.out.println(peticiones.size());
                guardarDatosPeticiones();
            }

            switch (eleccionMenu) {
                case 1:
                    generarPeticion();
                    System.out.println();
                    break;
                case 2:
                    ArrayList<Peticion> peticionesUsuario = filtrarPeticionesPorUsuario(idIngresada);
                    imprimirPeticiones(peticionesUsuario);

                    System.out.println("Introduzca el ID de la petición que quiere modificar: ");
                    int indicePeticion = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Introduzca la nueva descripción (se borrará la descripción anterior): ");
                    String nuevaDescripcion = scanner.nextLine();

                    boolean descripcionCambiada = modificarDescripcion(indicePeticion, nuevaDescripcion);

                    if (!descripcionCambiada) {
                        System.out.println("No tiene ninguna solicitud abierta con el ID de petición proporcionado: no se ha podido cambiar la descripción.");
                    }
                    System.out.println();
                    break;
                case 3:
                    consultarPeticion();
                    System.out.println();
                    System.out.println();
                    break;
            }
        } while (eleccionMenu != 0);
    }

    public static void mostrarMenu() {
        System.out.println("0-Salir del programa");
        System.out.println("1-Generar una petición");
        System.out.println("2-Modificar la petición");
        System.out.println("3-Consultar la petición");
    }

    public static void identificarse(String rol) {
        System.out.println("Ingresa tu ID de " + rol);
        idIngresada = inputNumerico();
        usuarioEncontrado = buscarUsuarioPorId(idIngresada);
        pedirPassword();
        validarPassword();
    }

    public static Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null; // Devuelve null si no encuentra al usuario con la ID dada
    }

    public static Categoria buscarCategoriaPorId(int id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                return categoria;
            }
        }
        return null; // Devuelve null si no encuentra al usuario con la ID dada
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

    public static void guardarDatosPeticiones() {
        try {
            File peticionCSV = new File("./CSV/peticion.csv");

            PrintWriter f_sal = new PrintWriter(new FileWriter((peticionCSV), false), false);
            Peticion peticion;

            f_sal.println("idPeticion,idUsuario,descripcion,fecha,idCategoria,idAdmin,estado,resuelta");

            for (int i = 0; i < peticiones.size(); i++) {
                peticion = peticiones.get(i);
                f_sal.println(peticion.getId() + "," + peticion.getIdUsuario() + "," + peticion.getDescripcion() + "," + peticion.getFecha() + "," + peticion.getIdCategoria() + "," + peticion.getIdAdmin() + "," + peticion.getEstado() + "," + peticion.getResuelta());
            }
            f_sal.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void generarPeticion() {
        scanner.nextLine();

        System.out.println("Ingrese la descripción de la petición:");
        String descripcion = scanner.nextLine();

        String fecha = Fecha.ObtenerFechaActual();

        System.out.println("Ingrese el ID de la categoría de la petición:");
        int idCategoria = scanner.nextInt();

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
    }

    public static int obtenerNuevoIdPeticion() {
        return peticiones.size() + 1;
    }

    public static void consultarPeticion() {
    }

    public static boolean modificarDescripcion(int indicePeticion, String nuevaDescripcion) {
        boolean descripcionCambiada = false;
        for (int i = 0; i < peticiones.size(); i++) {
            if (peticiones.get(i).getId() == indicePeticion) {
                peticiones.get(i).setDescripcion(nuevaDescripcion);
                descripcionCambiada = true;
            }
        }
        return descripcionCambiada;
    }

    public static ArrayList<Peticion> filtrarPeticionesPorUsuario(int idUsuario) {
        ArrayList<Peticion> peticionesUsuario = new ArrayList<>();

        for (int i = 0; i < peticiones.size(); i++) {
            if (idUsuario == peticiones.get(i).getIdUsuario()) {
                peticionesUsuario.add(peticiones.get(i));
            }
        }
        return peticionesUsuario;
    }

    public static void imprimirPeticiones(ArrayList<Peticion> listaPeticiones) {
        for (Peticion peticion : listaPeticiones) {
            Usuario usuarioActual = buscarUsuarioPorId(peticion.getId());
            Categoria categoria = buscarCategoriaPorId(peticion.getIdCategoria());

            System.out.println("ID: " + peticion.getId() + "\t|\tFecha: " + peticion.getFecha() + "\t|\tPor: " + usuarioActual.getNombre());
            assert categoria != null;
            System.out.println("Categoría: " + categoria.getCategoria());
            System.out.println("Descripción: " + peticion.getDescripcion());
            System.out.println();
        }
    }

    public static boolean stringToBoolean(String s) {
        return s.equals("true");
    }

    public static int inputNumerico() {
        Scanner scanner = new Scanner(System.in);
        int input;
        try {
            input = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            input = -10;
        }
        return input;
    }
}