public class DispositivoInventario {
    private int id;

    private String nombre;

    private String sala;

    private String ip;

    DispositivoInventario(int id, String nombre, String sala, String ip) {
        this.id = id;
        this.nombre = nombre;
        this.sala = sala;
        this.ip = ip;
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

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
