package com.example.airbnb.models;


import java.io.Serializable;

public class Sejours implements Serializable {
    public String Ville;
    public String Pays;
    public String Prix;
    public String idHote;
    public String nomHote;
    public String nbPassagers;
    public String avis;
    public String datedebut;
    public String datefin;
    public String reserver;

    public String id;

    public void setVille(String ville) {
        Ville = ville;
    }

    public void setPays(String pays) {
        Pays = pays;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public Sejours(String Ville, String Pays, String Prix, String idHote,String nomHote, String nbPassagers, String avis, String datedebut, String datefin, String reserver, String id) {
        this.Ville = Ville;
        this.Pays = Pays;
        this.Prix = Prix;
        this.idHote = idHote;
        this.nomHote = nomHote;
        this.nbPassagers = nbPassagers;
        this.avis = avis;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.reserver = reserver;
        this.id = id;
    }

    public Sejours(){}


    public String getVille(){
        return Ville;
    }

    public String getPays() {
        return Pays;
    }

    public String getPrix() {
        return Prix;
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



    public void setNbPassagers(String nbPassagers) {
        this.nbPassagers = nbPassagers;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public void setReserver(String reserver) {
        this.reserver = reserver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdHote() {
        return idHote;
    }

    public void setIdHote(String idHote) {
        this.idHote = idHote;
    }

    public String getNomHote() {
        return nomHote;
    }

    public void setNomHote(String nomHote) {
        this.nomHote = nomHote;
    }
}
