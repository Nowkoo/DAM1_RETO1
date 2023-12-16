public class Peticion {
    private int id;
    private int idUsuario;
    private String descripcion;
    private String fecha;
    private int idCategoria;
    private int idAdmin;
    private boolean resuelta;


    Peticion(int id, int idUsuario, String descripcion, String fecha, int idCategoria, int idAdmin, boolean resuelta) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idCategoria = idCategoria;
        this.idAdmin = idAdmin;
        this.resuelta = resuelta;
    }

    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public boolean getResuelta() {
        return resuelta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }
}
