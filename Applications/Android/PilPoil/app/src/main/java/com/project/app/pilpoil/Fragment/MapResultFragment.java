package com.project.app.pilpoil.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapResultFragment extends SupportMapFragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleApiClient mGoogleApiClient = null;

    public static final String TAG = MapResultFragment.class.getSimpleName();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        initListeners();
    }

    private void initListeners() {
        getMap().setOnMarkerClickListener(this);
        //getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
        getMap().setOnMapClickListener(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "::: onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "::: onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "::: onConnectionFailed");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "::: onInfoWindowClick on marker : " + marker);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "::: onMapClick on latLng : " + latLng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d(TAG, "::: onMapLongClick on latLng: " + latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "::: onMarkerClick on marker : " + marker);
        return false;
    }
}
