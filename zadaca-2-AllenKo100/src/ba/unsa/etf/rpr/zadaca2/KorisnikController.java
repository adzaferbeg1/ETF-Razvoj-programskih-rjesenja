package ba.unsa.etf.rpr.zadaca2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;
import java.util.stream.Collectors;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Slider sliderGodinaRodjenja;
    public CheckBox cbAdmin;

    private KorisniciModel model;

    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setTrenutniKorisnik(newKorisnik);
            listKorisnici.refresh();
         });

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
                fldPasswordRepeat.textProperty().unbindBidirectional(oldKorisnik.passwordRepeatProperty() );
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(oldKorisnik.godinaRodjenjaProperty() );
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                fldPasswordRepeat.setText("");
                sliderGodinaRodjenja.setValue(2000);
            }
            else {
                fldIme.textProperty().bindBidirectional( newKorisnik.imeProperty() );
                fldPrezime.textProperty().bindBidirectional( newKorisnik.prezimeProperty() );
                fldEmail.textProperty().bindBidirectional( newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newKorisnik.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newKorisnik.passwordProperty() );
                fldPasswordRepeat.textProperty().bindBidirectional( newKorisnik.passwordRepeatProperty() );
                sliderGodinaRodjenja.valueProperty().bindBidirectional( newKorisnik.godinaRodjenjaProperty() );
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && valjaLiImePrezime(newIme)) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && valjaLiImePrezime(newIme)) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && valjaLiEmail(newIme)) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && valjaLiUsername(newIme)) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            //Pravimo pomocnog korisnika, da mozemo pozvati metodu iz Korisnika
            Korisnik tmp = new Korisnik("", "", "", "", newIme);
            if (!newIme.isEmpty() && tmp.checkPassword() && newIme.equals(fldPasswordRepeat.getText())) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPasswordRepeat.textProperty().addListener((obs, oldIme, newIme) -> {
            //Pravimo pomocnog korisnika, da mozemo pozvati metodu iz Korisnika
            Korisnik tmp = new Korisnik("", "", "", "", newIme);
            if (!newIme.isEmpty() && tmp.checkPassword() && newIme.equals(fldPassword.getText())) {
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeIspravno");
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().add("poljeNijeIspravno");
            }
        });

        cbAdmin.selectedProperty().addListener((obs, oldIme, newIme) -> {
            if(model.getTrenutniKorisnik() != null) {
                Korisnik tmp1 = model.getTrenutniKorisnik();
                Korisnik administrator = new Administrator(tmp1.getIme(), tmp1.getPrezime(), tmp1.getEmail(), tmp1.getUsername(), tmp1.getPassword());
                int mjestoUListi = model.getKorisnici().indexOf(tmp1);
                if(newIme) {
                    model.getKorisnici().set(mjestoUListi, administrator);
                    model.setTrenutniKorisnik(model.getKorisnici().get(mjestoUListi));
                } else {
                    Korisnik tmp2 = new Korisnik(tmp1.getIme(), tmp1.getPrezime(), tmp1.getEmail(), tmp1.getUsername(), tmp1.getPassword());
                    model.getKorisnici().set(mjestoUListi, tmp2);
                    model.setTrenutniKorisnik(tmp2);
                }
            }
        });
    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void obrisiAction(ActionEvent actionEvent) {
        model.getKorisnici().remove(model.getTrenutniKorisnik());
    }

    //Zadatak 6, provjera validnosti polja Ime, prezime, email, te korisničko ime
    private boolean valjaLiImePrezime(String imeIliPrezime) {
        if(imeIliPrezime.length() < 3) return false;
        if(imeIliPrezime.contains("_")) return false;
        return imeIliPrezime.matches("[\\- A-za-zČĆŽĐŠčćđžš]+");
    }

    private boolean valjaLiEmail (String email) {
        if(!email.contains("@")) return false;
        if(email.charAt(0)=='@') return false;
        if(email.charAt(email.length()-1)=='@') return false;
        return true;
    }

    private boolean valjaLiUsername (String username) {
        if(username.length() >16) return false;
        if(username.matches("[A-Za-z0-9_]+")) return true;
        return false;
    }
    //Kraj funkcija za Zadatak 6

    //dugme "Generisi"
    public void generisiAction(ActionEvent actionEvent) {
        //Zadatak 8, dugme "Generiši" za generisanje username-a
        if(model.getTrenutniKorisnik().getIme().length() != 0 && model.getTrenutniKorisnik().getPrezime().length() != 0) {
            String nickname = Character.toString(model.getTrenutniKorisnik().getIme().charAt(0)) + model.getTrenutniKorisnik().getPrezime();
            nickname = nickname.toLowerCase();
            //Koristit ćemo funkciju replaceAll koja mijenja sve članove koji se pošalju kao prvi argument sa drugim argumentom
            nickname = nickname.replaceAll("č","c");
            nickname = nickname.replaceAll("ć","c");
            nickname = nickname.replaceAll("đ","d");
            nickname = nickname.replaceAll("š","s");
            nickname = nickname.replaceAll("ž","z");
            //postavimo sada ovaj nickname trenutnom korisniku
            model.getTrenutniKorisnik().setUsername(nickname);
        }
        //Zadatak 9, dugme "Generiši" za generisanje passworda
        String generisaniPassword = new Random().ints(8, 33, 122).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());

        if(model.getTrenutniKorisnik() instanceof Administrator) {
            Korisnik tmp = new Administrator("", "", "", "", generisaniPassword);
            if (tmp.checkPassword()) {
                model.getTrenutniKorisnik().setPassword(generisaniPassword);
                model.getTrenutniKorisnik().setPasswordRepeat(generisaniPassword);
            }
        } else {
            Korisnik tmp = new Korisnik("", "", "", "", generisaniPassword);
            if (tmp.checkPassword()) {
                model.getTrenutniKorisnik().setPassword(generisaniPassword);
                model.getTrenutniKorisnik().setPasswordRepeat(generisaniPassword);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Vaša lozinka glasi: " + model.getTrenutniKorisnik().getPassword());

        alert.showAndWait();

    }
}