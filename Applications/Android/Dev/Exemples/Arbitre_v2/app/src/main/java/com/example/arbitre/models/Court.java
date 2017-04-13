package com.example.arbitre.models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Court {
	private int num;
	private String libelle;
	
	/**
	 * Permet de creer un Court a partir d un JSON
	 * @param json Le json
	 * @return leCourt
	 * @throws JSONException
	 */
	public static Court fromJSON(String json) throws JSONException{
		JSONObject jsonObj = new JSONObject(json);
		Court leCourt = new Court();
		leCourt.setNum(jsonObj.getInt("num"));
		leCourt.setLibelle(jsonObj.getString("libelle"));
		
		return leCourt;
	}
	
	/**
	 * Permet de renvoyer une liste de court a partir du json
	 * @param json le json contenant les courts
	 * @return la liste des courts
	 * @throws JSONException
	 * @throws ParseException
	 */
	public static List<Court> listFromJSON(String json) throws JSONException, ParseException{
		List<Court> lesCourts = new ArrayList<Court>();
		JSONObject jobj = new JSONObject(json);
		JSONArray jsonArray = jobj.getJSONArray("courts");
		
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject eventJSON = jsonArray.getJSONObject(i);
		    Court unCourt = Court.fromJSON(eventJSON.toString());
		    lesCourts.add(unCourt);
		}
		return lesCourts;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
