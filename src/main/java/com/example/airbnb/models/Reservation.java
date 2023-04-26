package com.example.airbnb.models;

public class Reservation {
	private String Ville;
	private String hote;
	private String prix;
	private String nbPassagers;
	private String reservation;
	private String datedebut;
	private String datefin;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVille() {
		return Ville;
	}

	public void setVille(String ville) {
		Ville = ville;
	}

	public String getHote() {
		return hote;
	}

	public void setHote(String hote) {
		this.hote = hote;
	}

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getNbPassagers() {
		return nbPassagers;
	}

	public void setNbPassagers(String nbPassagers) {
		this.nbPassagers = nbPassagers;
	}

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public String getDatedebut() {
		return datedebut;
	}

	public void setDatedebut(String datedebut) {
		this.datedebut = datedebut;
	}

	public String getDatefin() {
		return datefin;
	}

	public void setDatefin(String datefin) {
		this.datefin = datefin;
	}

	public Reservation(String Ville, String prix, String Hote, String nbPassagers, String reservation, String datedebut,
			String datefin, String status) {
		this.Ville = Ville;
		this.prix = prix;
		this.hote = Hote;
		this.nbPassagers = nbPassagers;
		this.datedebut = datedebut;
		this.datefin = datefin;
		this.reservation = reservation;
		this.status = status;

	}

}
