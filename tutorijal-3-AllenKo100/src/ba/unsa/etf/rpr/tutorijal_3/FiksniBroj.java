package ba.unsa.etf.rpr.tutorijal_3;

public class FiksniBroj extends TelefonskiBroj {
    private Grad grad;
    private String broj;

    public FiksniBroj(Grad grad, String broj){
        this.grad=grad;
        this.broj=broj;
    }

    public Grad getGrad() {
        return grad;
    }

    @Override
    public String ispisi(){
        String ispis="";
        ispis=ispis+grad+"/"+broj;
        return ispis;
    }

    @Override
    public int hashCode(){
        return broj.hashCode();
    }
}
