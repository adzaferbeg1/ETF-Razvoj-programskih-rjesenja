package ba.unsa.etf.rpr.t7;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class KorisniciModel {
    private ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<Korisnik> trenutniKorisnik = new SimpleObjectProperty<>();

    private Connection conn;
    private PreparedStatement spisakKorisnikaUpit, brisanjeKorisnikaUpit, izmjenaKorisnikaUpit, trenutniIdKorisnika, dodajKorisnikaUpit;

    public KorisniciModel() {
        try {
            if(conn == null) {
                conn = DriverManager.getConnection("jdbc:sqlite:korisnici.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            spisakKorisnikaUpit = conn.prepareStatement("SELECT * FROM korisnik");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                spisakKorisnikaUpit = conn.prepareStatement("SELECT * FROM korisnik");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            brisanjeKorisnikaUpit = conn.prepareStatement("DELETE FROM korisnik WHERE id=?");
            izmjenaKorisnikaUpit = conn.prepareStatement("UPDATE korisnik SET ime=?, prezime=?, email=?, username=?, password=? WHERE id=?");
            trenutniIdKorisnika = conn.prepareStatement("SELECT Max(id)+1 FROM korisnik");
            dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnik VALUES(?,?,?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("korisnici.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void diskonektuj() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn = null;
    }

    public void napuni() {
        /*korisnici.add(new Korisnik("Vedran", "Ljubović", "vljubovic@etf.unsa.ba", "vedranlj", "test"));
        korisnici.add(new Korisnik("Amra", "Delić", "adelic@etf.unsa.ba", "amrad", "test"));
        korisnici.add(new Korisnik("Tarik", "Sijerčić", "tsijercic1@etf.unsa.ba", "tariks", "test"));
        korisnici.add(new Korisnik("Rijad", "Fejzić", "rfejzic1@etf.unsa.ba", "rijadf", "test"));*/

        korisnici.removeAll();

        try {
            ResultSet rs = spisakKorisnikaUpit.executeQuery();
            while(rs.next()) {
                Korisnik k = new Korisnik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                korisnici.add(k);
                trenutniKorisnik.set(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniKorisnika(Korisnik k){
        try {
            izmjenaKorisnikaUpit.setString(1, k.getIme());
            izmjenaKorisnikaUpit.setString(2, k.getPrezime());
            izmjenaKorisnikaUpit.setString(3, k.getEmail());
            izmjenaKorisnikaUpit.setString(4, k.getUsername());
            izmjenaKorisnikaUpit.setString(5, k.getPassword());
            izmjenaKorisnikaUpit.setInt(6, k.getId());
            izmjenaKorisnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dodajKorisnika(Korisnik k) {
        ResultSet rs;
        int noviId = 0;
        try {
            rs = trenutniIdKorisnika.executeQuery();
            noviId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            dodajKorisnikaUpit.setString(1, k.getIme());
            dodajKorisnikaUpit.setString(2, k.getPrezime());
            dodajKorisnikaUpit.setString(3, k.getEmail());
            dodajKorisnikaUpit.setString(4, k.getUsername());
            dodajKorisnikaUpit.setString(5, k.getPassword());
            dodajKorisnikaUpit.setInt(6, noviId);
            dodajKorisnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiKorisnika(Korisnik k) {
        try {
            brisanjeKorisnikaUpit.setInt(1, k.getId());
            brisanjeKorisnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void zapisiDatoteku(File file) {
        if(file == null) return;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Korisnik k : getKorisnici()) {
            String username = k.getUsername();
            String password = k.getPassword();
            int id = k.getId();
            String stringId = Integer.toString(id);
            String imePrezime = k.getIme() + " " + k.getPrezime();

            String konacni = username + ":" + password + ":" + stringId + ":" + stringId +
                    ":" + imePrezime + "::" + "\n";
            //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            try {
                writer.write(konacni);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<Korisnik> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(Korisnik trenutniKorisnik) {
        //this.trenutniKorisnik.set(trenutniKorisnik);
        if(this.trenutniKorisnik.get()!=null) {
            izmijeniKorisnika(this.trenutniKorisnik.get());
        }
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    public void setTrenutniKorisnik(int i) {
        this.trenutniKorisnik.set(korisnici.get(i));
    }

    public Connection getConn() {
        return conn;
    }

    //Metoda koja služi kako bi baza na Windowsu se fino zatvorila, na Linuxu nije potrebna
    public void regenerisi() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:korisnici.db");
            PreparedStatement obrisiSve = conn.prepareStatement("delete from korisnik");
            obrisiSve.executeUpdate();

            Scanner ulaz = new Scanner(new FileInputStream("korisnici.db.sql"));
            StringBuilder upit = new StringBuilder();
            while (ulaz.hasNext()) {
                upit.append(ulaz.nextLine());
                if (upit.length() > 1) {
                    if (upit.charAt(upit.length() - 1) == ';') {
                        PreparedStatement stmt = conn.prepareStatement(upit.toString());
                        stmt.execute();
                        upit = new StringBuilder();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
