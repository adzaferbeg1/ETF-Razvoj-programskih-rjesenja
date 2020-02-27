package ba.unsa.etf.rpr.tutorijal_3;

public enum Grad {
    TRAVNIK("030"),
    ORASJE("031"),
    ZENICA("032"),
    SARAJEVO("033"),
    LIVNO("034"),
    TUZLA("035"),
    MOSTAR("036"),
    BIHAC("037"),
    GORAZDE("038"),
    SIROKIBRIJEG("039"),
    BRCKO("049");

    private final String broj;

    private Grad(String broj) { this.broj=broj; }

    @Override
    public String toString() { return broj; }
}
