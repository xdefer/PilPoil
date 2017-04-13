package epsi.utilisateur.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Match implements Serializable {

    private String Court;
    private Joueur Joueur1;
    private Joueur Joueur2;
    private String ID;

    public Match(){
        Court = "";
        Joueur1 = new Joueur();
        Joueur2 = new Joueur();
        ID = "";
    }

    public Match(String json) throws JSONException {

        JSONObject jsonObj = new JSONObject(json);

        Joueur1 = new Joueur();
        Joueur1.setId(jsonObj.getInt("idJoueur1"));
        Joueur1.setNom(jsonObj.getString("nomJoueur1"));
        Joueur1.setPrenom(jsonObj.getString("prenomJoueur1"));
        Joueur1.setNationalite(jsonObj.getString("nationalite1"));

        Joueur2 = new Joueur();
        Joueur2.setId(jsonObj.getInt("idJoueur2"));
        Joueur2.setNom(jsonObj.getString("nomJoueur2"));
        Joueur2.setPrenom(jsonObj.getString("prenomJoueur2"));
        Joueur2.setNationalite(jsonObj.getString("nationalite2"));

        setCourt(jsonObj.getString("libelleCourt"));
        setID(jsonObj.getString("idMatch"));
    }

    public static ArrayList<Match> JsonToList(String json) throws JSONException {
        ArrayList<Match> listeMatchs = new ArrayList<>();
        JSONObject jobj = new JSONObject(json);
        JSONArray jsonArray = jobj.getJSONArray("matchs");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject eventJSON = jsonArray.getJSONObject(i);
            Match match = new Match(eventJSON.toString());
            listeMatchs.add(match);
        }

        return listeMatchs;
    }

    public void setCourt(String _court){ Court = _court; }
    public void setJoueur1(Joueur _j1){ Joueur1 = _j1; }
    public void setJoueur2(Joueur _j2){ Joueur2 = _j2; }
    public void setID(String _id){ ID = _id; }

    public String getCourt(){ return Court; }
    public Joueur getJoueur1(){ return Joueur1; }
    public Joueur getJoueur2(){ return Joueur2; }
    public String getID(){ return ID; }

}
