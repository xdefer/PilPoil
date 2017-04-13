package com.example.arbitre.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Match {
	private int id;
	private String raisonPause;
	
	public Match() {
		this.id = -1;
		this.raisonPause = "";
	}
	
	public static Match fromJSON(String json) throws JSONException{
		JSONObject jsonObj = new JSONObject(json);
		Match leMatch = new Match();
		leMatch.setId(jsonObj.getInt("matchId"));
		leMatch.setRaisonPause(jsonObj.getString("raison"));
		
		return leMatch;
	}

	
	public Match(int id, String raisonPause) {
		this.id = id;
		this.raisonPause = raisonPause;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRaisonPause() {
		return raisonPause;
	}
	public void setRaisonPause(String raisonPause) {
		this.raisonPause = raisonPause;
	}
}
