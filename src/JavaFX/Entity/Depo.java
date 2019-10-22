package JavaFX.Entity;

public class Depo {

    private int id;
    private String depo;
    private int kolobezky;
    private int bicykle;

    public Depo(int id, String depo, int kolobezky, int bicykle) {
        this.id = id;
        this.depo = depo;
        this.kolobezky = kolobezky;
        this.bicykle = bicykle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepo() {
        return depo;
    }

    public void setDepo(String depo) {
        this.depo = depo;
    }

    public int getKolobezky() {
        return kolobezky;
    }

    public void setKolobezky(int kolobezky) {
        this.kolobezky = kolobezky;
    }

    public int getBicykle() {
        return bicykle;
    }

    public void setBicykle(int bicykle) {
        this.bicykle = bicykle;
    }
}
