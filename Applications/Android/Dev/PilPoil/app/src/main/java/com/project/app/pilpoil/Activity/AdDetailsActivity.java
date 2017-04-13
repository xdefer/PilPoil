package com.project.app.pilpoil.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.AdService;
import com.project.app.pilpoil.Interface.UserService;
import com.project.app.pilpoil.Metier.AdPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GlobalState;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.project.app.pilpoil.R.*;

import java.util.ArrayList;


public class AdDetailsActivity extends BaseActivity implements View.OnClickListener {

    private AdPojo adToSee;
    private ArrayList<AdPojo> lstAds;
    private ImageView next, prev, petPic, phone, email;
    private TextView findingDate, adress, ville, breed, tatoo, rescued, age, gender, colors, precisions, report;
    private LinearLayout activityLayout;
    GlobalState gs;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private AdService adService;

    private MaterialDialog currentWaitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_ad_details);
        FacebookSdk.sdkInitialize(getApplicationContext());

        if (getIntent().getExtras() != null) {
            /* Get Intent */
            adToSee = (AdPojo) getIntent().getSerializableExtra("ad");
            lstAds = (ArrayList<AdPojo>)getIntent().getSerializableExtra("ads");
        }

        //FacebookSdk.getApplicationSignature(getApplicationContext());
        /*callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);*/

        gs = (GlobalState) getApplication();

        /* Get Elements */
        phone = (ImageView) findViewById(id.phone);
        email = (ImageView) findViewById(id.email);
        next = (ImageView) findViewById(id.imgViewNext);
        prev = (ImageView) findViewById(id.imgViewPrev);
        petPic = (ImageView) findViewById(id.imgViewPetPic);
        findingDate = (TextView) findViewById(id.txtViewFindingDate);
        adress = (TextView) findViewById(id.txtViewAdress);
        ville = (TextView) findViewById(id.txtViewVille);
        breed = (TextView) findViewById(id.animalBreed);
        tatoo = (TextView) findViewById(id.animalTatoo);
        rescued = (TextView) findViewById(id.animalRescued);
        age = (TextView) findViewById(id.animalAge);
        gender = (TextView) findViewById(id.animalGender);
        colors = (TextView) findViewById(id.animalColors);
        precisions = (TextView) findViewById(id.adPrecisions);
        report = (TextView) findViewById(id.reportAd);


        activityLayout = (LinearLayout)this.findViewById(id.activityLayout);


        /* Set Click Listener */
        phone.setOnClickListener(this);
        email.setOnClickListener(this);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        report.setOnClickListener(this);

        if(null != adToSee)
            initForm();
    }

    private void initForm(){
        // Btn next and prev hide if just 1 ad in array
        if(lstAds.size() <= 1){
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
            activityLayout.setOnTouchListener(activitySwipeDetector);
        }
        // Animal Img
        if(null != adToSee.getAnimal().getPhoto())
            Picasso.with(AdDetailsActivity.this).load(adToSee.getAnimal().getPhoto()).into(petPic);
        else{
            if(adToSee.getAnimal().getAnimalType().getId() == 1)
                petPic.setImageDrawable(getResources().getDrawable(drawable.cat_icon));
            else if (adToSee.getAnimal().getAnimalType().getId() == 2)
                petPic.setImageDrawable(getResources().getDrawable(drawable.dog_icon));
            else
                petPic.setImageDrawable(getResources().getDrawable(drawable.other_icon));
            petPic.setBackgroundResource(color.black);
        }
        // adDate + userCreator
        if(null != adToSee.getUser()){
            boolean phoneBool = true;
            findingDate.setText(adToSee.getDate() + getString(string.by) + adToSee.getUser().getLogin());
            if(null == adToSee.getUser().getPhone()){
                LinearLayout phoneLayout = (LinearLayout) phone.getParent();
                phoneLayout.setVisibility(View.GONE);
                phoneBool = false;
            }
            if(null == adToSee.getUser().getEmail()){
                LinearLayout emailLayout = (LinearLayout) email.getParent();
                emailLayout.setVisibility(View.GONE);
                if(!phoneBool){
                    LinearLayout bottomLayout = (LinearLayout) emailLayout.getParent();
                    bottomLayout.setVisibility(View.GONE);
                }
            }
        }
        else {
            boolean phoneBool = true;
            if(null == adToSee.getPhone()){
                LinearLayout phoneLayout = (LinearLayout) phone.getParent();
                phoneLayout.setVisibility(View.GONE);
                phoneBool = false;
            }
            if(null == adToSee.getEmail()){
                LinearLayout emailLayout = (LinearLayout) email.getParent();
                emailLayout.setVisibility(View.GONE);
                if(!phoneBool){
                    LinearLayout bottomLayout = (LinearLayout) emailLayout.getParent();
                    bottomLayout.setVisibility(View.GONE);
                }
            }
            findingDate.setText(" " + adToSee.getDate());
        }
        // Localisation
        adress.setText(adToSee.getAdress());
        ville.setText(adToSee.getCity() + " " + adToSee.getPostalCode());
        // Animal Breed
        if(null != adToSee.getAnimal().getBreed())
            breed.setText(adToSee.getAnimal().getBreed().getLabel());
        else
            breed.setText(getString(string.notIndic));
        // animalTatoo
        if(null != adToSee.getAnimal().getTatoo())
            tatoo.setText(adToSee.getAnimal().getTatoo());
        else
            tatoo.setText(getString(string.notIndic));
        // animalGender
        if(null != adToSee.getAnimal().getSexe())
            gender.setText(adToSee.getAnimal().getSexe());
        else
            gender.setText(getString(string.notIndic));
        // animalAge
        if(null != adToSee.getAnimal().getAge())
            age.setText(adToSee.getAnimal().getAge());
        else
            age.setText(getString(string.notIndic));
        // Animal Recover
        if(null != adToSee.getRecover())
            if(adToSee.getRecover() == true)
                rescued.setText(getString(string.form_rescYes));
            else
                rescued.setText(getString(string.form_rescNo));
        else
            rescued.setText(getString(string.form_rescNo));
        // AnimalColor
        if(null != adToSee.getAnimal().getColors()) {
            colors.setText(adToSee.getAnimal().getColors());
        } else
            colors.setText(getString(string.notIndic));
        // Ad precisions
        if(null != adToSee.getDescription())
            precisions.setText(adToSee.getDescription());
        else
            precisions.setText(getString(string.notIndic));
    }

    // Button Listener
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case id.phone:
                showPopUpCall();
                break;
            case id.email:
                i = new Intent(Intent.ACTION_SEND);
                String emailSubject = getString(string.ad) + " " + adToSee.getAnimal().getAnimalType().getLabel() + " " + getString(string.lost) + " " + getString(string.in) + " " + adToSee.getCity();
                i.setType("message/rfc822");
                String userEmail = "";
                if(null != adToSee.getEmail()) {
                    if (!adToSee.getEmail().equals(""))
                        userEmail = adToSee.getEmail();
                }
                if(null != adToSee.getUser()) {
                    if (!adToSee.getUser().getEmail().equals(""))
                        userEmail = adToSee.getUser().getEmail();
                }
                if (userEmail.equals("")){
                    //Toast.makeText(AdDetailsActivity.this, getString(string.emailError), Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, getString(string.emailError), Snackbar.LENGTH_SHORT).show();
                    break;
                }
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{userEmail});
                i.putExtra(Intent.EXTRA_SUBJECT, "PilPoil : " + emailSubject);
                //i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, getString(string.emailSend)));
                } catch (android.content.ActivityNotFoundException ex) {
                    //Toast.makeText(AdDetailsActivity.this, getString(string.emailError), Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, getString(string.emailError), Snackbar.LENGTH_SHORT).show();
                }
                break;
            //case R.id.share:
                //facebookShare();
                //break;
            case id.reportAd:
                currentWaitDialog = popupWait(false);
                showPopupSignalement();
                //Toast.makeText(AdDetailsActivity.this, "L'annonce n°: " + adToSee.getId().toString() + " a été signalé" , Toast.LENGTH_SHORT).show();
                //Snackbar.make(v, "L'annonce n°: " + adToSee.getId().toString() + " a été signalé", Snackbar.LENGTH_SHORT).show();
                break;
            case id.imgViewNext:
                i = new Intent(AdDetailsActivity.this, AdDetailsActivity.class);
                i.putExtra("currentAnimalTypeId", adToSee.getAnimal().getAnimalType().getId());
                i.putExtra("ads", lstAds);
                i.putExtra("ad", findNextAd(lstAds, adToSee));
                startActivity(i);
                overridePendingTransition(anim.slide_in, anim.slide_out);
                finish();
                break;
            case id.imgViewPrev:
                i = new Intent(AdDetailsActivity.this, AdDetailsActivity.class);
                i.putExtra("currentAnimalTypeId", adToSee.getAnimal().getAnimalType().getId());
                i.putExtra("ads", lstAds);
                i.putExtra("ad", findPrevAd(lstAds, adToSee));
                startActivity(i);
                overridePendingTransition(anim.slide_in2, anim.slide_out2);
                finish();
                break;
            default:
                break;
        }
    }

    private AdPojo findNextAd(ArrayList<AdPojo> ads, AdPojo ad){
        for(int i = 0; i < ads.size(); i++){
            if(ads.get(i).getId().intValue() == ad.getId().intValue()){
                if(i+1 < ads.size())
                    return ads.get(i+1);
                else
                    return ads.get(0);
            }
        }
        return ad;
    }

    private AdPojo findPrevAd(ArrayList<AdPojo> ads, AdPojo ad){
        for(int i = 0; i < ads.size(); i++){
            if(ads.get(i).getId().intValue() == ad.getId().intValue()){
                if(i-1 >= 0)
                    return ads.get(i-1);
                else
                    return ads.get(ads.size()-1);
            }
        }
        return ad;
    }

    public void showPopUpCall() {
        new MaterialDialog.Builder(this)
                .content(string.callUser)
                .positiveText(R.string.call)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String tel;
                        if(null != adToSee.getPhone())
                            tel = adToSee.getPhone();
                        else
                            tel = adToSee.getUser().getPhone();
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel)));
                    }
                })
                .show();


    }

    public void facebookShare(){
        // TODO : Gestion Fb pas DL
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            Bitmap image = BitmapFactory.decodeResource(getResources(), drawable.cat_icon);
            SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
            SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
            //image.recycle();
            shareDialog.show(content);
        }
    }

    public void showPopupSignalement() {
        new MaterialDialog.Builder(this)
                .title(R.string.signalAbus)
                .negativeText("Annuler")
                .items(array.arraySignalement)
                .itemsCallbackSingleChoice(2, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, final View view, int which, CharSequence text) {
                        adService = retrofit.create(AdService.class);
                        Call<ResponseBody> rep = adService.reportAd(adToSee.getId(), text.toString(), gs.getAuthTokenPojo().getToken());
                        rep.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                currentWaitDialog.cancel();
                                if (response.code() == 200) {
                                    //Toast.makeText(getBaseContext(), "Signalement envoyé !", Toast.LENGTH_LONG).show();
                                    Snackbar.make(view, "Signalement envoyé !", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(getBaseContext(), "Erreur de signalement", Toast.LENGTH_LONG).show();
                                    Snackbar.make(view, "Erreur de signalement", Snackbar.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                currentWaitDialog.cancel();
                                //Toast.makeText(getBaseContext(), "Erreur", Toast.LENGTH_LONG).show();
                                Snackbar.make(view, "Erreur", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                        return true; // allow selection
                    }
                })
                .positiveText("Signaler")
                .show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AdDetailsActivity.this, Maps2Activity.class);
        i.putExtra("currentAnimalTypeId", adToSee.getAnimal().getAnimalType().getId());
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
            Intent i = new Intent(AdDetailsActivity.this, AdDetailsActivity.class);
            i.putExtra("currentAnimalTypeId", adToSee.getAnimal().getAnimalType().getId());
            i.putExtra("ads", lstAds);
            i.putExtra("ad", findNextAd(lstAds, adToSee));
            startActivity(i);
            overridePendingTransition(anim.slide_in, anim.slide_out);
            finish();
        }

        public void onLeftSwipe(){
            Log.i(logTag, "LeftToRightSwipe!");
            Intent i = new Intent(AdDetailsActivity.this, AdDetailsActivity.class);
            i.putExtra("currentAnimalTypeId", adToSee.getAnimal().getAnimalType().getId());
            i.putExtra("ads", lstAds);
            i.putExtra("ad", findPrevAd(lstAds, adToSee));
            startActivity(i);
            overridePendingTransition(anim.slide_in2, anim.slide_out2);
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
