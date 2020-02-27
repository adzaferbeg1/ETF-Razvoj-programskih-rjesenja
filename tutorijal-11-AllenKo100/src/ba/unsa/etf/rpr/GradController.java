package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

public class GradController {
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public ChoiceBox<Drzava> choiceDrzava;

    public ImageView viewSlika;

    public TextField fieldPostanskiBroj;

    public ObservableList<Drzava> drzave;

    private Grad grad;

    //Potrebno nam u GlavnaController, jer ćemo koristiti ovu metodu
    public Grad getGrad() {
        return grad;
    }

    //Konstruktor za GradController stavlja grad na poslani i ObservableListu drzava na one koje su poslane
    public GradController(Grad grad, ArrayList<Drzava> drzave) {
        this.grad = grad;
        this.drzave = FXCollections.observableArrayList(drzave);
    }

    @FXML
    public void initialize() {
        choiceDrzava.setItems(drzave);
        if (grad != null) {
            fieldNaziv.setText(grad.getNaziv());
            fieldBrojStanovnika.setText(Integer.toString(grad.getBrojStanovnika()));
            fieldPostanskiBroj.setText("");
            fieldPostanskiBroj.setText(Integer.toString(grad.getPostanskiBroj()));
            for (Drzava drzava : drzave)
                if (drzava.getId() == grad.getDrzava().getId())
                    choiceDrzava.getSelectionModel().select(drzava);
            if (grad.getSlikaPath() != null && !grad.getSlikaPath().equals("")) {
                postaviSliku(grad.getSlikaPath());
            }
        } else {
            choiceDrzava.getSelectionModel().selectFirst();
        }
    }

    //Ako klikne na Cancel izlazimo iz tog prozora
    public void clickCancel(ActionEvent actionEvent) {
        grad = null;
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

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

        int brojStanovnika = 0;

        try {
            brojStanovnika = Integer.parseInt(fieldBrojStanovnika.getText());
        } catch (NumberFormatException e) {
            //ne treba ništa radit
        }
        //Ukoliko je neko unio broj stanovnika koji je manji od nula, to je neispravno, te ne smije proći validaciju
        if (brojStanovnika <= 0) {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
            daLiJeSveIspravno = false;
        } else {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
        }

        validatePostanski();

        //Izadji i ništa ne radi ako nešto nije ispravno
        if (!daLiJeSveIspravno) return;

        if (grad == null) grad = new Grad();

        grad.setNaziv(fieldNaziv.getText());
        grad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
        grad.setDrzava(choiceDrzava.getValue());

        if(isPostanskiValid()) {
            grad.setPostanskiBroj(Integer.parseInt(fieldPostanskiBroj.getText()));
        }

        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    public void slikaAction(ActionEvent actionEvent){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Odaberi sliku");
        dialog.setHeaderText("Put do slike");
        dialog.setContentText("Path:");
        Optional<String> res = dialog.showAndWait();
        if(res.isPresent()){
            if(res.get() != null && !res.get().isEmpty() && !res.get().equals("")){
                grad.setSlikaPath(res.get());
                postaviSliku(res.get());
            }
        }
    }

    private void postaviSliku(String path) {
        File file = new File(path);
        if (file.exists()) {
            FileInputStream input = null;
            try {
                input = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            assert input != null;
            Image img = new Image(input);
            viewSlika.setImage(img);
        }
    }

    private void validatePostanski(){
        new Thread(()->{
            System.out.println("Krenuo thread ");
            if(isPostanskiValid()) {
                Platform.runLater(() -> {
                    fieldPostanskiBroj.getStyleClass().removeAll("poljeNijeIspravno");
                    fieldPostanskiBroj.getStyleClass().addAll("poljeIspravno");
                });
                System.out.println("Ispravan ");
            }
            else{
                Platform.runLater(()->{
                    fieldPostanskiBroj.getStyleClass().addAll("poljeNijeIspravno");
                });
            }
        }).start();
    }

    private boolean isPostanskiValid()   {
        String apiUrl = "http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=";
        try {
            URL url = new URL(apiUrl + fieldPostanskiBroj.getText().trim());
            BufferedReader ulaz = null;
            ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String res = ulaz.readLine();
            return res.trim().equals("OK");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}