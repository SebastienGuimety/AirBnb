package com.example.airbnb.models;

import java.io.Serializable;

public class Commentaire implements Serializable {

    private String commentaire;
    private String idClient;
    private String idSejour;

    private String nomClient;

    public Commentaire(String commentaire, String idClient, String nomClient,String idSejour ) {
        this.commentaire = commentaire;
        this.nomClient = nomClient;
        this.idClient = idClient;
        this.idSejour = idSejour;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdSejour() {
        return idSejour;
    }

    public void setIdSejour(String idSejour) {
        this.idSejour = idSejour;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }
}
