package com.example.airbnb.models;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.*;

public class User {
	String nom;
	String Prenom;
	String login;
	String password;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	String id;
	String role;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
