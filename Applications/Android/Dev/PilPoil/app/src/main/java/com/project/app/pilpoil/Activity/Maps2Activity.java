package com.project.app.pilpoil.Activity;

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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Dialog.DialogGooglePlayServicesUnavailable;
import com.project.app.pilpoil.Interface.AdService;
import com.project.app.pilpoil.Metier.AdPojo;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GPSUtil;
import com.project.app.pilpoil.Service.GlobalState;
import com.project.app.pilpoil.Service.PermissionUtils;
import com.project.app.pilpoil.Service.Utils;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Maps2Activity extends BaseActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        SeekBar.OnSeekBarChangeListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener {

    /**
     * Declarations
     */
    private static double radius = 2000;  // in meters
    private static final int DISTANCE_MAX = 50000;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private Bundle bundle;
    private Integer currentAnimalTypeId;
    private ArrayList<LatLng> lattitudesLongitudes = null;
    private ArrayList<AdPojo> ads = null;
    private ArrayList<AdPojo> adsSelection = null;
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private GPSUtil gps = null;
    private SeekBar mDistanceBar = null;
    private List<DrawCircle> mCircles = new ArrayList<DrawCircle>(1);
    private float[] distance = new float[2];
    private UiSettings mUiSettings = null;
    private int stepSize = 1000;
    private static int mDistanceSaved = 0;
    private Marker lastMarker = null;
    private Double latitude = 0.0;;
    private Double longitude = 0.0;;
    private GlobalState gs = null;
    private HashMap <String, AdPojo> hashMap = new HashMap<String, AdPojo>();
    private LinearLayout filterDistance = null;
    private TextView distanceValue = null;
    private boolean mPermissionDenied = false;
    private GoogleMap mMap = null;
    private GoogleApiClient mGoogleApiClient;
    private Utils utils;

    final static int REQUEST_LOCATION = 199;

    /**
     * Custom Window Adapter
     */
    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;
        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int badge;

            //badge = R.drawable.badge_qld;
            //((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            // Spannable string allows us to edit the formatting of the text.
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(0xFF990D00), 10, titleText.length(), 0);
            titleUi.setText(titleText);

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            SpannableString snippetText = new SpannableString(snippet);
            snippetUi.setText(snippetText);
        }
    }

    /**
     * Draw circle on map
     */
    private class DrawCircle {
        private final Circle circle;
        private double radius;
        public DrawCircle(LatLng center, double radius) {
            this.radius = radius;
            circle = mMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(radius)
                    .strokeWidth(0)
                    .fillColor(0x25990D00));
        }

        public LatLng getCenter() { return circle.getCenter(); }
        public Double getRadius() { return circle.getRadius(); }

        // Use to change parameters on change progress bar immediately
        public void onStyleChange() {
            circle.setRadius((double) mDistanceBar.getProgress());
            for (Marker marker : markers) {
                marker.remove();
            }
            if(null != ads)
                addMarkersToMap();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        gs = (GlobalState) getApplication();
        this.init();

        this.launchActivity();
    }

    private void init() {
        utils = new Utils(Maps2Activity.this);
        gps = new GPSUtil(Maps2Activity.this);
        filterDistance = (LinearLayout) findViewById(R.id.filterDistance);
        lattitudesLongitudes = new ArrayList<LatLng>();
        mDistanceBar = (SeekBar) findViewById(R.id.distanceSeekBar);
        distanceValue = (TextView) findViewById(R.id.distanceValue);
    }

    private void launchActivity() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDistanceBar.setMax(DISTANCE_MAX);
        if (mDistanceSaved > 0)
            mDistanceBar.setProgress(mDistanceSaved);
        else
            mDistanceBar.setProgress(this.kmToMeters(this.getCurrentUser().getDistance()));
        distanceValue.setText(metersToKm(mDistanceBar.getProgress()) + " km");

        this.getExtras();

        if (currentAnimalTypeId == 1)
            setTitle(R.string.title_activity_maps_result_cat);
        if (currentAnimalTypeId == 2)
            setTitle(R.string.title_activity_maps_result_chien);
    }

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
                                status.startResolutionForResult(Maps2Activity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
            if (!utils.isGPSEnabled()) {
                enableLoc();
            }
        }
    }

    /**
     * When map is loaded
     *
     * @param map
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);

        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);

        mDistanceBar.setOnSeekBarChangeListener(this);

        enableMyLocation();

        DrawCircle circle = new DrawCircle(gps.getCurrentLocation(), radius);
        mCircles.add(circle);

        for (DrawCircle drawCircle : mCircles) {
            drawCircle.onStyleChange();
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gps.getCurrentLocation(), 10));

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    /**
     * Add markers on the map
     */
    private void addMarkersToMap() {
        adsSelection = new ArrayList<AdPojo>();
        markers.clear();

        for (AdPojo ad : ads) {
            latitude = Double.parseDouble(ad.getLattitude());
            longitude = Double.parseDouble(ad.getLongitude());
            Location.distanceBetween(latitude, longitude, gps.getLatitude(), gps.getLongitude(), distance);
            LatLng latLng = new LatLng(latitude, longitude);
            if(distance[0] <= mCircles.get(0).getRadius()) {
                adsSelection.add(ad);
                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker())
                        .title("Trouvé le " + ad.getDate())
                        .snippet(ad.getAdress() + ", " + ad.getCity());
                lastMarker = mMap.addMarker(options);
                markers.add(lastMarker);
                hashMap.put(lastMarker.getId(), ad);
            }
        }
        //if (adsSelection == null){
            //Toast.makeText(MapsActivity2.this, "Aucune annonce dans votre secteur." , Toast.LENGTH_LONG).show();
        //}

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) { return; }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /**
     * Actions when moved seekbar distance
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!checkReady()) {
            return;
        }
        progress = (progress / stepSize) * stepSize;
        if (progress < 1000)
            progress = 1000;
        seekBar.setProgress(progress);
        distanceValue.setText(this.metersToKm(mDistanceBar.getProgress()) + " km");

        for (DrawCircle drawCircle : mCircles) {
            drawCircle.onStyleChange();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Don't do anything here.
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Don't do anything here.
    }

    /**
     * Get extras from intent
     */
    private void getExtras() {
        bundle = getIntent().getExtras();

        if (null != bundle) {
            currentAnimalTypeId = getIntent().getExtras().getInt("currentAnimalTypeId");

            if (currentAnimalTypeId > 0) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AdService adService = retrofit.create(AdService.class);
                Call<List<AdPojo>> call = adService.getAdsByAnimalTypeId(currentAnimalTypeId, gs.getAuthTokenPojo().getToken());
                call.enqueue(new Callback<List<AdPojo>>() {
                    @Override
                    public void onResponse(Call<List<AdPojo>> call, Response<List<AdPojo>> response) {
                        ads = (ArrayList<AdPojo>) response.body();
                        addMarkersToMap();
                    }
                    @Override
                    public void onFailure(Call<List<AdPojo>> call, Throwable t) {
                        int toto = 2+2;
                    }
                });
            }
        }
    }

    private UserPojo getCurrentUser() {
        gs = (GlobalState) getApplication();
        return gs.getCurrentUser();
    }

    /**
     * Go to AdDetailsActivity with parameters ad, ads and typeAnimalId
     * @param v
     */
    public void viewAd(View v){
        Button b = (Button) v;
        RelativeLayout layoutButton = (RelativeLayout) b.getParent();
        LinearLayout layout = (LinearLayout) layoutButton.getParent();
        TextView txtViewId = (TextView) layout.getChildAt(0);
        Integer adId = Integer.parseInt((String)txtViewId.getText());
        AdPojo adSelect = null;
        if(null != adsSelection){
            for(AdPojo ad : adsSelection){
                if(ad.getId().intValue() == adId.intValue()){
                    adSelect = ad;
                    break;
                }
            }
        }
        if(null != adSelect){
            Intent i = new Intent(Maps2Activity.this, AdDetailsActivity.class);
            i.putExtra("ad", adSelect);
            i.putExtra("ads", this.adsSelection);
            i.putExtra("typeAnimalId", this.currentAnimalTypeId);
            startActivity(i);
            finish();
        }
    }

    /**
     * Convert meters to km
     *
     * @param distanceMeters
     * @return int
     */
    private int metersToKm(int distanceMeters) {
        return Integer.valueOf(distanceMeters / 1000);
    }

    /**
     * Convert km to meters
     *
     * @param distanceKm
     * @return int
     */
    private int kmToMeters(int distanceKm) {
        return Integer.valueOf(distanceKm * 1000);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Maps2Activity.this, MyPetsActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Verify if map is ready
     *
     * @return boolean
     */
    private boolean checkReady() {
        if (mMap == null) {
            //Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            Snackbar.make(Maps2Activity.this.findViewById(android.R.id.content).getRootView(), R.string.map_not_ready, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        AdPojo adSelect = (AdPojo) hashMap.get(marker.getId());
        Intent i = new Intent(Maps2Activity.this, AdDetailsActivity.class);
        i.putExtra("ad", adSelect);
        i.putExtra("ads", this.adsSelection);
        i.putExtra("typeAnimalId", this.currentAnimalTypeId);
        startActivity(i);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        mDistanceSaved = mDistanceBar.getProgress();
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Get options display in menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps_result, menu);
        return(super.onCreateOptionsMenu(menu));
    }

    /**
     * Actions on items in menu
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.filter:
                if (filterDistance.getVisibility() == View.VISIBLE)
                    filterDistance.setVisibility(View.INVISIBLE);
                else
                    filterDistance.setVisibility(View.VISIBLE);
                break;
        }
        return(super.onOptionsItemSelected(item));
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
}
