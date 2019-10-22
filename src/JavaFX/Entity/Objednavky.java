package JavaFX.Entity;

import java.sql.Time;

public class Objednavky {

    private int id;
    private String depo;
    private Boolean kolobezka;
    private long cas;
    private int hodiny;
    private String user_name;
    private String  mesto;

    public Objednavky(int id, String depo, Boolean kolobezka, long cas, int hodiny, String user_name, String mesto) {
        this.id = id;
        this.depo = depo;
        this.kolobezka = kolobezka;
        this.cas = cas;
        this.hodiny = hodiny;
        this.user_name = user_name;
        this.mesto = mesto;
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

    public Boolean getKolobezka() {
        return kolobezka;
    }

    public void setKolobezka(Boolean kolobezka) {
        this.kolobezka = kolobezka;
    }

    public long getCas() {
        return cas;
    }

    public void setCas(long cas) {
        this.cas = cas;
    }

    public int getHodiny() {
        return hodiny;
    }

    public void setHodiny(int hodiny) {
        this.hodiny = hodiny;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }
}
