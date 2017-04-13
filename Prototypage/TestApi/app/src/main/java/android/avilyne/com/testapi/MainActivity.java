package android.avilyne.com.testapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String API_URL = "/api/typeAnnonces/";
    private static final String API_AUTH_URL = "/api/authentication";
    private static final String API_LOGOUT = "/api/logout";
    private static final String SITE_URL = "http://192.168.46.2:8080";
    private static final String POST = "POST";
    private static final String POST_AUTH = "POST_AUTH";
    private static final String LOGOUT = "LOGOUT";
    private static final String GET = "GET";
    private DefaultHttpClient httpclient = new DefaultHttpClient();
    EditText etLibelle, etLogin, etMdp;
    Button btnPost, btnGet, btnClear, btnClearAuth, btnAuth, btnLogOut;
    TextView resGet, resAuth;

    @Override
    /* Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Init */
        etLibelle = (EditText) findViewById(R.id.libelle);
        btnPost = (Button) findViewById(R.id.bn_post);
        btnGet = (Button) findViewById(R.id.bn_get);
        btnClear = (Button) findViewById(R.id.bn_clear);
        resGet = (TextView) findViewById(R.id.res_get);
        resAuth = (TextView) findViewById(R.id.res_auth);
        etLogin = (EditText) findViewById(R.id.login);
        etMdp = (EditText) findViewById(R.id.mdp);
        btnClearAuth = (Button) findViewById(R.id.bn_clearAuth);
        btnAuth = (Button) findViewById(R.id.bn_auth);
        btnLogOut = (Button) findViewById(R.id.bn_logout);

        /* Add listener */
        btnPost.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnGet.setOnClickListener(this);
        btnAuth.setOnClickListener(this);
        btnClearAuth.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

        /* Hide Logout Btn */
        btnLogOut.setVisibility(View.INVISIBLE);

    }

    /* Get The Token CSRF */
    public String getCSRFVal(){

        try {

            /* 1 : Init Methode */
            HttpGet httpGet = new HttpGet(SITE_URL);

            /* 2 : Execute Request And Get Response */
            HttpResponse httpGetResponse = httpclient.execute(httpGet);
            if(httpGetResponse.getStatusLine().getStatusCode() != 200){
                return "";
            }

            /* 3 : Get Cookies From Response Header */
            List<Cookie> cookies = httpclient.getCookieStore().getCookies();
            if(cookies != null)
            {
                for(Cookie cookie : cookies)
                {
                    if(cookie.getName().equals("CSRF-TOKEN")){
                        return cookie.getValue();
                    }
                }
            }

        } catch (Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return "";
    }

    /* Method GET : Have to add catch exception */
    public String getTypeAnnonce(String url, String dataToSend) {

        /* Init */
        String result = "";

        try {

            /* 1 : Init Methode */
            HttpGet httpGet;

            /* 2 : GET ALL Or Get BY ID */
            if(dataToSend.equals("")){
                httpGet = new HttpGet(url+API_URL);
            }else{
                httpGet = new HttpGet(url+API_URL+dataToSend);
            }

            /* 3 : Execute Request And Get Response */
            HttpResponse httpResponse = httpclient.execute(httpGet);
            InputStream inputStream = httpResponse.getEntity().getContent();
            result = ((inputStream != null) ? convertInputStreamToString(inputStream) : "Did not work!");

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    /* Methode Post : Have to add catch exception */
    public String postTypeAnnonce(String url, String dataToSend) {

        String result = "";
        String CSRFVal = "";

        try {

            /* 1 : Get a new Token CSRF */
            CSRFVal = getCSRFVal();
            if(CSRFVal.equals("")){
                return "Did not work : No CSRFToken Found";
            }

            /* 2 : Create the POST Method With the CSRF Token */
            HttpPost httpPost = new HttpPost(url+API_URL);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.addHeader("X-CSRF-TOKEN", CSRFVal);

            /* 3 : Add Data to the Request */
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("libelle", dataToSend);
            String json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);

            /* 4 : Execute request And Get Response */
            HttpResponse httpResponse = httpclient.execute(httpPost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            result = ((inputStream != null) ? convertInputStreamToString(inputStream) : "Did not work!");

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    /* Methode Post : Have to add catch exception */
    public String postAuth(String url, String login, String mdp) {

        String result = "";
        String CSRFVal = "";

        try {

            /* 1 : Get a new Token CSRF */
            CSRFVal = getCSRFVal();
            if(CSRFVal.equals("")){
                return "Did not work : No CSRFToken Found";
            }

            /* 2 : Create the POST Method With the CSRF Token */
            HttpPost httpPost = new HttpPost(url+API_AUTH_URL);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.addHeader("X-CSRF-TOKEN", CSRFVal);

            /* 3 : Add Data to the Request Thanks To UrlEncoded */
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("j_username", login));
            nameValuePairs.add(new BasicNameValuePair("j_password", mdp));
            //nameValuePairs.add(new BasicNameValuePair("remember-me", "yes"));
            nameValuePairs.add(new BasicNameValuePair("submit", "Login"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            /* 4 : Execute request And Get Response */
            HttpResponse httpResponse = httpclient.execute(httpPost);

            /* 5 : Verify Response Code */
            if(httpResponse.getStatusLine().getStatusCode() != 200){
                return "Did not work : " + httpResponse.getStatusLine().getStatusCode() + " Connexion refusée";
            }

            /* 6 : Get Response */
            InputStream inputStream = httpResponse.getEntity().getContent();
            result = ((inputStream != null) ? convertInputStreamToString(inputStream) : "Did not work!");
            if(result.equals("")) {
                result = "Connecté !";
                btnAuth.setVisibility(View.INVISIBLE);
                btnLogOut.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    /* Method GET : Have to add catch exception */
    public String logOut(String url) {

        /* Init */
        String result = "";
        String CSRFVal = "";

        try {

            /* 1 : Get a new Token CSRF */
            CSRFVal = getCSRFVal();
            if(CSRFVal.equals("")){
                return "Did not work : No CSRFToken Found";
            }

            /* 1 : Init Methode */
            HttpPost httpPost  = new HttpPost(url+API_LOGOUT);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.addHeader("X-CSRF-TOKEN", CSRFVal);

            /* 2 : Execute Request And Get Response */
            HttpResponse httpResponse = httpclient.execute(httpPost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            result = ((inputStream != null) ? convertInputStreamToString(inputStream) : "Did not work!");
            if(result.equals("")) {
                result = "Déconnecté !";
                btnAuth.setVisibility(View.VISIBLE);
                btnLogOut.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    @Override
    /* Manage Click Btn */
    public void onClick(View view) {
        switch(view.getId()){
            /* Case POST */
            case R.id.bn_post:
                if(!validate())
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                else {
                    new HttpAsyncTask().execute(SITE_URL, POST, etLibelle.getText().toString());
                }
                break;
            /* Case GET */
            case R.id.bn_get:
                new HttpAsyncTask().execute(SITE_URL, GET, etLibelle.getText().toString());
                break;
            /* Case Clear */
            case R.id.bn_clear:
                etLibelle.setText("");
                resGet.setText("");
                break;
            /* Case ClearAuth */
            case R.id.bn_clearAuth:
                etLogin.setText("");
                etMdp.setText("");
                resAuth.setText("");
                break;
            /* Case Auth */
            case R.id.bn_auth:
                if(!validateAuth())
                    Toast.makeText(getBaseContext(), "Check Form Data Wrong !", Toast.LENGTH_LONG).show();
                else {
                    new HttpAsyncTask().execute(SITE_URL, POST_AUTH, etLogin.getText().toString(), etMdp.getText().toString());
                }
                break;
            /* Case Logout */
            case R.id.bn_logout:
                new HttpAsyncTask().execute(SITE_URL, LOGOUT);
                break;
        }
    }

    /* Check if Form is Valid */
    private boolean validate(){
        if(etLibelle.getText().toString().trim().equals(""))
            return false;
        return true;
    }

    /* Check if AuthForm is Valid */
    private boolean validateAuth(){
        if(etMdp.getText().toString().trim().equals("") || etLogin.getText().toString().trim().equals("") )
            return false;
        return true;
    }

    /* Convert Request Response in String */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    /* Class to Call Request in AsyncTask */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if(params[1].equals(POST)) {
                return postTypeAnnonce(params[0], params[2]);
            } else if (params[1].equals(GET)) {
                return getTypeAnnonce(params[0], params[2]);
            } else if(params[1].equals(POST_AUTH)){
                return postAuth(params[0], params[2], params[3]);
            } else {
                return logOut(params[0]);
            }
        }

        @Override
        /* Display results of AsyncTask */
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Request Send...", Toast.LENGTH_LONG).show();
            resAuth.setText(result);
        }

    }

}
