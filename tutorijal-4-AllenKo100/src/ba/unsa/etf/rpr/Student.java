package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class Student {
    private String imePrezime;
    private int index;
    private ArrayList<Predmet> izborni;

    public Student(String imePrezime, int index){
        this.imePrezime=imePrezime;
        this.index=index;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public int getIndex() {
        return index;
    }

    public void dodajIzborni(Predmet predmet){
        if(!predmet.izborni()){
            throw new IllegalArgumentException("Predmet koji ste unijeli nije izborni!");
        }

        this.izborni.add(predmet);
    }

    public void ukloniIzborni(Predmet predmet){
        if(!predmet.izborni()){
            throw new IllegalArgumentException("Predmet koji ste unijeli nije izborni!");
        }

        this.izborni.remove(predmet);
    }
}
