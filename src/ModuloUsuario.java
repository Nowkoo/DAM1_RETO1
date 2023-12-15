import jdk.jshell.execution.Util;

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

    static ArrayList<Usuario> usuarios = new ArrayList<>();
    static ArrayList<Categoria> categorias = new ArrayList<>();
    static ArrayList<Peticion> peticiones = new ArrayList<>();

    //Método que he usado para recorrer el arraylist usuarios
    //No es necesario pero podría mantenerse como una opción a la que puede acceder el admin

    public static void main(String[] args) {
        usuarios = GestorDatos.cargarDatosUsuario(usuarios);
        peticiones = GestorDatos.cargarDatosPeticiones(peticiones);
        categorias = GestorDatos.cargarDatosCategorias(categorias);

        int eleccionMenu;
        String rol = "usuario";

        do {

            mostrarUsuarios();
            while (!loginExitoso) {
                identificarse(rol);
            }

            mostrarMenu();
            eleccionMenu = Utilidades.inputNumerico();

            if (eleccionMenu < 0 || eleccionMenu > 3) {
                System.out.println("El número introducido no es válido, por favor introduce otro número");
            }
            if (eleccionMenu == 0) {
                GestorDatos.guardarDatosPeticiones(peticiones);
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

    public static void mostrarUsuarios() {
        System.out.println("//Iniciar sesión con ID: " + usuarios.get(0).getId() + ", CONTRASEÑA: " + usuarios.get(0).getPassword());
        System.out.println();
    }

    public static void mostrarCategorias() {
        for (Categoria categoria : categorias) {
            System.out.println(categoria.getId() + " " + categoria.getCategoria());
        }
    }

    public static void mostrarMenu() {
        System.out.println("0-Salir del programa");
        System.out.println("1-Generar una petición");
        System.out.println("2-Modificar la petición");
        System.out.println("3-Consultar la petición");
    }

    public static void identificarse(String rol) {
        System.out.println("Ingresa tu ID de " + rol);
        idIngresada = Utilidades.inputNumerico();
        usuarioEncontrado = Utilidades.buscarUsuarioPorId(idIngresada, usuarios);
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

    public static void generarPeticion() {
        scanner.nextLine();

        System.out.println("Ingrese la descripción de la petición:");
        String descripcion = scanner.nextLine();

        String fecha = Fecha.ObtenerFechaActual();

        mostrarCategorias();
        System.out.println("Ingrese el número de la categoría de la petición:");
        int idCategoria = Utilidades.inputNumerico();

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
        System.out.println("Introduce la Id de la peticion que deseas consultar: ");
        int idconsultarPeticion = Utilidades.inputNumerico();
        Peticion PeticionConsultada = buscarPeticionPorId(idconsultarPeticion);
        Usuario usuarioActual = Utilidades.buscarUsuarioPorId(PeticionConsultada.getIdUsuario(), usuarios);
        Categoria categoria = Utilidades.buscarCategoriaPorId(PeticionConsultada.getIdCategoria(), categorias);

        if (PeticionConsultada != null){
            System.out.println("Id:" +PeticionConsultada.getId());
            System.out.println("Usuario: " + usuarioActual.getNombre());//usuarioActual es temporal, más adelante arreglarlo
            System.out.println("Categoria: " + categoria.getCategoria());
            System.out.println("Descripcion: " + PeticionConsultada.getDescripcion() );
            System.out.println("Fecha: " + PeticionConsultada.getFecha());
            System.out.println("Estado: " + PeticionConsultada.getResuelta());

        } else {
            System.out.println("La Id introducida para mostrar la peticion es erronea.");
        }
    }
    public static void mostrarPeticion(Peticion peticion){
        Usuario usuarioActual= Utilidades.buscarUsuarioPorId(peticion.getIdUsuario(), usuarios);
        Categoria categoria = Utilidades.buscarCategoriaPorId(peticion.getIdCategoria(), categorias);
        System.out.println("Id: " +peticion.getId());
        System.out.println("Categoria: " + categoria.getCategoria());
        System.out.println("Descripcio: " + peticion.getDescripcion());
        System.out.println("Fecha: " + peticion.getFecha());
        System.out.println("Estado: " + peticion.getEstado());
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
    public static Peticion buscarPeticionPorId(int id) {
        for (Peticion peticion : peticiones) {
            if (peticion.getId() == id) {
                return peticion;
            }
        }
        return null; // Devuelve null si no encuentra la petición con la ID dada
    }
    public static void imprimirPeticiones(ArrayList<Peticion> listaPeticiones) {
        for (Peticion peticion : listaPeticiones) {
            Usuario usuarioActual = Utilidades.buscarUsuarioPorId(peticion.getId(), usuarios);
            Categoria categoria = Utilidades.buscarCategoriaPorId(peticion.getIdCategoria(), categorias);

            System.out.println("ID: " + peticion.getId() + "\t|\tFecha: " + peticion.getFecha() + "\t|\tPor: " + usuarioActual.getNombre());
            System.out.println("Categoría: " + categoria.getCategoria());
            System.out.println("Descripción: " + peticion.getDescripcion());
            System.out.println();
        }
    }
}