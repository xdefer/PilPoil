package com.project.app.pilpoil.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.project.app.pilpoil.Activity.ConnexionActivity;
import com.project.app.pilpoil.Activity.MainActivity;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.UserService;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegistrationFragment extends Fragment {

    private View rootView;
    private EditText loginInput, phoneInput, emailInput, passwordInput, confirmPasswordInput;
    private Button btnCreate;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private UserService userService;
    private ImageView closeConnexion;

    private MaterialDialog currentDialogWait;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        userService = retrofit.create(UserService.class);

        loginInput = (EditText) rootView.findViewById(R.id.input_registration_firstname);
        phoneInput = (EditText) rootView.findViewById(R.id.input_registration_phone);
        emailInput = (EditText) rootView.findViewById(R.id.input_registration_email);
        passwordInput = (EditText) rootView.findViewById(R.id.input_registration_password);
        confirmPasswordInput = (EditText) rootView.findViewById(R.id.input_registration_password_confirmation);
        btnCreate = (Button) rootView.findViewById(R.id.btn_signup);
        closeConnexion = (ImageView) rootView.findViewById(R.id.imgCloseConnexion);

        closeConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(getActivity(), ConnexionActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDialogWait = popupWait(false);
                if (dataRequiredForForm()) {
                    if (passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
                        if (passwordInput.getText().toString().length() >= 5) {
                            Call<ResponseBody> rep = userService.userRegister(createUser());
                            rep.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    currentDialogWait.cancel();
                                    if (response.code() == 201) {
                                        Intent i = new Intent(getActivity(), MainActivity.class);
                                        i.putExtra("createUser", true);
                                        startActivity(i);
                                        getActivity().finish();
                                    } else if (response.code() == 400) {
                                        //Toast.makeText(getActivity(), "Email déjà utilisé !", Toast.LENGTH_LONG).show();
                                        Snackbar.make(rootView, "Email déjà utilisé !", Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        //Toast.makeText(getActivity(), "Impossible de créer le compte !", Toast.LENGTH_LONG).show();
                                        Snackbar.make(rootView, "Impossible de créer le compte", Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    currentDialogWait.cancel();
                                    //Toast.makeText(getActivity(), "Impossible de créer le compte !", Toast.LENGTH_LONG).show();
                                    Snackbar.make(rootView, "Impossible de créer le compte", Snackbar.LENGTH_SHORT).show();
                                }

                            });
                        } else {
                            //Toast.makeText(getActivity(), "Le mot de passe doit contenir au moins 5 caractères !", Toast.LENGTH_LONG).show();
                            Snackbar.make(rootView, "Le mot de passe doit contenir au moins 5 caractères !", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(getActivity(), "Les deux mots de passe doivent avoir la même valeur !", Toast.LENGTH_LONG).show();
                        Snackbar.make(rootView, "Les deux mots de passe doivent avoir la même valeur !", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(getActivity(), "Certains champs ne sont pas renseignés !", Toast.LENGTH_LONG).show();
                    Snackbar.make(rootView, "Certains champs ne sont pas renseignés !", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private Boolean dataRequiredForForm(){
        if(loginInput.getText().toString().equals("") || emailInput.getText().toString().equals("") ||
                passwordInput.getText().toString().equals("") || confirmPasswordInput.getText().toString().equals(""))
            return false;
        return true;
    }

    private UserPojo createUser(){
        UserPojo user = new UserPojo();

        ArrayList<String> authorities = new ArrayList<String>();
        authorities.add(Constants.ROLE_USER);
        user.setAuthorities(authorities);

        user.setActivated(true);
        user.setDistance(10);

        if(!emailInput.getText().toString().equals("")){
            user.setEmail(emailInput.getText().toString());
            user.setLogin(emailInput.getText().toString());
        }

        if(!loginInput.getText().toString().equals(""))
            user.setFirstName(loginInput.getText().toString());

        //user.setLastName();

        user.setLangKey("fr");

        if (!passwordInput.getText().toString().equals(""))
            user.setPassword(passwordInput.getText().toString());

        if(!phoneInput.getText().toString().equals(""))
            user.setPhone(phoneInput.getText().toString());

        return user;
    }

    private MaterialDialog popupWait(boolean horizontal) {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .show();

        dialog.setCancelable(false);
        return dialog;
    }
}
