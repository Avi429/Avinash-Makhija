package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.widget.Button;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.gms.location.FusedLocationProviderClient;


public class DriverMap extends FragmentActivity implements OnMapReadyCallback/*GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener*/{

    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLong;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private GoogleMap Map;
    private SupportMapFragment mapFragment;
    private SearchView searchView;
    private SearchView searchView1;
    private static final int LOCATION_REQUEST = 500;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    // Button getDirection;
    private Polyline currentPolyline;
    private LatLng location1, location2;
    double Distance;
   // private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CODE = 101;
    Location currentLocation;
    FirebaseAuth auth;
    DatabaseReference dbr;
    private Button Request;
    LatLng latLng;
    //protected LocationManager locationManager;
    //protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    Location mLastlocation;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    private TextView distance;
    // private double SphericalUtil;
    MarkerOptions marker;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.example.myapplication1.R.id.map1);
        mapFragment.getMapAsync(this);
        auth = FirebaseAuth.getInstance();
        dbr = FirebaseDatabase.getInstance().getReference().child("CustomersRequests");
        //distance =  findViewById(com.example.myapplication1.R.id.);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //fetchLocation();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Map = googleMap;
        LatLng Charusat=new LatLng(22.599749,72.820465);
        Map.addMarker(new MarkerOptions().position(Charusat).title("Your Location"));
        Map.moveCamera(CameraUpdateFactory.newLatLng(Charusat));
        Map.animateCamera(CameraUpdateFactory.newLatLngZoom(Charusat,18),5000,null);
        //buildGoogleApiClient();
        Map.setMyLocationEnabled(true);




    }
   /* @Override
    public void onLocationChanged(Location location) {
        //mLastlocation =location;
        //LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
     //   mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
       // mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        //Map.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }*/
}