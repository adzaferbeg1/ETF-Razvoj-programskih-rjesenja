package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class Semestar {
    private PlanStudija planStudija;
    private ArrayList<Student> studenti = new ArrayList<>();
    private static int minimalniEctsPoeni = 30;

    public Semestar(PlanStudija planStudija){
        this.planStudija = planStudija;
    }
    
    public void izbaci(Student student){
        studenti.remove(student);
    }

    public void setPlanStudija(PlanStudija planStudija){
        this.planStudija = planStudija;
    }

    public PlanStudija getPlanStudija(){
        return planStudija;
    }

    public int brojStudenata(){
        return studenti.size();
    }
}
