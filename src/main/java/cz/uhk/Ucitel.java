package cz.uhk;

public class Ucitel {
    private String titulPred;
    private String titulZa;
    private String jmeno;
    private String prijmeni;

    public String getUcitel() {
        return (titulPred + " " + jmeno + " "  + prijmeni + " " + titulZa);
    }
}
