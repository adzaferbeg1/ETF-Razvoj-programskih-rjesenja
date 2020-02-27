package com.rpr.t5;

import java.util.ArrayList;
import java.util.List;

public class Banka {
    private static Long brojRacuna = 1000000000000000L;
    private List<Uposlenik> uposleni;
    private List<Korisnik> korisnici;

    public Banka() {
        uposleni = new ArrayList<>();
        korisnici = new ArrayList<>();
    }

    public List<Uposlenik> getUposleni() {
        return uposleni;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public Korisnik kreirajNovogKorisnika(String ime, String prezime){
        Korisnik korisnik = new Korisnik(ime, prezime);
        korisnici.add(korisnik);
        return korisnik;
    }

    public Uposlenik dodajNovogUposlenika(String ime, String prezime){
        Uposlenik uposlenik = new Uposlenik(ime, prezime);
        uposleni.add(uposlenik);
        return uposlenik;
    }


}
