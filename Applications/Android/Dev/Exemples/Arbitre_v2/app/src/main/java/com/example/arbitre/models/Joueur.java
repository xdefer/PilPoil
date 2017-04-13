package com.example.arbitre.models;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Joueur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nom;
	private String prenom;
	private String nationalite;
	//private int service;
	private String civilite;
	
	
	public Joueur(int id, String nom, String prenom, String nationalite,
			 String civilite) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.nationalite = nationalite;
		//this.service = service;
		this.civilite = civilite;
	}
	
	public Joueur(){
		
	}

	public static List<Joueur> listFromJSON(String json) throws JSONException, ParseException {
		List<Joueur> lesJoueurs = new ArrayList<Joueur>();
		JSONObject jobj = new JSONObject(json);
		JSONArray jsonArray = jobj.getJSONArray("joueurs");
		
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject eventJSON = jsonArray.getJSONObject(i);
		    Joueur unJoueur = Joueur.fromJSON(eventJSON.toString());
		    lesJoueurs.add(unJoueur);
		}
		return lesJoueurs;
	}	
	
	public static Joueur fromJSON(String json) throws JSONException{
		JSONObject jsonObj = new JSONObject(json);
		Joueur leJoueur = new Joueur();
		leJoueur.setId(jsonObj.getInt("id"));
		leJoueur.setNom(jsonObj.getString("nom"));
		leJoueur.setPrenom(jsonObj.getString("prenom"));
		leJoueur.setCivilite(jsonObj.getString("civilite"));
		leJoueur.setNationalite(jsonObj.getString("nationalite"));
		//leJoueur.setService(jsonObj.getInt("service"));
		
		return leJoueur;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getNationalite() {
		return nationalite;
	}


	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}


	/*public int isService() {
		return service;
	}


	public void setService(int service) {
		this.service = service;
	}*/

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
}
