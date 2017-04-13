package epsi.utilisateur;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import epsi.utilisateur.models.Match;

public class MatchScores extends Activity {

    Match m;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout_v2);

        Intent intent = getIntent();
        m = (Match)intent.getSerializableExtra("match");
        DisplayMatch(m);
        DisplayScores(intent.getStringExtra("scores"));

        this.ListenForChanges();
    }

    public void btnPrecedentOnClick(View v){
        this.finish();
    }

    public void ListenForChanges(){
        Connexion.getInstance().ListenForChangesInScore(this);
    }

    public void DisplayMatch(Match m){

        TextView j1 = (TextView)findViewById(R.id.joueur1);
        TextView j2 = (TextView)findViewById(R.id.joueur2);
        ImageView flag1 = (ImageView)findViewById(R.id.flag1);
        ImageView flag2 = (ImageView)findViewById(R.id.flag2);

        flag1.setImageResource( getResources().getIdentifier(m.getJoueur1().getNationalite().toLowerCase().trim(), "drawable", getPackageName()));
        flag2.setImageResource( getResources().getIdentifier(m.getJoueur2().getNationalite().toLowerCase().trim(), "drawable", getPackageName()));

        j1.setText(m.getJoueur1().getFullName());
        j2.setText(m.getJoueur2().getFullName());

        TextView end_game = (TextView)findViewById(R.id.end_game);
        end_game.setVisibility(View.INVISIBLE);

    }

    void DisplayScores(String score){

        if(score.equals("")){
            return;
        }

        System.out.println("Scores recus : " + score);

        TextView[] j1_sets = new TextView[5];
        j1_sets[0] = (TextView)findViewById(R.id.j1_set1);
        j1_sets[1] = (TextView)findViewById(R.id.j1_set2);
        j1_sets[2] = (TextView)findViewById(R.id.j1_set3);
        j1_sets[3] = (TextView)findViewById(R.id.j1_set4);
        j1_sets[4] = (TextView)findViewById(R.id.j1_set5);
        TextView j1_jeu = (TextView)findViewById(R.id.j1_jeu);

        TextView[] j2_sets = new TextView[5];
        j2_sets[0] = (TextView)findViewById(R.id.j2_set1);
        j2_sets[1] = (TextView)findViewById(R.id.j2_set2);
        j2_sets[2] = (TextView)findViewById(R.id.j2_set3);
        j2_sets[3] = (TextView)findViewById(R.id.j2_set4);
        j2_sets[4] = (TextView)findViewById(R.id.j2_set5);
        TextView j2_jeu = (TextView)findViewById(R.id.j2_jeu);

        ImageView j1_service = (ImageView)findViewById(R.id.service_j1);
        ImageView j2_service = (ImageView)findViewById(R.id.service_j2);


        try {
            JSONObject json = new JSONObject(score).getJSONObject("score");

            JSONObject j1 = json.getJSONObject("j1");
            JSONObject j2 = json.getJSONObject("j2");

            for(int i = 0; i<j1.getJSONArray("sets").length(); i++){
                j1_sets[i].setText(j1.getJSONArray("sets").getString(i));
                j2_sets[i].setText(j2.getJSONArray("sets").getString(i));
            }

            String j1_pts = j1.get("point").toString();
            String j2_pts = j2.get("point").toString();


            if(j1_pts.equals("41")){
                j1_pts = "AV";
                j2_pts = "";
            }

            if(j2_pts.equals("41")) {
                j2_pts = "AV";
                j1_pts = "";
            }

            if( json.get("fini").toString().equals("1") ){
                j1_pts = "";
                j2_pts = "";

                TextView end_game = (TextView)findViewById(R.id.end_game);
                end_game.setVisibility(View.VISIBLE);
            }

            int service = json.getInt("service");
            if(service == 1){
                j1_service.setVisibility(View.VISIBLE);
                j2_service.setVisibility(View.INVISIBLE);
            }else{
                j1_service.setVisibility(View.INVISIBLE);
                j2_service.setVisibility(View.VISIBLE);
            }

            j1_jeu.setText(j1_pts);
            j2_jeu.setText(j2_pts);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
