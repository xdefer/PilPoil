package epsi.utilisateur;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;

public class Connexion {

    /*
    Singleton utilisé pour avoir accés a la connexion avec le serveur
    la classe s'assure de n'avoir qu'un seul socket de connecté a tout moment
     */

    private static Connexion INSTANCE = new Connexion();
    private static Socket socket = null;
    private static boolean done = true;
    private String async_result = null;
    private static boolean auth = false;

    private Connexion() {
        Log.i("Connexion", "Entering connexion constructor");
        if(socket == null){
            socket = new Socket();
            SendHandshake();
        }
    }

    public static Connexion getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Connexion();
        }
        return INSTANCE;
    }

    // Envoie la commande d'authentification au serveur afin de prévenir de la
    // présence d'un utilisateur
    public void SendHandshake() {
        if(auth){
            Log.i("Connexion", "Already authenticated, skipping handshake");
            return;
        }
        Log.i("Connexion", "Sending CLIENT handshake to server");
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "handshake");
            json.put("type", "client");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ExecuteTask(json.toString());
        auth = true;
    }

    // retourne la liste de tout les matchs
    public String GetMatchList() {
        Log.i("Connexion", "Sending getMatchList to server");
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "listMatches");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ExecuteTask(json.toString());
    }

    // Fait suivre le match demandé. Le serveur enverra automatiquement tout les changements
    // dans le score aprés cette commande
    public String FollowMatchWithID(String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("cmd", "follow");
            json.put("match", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ExecuteTask(json.toString());
    }

    // Execute une tache synchrone qui renvoie une chaine de caractére
    private String ExecuteTask(String msg){
        String result = null;
        if(socket == null){
            socket = new Socket();
        }

        // On crée une tache asynchrone
        NetworkTask nt = new NetworkTask(null);
        nt.setNsocket(socket);
        nt.execute("Write", msg);

        // Et on attends un résultat.
        while( result == null){
            result = nt.getMessage();
        }
        System.out.println("Received data : " + result);
        return result;
    }

    // Execute une tache asynchrone qui n'est utilisée que pour l'affichage du score en
    // temps reel
    // Si les pointeurs sur fonctions existaient en java, les choses auraient étées beaucoup plus
    // simple et les requetes synchrones complétement inutiles.
    private void ExecuteAsyncTask(MatchScores a){
        NetworkTask nt = new NetworkTask(a);
        nt.setNsocket(socket);
        System.out.println("Executing in background ");
        nt.execute("Read");
    }

    // appelée depuis l'activité des scores uniquement. Lance la tache d'écoute
    public void ListenForChangesInScore(MatchScores a){
        ExecuteAsyncTask(a);
    }

    // Reset la connexion. A utiliser en cas de probléme.
    public static void Reset(){
        try {
            if(socket != null){
                socket.close();
            }
            INSTANCE = null;
            socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



