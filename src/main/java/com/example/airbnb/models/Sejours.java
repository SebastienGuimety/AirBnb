package com.example.airbnb.models;


public class Sejours {
    private String Ville;
    private String Pays;
    private String Prix;
    private String Hote;
    private String nbPassagers;
    private String avis;
    private String datedebut;
    private String datefin;
    private String reserver;

    public Sejours(String Ville, String Pays, String Prix, String Hote, String nbPassagers, String avis, String datedebut, String datefin, String reserver) {
        this.Ville = Ville;
        this.Pays = Pays;
        this.Prix = Prix;
        this.Hote = Hote;
        this.nbPassagers = nbPassagers;
        this.avis = avis;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.reserver = reserver;
    }



    public String getVille(){
        return Ville;
    }

    public String getPays() {
        return Pays;
    }

    public String getPrix() {
        return Prix;
    }

    public String getHote() {
        return Hote;
    }

    public String getNbPassagers(){
        return nbPassagers;
    }

    public String getAvis(){
        return avis;
    }

    public String getDatedebut(){
        return datedebut;
    }

    public String getDatefin(){
        return datefin;
    }

    public String getReserver(){
        return reserver;
    }
}
