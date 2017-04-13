package com.project.app.pilpoil.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.AnimalService;
import com.project.app.pilpoil.Interface.BreedService;
import com.project.app.pilpoil.Metier.AnimalPojo;
import com.project.app.pilpoil.Metier.BreedPojo;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GlobalState;
import com.project.app.pilpoil.Service.UploadImageTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetDetailActivity extends BaseActivity implements View.OnClickListener {

    private AnimalPojo currentAnimal = null;
    private TextView txtViewName, txtViewAnimalType, txtViewGender, txtViewChip, txtViewBreed, txtViewTatoo, txtViewAge, txtViewColors;
    private EditText editTxtName, editTxtChip, editTxtTatoo, editTxtColors;
    private CircularImageView circularImgViewPetPic, circularImgViewPetPicEdit;
    private LinearLayout layoutActionsReadMode, layoutActionsEditMode;
    private ScrollView mainContentReadMode, mainContentEditMode;
    private Spinner spinAge, spinBreed, spinGender;
    private ImageView imgUpdPetPic, next, prev;

    private ArrayList<AnimalPojo> lstAnimals;

    private GlobalState gs;
    private UserPojo currentUser = null;
    private ArrayList<BreedPojo> breeds = null;

    public static final String TAG = PetDetailActivity.class.getSimpleName();
    private String urlImg;
    private String currentPicturePath = null;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private BreedService breedService;
    private AnimalService animalService;

    private MaterialDialog currentDialogWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pet_detail);

        breedService = retrofit.create(BreedService.class);
        animalService = retrofit.create(AnimalService.class);

        gs = (GlobalState) getApplication();
        currentUser = gs.getCurrentUser();

        initElements();
        getExtras();

        if (currentAnimal.getName() != null)
            setTitle(currentAnimal.getName());

    }

    private void initElements() {
        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewAnimalType = (TextView) findViewById(R.id.txtViewAnimalType);
        txtViewGender = (TextView) findViewById(R.id.txtViewGender);
        txtViewChip = (TextView) findViewById(R.id.txtViewChip);
        txtViewBreed = (TextView) findViewById(R.id.txtViewBreed);
        txtViewTatoo = (TextView) findViewById(R.id.txtViewTatoo);
        txtViewAge = (TextView) findViewById(R.id.txtViewAge);
        circularImgViewPetPic = (CircularImageView) findViewById(R.id.imgViewPetPic);
        circularImgViewPetPicEdit = (CircularImageView) findViewById(R.id.imgViewPetPicEdit);
        txtViewColors = (TextView) findViewById(R.id.txtViewColors);
        layoutActionsReadMode = (LinearLayout) findViewById(R.id.layoutActionsReadMode);
        layoutActionsEditMode = (LinearLayout) findViewById(R.id.layoutActionsEditMode);
        mainContentReadMode = (ScrollView) findViewById(R.id.mainContentReadMode);
        mainContentEditMode = (ScrollView) findViewById(R.id.mainContentEditMode);
        editTxtName = (EditText) findViewById(R.id.editTxtName);
        editTxtChip = (EditText) findViewById(R.id.editTxtChip);
        editTxtTatoo = (EditText) findViewById(R.id.editTxtTatoo);
        editTxtColors = (EditText) findViewById(R.id.editTxtColors);
        spinAge = (Spinner) findViewById(R.id.spinAge);
        spinBreed = (Spinner) findViewById(R.id.spinBreed);
        spinGender = (Spinner) findViewById(R.id.spinGender);

        next = (ImageView) findViewById(R.id.imgViewNext);
        prev = (ImageView) findViewById(R.id.imgViewPrev);

        //ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
        //mainContentReadMode.setOnTouchListener(activitySwipeDetector);

        imgUpdPetPic = (ImageView) findViewById(R.id.imgUpdPetPic);
        imgUpdPetPic.setOnClickListener(this);

        next.setOnClickListener(this);
        prev.setOnClickListener(this);

        /* Init spinner for Age and Gender */
        ArrayAdapter<String> spinAgeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ageArrayForPetDetails));
        spinAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAge.setAdapter(spinAgeAdapter);
        ArrayAdapter<String> spinGenderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.genderArrayForPetDetails));
        spinGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(spinGenderAdapter);
    }

    private void getExtras() {
        if (getIntent().getExtras() != null) {
            currentAnimal = (AnimalPojo) this.getIntent().getSerializableExtra("animal");
            lstAnimals = (ArrayList<AnimalPojo>)getIntent().getSerializableExtra("animals");
            if(currentAnimal != null){
                Call<List<BreedPojo>> call = breedService.getBreedsByAnimalTypeId(currentAnimal.getAnimalType().getId());
                call.enqueue(new Callback<List<BreedPojo>>() {
                    @Override
                    public void onResponse(Call<List<BreedPojo>> call, Response<List<BreedPojo>> response) {
                        breeds = (ArrayList<BreedPojo>) response.body();
                        initForms(currentAnimal);
                    }
                    @Override
                    public void onFailure(Call<List<BreedPojo>> call, Throwable t) {
                        //Toast.makeText(PetDetailActivity.this, "Une erreur est survenue" , Toast.LENGTH_LONG).show();
                        Snackbar.make(PetDetailActivity.this.findViewById(android.R.id.content).getRootView(), "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        } else { backToList(); }
    }

    private void backToList() {
        Intent i = new Intent(PetDetailActivity.this, MyPetsActivity.class);
        startActivity(i);
        finish();
    }

    private void initForms(AnimalPojo animal){
        if(lstAnimals.size() <= 1){
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        }else{
            ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
            mainContentReadMode.setOnTouchListener(activitySwipeDetector);
        }
        // Optional
        if(null != currentAnimal.getPhoto()){
            Picasso.with(PetDetailActivity.this).load(currentAnimal.getPhoto()).into(circularImgViewPetPic);
            Picasso.with(PetDetailActivity.this).load(currentAnimal.getPhoto()).into(circularImgViewPetPicEdit);
        }else{
            if(currentAnimal.getAnimalType().getId() == 1){
                circularImgViewPetPic.setImageDrawable(getResources().getDrawable(R.drawable.cat_icon));
                circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.cat_icon));
            }else if (currentAnimal.getAnimalType().getId() == 2){
                circularImgViewPetPic.setImageDrawable(getResources().getDrawable(R.drawable.dog_icon));
                circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.dog_icon));
            }else{
                circularImgViewPetPic.setImageDrawable(getResources().getDrawable(R.drawable.other_icon));
                circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.other_icon));
            }
        }

        // Required
        if(null != currentAnimal.getName()){
            txtViewName.setText(currentAnimal.getName());
            editTxtName.setText(currentAnimal.getName());
        } else { txtViewName.setText(getString(R.string.notIndicNoWhiteSpace)); }

        // Required
        if(currentAnimal.getAnimalType() != null){
            txtViewAnimalType.setText(currentAnimal.getAnimalType().getLabel());
        } else { txtViewAnimalType.setText(getString(R.string.notIndicNoWhiteSpace)); }

        // Optional
        if(null != currentAnimal.getAge()){
            txtViewAge.setText(currentAnimal.getAge());
            if(animal.getAge().equals("Jeune")){ spinAge.setSelection(1); }
            else if(animal.getAge().equals("Adulte")) { spinAge.setSelection(2); }
        } else { txtViewAge.setText(getString(R.string.notIndicNoWhiteSpace)); }

        // Optional
        if(null != currentAnimal.getSexe()){
            txtViewGender.setText(currentAnimal.getSexe());
            if(animal.getSexe().equals("Mâle")){ spinGender.setSelection(1); }
            else if(animal.getSexe().equals("Femelle")) { spinGender.setSelection(2); }
        } else { txtViewGender.setText(getString(R.string.notIndicNoWhiteSpace)); }

        // Optional
        if(null != currentAnimal.getChip()){
            txtViewChip.setText(currentAnimal.getChip());
            editTxtChip.setText(currentAnimal.getChip());
        } else { txtViewChip.setText(getString(R.string.notIndicNoWhiteSpace)); }

        // Optional
        if(currentAnimal.getBreed() != null){
            txtViewBreed.setText(currentAnimal.getBreed().getLabel());
            initBreedSpinnerWithVal(breeds, currentAnimal.getBreed());
        } else {
            txtViewBreed.setText(getString(R.string.notIndicNoWhiteSpace));
            initBreedSpinner(breeds);
        }

        // Optional
        if(null != currentAnimal.getTatoo()){
            txtViewTatoo.setText(currentAnimal.getTatoo());
            editTxtTatoo.setText(currentAnimal.getTatoo());
        } else { txtViewTatoo.setText(getString(R.string.notIndicNoWhiteSpace)); }

        // Optional
        if(null != currentAnimal.getColors()){
            txtViewColors.setText(currentAnimal.getColors());
            editTxtColors.setText(currentAnimal.getColors());
        } else { txtViewColors.setText(getString(R.string.notIndicNoWhiteSpace)); }

    }

    public void switchReadMode(View v){
        layoutActionsReadMode.setVisibility(View.VISIBLE);
        layoutActionsEditMode.setVisibility(View.GONE);
        mainContentReadMode.setVisibility(View.VISIBLE);
        mainContentEditMode.setVisibility(View.GONE);
    }

    public void switchEditMode(View v){
        layoutActionsReadMode.setVisibility(View.GONE);
        layoutActionsEditMode.setVisibility(View.VISIBLE);
        mainContentEditMode.setVisibility(View.VISIBLE);
        mainContentReadMode.setVisibility(View.GONE);
    }

    private void initBreedSpinner(ArrayList<BreedPojo> lst){
        if(lst.isEmpty()){ ((LinearLayout) spinBreed.getParent()).setVisibility(View.GONE); }
        else {
            BreedPojo defaultBreed = new BreedPojo();
            defaultBreed.setLabel(getString(R.string.form_unknowF));
            lst.add(0, defaultBreed);
            ArrayAdapter<BreedPojo> adapter = new ArrayAdapter<BreedPojo>(this, android.R.layout.simple_spinner_item, lst);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinBreed.setAdapter(adapter);
        }
    }

    private void initBreedSpinnerWithVal(ArrayList<BreedPojo> lst, BreedPojo animalBreed){
        BreedPojo defaultBreed = new BreedPojo();
        defaultBreed.setLabel(getString(R.string.form_unknowF));
        lst.add(0, defaultBreed);
        ArrayAdapter<BreedPojo> adapter = new ArrayAdapter<BreedPojo>(this, android.R.layout.simple_spinner_item, lst);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBreed.setAdapter(adapter);
        for(BreedPojo breed:lst){
            if(breed.getId() != null && breed.getId().intValue() == animalBreed.getId().intValue()){
                spinBreed.setSelection(adapter.getPosition(breed));
                break;
            }
        }
    }

    public void saveAnimal(final View v){
        currentDialogWait = popupWait(false);
        if(editTxtName.getText().toString().equals("")){
            //Toast.makeText(PetDetailActivity.this, getString(R.string.nameRequired), Toast.LENGTH_LONG).show();
            Snackbar.make(v, getString(R.string.nameRequired), Snackbar.LENGTH_SHORT).show();
        } else {
            AnimalPojo animalToSave = currentAnimal;
            // Attributs
            animalToSave.setUser(currentUser);
            animalToSave.setColors(editTxtColors.getText().toString());
            animalToSave.setName(editTxtName.getText().toString());
            animalToSave.setChip(editTxtChip.getText().toString());
            animalToSave.setTatoo(editTxtTatoo.getText().toString());

            // Age
            if(!spinAge.getSelectedItem().toString().equals(getString(R.string.form_unknowM)))
                animalToSave.setAge(spinAge.getSelectedItem().toString());
            else
                animalToSave.setAge("");

            // Sexe
            if(!spinGender.getSelectedItem().toString().equals(getString(R.string.form_unknowF)))
                animalToSave.setSexe(spinGender.getSelectedItem().toString());
            else
                animalToSave.setSexe("");

            // Race (Si chien ou chat)
            if(currentAnimal.getAnimalType().getId() == 1 || currentAnimal.getAnimalType().getId() == 2){
                if(!spinBreed.getSelectedItem().toString().equals(getString(R.string.form_unknowF)))
                    animalToSave.setBreed((BreedPojo) spinBreed.getSelectedItem());
                else
                    animalToSave.setBreed(null);
            } else
                animalToSave.setBreed(null);

            try {
                //Image non touché
                if(this.currentPicturePath == null){
                    this.urlImg = currentAnimal.getPhoto();
                //Suppression image
                }else if(this.currentPicturePath.equals("")){
                    this.urlImg = null;
                }else{
                    this.urlImg = new UploadImageTask(this.currentPicturePath).execute().get();
                }
                animalToSave.setPhoto(this.urlImg);
            }
            catch (InterruptedException e) { e.printStackTrace(); }
            catch (ExecutionException e) { e.printStackTrace(); }

            Call<AnimalPojo> call = animalService.updateAnimal(animalToSave, gs.getAuthTokenPojo().getToken()); //animalToSave.getId()
            call.enqueue(new Callback<AnimalPojo>() {
                @Override
                public void onResponse(Call<AnimalPojo> call, Response<AnimalPojo> response) {
                    currentDialogWait.cancel();
                    AnimalPojo animalSaved = response.body();
                    Intent i = new Intent(PetDetailActivity.this, PetDetailActivity.class);
                    i.putExtra("animals", lstAnimals);
                    i.putExtra("animal", animalSaved);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onFailure(Call<AnimalPojo> call, Throwable t) {
                    currentDialogWait.cancel();
                    //Toast.makeText(PetDetailActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG).show();
                    Snackbar.make(v, "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void deleteAnimal(final View v) {
        currentDialogWait = popupWait(false);
        new MaterialDialog.Builder(this)
                .title(R.string.deleteDialogTitle)
                .content(R.string.deleteDialogMsg)
                .positiveText(R.string.form_deleteanimal_confirm_button)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Call<ResponseBody> call = animalService.deleteAnimal(currentAnimal.getId(), gs.getAuthTokenPojo().getToken());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                currentDialogWait.cancel();
                                if (response.code() == 200) {
                                    backToList();
                                } else{
                                    //Toast.makeText(PetDetailActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG).show();
                                    Snackbar.make(v, "Une erreur est survenue", Snackbar.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                currentDialogWait.cancel();
                                //Toast.makeText(PetDetailActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG).show();
                                Snackbar.make(v, "Une erreur est survenue", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = gs.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.imgUpdPetPic:
                showPictureChoice();
                break;
            case R.id.imgViewNext:
                i = new Intent(PetDetailActivity.this, PetDetailActivity.class);
                i.putExtra("animals", lstAnimals);
                i.putExtra("animal", findNextAnimal(lstAnimals, currentAnimal));
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
                break;
            case R.id.imgViewPrev:
                i = new Intent(PetDetailActivity.this, PetDetailActivity.class);
                i.putExtra("animals", lstAnimals);
                i.putExtra("animal", findPrevAnimal(lstAnimals, currentAnimal));
                startActivity(i);
                overridePendingTransition(R.anim.slide_in2, R.anim.slide_out2);
                finish();
                break;
            default:
                break;
        }
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
                                startActivityForResult(takePictureIntent, 2);
                            }
                        } else if (which == 2) {
                            currentPicturePath = "";
                            if (currentAnimal.getAnimalType().getId() == 1) {
                                circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.cat_icon));
                            } else if (currentAnimal.getAnimalType().getId() == 2) {
                                circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.dog_icon));
                            } else {
                                circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.other_icon));
                            }
                        }
                        return true; // allow selection
                    }
                })
                .positiveText("OK")
                .show();
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            if (requestCode == 1) { // Photo
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap image = BitmapFactory.decodeFile(picturePath);
                circularImgViewPetPicEdit.setImageBitmap(image);

                this.currentPicturePath = getPath(selectedImage);
                //Toast.makeText(getBaseContext(), "PATH FILE --> " + this.currentPicturePath, Toast.LENGTH_LONG).show();

            } else if (requestCode == 2) {
                //Toast.makeText(getBaseContext(), "++++++ IMAGE APP PHOTO ++++++", Toast.LENGTH_LONG).show();
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

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
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.currentPicturePath = f.getPath();
                //Toast.makeText(getBaseContext(), "PATH FILE --> " + this.currentPicturePath, Toast.LENGTH_LONG).show();
                circularImgViewPetPicEdit.setImageBitmap(imageBitmap);

            } else {
                Log.d(TAG, "No Img");
                imgUpdPetPic.setImageResource(R.drawable.addpic_icon);
            }
        }
    }

    private AnimalPojo findNextAnimal(ArrayList<AnimalPojo> animals, AnimalPojo animal){
        for(int i = 0; i < animals.size(); i++){
            if(animals.get(i).getId().intValue() == animal.getId().intValue()){
                if(i+1 < animals.size())
                    return animals.get(i+1);
                else
                    return animals.get(0);
            }
        }
        return animal;
    }

    private AnimalPojo findPrevAnimal(ArrayList<AnimalPojo> animals, AnimalPojo animal){
        for(int i = 0; i < animals.size(); i++){
            if(animals.get(i).getId().intValue() == animal.getId().intValue()){
                if(i-1 >= 0)
                    return animals.get(i-1);
                else
                    return animals.get(animals.size()-1);
            }
        }
        return animal;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PetDetailActivity.this, MyPetsActivity.class);
        startActivity(intent);
        finish();
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

    public class ActivitySwipeDetector implements View.OnTouchListener {

        static final String logTag = "ActivitySwipeDetector";
        private Activity activity;
        static final int MIN_DISTANCE = 100;
        private float downX, downY, upX, upY;

        public ActivitySwipeDetector(Activity activity){
            this.activity = activity;
        }

        public void onRightSwipe(){
            Log.i(logTag, "RightToLeftSwipe!");
            Intent i = new Intent(PetDetailActivity.this, PetDetailActivity.class);
            i.putExtra("animals", lstAnimals);
            i.putExtra("animal", findNextAnimal(lstAnimals, currentAnimal));
            startActivity(i);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        }

        public void onLeftSwipe(){
            Log.i(logTag, "LeftToRightSwipe!");
            Intent i = new Intent(PetDetailActivity.this, PetDetailActivity.class);
            i.putExtra("animals", lstAnimals);
            i.putExtra("animal", findNextAnimal(lstAnimals, currentAnimal));
            startActivity(i);
            overridePendingTransition(R.anim.slide_in2, R.anim.slide_out2);
            finish();
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    downY = event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    upX = event.getX();
                    upY = event.getY();
                    float deltaX = downX - upX;
                    float deltaY = downY - upY;

                    if(Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > MIN_DISTANCE){
                        if(deltaX > 0) { this.onRightSwipe(); return true; }
                        if(deltaX < 0) { this.onLeftSwipe(); return true; }
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
