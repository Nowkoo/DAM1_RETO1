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
        return null; // Devuelve null si no encuentra al usuario con la ID dada
    }

    public static Categoria buscarCategoriaPorId(int id, ArrayList<Categoria> categorias) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                return categoria;
            }
        }
        return null; // Devuelve null si no encuentra al usuario con la ID dada
    }

    public static Admin buscarAdminisPorId(int id, ArrayList<Admin> admins) {
        for (Admin admin : admins) {
            if (admin.getId() == id) {
                return admin;
            }
        }
        return null; // Devuelve null si no encuentra al usuario con la ID dada
    }
}
