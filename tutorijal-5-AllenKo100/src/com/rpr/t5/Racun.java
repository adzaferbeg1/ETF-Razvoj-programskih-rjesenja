package com.rpr.t5;

public class Racun {
    private final Long brojRacuna;
    private final Osoba korisnikRacuna;
    private Double stanjeRacuna;
    private boolean odobrenjePrekoracenja;

    public Racun(Long brojRacuna, Osoba korisnikRacuna) {
        this.brojRacuna = brojRacuna;
        this.korisnikRacuna = korisnikRacuna;
        this.stanjeRacuna = 0d;
        this.odobrenjePrekoracenja = false;
    }

    public boolean izvrsiUplatu(Double uplata){
        if(uplata < 0) return false;
        stanjeRacuna = stanjeRacuna + uplata;
        return true;
    }

    public boolean izvrsiIsplatu(Double isplata){
        if(!provjeriOdobrenjePrekoracenja(isplata)) return false;
        stanjeRacuna = stanjeRacuna - isplata;
        return true;
    }

    private boolean provjeriOdobrenjePrekoracenja(Double iznos){
        return odobrenjePrekoracenja || !odobrenjePrekoracenja && (stanjeRacuna - iznos) > 0;
    }

    public void odobriPrekoracenje(){
        odobrenjePrekoracenja = true;
    }

    public Long getBrojRacuna() {
        return brojRacuna;
    }

    public Osoba getKorisnikRacuna() {
        return korisnikRacuna;
    }

    public Double getStanjeRacuna() {
        return stanjeRacuna;
    }

    public boolean isOdobrenjePrekoracenja() {
        return odobrenjePrekoracenja;
    }
}
