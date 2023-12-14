public class Peticion {
    private int id;
    private int idUsuario;
    private String descripcion;
    private String fecha;
    private int idCategoria;
    private int idAdmin;
    private int estado;
    private boolean resuelta;


    Peticion(int id, int idUsuario, String descripcion, String fecha, int idCategoria, int idAdmin, int estado, boolean resuelta) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idCategoria = idCategoria;
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

    public int getEstado() {
        return estado;
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

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }
}
