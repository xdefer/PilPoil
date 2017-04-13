package com.example.arbitre;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.ConsoleHandler;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.arbitre.models.ClientSocket;
import com.example.arbitre.models.Connection;
import com.example.arbitre.models.Fichier;
import com.example.arbitre.models.Joueur;
import com.example.arbitre.models.Match;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Jeu extends Activity {
	private ArrayList<Joueur> players;
	private int matchId;
	private int jeuId;
	private Context c;
	private ClientSocket cs = ClientSocket.GetInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jeu);
		
		Intent intent = getIntent();
		c = this;
		players = new ArrayList<Joueur>();
		players = (ArrayList<Joueur>) intent.getSerializableExtra("players");
		matchId = intent.getIntExtra("matchID",0);
		jeuId = -1;
		
		// Traitement pour afficher le nom des joueurs
		Button btnJoueurPlay1 = (Button) findViewById(R.id.btnJoueurPlay1);
		Button btnJoueurPlay2 = (Button) findViewById(R.id.btnJoueurPlay2);
		MenuItem pause = (MenuItem) findViewById(R.id.action_pause);
		
		btnJoueurPlay1.setText(players.get(0).getNom() + " - " + players.get(0).getPrenom() + "\n +1 point");
		btnJoueurPlay2.setText(players.get(1).getNom() + " - " + players.get(1).getPrenom() + "\n +1 point");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jeu, menu);
		return true;
	}
	
	public void btnJoueurPlay1OnClick(View v){
		AddPoints(0);
	}
	
	public void btnJoueurPlay2OnClick(View v){
		AddPoints(1);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.action_pause:
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.popup_pause);
			dialog.setTitle("Pause");
			
			Button btnValiderPopUpPause = (Button) dialog.findViewById(R.id.btnValiderPopUpPause);
			Button btnAnnulerPopUp = (Button) dialog.findViewById(R.id.btnAnnulerPopUp);
			
			// if button is clicked, close the custom dialog
			btnValiderPopUpPause.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText txtRaison = (EditText) dialog.findViewById(R.id.txtRaison);
					// enregistre matchId fichier
					String json = "{\"matchId\":\""+Integer.toString(matchId)+"\","+"\"raison\":\""+txtRaison.getText().toString()+"\"}";
					Fichier.enregistrerDonnees(json, c);
					dialog.dismiss();		
				}
			});
			
			btnAnnulerPopUp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			
			dialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}	
	}
	
	/**
	 * Envoi au server le json pour mettre a jour le score
	 * @param playerInt permet de savoir dans le tableau quel joueur marque le point
	 */
	private void AddPoints(int playerInt){
		String json = "{\"cmd\":\"addpoint\",\"match\":\""+matchId+"\",\"joueur\":\""+players.get(playerInt).getId()+"\"}";	

        System.out.println("JSON DU ADDPOINT : " + json);
		// mock envoi donnee
		//String json = "{\"cmd\":\"addpoint\",\"match\":\""+1+"\",\"joueur\":\""+1+"\"}";	
		
		// recuperation de l id du jeu (match)
        String result = cs.Execute(json);

        System.out.println("Result : " + result);
        if(!result.equals("fini")){
            return;
        }

        System.out.println("Match FINI !!!");

        Toast.makeText(getApplicationContext(), R.string.popUpWindowsFinMatch, Toast.LENGTH_LONG).show();

        // on retourne a la premiere activity
        cs.Close();
        onDestroy();
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);

		// mock reception
		//jeuId = -1;
		//json = "{\"jeu\":[{\"jeuID\":\""+jeuId+"\",\"matchID\":\""+matchId+"\",\"joueur\":\""+players.get(playerInt).getId()+"\"}]}";
	}
}
