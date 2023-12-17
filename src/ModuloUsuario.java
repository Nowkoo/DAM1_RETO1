
import java.util.ArrayList;
import java.util.Scanner;

public class ModuloUsuario {
    static Scanner scanner = new Scanner(System.in);
    static Usuario usuarioEncontrado;
    static int idIngresada;
    static boolean loginExitoso = false;

    static ArrayList<Usuario> usuarios = new ArrayList<>();
    static ArrayList<Categoria> categorias = new ArrayList<>();
    static ArrayList<Peticion> peticiones = new ArrayList<>();
    static ArrayList<Peticion> peticionesUsuario = new ArrayList<>();

    public static void ingresarComoUsuario() {
        usuarios = GestorDatos.cargarDatosUsuario(usuarios);
        peticiones = GestorDatos.cargarDatosPeticiones(peticiones);
        categorias = GestorDatos.cargarDatosCategorias(categorias);

        int eleccionMenu;
        String rol = "usuario";

        do {
            while (!loginExitoso) {
                mostrarUsuarios();
                loginUsuario();
            }

            mostrarMenu();
            eleccionMenu = Utilidades.inputNumerico();

            if (eleccionMenu < 0 || eleccionMenu > 4) {
                System.out.println("El número introducido no es válido, por favor introduzca otro número.\n");
                System.out.println("El número introducido no es válido, por favor introduzca otro número.\n");
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
                    peticionesUsuario = Utilidades.filtrarPeticionesPorUsuario(idIngresada, peticiones);
                    Utilidades.imprimirPeticiones(peticionesUsuario, usuarios, categorias);
                    Filtrar();
                    break;
                case 3:
                    consultarPeticionPorID();
                    System.out.println();
                    System.out.println();
                    break;
                case 4:
                    modificarPeticion();
                    break;
            }

        } while (eleccionMenu != 0);
    }

    public static void mostrarUsuarios() {
        System.out.println("//Iniciar sesión con ID: " + usuarios.get(0).getId() + ", CONTRASEÑA: " + usuarios.get(0).getPassword());
        System.out.println();
    }

    public static void mostrarMenu() {
        System.out.println("0-Salir del programa.");
        System.out.println("1-Generar una petición.");
        System.out.println("2-Consultar peticiones.");
        System.out.println("3-Buscar una petición por su ID.");
        System.out.println("4-Modificar una petición.");
    }

    public static void loginUsuario() {
        System.out.println("Ingresa tu ID de usuario:");
        idIngresada = Utilidades.inputNumerico();
        usuarioEncontrado = Utilidades.buscarUsuarioPorId(idIngresada, usuarios);

        if(usuarioEncontrado == null) {
            System.out.println("Usuario no encontrado.\n");
            return;
        }

        String passwordIngresada = Utilidades.pedirPassword();
        boolean esCorrecta = Utilidades.validarPassword(passwordIngresada, usuarioEncontrado.getPassword());

        if (esCorrecta) {
            System.out.println("\n¡Bienvenido, " + usuarioEncontrado.getNombre() + "! :) \n");
            loginExitoso = true;
        } else
            System.out.println("Contraseña incorrecta.\n");
    }

    public static void generarPeticion() {
        System.out.println("Ingrese la descripción de la petición:");
        String descripcion = scanner.nextLine();
        System.out.println();

        String fecha = Fecha.ObtenerFechaActual();

        Categoria categoria = elegirCategoria();
        if (categoria == null) {
            System.out.println("La categoría seleccionada no existe: no se ha podido generar la petición.\n");
            return;
        }

        Peticion nuevaPeticion = new Peticion(
                obtenerNuevoIdPeticion(),
                idIngresada,
                descripcion,
                fecha,
                categoria.getId(),
                2, //harcodeado de forma provisional
                false
        );

        // Agrega la nueva petición al ArrayList de peticiones
        peticiones.add(nuevaPeticion);

        System.out.println("La petición ha sido generada con éxito.\n");
    }

    public static Categoria elegirCategoria() {
        Categoria categoria;
        System.out.println("CATEGORÍAS:");
        Utilidades.imprimirCategorias(categorias);
        System.out.println("Introduzca el número de categoría: ");
        return categoria = Utilidades.buscarCategoriaPorId(Utilidades.inputNumerico(), categorias);
    }

    public static int obtenerNuevoIdPeticion() {
        return peticiones.size() + 1;
    }

    public static void consultarPeticionPorID() {
        System.out.println("Introduzca el ID de la peticion que desea consultar: ");
        int idconsultarPeticion = Utilidades.inputNumerico();
        peticionesUsuario = Utilidades.filtrarPeticionesPorUsuario(idIngresada, peticiones);
        Peticion PeticionConsultada = Utilidades.buscarPeticionPorId(idconsultarPeticion, peticionesUsuario);

        if (PeticionConsultada != null){
            Categoria categoria = Utilidades.buscarCategoriaPorId(PeticionConsultada.getIdCategoria(), categorias);

            Utilidades.imprimirSeparador();
            System.out.println("Id:" + PeticionConsultada.getId());
            System.out.println("Usuario: " + usuarioEncontrado.getNombre());
            System.out.println("Categoria: " + categoria.getCategoria());
            System.out.println("Descripcion: " + PeticionConsultada.getDescripcion() );
            System.out.println("Fecha: " + PeticionConsultada.getFecha());
            System.out.println("Estado: " + Utilidades.estado(PeticionConsultada.getResuelta()));
            Utilidades.imprimirSeparador();

        } else {
            System.out.println("No existen peticiones a su nombre con el ID proporcionado.\n");
        }
    }

    public static void modificarPeticion() {
        peticionesUsuario = Utilidades.filtrarPeticionesPorUsuario(idIngresada, peticiones);
        Utilidades.imprimirPeticiones(peticionesUsuario, usuarios, categorias);

        System.out.println("Introduzca el ID de la petición que quiere modificar: ");
        Peticion peticion = Utilidades.buscarPeticionPorId(Utilidades.inputNumerico(), peticionesUsuario);
        if (peticion == null) {
            System.out.println("No existen peticiones a su nombre con el ID proporcionado.\n");
            return;
        } else if (peticion.getResuelta()) {
            System.out.println("No se puede modificar una petición que ya ha sido resuelta.\n");
            return;
        }

        System.out.println("Introduzca la nueva descripción (se borrará la descripción anterior): ");
        String nuevaDescripcion = scanner.nextLine();
        System.out.println();
        System.out.println("La descripción ha sido modificada.\n");

        modificarDescripcion(peticion.getId(), nuevaDescripcion);
    }

    public static boolean modificarDescripcion(int idPeticion, String nuevaDescripcion) {
        boolean descripcionCambiada = false;
        for (int i = 0; i < peticiones.size(); i++) {
            if (peticiones.get(i).getId() == idPeticion) {
                peticiones.get(i).setDescripcion(nuevaDescripcion);
                descripcionCambiada = true;
            }
        }
        return descripcionCambiada;
    }

    public static void Filtrar() {
        int option = 1;
        while (option != 0) {
            System.out.println("0: Atrás\t1: Filtrar categoría \t2: Filtrar sin resolver\t3: Filtrar resueltas");
            System.out.println("¿Qué acción desea realizar? ");
            option = Utilidades.inputNumerico();
            ArrayList<Peticion> peticionesFiltradas;

            if (option == 1) {
                filtrarCategoria();
            } else if (option == 2) {
                filtrarSinResolver();
            } else if (option == 3) {
                filtrarResueltas();
            } else if (option == 0) {
                return;
            } else {
                System.out.println("La opción seleccionada no existe.\n");
            }
        }
    }

    public static void filtrarCategoria() {
        Categoria categoria = elegirCategoria();
        if (categoria == null) {
            System.out.println("Categoría inválida.\n");
            return;
        }
        ArrayList<Peticion> peticionesFiltradas = Utilidades.filtrarPeticionesPorCategoria(categoria.getId(), peticiones);
        if (peticionesFiltradas.isEmpty()) {
            System.out.println("No hay peticiones que pertenezcan a esta categoría.\n");
            return;
        }
        System.out.println("Peticiones de la categoría " + categoria.getCategoria() + ":");
        Utilidades.imprimirPeticiones(peticionesFiltradas, usuarios, categorias);
    }
    public static void filtrarSinResolver() {
        ArrayList<Peticion> peticionesFiltradas = Utilidades.filtrarPeticionesPorEstado(false, peticiones);
        if (peticionesFiltradas.isEmpty()) {
            System.out.println("Todas las peticiones han sido resueltas.\n");
            return;
        }
        System.out.println("Peticiones sin resolver:");
        Utilidades.imprimirPeticiones(peticionesFiltradas, usuarios, categorias);
    }

    public static void filtrarResueltas() {
        ArrayList<Peticion> peticionesFiltradas = Utilidades.filtrarPeticionesPorEstado(true, peticiones);
        if (peticionesFiltradas.isEmpty()) {
            System.out.println("No hay peticiones resueltas.\n");
            return;
        }
        System.out.println("Peticiones resueltas:");
        Utilidades.imprimirPeticiones(peticionesFiltradas, usuarios, categorias);
    }
}