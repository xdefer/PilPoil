package epsi.utilisateur;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import epsi.utilisateur.models.Match;


public class MatchSelection extends ListActivity {
    ListView matchList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Connexion.getInstance().SendHandshake();

        matchList = (ListView)findViewById(android.R.id.list);

        final Button refresh_button = (Button) findViewById(R.id.refresh_list);
        refresh_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                generateListView();
            }
        });

        generateListView();
    }

    // récupére un json coté serveur contenant tout les matchs existants et
    // rempli l'arrayadapter en fonction
    public void generateListView(){
        try{
            Log.i("MatchSelection", "Generating the list view");
            String json = Connexion.getInstance().GetMatchList();
            System.out.println("JSON GENERATE LIST VIEW : " + json);

            ArrayList<Match> listeMatch = Match.JsonToList(json);

            MatchAdapter adapter = new MatchAdapter(this, R.layout.match_layout, listeMatch);

            matchList = (ListView)findViewById(android.R.id.list);

            matchList.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected final void onListItemClick(final ListView l, final View v,
                                         final int position, final long id) {
        super.onListItemClick(l, v, position, id);
        Match match = (Match) l.getItemAtPosition(position);
        System.out.println("Match : " + match.toString());
        System.out.println("Match id : " + match.getID());

        String scores = Connexion.getInstance().FollowMatchWithID(match.getID());
        //String scores = "{\"scores\" : {\"joueur1\": {\"id\" : \"1\", \"sets\" : [\"1\", \"2\", \"4\", \"0\", \"0\"], \"service\" : \"oui\"}, \"joueur2\": {\"id\" : \"2\", \"sets\" : [\"2\", \"3\", \"0\", \"0\", \"0\"], \"service\" : \"non\"}}}";
        System.out.println(scores);
        Intent intent = new Intent(this, MatchScores.class);
        intent.putExtra("match", match);
        intent.putExtra("scores", scores);
        startActivity(intent);
    }

}
