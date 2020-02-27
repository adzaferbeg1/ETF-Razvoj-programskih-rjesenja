package com.rpr.t5.util;

import com.rpr.t5.Korisnik;

import java.util.List;

public class Kredit {
    private Kredit(){}

    public static Double dajKreditnuSposobnost(KreditnaSposobnost ks, Korisnik k){
        return ks.provjeri(k.getRacun());
    }

    public static void bezPrekoracenja(List<Korisnik> korisnici){
        korisnici.stream().filter(k -> k.getRacun().getStanjeRacuna()>=0).forEach(System.out::println);
    }

    public static void odobriPrekoracenje(List<Korisnik> korisnici){
        korisnici.stream().filter(k -> k.getRacun().getStanjeRacuna()>=10000).forEach(k -> k.getRacun().odobriPrekoracenje());
    }
}
