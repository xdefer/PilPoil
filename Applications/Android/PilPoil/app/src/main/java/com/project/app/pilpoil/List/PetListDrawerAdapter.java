package com.project.app.pilpoil.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.joanzapata.iconify.widget.IconTextView;
import com.project.app.pilpoil.Activity.FindAndLostActivity;
import com.project.app.pilpoil.Activity.Maps2Activity;
import com.project.app.pilpoil.Activity.MyPetsActivity;
import com.project.app.pilpoil.Activity.PetDetailActivity;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.AdService;
import com.project.app.pilpoil.Interface.AnimalService;
import com.project.app.pilpoil.Metier.AdPojo;
import com.project.app.pilpoil.Metier.AnimalPojo;
import com.project.app.pilpoil.Metier.AuthTokenPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetListDrawerAdapter extends BaseAdapter {

    Context mContext;
    AuthTokenPojo token;
    ArrayList<AnimalPojo> petList;
    private Utils utils;
    private GoogleApiClient mGoogleApiClient;
    final static int REQUEST_LOCATION = 199;

    public PetListDrawerAdapter(Context context, ArrayList<AnimalPojo> petListItem, AuthTokenPojo token) {
        this.mContext = context;
        this.petList = petListItem;
        this.token = token;
        this.utils = new Utils(context);
    }

    @Override
    public int getCount() {
        return petList.size();
    }

    @Override
    public Object getItem(int position) {
        return petList.get(position);
    }

    public AnimalPojo getNavItem(int position)
    {
        return petList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_my_pets, null);
        }
        else { view = convertView; }

        final AnimalPojo currentPet = this.petList.get(position);
        final RelativeLayout animalStateButton = (RelativeLayout) view.findViewById(R.id.animalStateButton);
        final RelativeLayout animalRescuedButton = (RelativeLayout) view.findViewById(R.id.animalRescuedSlide);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AdService adService = retrofit.create(AdService.class);
        Call<List<AdPojo>> call = adService.getAdsByAnimalId(currentPet.getId(), token.getToken());
        call.enqueue(new Callback<List<AdPojo>>() {
            @Override
            public void onResponse(Call<List<AdPojo>> call, Response<List<AdPojo>> response) {
                final ArrayList<AdPojo> ads = (ArrayList<AdPojo>) response.body();
                if (ads.size() <= 0){
                    animalStateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PetListDrawerAdapter.this.declarePetLost(currentPet);
                        }
                    });
                    animalRescuedButton.setVisibility(View.GONE);
                } else {
                    IconTextView itv = (IconTextView)animalStateButton.getChildAt(0);
                    itv.setText("{fa-search}");
                    animalStateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PetListDrawerAdapter.this.viewPetLostDeclaration(currentPet);
                        }
                    });
                    animalRescuedButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PetListDrawerAdapter.this.declarePetRescued(ads, convertView);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<AdPojo>> call, Throwable t) {
                //Toast.makeText(convertView.getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                Snackbar.make(convertView, "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
            }
        });

        SwipeLayout swipeLayout =  (SwipeLayout) view.findViewById(R.id.petItem);
        ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView shortDesc  = (TextView) view.findViewById(R.id.shortDesc);
        RelativeLayout deleteButton = (RelativeLayout) view.findViewById(R.id.deletePetButton);

        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        avatar.setColorFilter(Color.parseColor("#660000"));

        if (null != currentPet.getName()) {
            name.setText(currentPet.getName());
        }

        if (currentPet.getAnimalType() != null) {
            if (currentPet.getPhoto() == null){
                if (currentPet.getAnimalType().getLabel().equals("Chat")) {
                    avatar.setImageResource(R.drawable.cat_icon);
                } else if (currentPet.getAnimalType().getLabel().equals("Chien")){
                    avatar.setImageResource(R.drawable.dog_icon);
                } else {
                    avatar.setImageResource(R.drawable.other_icon);
                }
                //avatar.setBackgroundResource(R.color.black);
            }else{
                Picasso.with(this.mContext).load(currentPet.getPhoto()).into(avatar);
            }

        }

        ArrayList<String> shortDescText = new ArrayList<String>();
        if (currentPet.getAnimalType() != null)
            shortDescText.add(currentPet.getAnimalType().getLabel());

        if (currentPet.getAge() != null && !currentPet.getAge().equals(""))
            shortDescText.add(currentPet.getAge());

        if (null != currentPet.getChip() && !currentPet.getChip().equals(""))
            shortDescText.add(currentPet.getChip());

        shortDesc.setText(android.text.TextUtils.join(" - ", shortDescText));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.LOGK_MYPETSACTIVITY, String.valueOf(currentPet.getId()));
                PetListDrawerAdapter.this.deleteAnimal(currentPet.getId(), convertView);
            }
        });

        swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetListDrawerAdapter.this.viewAnimalDetail(currentPet);
            }
        });

        return view;
    }

    private void declarePetLost(AnimalPojo animal) {
        Intent i = new Intent(this.mContext, FindAndLostActivity.class);
        i.putExtra("adTypeId", 1);
        i.putExtra("animal",  animal);
        this.mContext.startActivity(i);
        ((Activity)this.mContext).finish();
    }

    private void declarePetRescued(ArrayList<AdPojo> ads, final View convertView) {
        Integer adToArchive = null;
        for(AdPojo ad : ads){
            if(ad.getAdType().getId() == 1)
                adToArchive = ad.getId();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AdService adService = retrofit.create(AdService.class);
        if(adToArchive != null) {
            Call<ResponseBody> call = adService.adToArchive(adToArchive, token.getToken());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200){
                        Intent i = new Intent(mContext, MyPetsActivity.class);
                        mContext.startActivity(i);
                        ((Activity)mContext).finish();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //Toast.makeText(convertView.getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                    Snackbar.make(convertView, "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void viewPetLostDeclaration(AnimalPojo animal) {
        if (!hasGPSDevice(mContext)) {
            Snackbar.make(((Activity)mContext).findViewById(android.R.id.content).getRootView(), "Votre appareil ne dispose pas de GPS", Snackbar.LENGTH_SHORT).show();
        } else {
            if (utils.isGPSEnabled()) {
                Intent i = new Intent(this.mContext, Maps2Activity.class);
                i.putExtra("currentAnimalTypeId", animal.getAnimalType().getId());
                this.mContext.startActivity(i);
                ((Activity) this.mContext).finish();
            } else {
                mGoogleApiClient = null;
                enableLoc();
            }
        }
    }

    private void enableLoc() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
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
                                if (mContext instanceof MyPetsActivity) {
                                    status.startResolutionForResult((MyPetsActivity)mContext, REQUEST_LOCATION);
                                }
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

    private void deleteAnimal(int id, final View convertView) {
        final String toDeleteID = Integer.toString(id);
        Log.d(Constants.LOGK_MYPETSACTIVITY, "Deleting this animal with ID" + id);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.mContext);
        alertDialog.setTitle("Suppression d'un animal");
        alertDialog.setMessage("Voulez-vous supprimer cet animal de votre liste ?");
        alertDialog.setPositiveButton(R.string.form_deleteanimal_confirm_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(Constants.LOGK_MYPETSACTIVITY, "requesting the deletion of animal with ID" + toDeleteID);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AnimalService animalService = retrofit.create(AnimalService.class);
                Call<ResponseBody> call = animalService.deleteAnimal(Integer.parseInt(toDeleteID), token.getToken());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Intent i = new Intent(mContext, MyPetsActivity.class);
                            mContext.startActivity(i);
                            ((Activity)mContext).finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Toast.makeText(convertView.getContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                        Snackbar.make(convertView, "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void viewAnimalDetail(AnimalPojo animal) {
        Intent i = new Intent(this.mContext, PetDetailActivity.class);
        i.putExtra("animal", animal);
        i.putExtra("animals", petList);
        this.mContext.startActivity(i);
        ((Activity)this.mContext).finish();
    }
}