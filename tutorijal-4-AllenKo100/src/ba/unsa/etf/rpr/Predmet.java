package ba.unsa.etf.rpr;

public class Predmet {
    private String imePredmeta;
    private int ectsPoeni;
    private boolean jelObavezan;

    public Predmet(String imePredmeta, int ectsPoeni, boolean jelObavezan){
        this.imePredmeta=imePredmeta;
        this.ectsPoeni=ectsPoeni;
        this.jelObavezan=jelObavezan;
    }

    public String getImePredmeta() {
        return imePredmeta;
    }

    public int getEctsPoeni() {
        return ectsPoeni;
    }

    public boolean isJelObavezan() {
        return jelObavezan;
    }
}
