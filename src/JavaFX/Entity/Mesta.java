package JavaFX.Entity;

public class Mesta {

    private Integer id;
    private String depo;
    private Integer kolobezky;
    private Integer bicykle;

    public Mesta(Integer id, String depo, Integer kolobezky, Integer bicykle) {
        this.id = id;
        this.depo = depo;
        this.kolobezky = kolobezky;
        this.bicykle = bicykle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepo() {
        return depo;
    }

    public void setDepo(String depo) {
        this.depo = depo;
    }

    public Integer getKolobezky() {
        return kolobezky;
    }

    public void setKolobezky(Integer kolobezky) {
        this.kolobezky = kolobezky;
    }

    public Integer getBicykle() {
        return bicykle;
    }

    public void setBicykle(Integer bicykle) {
        this.bicykle = bicykle;
    }
}