package com.project.app.pilpoil.Fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.project.app.pilpoil.Activity.FindAndLostActivity;
import com.project.app.pilpoil.Metier.Adress;
import com.project.app.pilpoil.R;
import java.io.IOException;
import java.util.List;

public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private Adress adress = new Adress();
    public static final String TAG = FindAndLostActivity.class.getSimpleName();

    private final int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE
    };
    private int curMapTypeIndex = 0;


    public Adress getAdress(){
        return this.adress;
    }

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

    private void removeListeners() {
        if( getMap() != null ) {
            getMap().setOnMarkerClickListener( null );
            getMap().setOnMapLongClickListener(null);
            getMap().setOnInfoWindowClickListener(null);
            getMap().setOnMapClickListener(null);
        }
    }

    public void onSearch() {
        Activity context = getActivity();
        EditText location_tf = (EditText) context.findViewById(R.id.editTxtAddress);
        String location = location_tf.getText().toString();

        List<Address> addressList = null;

        if (location != null && !location.equals("") ) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(location, 2);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            this.onMapClick(latLng);
            getMap().addMarker(new MarkerOptions().position(latLng).title(location));
            getMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeListeners();
    }

    private void initCamera( Location location ) {
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( location.getLatitude(), location.getLongitude() ) )
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        getMap().animateCamera( CameraUpdateFactory.newCameraPosition( position ), null );

        getMap().setMapType( MAP_TYPES[curMapTypeIndex] );
        getMap().setTrafficEnabled( false );
        getMap().setMyLocationEnabled(true);
        getMap().getUiSettings().setZoomControlsEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient );

        initCamera(mCurrentLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        //handle play services disconnecting if location is being constantly used
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Create a default location if the Google API Client fails. Placing location at Googleplex
        mCurrentLocation = new Location( "" );
        mCurrentLocation.setLatitude( 37.422535 );
        mCurrentLocation.setLongitude( -122.084804 );
        initCamera(mCurrentLocation);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {}

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        getMap().clear();
        MarkerOptions options = new MarkerOptions().position( latLng );
        String adressSelect = "";
        try {
            adressSelect = getAddressFromLatLng(latLng);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EditText editTxtAddress = (EditText)getActivity().findViewById(R.id.editTxtAddress);

        if(!adressSelect.equals("")){
            options.title(adressSelect);
            editTxtAddress.setText(adressSelect);
        }

        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker(options);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        try {
            options.title( getAddressFromLatLng(latLng) );
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        options.icon(BitmapDescriptorFactory.fromBitmap(image));
        image.recycle();

        getMap().addMarker(options);
    }

    private String getAddressFromLatLng( LatLng latLng ) throws Exception {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder( getActivity() );

        addresses = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 );

        adress.setLongitude(Double.toString(latLng.longitude));
        adress.setLattitude(Double.toString(latLng.latitude));
        adress.setAdress(addresses.get(0).getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        adress.setCity(addresses.get(0).getLocality());
        adress.setCountry(addresses.get(0).getCountryName());
        adress.setPostalCode(addresses.get(0).getPostalCode());

        return adress.toString();
    }

    private void drawCircle( LatLng location ) {
        CircleOptions options = new CircleOptions();
        options.center( location );
        //Radius in meters
        options.radius(50);
        options.fillColor( getResources().getColor( R.color.colorPrimary ) );
        options.strokeColor(getResources().getColor(R.color.colorPrimaryDark));
        options.strokeWidth(10);
        getMap().addCircle(options);
    }

    private void drawPolygon( LatLng startingLocation ) {
        LatLng point2 = new LatLng( startingLocation.latitude + .001, startingLocation.longitude );
        LatLng point3 = new LatLng( startingLocation.latitude, startingLocation.longitude + .001 );

        PolygonOptions options = new PolygonOptions();
        options.add(startingLocation, point2, point3);

        options.fillColor( getResources().getColor( R.color.colorPrimary ) );
        options.strokeColor(getResources().getColor(R.color.colorPrimaryDark));
        options.strokeWidth(10);

        getMap().addPolygon(options);
    }

    private void drawOverlay( LatLng location, int width, int height ) {
        GroundOverlayOptions options = new GroundOverlayOptions();
        options.position(location, width, height);

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        options.image(BitmapDescriptorFactory
                .fromBitmap(image));
        image.recycle();
        getMap().addGroundOverlay(options);
    }

    private void toggleTraffic() {
        getMap().setTrafficEnabled(!getMap().isTrafficEnabled());
    }

    private void cycleMapType() {
        if( curMapTypeIndex < MAP_TYPES.length - 1 ) {
            curMapTypeIndex++;
        } else {
            curMapTypeIndex = 0;
        }

        getMap().setMapType(MAP_TYPES[curMapTypeIndex]);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_maps, menu);
    }
}
