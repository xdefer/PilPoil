package epsi.utilisateur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class NetworkTask extends AsyncTask<String, byte[], Boolean> {
    public enum Error {TIMEOUT}
    private  Socket nsocket; //Network Socket
    private BufferedReader nis; //Network Input Stream
    private OutputStream nos; //Network Output Stream
    private String message;
    private String errorMessage;
    private int errorCode;
    private MatchScores activity;

    private String serverIP = "151.80.159.27";
    private int serverPort = 6789;

    public NetworkTask(MatchScores a){
        this.activity = a;
    }

    @Override
    protected void onPreExecute() {
        Log.i("AsyncTask", "onPreExecute");
    }

    @Override
    protected Boolean doInBackground(String... params) { //This runs on a different thread
        boolean result = false;
        try {

            if(!nsocket.isConnected()){
                Log.i("AsyncTask", "doInBackground: Creating socket");
                SocketAddress sockaddr = new InetSocketAddress(serverIP, serverPort);
                nsocket.connect(sockaddr, 5000); //10 second connection timeout
            }

            nis = new BufferedReader(new InputStreamReader(nsocket.getInputStream())); // recuperation datas
            nos = nsocket.getOutputStream(); // ecrire

            Log.i("AsyncTask", "doInBackground: Socket is created, streams assigned");


            // ecriture
            if(params[0] == "Write") {
                PrintStream out = new PrintStream(nos, true);
                out.write(params[1].getBytes());
            }

            Log.i("AsyncTask", "doInBackground: Data send, Waiting for response...");
            int retries = 5;
            if(params[0] == "Read"){
                retries = -1;
            }
            // lecture
            String line = ReadLine(nis, retries);

            Log.i("AsyncTask", "Received data : " + line);

            setMessage(line);

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("AsyncTask", "doInBackground: IOException");
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("AsyncTask", "doInBackground: Exception");
            result = true;
        } finally {
           /*try {
                nis.close();
                nos.close();
               // nsocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            Log.i("AsyncTask", "doInBackground: Finished");
        }
        return result;
    }

    private String ReadLine(BufferedReader nis, int retries) throws IOException {
        boolean done = false;
        String line = "";

        int count = 0;
        do{

            if(nis.ready()) {
                count = 0;
                int c = nis.read();

                if (c == -1) {
                    return line;
                }
                line += (char) c;

                if ((line.length() >= 2 && line.substring(line.length() - 2).equals("\\n")) || (line.charAt(line.length() - 1) == '\n')) {
                    Log.i("AsyncTask", "End line character received, stopping reception");
                    line = line.substring(0, line.length() - 2);
                    System.out.println("Line received : " + line);
                    return line;
                }
            } else if( retries != -1) {
                count++;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if(count > retries){
                        done = true;
                        setError(Error.TIMEOUT);
                    }
                }
            }
        }while(!done);

        return line;
    }

    public void SendDataToNetwork(String cmd) { //You run this from the main thread.
        try {
            if (nsocket.isConnected()) {
                Log.i("AsyncTask", "SendDataToNetwork: Writing received message to socket");
                nos.write(cmd.getBytes());
            } else {
                Log.i("AsyncTask", "SendDataToNetwork: Cannot send message. Socket is closed");
            }
        } catch (Exception e) {
            Log.i("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception");
        }
    }

    @Override
    protected void onProgressUpdate(byte[]... values) {
        if (values.length > 0) {
            try {
                Log.i("AsyncTask", "onProgressUpdate: " + values[0].length + " bytes received.");
                Log.i("AsyncTask", "onProgressUpdate: recieved: " + new String(values[0], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCancelled() {
        Log.i("AsyncTask", "Cancelled.");
    }
    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Log.i("AsyncTask", "onPostExecute: Completed with an Error.");
        } else {
            Log.i("AsyncTask", "onPostExecute: Completed.");
        }

        if(this.activity != null){
            this.activity.DisplayScores(message);
            this.activity.ListenForChanges();
        }
    }

    public void Close(){
        try {
            nsocket.close();
        } catch (IOException e) {
            Log.i("AsyncTask", "Close socket: Error.");
            e.printStackTrace();
        }
    }

    public void setError(Error error_type){
        switch(error_type){
            case TIMEOUT:
                errorCode = 408;
                errorMessage = "Impossible de se connecter au serveur, veuillez réessayer plus tard";
            default:
                errorCode = 1;
                errorMessage = "Une erreur inconnue est survenue, veuillez réessayer plus tard";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Socket getNsocket() {
        return nsocket;
    }

    public void setNsocket(Socket nsocket) {
        this.nsocket = nsocket;
    }
}