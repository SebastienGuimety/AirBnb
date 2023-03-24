package com.example.airbnb.models;

public class Sejour {
	private String ville;
	private String pays;
	private String dateDebut;
	private String dateFin;
	private Hote hote;
	
	public String getVille() {
		return ville;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getPays() {
		return pays;
	}
	
	public void setPays(String pays) {
		this.pays = pays;
	}
	
	public String getDateDebut() {
		return dateDebut;
	}
	
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	
	public String getDateFin() {
		return dateFin;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
	
	public Hote getHote() {
		return hote;
	}
	public void setHote(Hote hote){
		this.hote=hote;
	}
	   
	
	public boolean verifierDisponibilite(String date_debut,String date_fin){
		  
		return true;
	}
	

}
