package com.project.app.pilpoil.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Dialog.DialogGooglePlayServicesUnavailable;
import com.project.app.pilpoil.Interface.AdService;
import com.project.app.pilpoil.Interface.AnimalService;
import com.project.app.pilpoil.Interface.AnimalTypeService;
import com.project.app.pilpoil.Interface.BreedService;
import com.project.app.pilpoil.Metier.AdPojo;
import com.project.app.pilpoil.Metier.AdTypePojo;
import com.project.app.pilpoil.Metier.Adress;
import com.project.app.pilpoil.Metier.AnimalPojo;
import com.project.app.pilpoil.Metier.AnimalTypePojo;
import com.project.app.pilpoil.Metier.BreedPojo;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GPSUtil;
import com.project.app.pilpoil.Service.GlobalState;
import com.project.app.pilpoil.Service.UploadImageTask;
import com.project.app.pilpoil.Service.Utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.picasso.Picasso;

public class FindAndLostActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = FindAndLostActivity.class.getSimpleName();
    private RadioGroup rgResc;
    private Spinner spinBreed, spinAge, spinGender;
    private LinearLayout linearInfoGeneFind, linearInfoGeneLost, linearInfoUser;
    private ImageView imgCalendar, imgCalendarLost, imgLocalisation, imgLostLocation, imgAddPetPic, imgUpdPetPic, imgPicturePet;;
    private EditText editTxtTatoo, editTxtMail, editTxtPhone, editTxtPrecisionFind, editTxtPrecisionLost, editTxtName, editTxtChip, editTxtColors;
    private TextView txtViewPublish, txtViewFindingDate, txtViewLoosingDate, txtViewLocation, txtViewLostLocation;

    private UserPojo currentUser = null;
    private Adress currentAdress = null;
    private AnimalPojo currentAnimal = null;

    private int currentAnimalTypeId;
    private int currentAdTypeId;
    private Double currentDistanceUser;

    private Utils utils;
    private LocationRequest mLocationRequest;
    private GPSUtil gps;    // GPSTracker class
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;   // latest client in Google Play Services to use functions
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000; // defines a request code to send to Google Play services

    GlobalState gs;

    private String urlImg;
    private String currentPicturePath = null;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private BreedService breedService;
    private AnimalTypeService animalTypeService;
    private AnimalService animalService;
    private AdService adService;
    private MaterialDialog currentDialogWait;

    final static int REQUEST_LOCATION = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_and_lost);

        breedService = retrofit.create(BreedService.class);
        animalTypeService = retrofit.create(AnimalTypeService.class);
        animalService = retrofit.create(AnimalService.class);
        adService = retrofit.create(AdService.class);

        initElements();

        // Check if Google Play Services is available or show dialog
        if (!utils.isGooglePlayServicesAvailable(FindAndLostActivity.this)){
            DialogGooglePlayServicesUnavailable dialogGooglePlayServicesUnavailable = DialogGooglePlayServicesUnavailable.newInstance();
            dialogGooglePlayServicesUnavailable.show(getFragmentManager(), "DialogGooglePlayServicesUnavailable");
        } else {
            if (!hasGPSDevice(getBaseContext())) {
                Snackbar.make(FindAndLostActivity.this.findViewById(android.R.id.content).getRootView(), "Votre appareil ne dispose pas de GPS", Snackbar.LENGTH_SHORT).show();
            } else {
                utils.checkNetwork();
                if (!utils.isGPSEnabled()) {
                    enableLoc();
                }
                setCurrentUser();
                getExtras();
            }
        }
    }

    private void initElements() {
        /* Get Elements */
        spinBreed = (Spinner) findViewById(R.id.spinBreed);
        spinAge = (Spinner) findViewById(R.id.spinAge);
        spinGender = (Spinner) findViewById(R.id.spinGender);

        imgLocalisation = (ImageView) findViewById(R.id.imgLocalisation);
        imgLostLocation = (ImageView) findViewById(R.id.imgLostLocalisation);
        imgCalendar = (ImageView) findViewById(R.id.imgCalendar);
        imgCalendarLost = (ImageView) findViewById(R.id.imgCalendarLost);
        imgAddPetPic = (ImageView) findViewById(R.id.imgAddPetPic);

        txtViewLocation = (TextView) findViewById(R.id.txtViewLocation);
        txtViewLostLocation = (TextView) findViewById(R.id.txtViewLostLocation);
        txtViewFindingDate = (TextView) findViewById(R.id.txtViewFindingDate);
        txtViewLoosingDate = (TextView) findViewById(R.id.txtViewLoosingDate);
        txtViewPublish = (TextView) findViewById(R.id.txtViewPublish);

        editTxtTatoo = (EditText) findViewById(R.id.editTxtTatoo);
        editTxtChip = (EditText) findViewById(R.id.editTxtChip);
        editTxtName = (EditText) findViewById(R.id.editTxtName);
        editTxtPhone = (EditText) findViewById(R.id.editTxtPhone);
        editTxtMail = (EditText) findViewById(R.id.editTxtMail);
        editTxtColors = (EditText) findViewById(R.id.editTxtColors);
        editTxtPrecisionFind = (EditText) findViewById(R.id.editTxtPrecision);
        editTxtPrecisionLost = (EditText) findViewById(R.id.editTxtPrecisionLost);

        rgResc = (RadioGroup) findViewById(R.id.rgResc);

        imgPicturePet = (ImageView) findViewById(R.id.imgPicturePet);
        imgUpdPetPic = (ImageView) findViewById(R.id.imgUpdPetPic);

        imgUpdPetPic.setVisibility(View.GONE);
        imgPicturePet.setVisibility(View.GONE);

        linearInfoGeneFind = (LinearLayout) findViewById(R.id.linearInfoGeneFind);
        linearInfoGeneLost = (LinearLayout) findViewById(R.id.linearInfoGeneLost);
        linearInfoUser = (LinearLayout) findViewById(R.id.linearInfoUser);

        /* GoogleMap */
        utils = new Utils(FindAndLostActivity.this);
        gps = new GPSUtil(FindAndLostActivity.this);

        /* Init spinner for Age and Gender */
        ArrayAdapter<String> spinAgeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ageArray));
        spinAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAge.setAdapter(spinAgeAdapter);
        ArrayAdapter<String> spinGenderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.genderArray));
        spinGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(spinGenderAdapter);

        /* Add listener on buttons */
        txtViewLocation.setOnClickListener(this);
        txtViewLostLocation.setOnClickListener(this);
        imgLocalisation.setOnClickListener(this);
        imgLostLocation.setOnClickListener(this);
        imgCalendar.setOnClickListener(this);
        imgCalendarLost.setOnClickListener(this);
        imgAddPetPic.setOnClickListener(this);
        imgUpdPetPic.setOnClickListener(this);
        txtViewPublish.setOnClickListener(this);
    }

    private void getExtras() {
        if (getIntent().getExtras() != null) {
            currentAnimal = (AnimalPojo) getIntent().getSerializableExtra("animal");
            currentAdTypeId = getIntent().getExtras().getInt("adTypeId");
            if(null != currentAnimal && currentAdTypeId == 1){ // Animal Lost
                currentAnimalTypeId = currentAnimal.getAnimalType().getId();
                try { initFormData(currentAnimal); }
                catch (ExecutionException e) {  e.printStackTrace(); }
                catch (InterruptedException e) { e.printStackTrace(); }
            } else {  // Animal Found
                currentAnimalTypeId = getIntent().getExtras().getInt("animalTypeId");
                try { initForm(); }
                catch (ExecutionException e) { e.printStackTrace(); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    public void onClick(final View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.txtViewLocation:
                if (utils.isGPSEnabled()) {
                    i = new Intent(FindAndLostActivity.this, MapsActivity.class);
                    startActivityForResult(i, 2);
                } else {
                    mGoogleApiClient = null;
                    enableLoc();
                }
                break;
            case R.id.txtViewLostLocation:
                if (utils.isGPSEnabled()) {
                    i = new Intent(FindAndLostActivity.this, MapsActivity.class);
                    startActivityForResult(i, 2);
                } else {
                    mGoogleApiClient = null;
                    enableLoc();
                }
                break;
            case R.id.imgLocalisation:
                if (utils.isGPSEnabled()) {
                    this.initGoogleAPI();
                    try { this.writeCurrentLocation(); }
                    catch (IOException e) { e.printStackTrace(); }
                } else {
                    mGoogleApiClient = null;
                    enableLoc();
                }
                break;
            case R.id.imgLostLocalisation:
                if (utils.isGPSEnabled()) {
                    this.initGoogleAPI();
                    try { this.writeCurrentLocation(); }
                    catch (IOException e) { e.printStackTrace(); }
                } else {
                    mGoogleApiClient = null;
                    enableLoc();
                }
                break;
            case R.id.imgCalendar:
                new DatePickerDialog(FindAndLostActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.imgCalendarLost:
                new DatePickerDialog(FindAndLostActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.imgAddPetPic:
                showPictureChoice();
                break;
            case R.id.imgUpdPetPic:
                showPictureChoice();
                break;
            case R.id.txtViewPublish:
                if(dataRequiredForFindForm()) {
                    currentDialogWait = popupWait(false);
                    try { this.urlImg = new UploadImageTask(this.currentPicturePath).execute().get(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                    catch (ExecutionException e) { e.printStackTrace(); }
                }
                if(null != currentAnimal){ // Lost
                    if(dataRequiredForLostForm()){ createAdWithAnimal(currentAnimal, true); }
                    else {
                        //Toast.makeText(FindAndLostActivity.this, "Vous devez renseigner une date valide ainsi qu'une adresse afin de valider votre annonce", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Vous devez renseigner une date valide ainsi qu'une adresse afin de valider votre annonce", Snackbar.LENGTH_SHORT).show();
                    }
                } else { // Find
                    if(dataRequiredForFindForm()){
                        Call<AnimalPojo> call = animalService.createAnimal(getAnimal());
                        call.enqueue(new Callback<AnimalPojo>() {
                            @Override
                            public void onResponse(Call<AnimalPojo> call, Response<AnimalPojo> response) {
                                currentDialogWait.cancel();
                                AnimalPojo animalCreate = response.body();
                                createAdWithAnimal(animalCreate, false);
                            }
                            @Override
                            public void onFailure(Call<AnimalPojo> call, Throwable t) {
                                currentDialogWait.cancel();
                                //Toast.makeText(FindAndLostActivity.this, "Une erreur est survenue" , Toast.LENGTH_LONG).show();
                                Snackbar.make(v, "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        //Toast.makeText(FindAndLostActivity.this, "Vous devez renseigner une date valide ainsi qu'une adresse afin de valider votre annonce", Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "Vous devez renseigner une date valide ainsi qu'une adresse afin de valider votre annonce", Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void createAdWithAnimal(AnimalPojo animal, final boolean lost){
        Call<AdPojo> call = adService.createAd(getAd(animal));
        call.enqueue(new Callback<AdPojo>() {
            @Override
            public void onResponse(Call<AdPojo> call, Response<AdPojo> response) {
                currentDialogWait.cancel();
                if(lost) { // perdu
                    currentDistanceUser = (double) currentUser.getDistance();
                    Intent i = new Intent(FindAndLostActivity.this, Maps2Activity.class);
                    i.putExtra("currentAnimalTypeId", currentAnimalTypeId);
                    i.putExtra("currentDistanceUser", currentDistanceUser);
                    startActivity(i);
                    finish();
                } else { // trouvé
                    Intent i = new Intent(FindAndLostActivity.this, MainActivity.class);
                    i.putExtra("createAd", true);
                    startActivity(i);
                    finish();

                }
            }
            @Override
            public void onFailure(Call<AdPojo> call, Throwable t) {
                currentDialogWait.cancel();
                //Toast.makeText(FindAndLostActivity.this, "Une erreur est survenue" , Toast.LENGTH_LONG).show();
                Snackbar.make(FindAndLostActivity.this.findViewById(android.R.id.content).getRootView(), "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initForm() throws ExecutionException, InterruptedException {
        // Default : Today
        String fDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        txtViewFindingDate.setText(fDate);

        /* If Cat Or Dog Get Races */
        if (currentAnimalTypeId == 1 || currentAnimalTypeId == 2){
            Call<List<BreedPojo>> call = breedService.getBreedsByAnimalTypeId(currentAnimalTypeId);
            call.enqueue(new Callback<List<BreedPojo>>() {
                @Override
                public void onResponse(Call<List<BreedPojo>> call, Response<List<BreedPojo>> response) {
                    ArrayList<BreedPojo> breeds = (ArrayList<BreedPojo>) response.body();
                    initBreedSpinner(breeds);
                }
                @Override
                public void onFailure(Call<List<BreedPojo>> call, Throwable t) {
                    //Toast.makeText(FindAndLostActivity.this, "Une erreur est survenue" , Toast.LENGTH_LONG).show();
                    Snackbar.make(FindAndLostActivity.this.findViewById(android.R.id.content).getRootView(), "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                }
            });
        } else { ((LinearLayout) spinBreed.getParent()).setVisibility(View.GONE); }

        // If Register user
        if(null != currentUser){ linearInfoUser.setVisibility(View.GONE); }
    }

    private void initFormData(final AnimalPojo animal) throws ExecutionException, InterruptedException {
        formLostMode();

        // Init breed
        if(null != animal.getBreed() && (animal.getAnimalType().getId() == 1 || animal.getAnimalType().getId() == 2)) {
            Call<List<BreedPojo>> call = breedService.getBreedsByAnimalTypeId(animal.getAnimalType().getId());
            call.enqueue(new Callback<List<BreedPojo>>() {
                @Override
                public void onResponse(Call<List<BreedPojo>> call, Response<List<BreedPojo>> response) {
                    ArrayList<BreedPojo> breeds = (ArrayList<BreedPojo>) response.body();
                    initBreedSpinnerWithVal(breeds, animal.getBreed());
                }
                @Override
                public void onFailure(Call<List<BreedPojo>> call, Throwable t) {
                    //Toast.makeText(FindAndLostActivity.this, "Une erreur est survenue" , Toast.LENGTH_LONG).show();
                    Snackbar.make(FindAndLostActivity.this.findViewById(android.R.id.content).getRootView(), "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                }
            });
        } else { ((LinearLayout) spinBreed.getParent()).setVisibility(View.GONE); }

        // Animal Img TODO : Cloudinary
        if (null != animal.getPhoto())
            Picasso.with(FindAndLostActivity.this).load(animal.getPhoto()).into(imgAddPetPic);
        else {
            if (animal.getAnimalType().getId() == 1)
                imgAddPetPic.setImageDrawable(getResources().getDrawable(R.drawable.cat_icon));
            else if (animal.getAnimalType().getId() == 2)
                imgAddPetPic.setImageDrawable(getResources().getDrawable(R.drawable.dog_icon));
            else
                imgAddPetPic.setImageDrawable(getResources().getDrawable(R.drawable.other_icon));
            imgAddPetPic.setBackgroundResource(R.color.black);
        }

        // Init Name
        if (null != animal.getName()) {
            editTxtName.setText(animal.getName());
            ((LinearLayout) editTxtName.getParent()).setVisibility(View.VISIBLE);
        } else {
            ((LinearLayout) editTxtName.getParent()).setVisibility(View.GONE);
        }

        // Init Gender
        if (null != animal.getSexe()) {
            if (animal.getSexe().equals("Male")) {
                spinGender.setSelection(1);
            } else if (animal.getSexe().equals("Femelle")) {
                spinGender.setSelection(2);
            }
        } else {
            ((LinearLayout) spinGender.getParent()).setVisibility(View.GONE);
        }

        // Init Morphologie
        if (null != animal.getAge()) {
            if (animal.getAge().equals("Jeune")) {
                spinAge.setSelection(1);
            } else if (animal.getAge().equals("Adulte")) {
                spinAge.setSelection(2);
            }
        } else {
            ((LinearLayout) spinAge.getParent()).setVisibility(View.GONE);
        }

        // Init Tatoo
        if (null != animal.getTatoo()) {
            editTxtTatoo.setText(animal.getTatoo());
        } else {
            ((LinearLayout) editTxtTatoo.getParent()).setVisibility(View.GONE);
        }

        // Init Colors
        if (null != animal.getColors()) {
            editTxtColors.setText(animal.getColors());
        } else {
            ((LinearLayout) editTxtColors.getParent()).setVisibility(View.GONE);
        }

        // Init Chip
        if (null != animal.getChip()) {
            editTxtChip.setText(animal.getChip());
            ((LinearLayout) editTxtChip.getParent()).setVisibility(View.VISIBLE);
        } else {
            ((LinearLayout) editTxtChip.getParent()).setVisibility(View.GONE);
        }
        formAnimalDisabled();
    }

    private void formLostMode(){
        linearInfoGeneFind.setVisibility(View.GONE);
        linearInfoUser.setVisibility(View.GONE);
        ((LinearLayout) editTxtPrecisionFind.getParent()).setVisibility(View.GONE);
        linearInfoGeneLost.setVisibility(View.VISIBLE);

        // Default : Today
        String fDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        txtViewLoosingDate.setText(fDate);
    }

    Calendar myCalendar = Calendar.getInstance();
    /* Create a Calendar dialog init on now */
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if(null != currentAnimal){ txtViewLoosingDate.setText(getNewDate()); }
            else { txtViewFindingDate.setText(getNewDate()); }
        }
    };

    private void formAnimalDisabled(){
        editTxtName.setEnabled(false);
        spinGender.setEnabled(false);
        spinAge.setEnabled(false);
        editTxtTatoo.setEnabled(false);
        editTxtChip.setEnabled(false);
        editTxtColors.setEnabled(false);
        spinBreed.setEnabled(false);
    }

    private void initBreedSpinner(ArrayList<BreedPojo> lst){
        if(lst.isEmpty()){ ((LinearLayout) spinBreed.getParent()).setVisibility(View.GONE); }
        else {
            BreedPojo defaultBreed = new BreedPojo();
            defaultBreed.setLabel(getString(R.string.form_default_breed));
            lst.add(0, defaultBreed);
            ArrayAdapter<BreedPojo> adapter = new ArrayAdapter<BreedPojo>(this, android.R.layout.simple_spinner_item, lst);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinBreed.setAdapter(adapter);
        }
    }

    private void initBreedSpinnerWithVal(ArrayList<BreedPojo> lst, BreedPojo animalBreed){
        ArrayAdapter<BreedPojo> adapter = new ArrayAdapter<BreedPojo>(this, android.R.layout.simple_spinner_item, lst);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBreed.setAdapter(adapter);
        for(BreedPojo breed:lst){
            if(breed.getId().intValue() == animalBreed.getId().intValue()){
                spinBreed.setSelection(adapter.getPosition(breed));
                break;
            }
        }
    }

    private Boolean dataRequiredForLostForm(){
        Calendar myCalendarToday = Calendar.getInstance();
        if(txtViewLoosingDate.getText().toString().equals("") || txtViewLostLocation.getText().toString().equals(""))
            return false;
        if(null != myCalendar && myCalendar.compareTo(myCalendarToday) >= 1)
            return false;
        return true;
    }

    private Boolean dataRequiredForFindForm(){
        Calendar myCalendarToday = Calendar.getInstance();
        if(txtViewFindingDate.getText().toString().equals("") || txtViewLocation.getText().toString().equals(""))
            return false;
        if(null != myCalendar && myCalendar.compareTo(myCalendarToday) >= 1)
            return false;
        return true;
    }

    private AdPojo getAd(AnimalPojo animal){
        AdPojo ad = new AdPojo();

        // AdType
        AdTypePojo adType = new AdTypePojo();
        adType.setId(currentAdTypeId);
        ad.setAdType(adType);

        // Animal
        ad.setAnimal(animal);

        // Precision
        if(!editTxtPrecisionFind.getText().toString().equals("") && currentAdTypeId == 2){ // Find
            ad.setDescription(editTxtPrecisionFind.getText().toString());
        } else if (!editTxtPrecisionLost.getText().toString().equals("") && currentAdTypeId == 1){ // Lost
            ad.setDescription(editTxtPrecisionLost.getText().toString());
        }

        // Resc
        RadioButton rbSelect = (RadioButton) findViewById(rgResc.getCheckedRadioButtonId());
        if(rbSelect.getText().toString().equals(getString(R.string.form_rescYes)) && currentAdTypeId == 2){
            ad.setRecover(true);
        } else {
            ad.setRecover(false);
        }

        // Finding Date
        if(!txtViewFindingDate.getText().toString().equals("") && currentAdTypeId == 2) { // Find
            ad.setDate(txtViewFindingDate.getText().toString());
        } else if (!txtViewLoosingDate.getText().toString().equals("") && currentAdTypeId == 1) { // Lost
            ad.setDate(txtViewLoosingDate.getText().toString());
        }

        // Finding adress
        if(null != currentAdress){
            ad.setAdress(currentAdress.getAdress());
            ad.setCountry(currentAdress.getCountry());
            ad.setPostalCode(currentAdress.getPostalCode());
            ad.setCity(currentAdress.getCity());
            ad.setLattitude(currentAdress.getLattitude());
            ad.setLongitude(currentAdress.getLongitude());
        }

        // Phone
        if(!editTxtPhone.getText().toString().equals("") && currentAdTypeId == 2)
            ad.setPhone(editTxtPhone.getText().toString());

        // Email
        if(!editTxtMail.getText().toString().equals("") && currentAdTypeId == 2)
            ad.setEmail(editTxtMail.getText().toString());

        // User
        if(null != currentUser)
            ad.setUser(currentUser);

        return ad;
    }

    private AnimalPojo getAnimal(){
        AnimalPojo animal = new AnimalPojo();

        // Age
        if(!spinAge.getSelectedItem().toString().equals(getString(R.string.form_default_age)))
            animal.setAge(spinAge.getSelectedItem().toString());

        // Sexe
        if(!spinGender.getSelectedItem().toString().equals(getString(R.string.form_default_gender)))
            animal.setSexe(spinGender.getSelectedItem().toString());

        // Race (Si chien ou chat)
        if(currentAnimalTypeId != 3){
            if(!spinBreed.getSelectedItem().toString().equals(getString(R.string.form_default_breed)))
                animal.setBreed((BreedPojo) spinBreed.getSelectedItem());
        }

        // Couleurs
        if(!editTxtColors.getText().toString().equals(""))
            animal.setColors(editTxtColors.getText().toString());

        // Tatouage
        if(!editTxtTatoo.getText().toString().equals(""))
            animal.setTatoo(editTxtTatoo.getText().toString());

        // AnimalType
        AnimalTypePojo animalType = new AnimalTypePojo();
        animalType.setId(currentAnimalTypeId);
        animal.setAnimalType(animalType);

        animal.setPhoto(this.urlImg);
        // TODO Cloudinary Host img and Get url

        return animal;
    }

    private String getNewDate() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        return sdf.format(myCalendar.getTime());
    }

    private void setCurrentUser() {
        gs = (GlobalState) getApplication();
        currentUser = gs.getCurrentUser();
    }

    private void setAdress(double currentLatitude, double currentLongitude) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        currentAdress = new Adress();
        currentAdress.setLattitude(Double.toString(currentLatitude));
        currentAdress.setLongitude(Double.toString(currentLongitude));
        currentAdress.setAdress(addresses.get(0).getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        currentAdress.setCity(addresses.get(0).getLocality());
        currentAdress.setCountry(addresses.get(0).getCountryName());
        currentAdress.setPostalCode(addresses.get(0).getPostalCode());

        if(null != currentAnimal){ txtViewLostLocation.setText(currentAdress.toString()); }
        else { txtViewLocation.setText(currentAdress.toString()); }
    }

    /* Set current address location */
    private void writeCurrentLocation() throws IOException {
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        this.setAdress(latitude, longitude);
    }

    /* Init Google API */
    private void initGoogleAPI() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        // Accurate location, Interval between active location (10seconds in milli), fastest intervel app receive updates (1second in milli)
        mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10 * 1000).setFastestInterval(1 * 1000);
    }

    private void CheckEnableGPS(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!provider.equals("")){ //GPS Enabled
            //Toast.makeText(FindAndLostActivity.this, "GPS Enabled: " + provider, Toast.LENGTH_LONG).show();
            Snackbar.make(FindAndLostActivity.this.findViewById(android.R.id.content).getRootView(), "GPS Enabled: " + provider, Snackbar.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivity(intent);
        }
    }

    public void updateLocation() throws IOException {
        //getCurrentLocation();
    }

    private void handleNewLocation(Location location) throws IOException {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        this.setAdress(currentLatitude, currentLongitude);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try { connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST); }
            catch (IntentSender.SendIntentException e) { e.printStackTrace(); }
        } else
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        try { handleNewLocation(location); }
        catch (IOException e) { e.printStackTrace(); }
    }

    /* After client is connected to the location services, updates when the last location is not known */
    @Override
    public void onConnected(Bundle bundle) {
        try { this.writeCurrentLocation(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            if (requestCode == 1) { // Photo
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                imgAddPetPic.setVisibility(View.GONE);
                imgUpdPetPic.setVisibility(View.VISIBLE);
                imgPicturePet.setVisibility(View.VISIBLE);
                Bitmap image = BitmapFactory.decodeFile(picturePath);
                imgPicturePet.setImageBitmap(image);
                //image.recycle();

                this.currentPicturePath = getPath(selectedImage);
                //Toast.makeText(getBaseContext(), "PATH FILE --> " + this.currentPicturePath, Toast.LENGTH_LONG).show();

            } else if (requestCode == 2) { // Maps
                if (resultCode == RESULT_OK) {
                    if (data.getExtras() != null && data.getExtras().containsKey("adress")) {
                        currentAdress = (Adress) data.getExtras().getSerializable("adress");
                        if (null != currentAnimal) {
                            txtViewLostLocation.setText(currentAdress.toString());
                        } else {
                            txtViewLocation.setText(currentAdress.toString());
                        }
                    } else {
                        Log.d(TAG, "extras address //// Null Extras");
                    }
                }
                if (resultCode == RESULT_CANCELED) {
                    Log.d(TAG, "No Data From Maps");
                }
            }else if (requestCode == 3){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imgAddPetPic.setVisibility(View.GONE);
                imgUpdPetPic.setVisibility(View.VISIBLE);
                imgPicturePet.setVisibility(View.VISIBLE);

                //create a file to write bitmap data
                File f = new File(getApplicationContext().getCacheDir(), "new_picture");
                try {
                    f.createNewFile();

                    Bitmap bitmap = imageBitmap;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (Exception e) { e.printStackTrace(); }

                this.currentPicturePath = f.getPath();
                //Toast.makeText(getBaseContext(), "PATH FILE --> " + this.currentPicturePath, Toast.LENGTH_LONG).show();

                imgPicturePet.setImageBitmap(imageBitmap);

            } else {
                Log.d(TAG, "No Img");
                imgAddPetPic.setImageResource(R.drawable.addpic_icon);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gps.stopUsingGPS(); //stop using GPS service on pause
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FindAndLostActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Intent getParentActivityIntent() { return super.getParentActivityIntent(); }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showPictureChoice_old(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(FindAndLostActivity.this);
        builderSingle.setTitle("Ouvrir");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                FindAndLostActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Gallery");
        arrayAdapter.add("Camera");
        arrayAdapter.add("Supprimer la photo");

        builderSingle.setNegativeButton(
                "Annuler",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //Toast.makeText(getBaseContext(), "Gallery", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, 1);
                        } else if (which == 1) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, 3);
                            }
                        } else if (which == 2) {
                            currentPicturePath = "";
                            imgAddPetPic.setImageResource(R.drawable.addpic_icon);
                        }
                    }
                });
        builderSingle.show();
    }

    public void showPictureChoice() {
        new MaterialDialog.Builder(this)
                .title(R.string.openPicture)
                .negativeText("Annuler")
                .items(R.array.arrayChoicePicture)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            //Toast.makeText(getBaseContext(), "Gallery", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, 1);
                        } else if (which == 1) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, 3);
                            }
                        } else if (which == 2) {
                            currentPicturePath = "";
                            imgAddPetPic.setImageResource(R.drawable.addpic_icon);
                        }
                        return true; // allow selection
                    }
                })
                .positiveText("OK")
                .show();
    }

    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file to upload "), 5);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void uploadFile(Bitmap bitmap, String pathFile){

        Map config = new HashMap();
        config.put("cloud_name", R.string.cloud_name_cloudinary);
        config.put("api_key", R.string.key_cloudinary);
        config.put("api_secret", R.string.password_cloudinary);
        //Cloudinary cloudinary = new Cloudinary(config);

        Cloudinary cloudinary = new Cloudinary("cloudinary://786239969984472:6zbImcBbWO1WGqAp3pK_sq6Ba1w@lookas33");
        Cloudinary old_cloudinary = new Cloudinary("cloudinary://123456789012345:abcdeghijklmnopqrstuvwxyz12@n07t21i7");

        //Cloudinary cl = new Cloudinary(FindAndLostActivity.this);
        /*
        Map config = new HashMap();
        config.put("cloud_name", R.string.cloud_name_cloudinary);
        Cloudinary mobileCloudinary = new Cloudinary(config);
        */


        /*ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();
        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);*/

        //InputStream is = getResources().openRawResource(R.drawable.dog_icon);
        //InputStream is = (InputStream) getResources().openRawResource(R.drawable.cat_icon);
        //InputStream is = new ByteArrayInputStream(Bitmap2Bytes(bitmap));

        try{
            FileInputStream fileInputStream = null;
            try { fileInputStream = new FileInputStream(new File(pathFile)); }
            catch (FileNotFoundException e) { e.printStackTrace(); }

            try {
                //cloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap())
                cloudinary.uploader().upload(fileInputStream, ObjectUtils.emptyMap());//Collections.emptyMap());
            } catch (IOException e) { e.printStackTrace(); }
            cloudinary.url().generate("test_chien.jpg");
        }catch (Exception e){ e.printStackTrace(); }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { super.onRestoreInstanceState(savedInstanceState); }

    private void enableLoc() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            mGoogleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            mGoogleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(FindAndLostActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
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