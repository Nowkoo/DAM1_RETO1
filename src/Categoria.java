public class Categoria {
    private int id;
    private String categoria;
    Categoria(int id, String categoria){
        this.id=id;
        this.categoria=categoria;
    }
    public int getId(){
        return id;
    }
    public String getCategoria(){
        return categoria;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setCategoria(String categoria){
        this.categoria=categoria;
    }
}
