package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Grad {
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty naziv = new SimpleStringProperty();
    SimpleIntegerProperty brojStanovnika = new SimpleIntegerProperty();
    SimpleObjectProperty<Drzava> drzava = new SimpleObjectProperty<>();
    SimpleStringProperty slikaPath = new SimpleStringProperty();
    SimpleIntegerProperty postanskiBroj = new SimpleIntegerProperty();

    public Grad() {}

    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava) {
        this.id.set(id);
        this.naziv.set(naziv);
        this.brojStanovnika.set(brojStanovnika);
        this.drzava.set(drzava);
    }

    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava, String slikaPath, int postanskiBroj) {
        this.id.set(id);
        this.naziv.set(naziv);
        this.brojStanovnika.set(brojStanovnika);
        this.drzava.set(drzava);
        this.slikaPath.set(slikaPath);
        this.postanskiBroj.set(postanskiBroj);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNaziv() {
        return naziv.get();
    }

    public SimpleStringProperty nazivProperty() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv.set(naziv);
    }

    public int getBrojStanovnika() {
        return brojStanovnika.get();
    }

    public SimpleIntegerProperty brojStanovnikaProperty() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika.set(brojStanovnika);
    }

    public Drzava getDrzava() {
        return drzava.get();
    }

    public SimpleObjectProperty<Drzava> drzavaProperty() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava.set(drzava);
    }

    public String getSlikaPath() {
        return slikaPath.get();
    }

    public SimpleStringProperty slikaPathProperty() {
        return slikaPath;
    }

    public void setSlikaPath(String slikaPath) {
        this.slikaPath.set(slikaPath);
    }

    public int getPostanskiBroj() {
        return postanskiBroj.get();
    }

    public SimpleIntegerProperty postanskiBrojProperty() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(int postanskiBroj) {
        this.postanskiBroj.set(postanskiBroj);
    }

    @Override
    public String toString() {
        return naziv.get();
    }
}
