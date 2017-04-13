package com.example.arbitre;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.example.arbitre.models.ClientSocket;
import com.example.arbitre.models.Connection;
import com.example.arbitre.models.Joueur;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class ChoixJoueurs extends Activity {
	private int matchId;

	private List<Joueur> lesJoueurs = new ArrayList<Joueur>();
	private ClientSocket cs = ClientSocket.GetInstance();
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_joueurs);
		final Spinner j1 = (Spinner) findViewById(R.id.spinnerJ1);
		final Spinner j2 = (Spinner) findViewById(R.id.spinnerJ2);
		ArrayList<Spinner> spinners = new ArrayList<Spinner>();
		spinners.add(j1);
		spinners.add(j2);
		
		// Recuperation liste joueurs serveur
		intent = getIntent();
		String json = intent.getStringExtra("json");
		
		// suppression du \n
		//json = json.substring(0, json.length()-2);
		
        // Charger les spinners avec le json precedement recupere
		loadSpinner(spinners, json);
	}
	
	private void loadSpinner(ArrayList<Spinner> spinners, String json) {
		for(Spinner spinner : spinners){
	    	List<String> spinnerArray =  new ArrayList<String>();
	    	
	    	try {
				lesJoueurs = Joueur.listFromJSON(json);
			} catch (JSONException e) {
				Log.e("JSON Err","JSON Error: "+ e);
				e.printStackTrace();
			} catch (ParseException e) {
				Log.e("Parse Err", "Parse Error: " + e);
				e.printStackTrace();
			}
	    	
	    	for(Joueur unJoueur : lesJoueurs){
	    		spinnerArray.add(unJoueur.getNom() + " - " + unJoueur.getPrenom());
	    	}

	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	            this, android.R.layout.simple_spinner_item, spinnerArray);

	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        Spinner sItems = (Spinner) findViewById(spinner.getId());
	        sItems.setAdapter(adapter);
		}
		
	}

	public void btnPrecedentOnClick(View v){
		this.finish();	
	}
	
	public void btnJouerOnClick(View v){
		// Recuperation des infos de la vue
		Intent intent = getIntent();
		
		String courtNum = intent.getStringExtra("courtName");

		final Spinner j1 = (Spinner) findViewById(R.id.spinnerJ1);
		final Spinner j2 = (Spinner) findViewById(R.id.spinnerJ2);
		RadioButton rbtnJ1 = (RadioButton) findViewById(R.id.rbtnJ1);
	
		// 
		Joueur joueur1 = new Joueur();
		Joueur joueur2 = new Joueur();
		String servingPlayer = "";
		
		for(Joueur unJoueur : lesJoueurs){
			if((unJoueur.getNom()+" - " + unJoueur.getPrenom()).equals(j1.getSelectedItem().toString())){
				joueur1 = unJoueur;
			}
			
			if((unJoueur.getNom()+" - " + unJoueur.getPrenom()).equals(j2.getSelectedItem().toString())){
				joueur2 = unJoueur;
			}
		}
		
		
		if(rbtnJ1.isChecked()){
			servingPlayer = Integer.toString(joueur1.getId());
		}
		else{
			servingPlayer = Integer.toString(joueur2.getId());
		}		
		
		// Creation du json
		String json = "{\"cmd\":\"creatematch\",\"j1\":\""+joueur1.getId()+"\",\"j2\":\""+joueur2.getId()+"\",\"court\":\""+courtNum+"\",\"service\":\""+servingPlayer+"\"}";
		System.out.println("JSON A ENVOYER :::::: " + json);
		matchId = Integer.parseInt(cs.Execute(json));
		// mock
		//json = "{\"cmd\":\"creatematch\",\"j1\":\""+1+"\",\"j2\":\""+2+"\",\"court\":\""+1+"\",\"service\":\""+1+"\"}";
		//matchId = 0;

        System.out.println("LE MESSAGE A BIEN ETE ENVOYE #########");
		
		Log.i("MatchID", Integer.toString(matchId));
		
		// Vue suivante
		Intent intent2 = new Intent(this, Jeu.class);
        // On envoie les données au layout
		ArrayList<Joueur> players = new ArrayList<Joueur>();
		players.add(joueur1);
		players.add(joueur2);
		
        intent2.putExtra("players", players);
        intent2.putExtra("matchID", matchId);

        System.out.println("VUE SUIVANTE EN COURS DE CREATION, STARTING ACTIVITY #########");

        // On "affiche"
        this.startActivity(intent2);
		
	}
}
