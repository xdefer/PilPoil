package com.project.app.pilpoil.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.project.app.pilpoil.Activity.FindAndLostActivity;
import com.project.app.pilpoil.Activity.MainActivity;
import com.project.app.pilpoil.Activity.RegistrationActivity;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.UserService;
import com.project.app.pilpoil.Metier.AuthTokenPojo;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GlobalState;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ConnexionFragment extends Fragment {

    private View rootView;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private UserService userService;
    GlobalState gs;
    private Integer currentAnimalTypeId, currentAdTypeId;
    private boolean lost;
    private EditText loginInput, passwordInput, emailInput;
    private Button btnAuth;
    private ImageView closeConnexion;
    private TextView createAccount, forgotPassword;

    private View positiveAction;

    private MaterialDialog currentWaitDialog;

    public ConnexionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_connexion, container, false);

        userService = retrofit.create(UserService.class);

        gs = (GlobalState) getActivity().getApplication();

        try {
            currentAnimalTypeId = getActivity().getIntent().getExtras().getInt("animalTypeId");
            currentAdTypeId = getActivity().getIntent().getExtras().getInt("adTypeId");
            lost = getActivity().getIntent().getExtras().getBoolean("lost");
        } catch (Exception e) {
            Log.w(Constants.LOGK_LATERALMENU, "Erreur Menu");
        }


        btnAuth = (Button) rootView.findViewById(R.id.btn_signin);
        loginInput = (EditText) rootView.findViewById(R.id.input_username);
        passwordInput = (EditText) rootView.findViewById(R.id.input_password);
        closeConnexion = (ImageView) rootView.findViewById(R.id.imgCloseConnexion);
        createAccount = (TextView) rootView.findViewById(R.id.txtViewCreateAccount);
        forgotPassword = (TextView) rootView.findViewById(R.id.txtViewForgotPassword);

        closeConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                currentWaitDialog = popupWait(false);
                Call<AuthTokenPojo> rep = userService.auth(loginInput.getText().toString(), passwordInput.getText().toString());
                rep.enqueue(new Callback<AuthTokenPojo>() {
                    @Override
                    public void onResponse(Call<AuthTokenPojo> call, Response<AuthTokenPojo> response) {
                        if (response.code() == 200) {
                            gs.setCurrentAuthTokenPojo(response.body());
                            setUserFromAccount();
                        } else {
                            //Toast.makeText(getActivity(), "Erreur de connexion !", Toast.LENGTH_LONG).show();
                            currentWaitDialog.cancel();
                            Snackbar.make(v, "Erreur de connexion !", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthTokenPojo> call, Throwable t) {
                        currentWaitDialog.cancel();
                        //Toast.makeText(getActivity(), "Erreur de connexion !", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Erreur de connexion !", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPoputResetPassword();
            }
        });

        return rootView;
    }

    public void setUserFromAccount(){
        UserService userService = retrofit.create(UserService.class);
        Call<UserPojo> rep = userService.getUserByLogin(loginInput.getText().toString(), gs.getAuthTokenPojo().getToken());
        rep.enqueue(new Callback<UserPojo>() {
            @Override
            public void onResponse(Call<UserPojo> call, Response<UserPojo> response) {
                currentWaitDialog.cancel();
                if (response.code() == 200) {
                    gs.setCurrentUser(response.body());
                    Intent i;
                    if (currentAnimalTypeId == null && currentAdTypeId == null){
                        i = new Intent(getActivity(), MainActivity.class);
                        i.putExtra("userLogon", true);
                        //Snackbar.make(getView(), "Vous êtes connecté", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        i = new Intent(getActivity(), FindAndLostActivity.class);
                        i.putExtra("animalTypeId", currentAnimalTypeId);
                        i.putExtra("adTypeId", currentAdTypeId);
                        i.putExtra("lost", lost);
                    }
                    startActivity(i);
                    getActivity().finish();
                } else {
                    //Toast.makeText(getActivity(), "Erreur de connexion !", Toast.LENGTH_LONG).show();
                    Snackbar.make(rootView, "Erreur de connexion !", Snackbar.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserPojo> call, Throwable t) {
                currentWaitDialog.cancel();
                //Toast.makeText(getActivity(), "Erreur de connexion !", Toast.LENGTH_LONG).show();
                Snackbar.make(rootView, "Erreur de connexion !", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void showPoputResetPassword() {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("Réinitialisation du mot de passe")
                .customView(R.layout.dialog_custom_reset_password, true)
                .positiveText("OK")
                .negativeText("Annuler")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (!emailInput.getText().toString().equals("")) {

                            String mail = emailInput.getText().toString();

                            UserService userService = retrofit.create(UserService.class);
                            Call<ResponseBody> rep = userService.resetPassword(mail);
                            rep.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.code() == 200) {
                                        Snackbar.make(rootView, "Email envoyé !", Snackbar.LENGTH_LONG).show();
                                        //Toast.makeText(getActivity().getBaseContext(), "Email envoyé !", Toast.LENGTH_LONG).show();
                                    } else if (response.code() == 400) {
                                        //Toast.makeText(getActivity().getBaseContext(), "Adresse email inconnue", Toast.LENGTH_LONG).show();
                                        Snackbar.make(rootView, "Adresse email inconnue", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        //Toast.makeText(getActivity().getBaseContext(), "Erreur", Toast.LENGTH_LONG).show();
                                        Snackbar.make(rootView, "Erreur", Snackbar.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    //Toast.makeText(getActivity().getBaseContext(), "Erreur", Toast.LENGTH_LONG).show();
                                    Snackbar.make(rootView, "Erreur", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            //Toast.makeText(getActivity().getBaseContext(), "L'adresse email ne peut être vide", Toast.LENGTH_LONG).show();
                            Snackbar.make(rootView, "L'adresse email ne peut être vide", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }).build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        emailInput = (EditText) dialog.getCustomView().findViewById(R.id.email);

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
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
