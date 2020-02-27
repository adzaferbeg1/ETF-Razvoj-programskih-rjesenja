package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.Scanner;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    public static String ispisiGradove() {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        String rezultat = "";
        for(Grad grad : dao.gradovi()) {
            rezultat += grad.getNaziv() + " (" + grad.getDrzava().getNaziv() +") - " +
                    grad.getBrojStanovnika() + "\n";
        }
        return rezultat;
    }

    public static void glavniGrad() {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite naziv države: ");
        String naziv = sc.nextLine();
        Grad grad = dao.glavniGrad(naziv);
        if(grad == null){
            System.out.println("Nepostojeća država");
        }
        else {
            System.out.println("Glavni grad države " + naziv + " je " + grad.getNaziv());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/glavna.fxml" ), bundle);
        GlavnaController ctrl = new GlavnaController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Gradovi svijeta");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
