import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GestorDatos {
    public static ArrayList<Usuario> cargarDatosUsuario(ArrayList<Usuario> u) {
        try {
            BufferedReader f_ent = new BufferedReader(new FileReader(new File("./CSV/usuario.csv")));
            String linea = f_ent.readLine();
            linea = f_ent.readLine();

            while (linea != null) {
                String[] palabras = linea.split(",");
                u.add(new Usuario((Integer.parseInt(palabras[0])), palabras[1], palabras[2]));
                linea = f_ent.readLine();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return u;
    }

    public static ArrayList<Peticion> cargarDatosPeticiones(ArrayList<Peticion> p) {
        try {
            BufferedReader f_ent = new BufferedReader(new FileReader(new File("./CSV/peticion.csv")));
            String linea = f_ent.readLine();
            linea = f_ent.readLine();

            while (linea != null) {
                String[] palabras = linea.split(",");
                p.add(new Peticion((Integer.parseInt(palabras[0])), Integer.parseInt(palabras[1]), palabras[2], palabras[3], Integer.parseInt(palabras[4]), Integer.parseInt(palabras[5]), Integer.parseInt(palabras[6]), ModuloUsuario.stringToBoolean(palabras[7])));
                linea = f_ent.readLine();
            }
            f_ent.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public static ArrayList<Categoria> cargarDatosCategorias(ArrayList<Categoria> c) {
        try {
            BufferedReader f_in = new BufferedReader(new FileReader(new File("./CSV/categoria.csv")));
            String fila = f_in.readLine();
            fila = f_in.readLine();
            while (fila != null) {
                String[] atributo = fila.split(",");
                c.add(new Categoria((Integer.parseInt(atributo[0])), atributo[1]));
                fila = f_in.readLine();

            }
            f_in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
    public static ArrayList<Admin> cargarDatosAdmins(ArrayList<Admin> a) {
        try {
            BufferedReader f_in = new BufferedReader(new FileReader(new File("./CSV/admin.csv")));
            String fila = f_in.readLine();
            fila = f_in.readLine();
            while (fila != null) {
                String[] atributo = fila.split(",");
                a.add(new Admin((Integer.parseInt(atributo[0])),atributo[1],atributo[2]));
                fila = f_in.readLine();

            }
            f_in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }
    public static ArrayList<Ticket> cargarDatosTickets(ArrayList<Ticket> t) {
        try {
            BufferedReader f_in = new BufferedReader(new FileReader(new File("./CSV/ticket.csv")));
            String fila = f_in.readLine();
            fila = f_in.readLine();
            while (fila != null) {
                String[] atributo = fila.split(",");
            //    t.add(new Ticket((Integer.parseInt(atributo[0])),Integer.parseInt(atributo[1],Integer.parseInt(atributo[2]), Integer.parseInt(atributo[3]);
                fila = f_in.readLine();

            }
            f_in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return t;
    }

}
