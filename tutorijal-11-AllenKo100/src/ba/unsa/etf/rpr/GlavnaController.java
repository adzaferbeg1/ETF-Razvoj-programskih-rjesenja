package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavnaController {

    private String jezici[] = {"Bosanski","Engleski","Francuski","Njemacki"};

    public ChoiceDialog choiceJezici;

    //potrebna nam GeografijaDAO za razne metode, između ostalog za resetovanje baze, tako da ćemo je definisat ovdje
    private GeografijaDAO dao;

    public TableView<Grad> tableViewGradovi;
    public TableColumn colGradId;
    public TableColumn colGradNaziv;
    public TableColumn colGradStanovnika;
    public TableColumn<Grad,String> colGradDrzava;

    private ObservableList<Grad> gradovi;

    public GlavnaController() {
        dao = GeografijaDAO.getInstance();
        gradovi = FXCollections.observableArrayList(dao.gradovi());
    }

    //Ovu metodu ćemo koristiti za vraćanje baze u polazno stanje
    //za potrebe testiranja, kao što je naglašeno u postavci tutorijala
    public void resetujBazu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        dao = GeografijaDAO.getInstance();
    }

    @FXML
    public void initialize() {
        choiceJezici = new ChoiceDialog(jezici[0], jezici);
        choiceJezici.setTitle("Program je dostupan na sljedecim jezicima:");
        choiceJezici.setContentText("Izaberite neki od jezika:");
        choiceJezici.setHeaderText("Jezici");

        tableViewGradovi.setItems(gradovi);
        colGradId.setCellValueFactory(new PropertyValueFactory("id"));
        colGradNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));
        colGradStanovnika.setCellValueFactory(new PropertyValueFactory("brojStanovnika"));
        colGradDrzava.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDrzava().getNaziv()));
    }

    public void actionJezici(ActionEvent actionEvent) {
        Optional<ButtonType> izaberiJezik = choiceJezici.showAndWait();
        if(izaberiJezik.isPresent()) {
            String noviJezik = (String) choiceJezici.getSelectedItem();
            if(noviJezik.equals("Bosanski")) {
                choiceJezici = new ChoiceDialog(jezici[0], jezici);
                Locale.setDefault(new Locale("bs","BA"));
            }
            else if(noviJezik.equals("Engleski")) {
                choiceJezici = new ChoiceDialog(jezici[1], jezici);
                Locale.setDefault(new Locale("en","US"));
            }
            else if(noviJezik.equals("Francuski")) {
                choiceJezici = new ChoiceDialog(jezici[2], jezici);
                Locale.setDefault(new Locale("fr","FRA"));
            }
            else if(noviJezik.equals("Njemacki")) {
                choiceJezici = new ChoiceDialog(jezici[3], jezici);
                Locale.setDefault(new Locale("de","DEU"));
            }

            Scene scene = tableViewGradovi.getScene();
            ResourceBundle noviBundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"), noviBundle);
            GlavnaController noviKontroler = new GlavnaController();
            loader.setController(noviKontroler);
            try {
                Parent root = loader.load();
                scene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionStampaj(ActionEvent actionEvent) {
        try {
            new GradoviReport().showReport(dao.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void actionDodajGrad(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/grad.fxml" ), bundle);
            GradController gradController = new GradController(null, dao.drzave());
            loader.setController(gradController);
            root = loader.load();
            stage.setTitle("Grad");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Grad grad = gradController.getGrad();
                if (grad != null) {
                    dao.dodajGrad(grad);
                    gradovi.setAll(dao.gradovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionObrisiGrad(ActionEvent actionEvent) {
        Grad grad = tableViewGradovi.getSelectionModel().getSelectedItem();
        if (grad == null) return;

        //Iskače prozor koji zahtjeva da potvrdite da želite obrisati selectovani grad
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("POTVRDA");
        alert.setHeaderText("Zahtjev za brisanje grada " + grad.getNaziv());
        alert.setContentText("Da li stvarno želite izbrisati grad " + grad.getNaziv()+"?");

        //Ukoliko je odgovor potvrdan
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.obrisiGrad(grad);
            gradovi.setAll(dao.gradovi());
        }
    }

    public void actionIzmijeniGrad(ActionEvent actionEvent) {
        Grad grad = tableViewGradovi.getSelectionModel().getSelectedItem();
        if (grad == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/grad.fxml" ), bundle);
            GradController gradController = new GradController(grad, dao.drzave());
            loader.setController(gradController);
            root = loader.load();
            stage.setTitle("Grad");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Grad noviGrad = gradController.getGrad();
                if (noviGrad != null) {
                    dao.izmijeniGrad(noviGrad);
                    gradovi.setAll(dao.gradovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionDodajDrzavu(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/drzava.fxml" ), bundle);
            DrzavaController drzavaController = new DrzavaController(null, dao.gradovi());
            loader.setController(drzavaController);
            root = loader.load();
            stage.setTitle("Država");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Drzava drzava = drzavaController.getDrzava();
                if (drzava != null) {
                    dao.dodajDrzavu(drzava);
                    gradovi.setAll(dao.gradovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}