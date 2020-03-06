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
import com.google.android.gms.common.api.GoogleApiClient;
import com.firebase.geofire.GeoLocation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.geofire.GeoFire;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.List;
//import static com.example.myapplication1.


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.android.SphericalUtil;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, View.OnClickListener {

    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLong;
    FusedLocationProviderClient fusedLocationProviderClient;
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
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CODE = 101;
    Location currentLocation;
    FirebaseAuth auth;
    DatabaseReference dbr;
    private Button Request;
    //protected LocationManager locationManager;
    //protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    Location mLastLocation;
    LocationRequest mLocationRequest;
    private TextView distance;
    // private double SphericalUtil;
    MarkerOptions marker;

    /*  Map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

          @Override
          public void onMapLongClick(LatLng arg0) {
              if (marker != null) {
                  marker.remove();
              }
              marker = MAP.addMarker(new MarkerOptions()
                      .position(
                              new LatLng(arg0.latitude,
                                      arg0.longitude))
                      .draggable(true).visible(true));
          }
      });*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.myapplication1.R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.example.myapplication1.R.id.map1);
        mapFragment.getMapAsync(this);
        searchView = findViewById(com.example.myapplication1.R.id.sv_location);
        searchView1 = findViewById(com.example.myapplication1.R.id.sv_location1);
        Request = findViewById(R.id.request);
        auth = FirebaseAuth.getInstance();
        dbr = FirebaseDatabase.getInstance().getReference().child("CustomersRequests");
        //distance =  findViewById(com.example.myapplication1.R.id.);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    location1 = new LatLng(address.getLatitude(), address.getLongitude());
                    //place1=new MarkerOptions().position(location1).title(location);
                    Map.addMarker(new MarkerOptions().position(location1).title(location));
                    Map.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView1.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    location2 = new LatLng(address.getLatitude(), address.getLongitude());
                    //  place2=new MarkerOptions().position(location2).title(location);

                    Map.addMarker(new MarkerOptions().position(location2).title(location));
                    Map.animateCamera(CameraUpdateFactory.newLatLngZoom(location2, 10));
                    // Distance = SphericalUtil.computeDistanceBetween(location1,location2);
                    //Distance = Distance/1000;
                    //String str = new Integer((int) Distance).toString();
                    //distance.setText(str);
                    //Toast.makeText(MapsActivity.this,Distance/1000+"KM",Toast.LENGTH_LONG).show();
                    //distance = SphericalUtil.computeDistanceBetween(location1,location2);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("CustomersRequest").child(User_ID);
                DatabaseReference myref = dbr.child(auth.getCurrentUser().getUid());
                myref.child("Latitude").setValue("Har Har Mahadev");
                //myref.child("Longitude").setValue(mLastLocation.getLongitude());
                GeoFire geoFire = new GeoFire(myref);
                //  geoFire.setLocation(auth.getCurrentUser().getUid(),new GeoLocation(,mLastLocation.getLongitude()));
                // LatLng pickUpLocation = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                //mMap.addMarker(new MarkerOptions().position(pickUpLocation).title("PickUp Here"));
                Request.setText("Getting Ur Driver.....");
            }
        });
      /*  locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Map = googleMap;

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
            return;
        }
        buildGoogleApiClient();
        Map.setMyLocationEnabled(true);



        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLastLocation=location;
                if (getApplicationContext() != null) {
                    userLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.clear();
                   // Map.addMarker(new MarkerOptions().position(userLatLong).title("Your Lcation"));
                    //Map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLong, 18), 5000, null);
                    //String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DriversAvilable");
                    //GeoFire geoFire = new GeoFire(ref);
                    // geoFire.setLocation(userID, new GeoLocation(22.599749,72.820465));
                   // geoFire.setLocation(userID, new GeoLocation(location.getLatitude(), location.getLongitude());

                }

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
        };
    }
    public void onRequestPermissionResults(int requestCode, @NonNull String[] permission,@NonNull int[] grantResults){
        switch (requestCode){
            case LOCATION_REQUEST:
                if(grantResults.length>0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED)
                {
                    Map.setMyLocationEnabled(true);
                }
                break;
        }
    }

    /*
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      switch (requestCode) {
         case REQUEST_CODE:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               fetchLocation();
         }
         break;
      }
   }
    */

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
        //buildGoogleApiClient();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,  this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //@Override
    public void onLocationChanged(Location location) {
        /*if(getApplicationContext()!=null)
        {
           // mLastLocation = location;
            //LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
           // mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

           // String User_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DiversAvailable");
            //GeoFire geofire = new GeoFire(ref);
            //geofire.setLocation(User_ID,new GeoLocation(location.getLatitude(),location.getLongitude()));
        }*/

    }
    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(com.example.myapplication1.R.id.map1);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }
    protected void onStop(){
        super.onStop();
       // String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DriversAvilable");
        //GeoFire geoFire = new GeoFire(ref);
        //geoFire.removeLocation(userID);
    }

    @Override
    public void onClick(View v) {

    }
}