import java.io.*;
import java.util.ArrayList;

//FUNCIONES QUE PERMITEN CARGAR EN ARRAYS LOS DATOS ALMACENADOS EN LOS CSV
//O QUE PERMITEN GUARDAR Y ACTUALIZAR LOS DATOS DE LOS CSV ANTES DE CERRAR EL
//PROGRAMA PARA QUE ESTOS NO SE PIERDAN.

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
                p.add(new Peticion((Integer.parseInt(palabras[0])), Integer.parseInt(palabras[1]), palabras[2], palabras[3], Integer.parseInt(palabras[4]), Integer.parseInt(palabras[5]), Utilidades.stringToBoolean(palabras[6])));
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
                a.add(new Admin((Integer.parseInt(atributo[0])),atributo[1],atributo[2], (Integer.parseInt(atributo[3]))));
                fila = f_in.readLine();

            }
            f_in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public static ArrayList<Tecnico> cargarDatosTecnicos(ArrayList<Tecnico> t) {
        try {
            BufferedReader f_in = new BufferedReader(new FileReader(new File("./CSV/tecnico.csv")));
            String fila = f_in.readLine();
            fila = f_in.readLine();
            while (fila != null) {
                String[] atributo = fila.split(",");
                t.add(new Tecnico((Integer.parseInt(atributo[0])),atributo[1],atributo[2]));
                fila = f_in.readLine();

            }
            f_in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return t;
    }

    public static ArrayList<DispositivoInventario> cargarDatosDispositivos(ArrayList<DispositivoInventario> d) {
        try {
            BufferedReader f_in = new BufferedReader(new FileReader(new File("./CSV/inventario.csv")));
            String fila = f_in.readLine();
            fila = f_in.readLine();
            while (fila != null) {
                String[] atributo = fila.split(",");
                d.add(new DispositivoInventario((Integer.parseInt(atributo[0])),atributo[1],atributo[2], atributo[3]));
                fila = f_in.readLine();

            }
            f_in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return d;
    }

    public static ArrayList<Ticket> cargarDatosTickets(ArrayList<Ticket> t) {
        try {
            BufferedReader f_in = new BufferedReader(new FileReader(new File("./CSV/ticket.csv")));
            String fila = f_in.readLine();
            fila = f_in.readLine();
            while (fila != null) {
                String[] atributo = fila.split(",");
                t.add(new Ticket((Integer.parseInt(atributo[0])), Integer.parseInt(atributo[1]), Integer.parseInt(atributo[2]),Integer.parseInt(atributo[3]), Integer.parseInt(atributo[4]), Integer.parseInt(atributo[5]), Utilidades.stringToBoolean(atributo[6]), atributo[7]));
                fila = f_in.readLine();
            }
            f_in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return t;
    }

    public static void guardarDatosPeticiones(ArrayList<Peticion> p) {
        try {
            File peticionCSV = new File("./CSV/peticion.csv");

            PrintWriter f_sal = new PrintWriter(new FileWriter((peticionCSV), false), false);
            Peticion peticion;

            f_sal.println("idPeticion,idUsuario,descripcion,fecha,idCategoria,idAdmin,resuelta");

            for (int i = 0; i < p.size(); i++) {
                peticion = p.get(i);
                f_sal.println(peticion.getId() + "," + peticion.getIdUsuario() + "," + peticion.getDescripcion() + "," + peticion.getFecha() + "," + peticion.getIdCategoria() + "," + peticion.getIdAdmin() + "," + peticion.getResuelta());
            }
            f_sal.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void guardarDatosTickets(ArrayList<Ticket> t) {
        try {
            File peticionCSV = new File("./CSV/ticket.csv");

            PrintWriter f_sal = new PrintWriter(new FileWriter((peticionCSV), false), false);
            Ticket ticket;

            f_sal.println("idTicket, idPeticion, idAdministrador,idTecnico,idDispositivo,urgencia,resuelto,descripcionTarea");

            for (int i = 0; i < t.size(); i++) {
                ticket = t.get(i);
                f_sal.println(ticket.getId() + "," + ticket.getIdPeticion() + "," + ticket.getIdAdmin() + "," + ticket.getIdTecnico() + "," + ticket.getIdDispositivos() + "," + ticket.getUrgencia() + "," + ticket.getResuelto() + "," + ticket.getDescripcion());
            }
            f_sal.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
