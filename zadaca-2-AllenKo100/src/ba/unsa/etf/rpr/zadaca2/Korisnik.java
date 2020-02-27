package ba.unsa.etf.rpr.zadaca2;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Korisnik {
    private SimpleStringProperty ime, prezime, email, username, password, passwordRepeat;
    private SimpleIntegerProperty godinaRodjenja;

    public Korisnik(String ime, String prezime, String email, String username, String password) {
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.email = new SimpleStringProperty(email);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.passwordRepeat = new SimpleStringProperty(password);
        this.godinaRodjenja = new SimpleIntegerProperty(2000);
    }

    @Override
    public String toString() {
        return prezime.get() + " " + ime.get();
    }

    public String getIme() {
        return ime.get();
    }

    public SimpleStringProperty imeProperty() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public String getPrezime() {
        return prezime.get();
    }

    public SimpleStringProperty prezimeProperty() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
        this.passwordRepeat.set(password);
    }

    public String getPasswordRepeat() {
        return passwordRepeat.get();
    }

    public SimpleStringProperty passwordRepeatProperty() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat.set(passwordRepeat);
    }

    public int getGodinaRodjenja() {
        return godinaRodjenja.get();
    }

    public SimpleIntegerProperty godinaRodjenjaProperty() {
        return godinaRodjenja;
    }

    public void setGodinaRodjenja(int godinaRodjenja) {
        this.godinaRodjenja.set(godinaRodjenja);
    }

    public boolean checkPassword() {
        //šifra validna ako ima bar jedno veliko slovo, jedno malo, te jedan broj
        String pass = password.get();
        int veliko = 0;
        int malo = 0;
        int broj = 0;
        for(int i = 0; i < pass.length(); i++) {
            if(Character.isUpperCase(pass.charAt(i))) veliko++;
            if(Character.isLowerCase(pass.charAt(i))) malo++;
            if(Character.isDigit(pass.charAt(i))) broj++;
        }

        if(veliko == 0 || malo == 0 || broj == 0) return false;

        return true;
    }
}
