package com.example.arbitre;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.example.arbitre.models.ClientSocket;
import com.example.arbitre.models.Fichier;
import com.example.arbitre.models.Joueur;
import com.example.arbitre.models.Match;
import com.example.arbitre.models.Court;
import com.example.arbitre.models.NetworkTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {
	List<Court> lesCourts = new ArrayList<Court>();
	private NetworkTask networkTask = new NetworkTask();
	private ClientSocket cs = ClientSocket.GetInstance();
	private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);            
        
        json = "{\"cmd\":\"handshake\",\"type\":\"arbitre\"}";

        System.out.println(" JSON DE MAIN ACTIVITY " + json);
        
        // envoi du type d application au serveur (arbitre)
        // cs.Execute(json);
        
        
        // lecture du fichier de donnees        
        // Pas de prise en charge de différent match en pause
        int matchIdFile = -1;
        String raisonPause = "";
		try {
			// on recupere l id / raison du match du fichier
			matchIdFile = Match.fromJSON(Fichier.lireFichier("arbitreDatas.txt",this)).getId();
			raisonPause = Match.fromJSON(Fichier.lireFichier("arbitreDatas.txt",this)).getRaisonPause();
			Log.i("Match File", "Id: " + Integer.toString(matchIdFile) + "raisonPause: " + raisonPause);
		} catch (JSONException e) {}
        
		if(matchIdFile != -1) // s il y a un match dans le fichier
		{
			// Affichage popup match existant
			AlertDialog dialog = createDialogBuilder(matchIdFile, raisonPause, this);
			dialog.show();
		}
		else
		{
			// Recuperation classique des courts / joueurs
	        recuperationCourtsJoueurs();
		}
	}

    /**
     * Creation du dialog dans le cas ou il y a un match dans le fichier
     * @param matchIdFile ID du match
     * @param raisonPause la raison de la mise en pause
     * @return le dialog a afficher
     */
	private AlertDialog createDialogBuilder(final int matchIdFile, String raisonPause, final Context c) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		// le titre ainsi que le message du popup
		builder.setTitle(R.string.popUpMatchExistantTitle);
		String message = getResources().getString(R.string.popUpMatchExistantMessageP1)+ " " + Integer.toString(matchIdFile) + " " + getResources().getString(R.string.popUpMatchExistantMessageP2) + " " +raisonPause + "\n"+ getResources().getString(R.string.popUpMatchExistantMessageP3);
		builder.setMessage(message);
		
		// les boutons
		builder.setPositiveButton(R.string.popUpMatchExistantOK, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // Appui sur le bouton OUI
		        	   
		        	   // Suppression du match du fichier
		        	   String json = "{\"matchId\":\"-1\","+"\"raison\":\"\"}";
		        	   Fichier.enregistrerDonnees(json, c);
						
		        	   // Recuperation des infos du match (joueurs / court)
		        	   json = "{\"cmd\":\"matchInfo\", \"idMatch\":\"" + matchIdFile + "\"}";
		        	   json = cs.Execute(json);
		        	   
		        	   // mock
		        	   //json = "{\"joueurs\":[{\"id\":\"3\",\"civilite\":\"F\",\"nom\":\"WILLIAMS\",\"prenom\":\"Serena\",\"nationalite\":\"USA\"},{\"id\":\"3\",\"civilite\":\"F\",\"nom\":\"WILLIAMS\",\"prenom\":\"Serena\",\"nationalite\":\"USA\"}]}";
		        	   
		        	   ArrayList<Joueur> players = new ArrayList<Joueur>();
		        	   try {
		        		   players = (ArrayList<Joueur>) Joueur.listFromJSON(json);
		        	   } catch (JSONException e) {
		        			   e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}
		        	   
		        	   // Redirection sur la page des points    
		        	   Intent intent = new Intent(c, Jeu.class);
		               intent.putExtra("players", players);
		               intent.putExtra("matchID", matchIdFile);
		               c.startActivity(intent);
		           }
		       });
		builder.setNegativeButton(R.string.popUpMatchExistantKO, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // Appui sur le bouton NON
		        	   
		        	   // fermeture du dialog
		        	   dialog.dismiss();
		        	   // Recuperation classique des courts / joueurs	
		        	   recuperationCourtsJoueurs();
		           }
		       });

		// Create the AlertDialog et affichage
		AlertDialog dialog = builder.create();
		return dialog;
	}

    /**
     * Récupère le JSON contenant la liste des courts ainsi que les joueurs disponible
     * Charge le spinner des courts
     */
	private void recuperationCourtsJoueurs() {
        json = cs.Execute(json);
        Log.i("JSON", "json1: " + json);
        
        // mock
        //json = "{\"courts\":[{\"num\":\"1\",\"libelle\":\"Court Philippe-Chatrier\"},{\"num\":\"2\",\"libelle\":\"Court Suzanne-Lenglen\"}],\"joueurs\":[{\"id\":\"3\",\"civilite\":\"F\",\"nom\":\"WILLIAMS\",\"prenom\":\"Serena\",\"nationalite\":\"USA\"}]}\\n";
        
        
        // Charger le spinner avec le json precedement recupere
        loadSpinner(json);
	}
    
    public void onBtnSuivantClick(View v){
    	// garder en memoire le court choisis

    	Spinner sItems = (Spinner) findViewById(R.id.spinnerCourt);
    	String courtNum = "";
    	
    	for(Court unCourt : lesCourts){
    		if(unCourt.getLibelle().equals(sItems.getSelectedItem().toString()))
    			courtNum = Integer.toString(unCourt.getNum());
    	}

        System.out.println("Court Num ######################################### : " + courtNum);
    	
    	Intent intent = new Intent(this, ChoixJoueurs.class);
        // On envoie les données au layout
        intent.putExtra("courtName", courtNum);
        intent.putExtra("json", json);
        // On "affiche"
        this.startActivity(intent);
    }
    
    private void loadSpinner(String json){
    	List<String> spinnerArray =  new ArrayList<String>();
    	
    	try {
			lesCourts = Court.listFromJSON(json);
		} catch (JSONException e) {
			Log.e("JSON Err","JSON Error: "+ e);
			e.printStackTrace();
		} catch (ParseException e) {
			Log.e("Parse Err", "Parse Error: " + e);
			e.printStackTrace();
		}
    	
    	for(Court unCourt : lesCourts){
    		spinnerArray.add(unCourt.getLibelle());
    	}

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinnerCourt);
        sItems.setAdapter(adapter);
    }
}
