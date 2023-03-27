package com.example.airbnb.models;


public class Sejours {
    private String Ville;
    private String Pays;
    private String Prix;
    private String Hote;
    private String nbPassagers;
    private String avis;

    public Sejours(String Ville, String Pays, String Prix, String Hote, String nbPassagers, String avis) {
        this.Ville = Ville;
        this.Pays = Pays;
        this.Prix = Prix;
        this.Hote = Hote;
        this.nbPassagers = nbPassagers;
        this.avis = avis;
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
}
