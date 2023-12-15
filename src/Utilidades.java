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
        } catch (InputMismatchException e) {
            input = -10;
        }
        return input;
    }

    public static boolean stringToBoolean(String s) {
        return s.equals("true");
    }

    public static Usuario buscarUsuarioPorId(int id, ArrayList<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
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

    public static Admin buscarAdminisPorId(int id, ArrayList<Admin> admins) {
        for (Admin admin : admins) {
            if (admin.getId() == id) {
                return admin;
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
            Usuario usuarioActual = Utilidades.buscarUsuarioPorId(peticion.getId(), u);
            Categoria categoria = Utilidades.buscarCategoriaPorId(peticion.getIdCategoria(), c);

            System.out.println("ID: " + peticion.getId() + "\t|\tFecha: " + peticion.getFecha() + "\t|\tPor: " + usuarioActual.getNombre());
            System.out.println("Categoría: " + categoria.getCategoria());
            System.out.println("Descripción: " + peticion.getDescripcion());
            System.out.println();
        }
    }

    public static void imprimirCategorias(ArrayList<Categoria> c) {
        for (Categoria categoria : c) {
            System.out.println(categoria.getId() + " " + categoria.getCategoria());
        }
    }
}
