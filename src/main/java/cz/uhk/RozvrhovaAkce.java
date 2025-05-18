package cz.uhk;

import com.google.gson.annotations.SerializedName;

public class RozvrhovaAkce {
    private String typAkce;
    private String predmet;
    private String den;
    private TimeValue hodinaSkutOd;
    private TimeValue hodinaSkutDo;
    private Ucitel ucitel;

    public String gettypAkce() {return typAkce;}
    public String getucitel() {return ucitel.getUcitel();}
    public String getDen() {
        return den;
    }
    public String getPredmet() {
        return predmet;
    }
    public String getHodinaSkutOd() {
        return hodinaSkutOd.getValue();
    }
    public String getHodinaSkutDo() {
        return hodinaSkutDo.getValue();
    }
}
