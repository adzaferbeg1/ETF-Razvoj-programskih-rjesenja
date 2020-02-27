package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class PlanStudija {
    private ArrayList<Predmet> obavezni;
    private ArrayList<Predmet> izborni;

    public PlanStudija(){}

    public void dodajPredmet(Predmet predmet){
        if(predmet.jelObavezan())
            obavezni.add(predmet);
        else
            izborni.add(predmet);
    }

    public void ukloniPredmet(Predmet predmet){
        if(predmet.jelObavezan())
            obavezni.remove(predmet);
        else
            izborni.remove(predmet);
    }

    public int ectsPoeniObavezni(){
        int ukupno = 0;

        for(Predmet p : obavezni){
            ukupno += p.getEctsPoeni();
        }

        return ukupno;
    }

    public int ectsPoeniIzborni(){
        int ukupno = 0;

        for(Predmet p : izborni){
            ukupno += p.getEctsPoeni();
        }

        return ukupno;
    }


}
