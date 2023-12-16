public class Admin {
    private int id;

    private String nombre;

    private String password;

    private int idCategoria;

    Admin(int id, String nombre, String password, int idCategoria) {

        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.idCategoria = idCategoria;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
