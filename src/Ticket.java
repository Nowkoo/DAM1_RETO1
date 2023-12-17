public class Ticket {

    private int id;
    private int idPeticion;
    private int idAdmin;
    private int idTecnico;
    private int idDispositivos;
    private boolean resuelto;
    private int urgencia;
    private String descripcion;

    Ticket(int id, int idPeticion, int idAdmin, int idTecnico, int idDispositivos, int urgencia, boolean resuelto, String descripcion) {

        this.id = id;
        this.idPeticion = idAdmin;
        this.idAdmin = idAdmin;
        this.idTecnico = idTecnico;
        this.idDispositivos = idDispositivos;
        this.resuelto = resuelto;
        this.urgencia = urgencia;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(int idPeticion) {
        this.idPeticion = idPeticion;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public int getIdDispositivos() {
        return idDispositivos;
    }

    public void setIdDispositivos(int idDispositivos) {
        this.idDispositivos = idDispositivos;
    }

    public boolean getResuelto() {
        return resuelto;
    }

    public void setResuelto(boolean resuelto) {
        this.resuelto = resuelto;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.replaceAll(",", "");
    }
}
