package com.project.app.pilpoil.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.UserService;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GlobalState;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilActivity extends BaseActivity {

    private SeekBar seekBarDistanceAlert;
    private TextView txtViewSettingsFirstname, txtViewSettingsPhone, txtViewSettingsDistanceAlert, txtViewSettingsEditDistanceAlert;
    private EditText editTxtSettingsFirstname, editTxtSettingsPhone;
    //private LinearLayout btnSave, btnPopupPassword;
    private ScrollView settingsMainContentReadMode, settingsMainContentEditMode;
    private Boolean isEditMode = false;
    private Menu menu;

    /* ALERT DIALOG VARIABLES */
    private View positiveAction;
    private EditText passwordInput;
    private EditText passwordConfirmInput;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    CoordinatorLayout rootLayout;
    FloatingActionButton fabBtnEdit, fabBtnPassword;

    EditText input_firstname, input_phone;
    Button btnSave;

    DiscreteSeekBar discreteSeekBar;

    private TextView tvInfoDistance, tvTitleDistance;

    GlobalState gs;
    private UserPojo currentUser = null;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private UserService userService;

    private MaterialDialog currentDialogWait;

    private int stepSize = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        try {
            boolean updateProfil = getIntent().getExtras().getBoolean("updateProfil");
            if(updateProfil)
                //Toast.makeText(getBaseContext(), "Merci de votre signalement", Toast.LENGTH_LONG).show();
                Snackbar.make(ProfilActivity.this.findViewById(android.R.id.content).getRootView(), "Mis à jour des informations réussie", Snackbar.LENGTH_SHORT).show();
        } catch(Exception e){
        }

        //initElements();
        //initForms();

        //initToolbar();
        initInstances();
        initForms();
    }

    private void initInstances() {

        ImageView imgToolBar = (ImageView) findViewById(R.id.imgToolbar);
        imgToolBar.setImageResource(R.drawable.user_icon);

        userService = retrofit.create(UserService.class);

        gs = (GlobalState) getApplication();
        currentUser = gs.getCurrentUser();

        input_firstname = (EditText) findViewById(R.id.input_firstname);
        input_phone = (EditText) findViewById(R.id.input_telephone);
        btnSave = (Button) findViewById(R.id.btn_save);
        discreteSeekBar = (DiscreteSeekBar) findViewById(R.id.discreteSeekBar);

        //tvInfoDistance = (TextView) findViewById(R.id.tv_distance);
        tvTitleDistance = (TextView) findViewById(R.id.tv_title_distance);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(ProfilActivity.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        btnSave.setVisibility(View.GONE);
        discreteSeekBar.setVisibility(View.GONE);

        discreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                tvTitleDistance.setText(getString(R.string.form_distance) + " (" + Integer.toString(value) + " km)");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Snackbar.make(v, "Enregistrement des informations", Snackbar.LENGTH_LONG).show();
                if (dataRequiredForForm()) {
                    currentDialogWait = popupWait(false);
                    Call<UserPojo> call = userService.updateUser(getUserFromForm(), gs.getAuthTokenPojo().getToken()); //currentUser.getLogin()
                    call.enqueue(new Callback<UserPojo>() {
                        @Override
                        public void onResponse(Call<UserPojo> call, Response<UserPojo> response) {
                            currentDialogWait.cancel();
                            if (response.code() == 200) {
                                gs.setCurrentUser(response.body());
                                //input_firstname.setEnabled(false);
                                //input_phone.setEnabled(false);
                                //Snackbar.make(v, "Mis à jour des informations réussie", Snackbar.LENGTH_LONG).show();
                                Intent i = new Intent(ProfilActivity.this, ProfilActivity.class);
                                i.putExtra("updateProfil", true);
                                startActivity(i);
                                finish();
                            } else {
                                Snackbar.make(v, "Modification du compte impossible !", Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserPojo> call, Throwable t) {
                            currentDialogWait.cancel();
                            Snackbar.make(v, "Modification du compte impossible !", Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Snackbar.make(v, "Vous devez renseigner votre prénom", Snackbar.LENGTH_LONG).show();
                }
            }
        });



        //rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);

        fabBtnEdit = (FloatingActionButton) findViewById(R.id.fabBtnEdit);
        fabBtnPassword = (FloatingActionButton) findViewById(R.id.fabBtnPassword);

        fabBtnPassword.setVisibility(View.GONE);

        fabBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitleDistance.setText(getString(R.string.form_distance) + " (" + currentUser.getDistance() + " km)");
                //tvInfoDistance.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                discreteSeekBar.setVisibility(View.VISIBLE);
                fabBtnPassword.setVisibility(View.VISIBLE);
                input_firstname.setEnabled(true);
                input_phone.setEnabled(true);
                if (input_phone.getText().toString().equals(getString(R.string.notIndicNoWhiteSpace))){
                    input_phone.setText("");
                }
                if (input_firstname.getText().toString().equals(getString(R.string.notIndicNoWhiteSpace))){
                    input_firstname.setText("");
                }
                //Snackbar.make(v, "TEST", Snackbar.LENGTH_SHORT).show();
            }
        });

        fabBtnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupPassword();
            }
        });
    }

    private void initForms() {
        // Required
        if(null != currentUser.getFirstName()){
            input_firstname.setText(currentUser.getFirstName());
        } else { input_firstname.setText(getString(R.string.notIndicNoWhiteSpace)); }

        // Required
        if(null != currentUser.getPhone()){
            input_phone.setText(currentUser.getPhone());
        } else { input_phone.setText(getString(R.string.notIndicNoWhiteSpace)); }

        // Required
        if(null != currentUser.getDistance()){
            discreteSeekBar.setProgress(currentUser.getDistance());
            tvTitleDistance.setText(getString(R.string.form_distance) + " (" + currentUser.getDistance() + " km)");
            //tvInfoDistance.setText(currentUser.getDistance().toString() + " km");
        }
    }

    /*private void initElements() {
        userService = retrofit.create(UserService.class);

        gs = (GlobalState) getApplication();
        currentUser = gs.getCurrentUser();

        settingsMainContentReadMode = (ScrollView) findViewById(R.id.settingsMainContentReadMode);
        settingsMainContentEditMode = (ScrollView) findViewById(R.id.settingsMainContentEditMode);

        txtViewSettingsFirstname = (TextView) findViewById(R.id.txtViewSettingsFirstname);
        txtViewSettingsPhone = (TextView) findViewById(R.id.txtViewSettingsPhone);
        txtViewSettingsDistanceAlert = (TextView) findViewById(R.id.txtViewSettingsReadDistanceAlert);
        txtViewSettingsEditDistanceAlert = (TextView) findViewById(R.id.txtViewSettingsEditDistanceAlert);

        editTxtSettingsFirstname = (EditText) findViewById(R.id.editTxtSettingsFirstname);
        editTxtSettingsPhone = (EditText) findViewById(R.id.editTxtSettingsPhone);

        seekBarDistanceAlert = (SeekBar) findViewById(R.id.seekBarEditDistanceAlert);

        //btnSave = (LinearLayout) findViewById(R.id.btnSave);
        //btnPopupPassword = (LinearLayout) findViewById(R.id.btnPopupPassword);
    }*/










    private UserPojo getUserFromForm(){
        currentUser.setDistance(discreteSeekBar.getProgress());
        currentUser.setFirstName(input_firstname.getText().toString());
        currentUser.setLastModifiedBy(currentUser.getLogin());
        currentUser.setPhone(input_phone.getText().toString());
        return currentUser;
    }

    private Boolean dataRequiredForForm(){
        if(input_firstname.getText().toString().equals(""))
            return false;
        return true;
    }
    private void logout(){
        gs.setCurrentUser(null);
        gs.setCurrentAuthTokenPojo(null);
        Intent i = new Intent(ProfilActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showPopupPassword() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Changement du mot de passe")
                .customView(R.layout.dialog_custom_change_password, true)
                .positiveText("OK")
                .negativeText("Annuler")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (!passwordInput.equals(passwordConfirmInput) && !passwordInput.equals("") && !passwordConfirmInput.equals("")) {
                            //Toast.makeText(getBaseContext(), "Les deux champs doivent avoir la même valeur et ne peuvent être vides !", Toast.LENGTH_LONG).show();
                            Snackbar.make(ProfilActivity.this.findViewById(android.R.id.content).getRootView(), "Les deux champs doivent avoir la même valeur et ne peuvent être vides !", Snackbar.LENGTH_SHORT).show();
                        } else {
                            currentDialogWait = popupWait(false);
                            //showToast("Password: " + passwordInput.getText().toString());
                            Call<ResponseBody> call = userService.updatePassword(passwordInput.toString(), gs.getAuthTokenPojo().getToken());
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    currentDialogWait.cancel();
                                    if (response.code() == 200) {
                                        //dialog.cancel();
                                        //Toast.makeText(getBaseContext(), "Nouveau mot de passe enregistré", Toast.LENGTH_LONG).show();
                                        Snackbar.make(ProfilActivity.this.findViewById(android.R.id.content).getRootView(), "Nouveau mot de passe enregistré", Snackbar.LENGTH_SHORT).show();
                                        logout();
                                    } else {
                                        //Toast.makeText(getBaseContext(), "Impossible de modifier le mot de passe !", Toast.LENGTH_LONG).show();
                                        Snackbar.make(ProfilActivity.this.findViewById(android.R.id.content).getRootView(), "Impossible de modifier le mot de passe !", Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    currentDialogWait.cancel();
                                    //Toast.makeText(getBaseContext(), "FAIL - Impossible de modifier le mot de passe !", Toast.LENGTH_LONG).show();
                                    Snackbar.make(ProfilActivity.this.findViewById(android.R.id.content).getRootView(), "Impossible de modifier le mot de passe !", Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
        passwordConfirmInput = (EditText) dialog.getCustomView().findViewById(R.id.confirm_password);
        passwordInput.addTextChangedListener(new TextWatcher() {
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


        // Toggling the show password CheckBox will mask or unmask the password input EditText
        CheckBox checkbox = (CheckBox) dialog.getCustomView().findViewById(R.id.showPassword);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwordInput.setInputType(!isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                passwordInput.setTransformationMethod(!isChecked ? PasswordTransformationMethod.getInstance() : null);
                passwordConfirmInput.setInputType(!isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                passwordConfirmInput.setTransformationMethod(!isChecked ? PasswordTransformationMethod.getInstance() : null);
            }
        });

        //int widgetColor = ThemeSingleton.get().widgetColor;

        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }

    private MaterialDialog popupWait(boolean horizontal) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .show();

        dialog.setCancelable(false);
        return dialog;
    }
}
