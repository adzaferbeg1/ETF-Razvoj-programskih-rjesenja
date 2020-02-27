
package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DrzavaController {
    public TextField fieldNaziv;
    public ChoiceBox<Grad> choiceGrad;

    private Drzava drzava;

    //Potrebno nam u GlavnaController, jer ćemo koristiti ovu metodu
    public Drzava getDrzava() {
        return drzava;
    }

    private ObservableList<Grad> gradovi;

    //konstruktor za DrzavaController, puni ObservableListu gradovima iz ArrayListe koji su poslani
    public DrzavaController(Drzava drzava, ArrayList<Grad> gradovi) {
        this.drzava = drzava;
        this.gradovi = FXCollections.observableArrayList(gradovi);
    }

    @FXML
    public void initialize() {
        choiceGrad.setItems(gradovi);
        if (drzava != null) {
            fieldNaziv.setText(drzava.getNaziv());
            choiceGrad.getSelectionModel().select(drzava.getGlavniGrad());
        } else {
            choiceGrad.getSelectionModel().selectFirst();
        }
    }

    //Metoda za zatvaranje trenutnog prozora
    private void closeWindow() {
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    //Ukoliko korisnik klikne na Cancel, samo se zatvara prozor
    public void clickCancel(ActionEvent actionEvent) {
        drzava = null;
        closeWindow();
    }

    //Testira validnost države koja je unesena, te prema tome mijenja izgled polja u određenu boju iz validacija.css
    public void clickOk(ActionEvent actionEvent) {
        boolean daLiJeSveIspravno = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            daLiJeSveIspravno = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }

        //Ako nešto nije ispravno, izadji i ne radi nista
        if (!daLiJeSveIspravno) return;

        if (drzava == null) drzava = new Drzava();

        drzava.setNaziv(fieldNaziv.getText());
        drzava.setGlavniGrad(choiceGrad.getSelectionModel().getSelectedItem());
        closeWindow();
    }
}