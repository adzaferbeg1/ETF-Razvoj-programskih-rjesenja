package ba.unsa.etf.rpr.tutorijal_3;

import java.util.*;

public class Imenik {
    private Map<String, TelefonskiBroj> brojevi;

    public Imenik(){
        brojevi = new HashMap<String, TelefonskiBroj>();
    }

    void dodaj(String ime, TelefonskiBroj broj){
        brojevi.put(ime, broj);
    }

    String dajBroj(String ime){
        TelefonskiBroj pomocni=brojevi.get(ime);
        return pomocni.ispisi();
    }

    public String dajIme(TelefonskiBroj broj){
        for(Map.Entry<String, TelefonskiBroj> it : brojevi.entrySet()){
            if(it.getValue().ispisi().equals(broj.ispisi())){
                return it.getKey();
            }
        }

        return "Osoba nije u mapi";
    }

    String naSlovo(char i){
        String pomocni="";
        int brojac=0;

        for(Map.Entry<String, TelefonskiBroj> it : brojevi.entrySet()){
            if(it.getKey().charAt(0)==i){
                brojac++;
                pomocni=pomocni+brojac+". "+it.getKey()+" - "+it.getValue().ispisi()+"\n";
            }
        }

        return pomocni;
    }

    Set<String> izGrada(Grad g){
        Set<String> set=new TreeSet<String>();
        for(Map.Entry<String, TelefonskiBroj> it : brojevi.entrySet()){
            if((it.getValue() instanceof FiksniBroj) && ((FiksniBroj) it.getValue()).getGrad()==g){
                set.add(it.getKey());
            }
        }

        return set;
    }

    Set<TelefonskiBroj> izGradaBrojevi(Grad g){
        Set<TelefonskiBroj> set=new TreeSet<TelefonskiBroj>((o1,o2) -> {
            String pomocni1=o1.ispisi();
            String pomocni2=o2.ispisi();
            return pomocni1.compareTo(pomocni2);
        });

        for(Map.Entry<String, TelefonskiBroj> it : brojevi.entrySet()){
            if((it.getValue() instanceof FiksniBroj) && ((FiksniBroj) it.getValue()).getGrad()==g){
                set.add(it.getValue());
            }
        }

        return set;
    }
}
