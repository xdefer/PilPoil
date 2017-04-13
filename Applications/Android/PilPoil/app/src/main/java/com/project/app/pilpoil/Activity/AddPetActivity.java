package com.project.app.pilpoil.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.AdService;
import com.project.app.pilpoil.Interface.AnimalService;
import com.project.app.pilpoil.Interface.AnimalTypeService;
import com.project.app.pilpoil.Interface.BreedService;
import com.project.app.pilpoil.Metier.AnimalPojo;
import com.project.app.pilpoil.Metier.AnimalTypePojo;
import com.project.app.pilpoil.Metier.BreedPojo;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GlobalState;
import com.project.app.pilpoil.Service.UploadImageTask;
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

public class AddPetActivity extends BaseActivity implements View.OnClickListener {

    GlobalState gs;
    public static final String TAG = PetDetailActivity.class.getSimpleName();
    private UserPojo currentUser = null;
    private LinearLayout btnSave, breedContainer;
    private EditText editTxtName, editTxtChip, editTxtTatoo, editTxtColors;
    private CircularImageView circularImgViewPetPicEdit;
    private Spinner spinAge, spinBreed, spinGender, spinAnimalType;
    private ImageView imgUpdPetPic;
    private ArrayList<BreedPojo> breeds = null;
    private ArrayList<AnimalTypePojo> animalTypesList = null;
    private String urlImg;
    private String currentPicturePath = null;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private BreedService breedService;
    private AnimalTypeService animalTypeService;
    private AnimalService animalService;

    private MaterialDialog currentDialogWait;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        gs = (GlobalState) getApplication();
        currentUser = gs.getCurrentUser();

        breedService = retrofit.create(BreedService.class);
        animalTypeService = retrofit.create(AnimalTypeService.class);
        animalService = retrofit.create(AnimalService.class);

        getExtras();
        initElements();
    }

    private void initElements() {
        circularImgViewPetPicEdit = (CircularImageView) findViewById(R.id.imgViewPetPicEdit);
        editTxtName = (EditText) findViewById(R.id.editTxtName);
        editTxtChip = (EditText) findViewById(R.id.editTxtChip);
        editTxtTatoo = (EditText) findViewById(R.id.editTxtTatoo);
        editTxtColors = (EditText) findViewById(R.id.editTxtColor);
        spinAnimalType = (Spinner) findViewById(R.id.spinAnimalType);
        spinAge = (Spinner) findViewById(R.id.spinAge);
        spinBreed = (Spinner) findViewById(R.id.spinBreed);
        spinGender = (Spinner) findViewById(R.id.spinGender);
        btnSave = (LinearLayout) findViewById(R.id.btnSave);
        breedContainer = (LinearLayout) findViewById(R.id.breedContainerEdit);
        imgUpdPetPic = (ImageView) findViewById(R.id.imgUpdPetPic);

        imgUpdPetPic.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        circularImgViewPetPicEdit.setOnClickListener(this);

        ArrayAdapter<String> spinAgeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.ageArrayForPetDetails));
        spinAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAge.setAdapter(spinAgeAdapter);
        ArrayAdapter<String> spinGenderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.genderArrayForPetDetails));
        spinGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(spinGenderAdapter);

        breedContainer.setVisibility(View.GONE);
        imgUpdPetPic.setVisibility(View.GONE);

        spinAnimalType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, final View selectedItemView, int position, long id) {

                AnimalTypePojo animalType = (AnimalTypePojo) spinAnimalType.getSelectedItem();
                boolean showBreed = true;
                if(animalType.getLabel().equals("Chat")){
                    //circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.cat_icon));
                }else if(animalType.getLabel().equals("Chien")){
                    //circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.dog_icon));
                }else{
                    //circularImgViewPetPicEdit.setImageDrawable(getResources().getDrawable(R.drawable.other_icon));
                    showBreed = false;
                    breedContainer.setVisibility(View.GONE);
                }

                if (showBreed){
                    Call<List<BreedPojo>> call = breedService.getBreedsByAnimalTypeId(animalType.getId());
                    call.enqueue(new Callback<List<BreedPojo>>() {
                        @Override
                        public void onResponse(Call<List<BreedPojo>> call, Response<List<BreedPojo>> response) {
                            breeds = (ArrayList<BreedPojo>) response.body();
                            if(breeds.isEmpty()){ ((LinearLayout) spinBreed.getParent()).setVisibility(View.GONE); }
                            else {
                                BreedPojo defaultBreed = new BreedPojo();
                                defaultBreed.setLabel(getString(R.string.form_default_breed));
                                breeds.add(0, defaultBreed);
                                ArrayAdapter<BreedPojo> adapter = new ArrayAdapter<BreedPojo>(AddPetActivity.this, R.layout.support_simple_spinner_dropdown_item, breeds);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinBreed.setAdapter(adapter);
                            }
                            breedContainer.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onFailure(Call<List<BreedPojo>> call, Throwable t) {
                            //Toast.makeText(AddPetActivity.this, "Une erreur est survenue" , Toast.LENGTH_LONG).show();
                            Snackbar.make(selectedItemView, "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    private void getExtras() {
        Call<List<AnimalTypePojo>> call = animalTypeService.getAllAnimalType(gs.getAuthTokenPojo().getToken());
        call.enqueue(new Callback<List<AnimalTypePojo>>() {
            @Override
            public void onResponse(Call<List<AnimalTypePojo>> call, Response<List<AnimalTypePojo>> response) {
                animalTypesList = (ArrayList<AnimalTypePojo>) response.body();
                ArrayAdapter<AnimalTypePojo> animalTypeAdapter = new ArrayAdapter<AnimalTypePojo>(AddPetActivity.this, android.R.layout.simple_spinner_item, animalTypesList);
                animalTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinAnimalType.setAdapter(animalTypeAdapter);
            }

            @Override
            public void onFailure(Call<List<AnimalTypePojo>> call, Throwable t) {
                //Toast.makeText(AddPetActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG).show();
                Snackbar.make(AddPetActivity.this.findViewById(android.R.id.content).getRootView(), "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddPetActivity.this, MyPetsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.imgViewPetPicEdit:
                showPictureChoice();
                break;
            case R.id.imgUpdPetPic:
                showPictureChoice();
                break;
            case R.id.btnSave:
                currentDialogWait = popupWait(false);
                if (dataRequiredForForm()){
                    try { this.urlImg = new UploadImageTask(this.currentPicturePath).execute().get(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                    catch (ExecutionException e) { e.printStackTrace(); }
                    // TODO TEST : Normalement il suffira d'envoyer l'objet sans le transformer en Json
                    Call<AnimalPojo> call = animalService.createAnimal(getAnimal());
                    call.enqueue(new Callback<AnimalPojo>() {
                        @Override
                        public void onResponse(Call<AnimalPojo> call, Response<AnimalPojo> response) {
                            currentDialogWait.cancel();
                            AnimalPojo animalCreate = response.body();
                            Intent i = new Intent(AddPetActivity.this, MyPetsActivity.class);
                            startActivity(i);
                            finish();
                        }
                        @Override
                        public void onFailure(Call<AnimalPojo> call, Throwable t) {
                            currentDialogWait.cancel();
                            //Toast.makeText(AddPetActivity.this, "Une erreur est survenue" , Toast.LENGTH_LONG).show();
                            Snackbar.make(v, "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    //Toast.makeText(AddPetActivity.this, "Le nom de l'animal est obligatoire" , Toast.LENGTH_LONG).show();
                    Snackbar.make(v, "Le nom de l'animal est obligatoire", Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private AnimalPojo getAnimal(){
        AnimalPojo animal = new AnimalPojo();

        // Colors
        if(!editTxtColors.getText().toString().equals(""))
            animal.setColors(editTxtColors.getText().toString());

        //Nom
        if(!editTxtName.getText().toString().equals(""))
            animal.setName(editTxtName.getText().toString());

        // Age
        if(!spinAge.getSelectedItem().toString().equals(getString(R.string.form_default_age)))
            animal.setAge(spinAge.getSelectedItem().toString());

        // Sexe
        if(!spinGender.getSelectedItem().toString().equals(getString(R.string.form_default_gender)))
            animal.setSexe(spinGender.getSelectedItem().toString());

        // Race (Si chien ou chat)
        if(!spinBreed.getSelectedItem().toString().equals(getString(R.string.form_default_breed)))
            animal.setBreed((BreedPojo) spinBreed.getSelectedItem());

        // Tatouage
        if(!editTxtTatoo.getText().toString().equals(""))
            animal.setTatoo(editTxtTatoo.getText().toString());

        // Chip
        if(!editTxtChip.getText().toString().equals(""))
            animal.setChip(editTxtChip.getText().toString());

        // Animal Type
        animal.setAnimalType((AnimalTypePojo) spinAnimalType.getSelectedItem());

        // Animal Img
        animal.setPhoto(this.urlImg);

        // Animal User
        animal.setUser(currentUser);

        return animal;
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
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, 1);
                        } else if (which == 1) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, 2);
                            }
                        } else if (which == 2){
                            circularImgViewPetPicEdit.setVisibility(View.VISIBLE);
                            imgUpdPetPic.setVisibility(View.GONE);
                            circularImgViewPetPicEdit.setImageResource(R.drawable.addpic_icon);
                        }
                        return true; // allow selection
                    }
                })
                .positiveText("OK")
                .show();
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
                //image.recycle();
                imgUpdPetPic.setVisibility(View.VISIBLE);
                this.currentPicturePath = getPath(selectedImage);
            } else if (requestCode == 2) {
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
                circularImgViewPetPicEdit.setImageBitmap(imageBitmap);
                imgUpdPetPic.setVisibility(View.VISIBLE);
            } else {
                Log.d(TAG, "No Img");
                imgUpdPetPic.setImageResource(R.drawable.addpic_icon);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private Boolean dataRequiredForForm(){
        if(editTxtName.getText().toString().equals(""))
            return false;
        return true;
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
