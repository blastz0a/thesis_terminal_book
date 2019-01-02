package template.solainteractive.com.androidsolatemplate.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import template.solainteractive.com.androidsolatemplate.Contract.MapListContract;
import template.solainteractive.com.androidsolatemplate.Presenter.MapList.MapListPresenter;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.Utils.Utils;
import template.solainteractive.com.androidsolatemplate.model.Terminal;

public class MapListActivity extends AppCompatActivity implements MapListContract.View,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @BindView(R.id.ivTerminal)
    ImageView ivTerminal;
    @BindView(R.id.tv_terminal_name)
    TextView tvTerminalName;
    @BindView(R.id.linearNama)
    LinearLayout linearNama;
    @BindView(R.id.tv_terminal_type_id)
    TextView tvTerminalTypeId;
    //    @BindView(R.id.ic_terminal_latitude)
//    ImageView icTerminalLatitude;
//    @BindView(R.id.tv_terminal_latitude)
//    TextView tvTerminalLatitude;
//    @BindView(R.id.ic_terminal_longitude)
//    ImageView icTerminalLongitude;
//    @BindView(R.id.tv_terminal_longitude)
//    TextView tvTerminalLongitude;
    @BindView(R.id.linearLatLong)
    LinearLayout linearLatLong;
    @BindView(R.id.ic_terminal_open_time)
    ImageView icTerminalOpenTime;
    @BindView(R.id.tv_terminal_open)
    TextView tvTerminalOpen;
    @BindView(R.id.tv_terminal_closed)
    TextView tvTerminalClosed;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.linearInactive)
    LinearLayout linearInactive;
    @BindView(R.id.cvTerminal)
    CardView cvTerminal;
    @BindView(R.id.flMapList)
    FrameLayout flMapList;
    @BindView(R.id.llTerminal)
    LinearLayout llTerminal;
    @BindView(R.id.ll_map)
    RelativeLayout llMap;
    @BindView(R.id.btnGetDirection)
    Button btnGetDirection;
//    @BindView(R.id.toolbar_title)
//    ImageView toolbarTitle;
    @BindView(R.id.toolbarList)
    Toolbar toolbarList;
    @BindView(R.id.ic_terminal_address)
    ImageView icTerminalAddress;
    @BindView(R.id.tv_terminal_address)
    TextView tvTerminalAddress;

    private MapListContract.Presenter mapListPresenter;

    List<Terminal> terminalList;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private LatLng mCenterLatLong;
    private LatLngBounds bounds;
    private LatLngBounds.Builder builder;
    private boolean isFirstOpen = true;

    String latitudeCenter, longitudeCenter, latitudeFormat, longitudeFormat;
    double currLat, currLong;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        ButterKnife.bind(this);

        mapListPresenter = new MapListPresenter(this);
        Utils.setupAppToolbarForActivity(MapListActivity.this,toolbarList,"Terminal Map View");
        setSupportActionBar(toolbarList);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarList.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        back();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkLocationPermission();

        mapListPresenter.getTerminalList();

    }

    public void back() {
        toolbarList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestSettings();
    }

    public void requestSettings() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currLat = location.getLatitude();
        currLong = location.getLongitude();
        if (isFirstOpen) {
            mapListPresenter.getTerminalList();
            isFirstOpen = false;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-6.175392, 106.827153)) //-0.782975
                .zoom(12)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(true);

        mGoogleApiClient.disconnect();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void setOnSuccessGetTerminalList(List<Terminal> terminalList) {
        this.terminalList = terminalList;

        if (mMap == null) {
            showToast();
        } else {
            mMap.clear();
            mapListPresenter.showTerminalResult(terminalList);
        }
    }

    @Override
    public void showTerminalMarker(int index, LatLng latLng, String activeStatus) {
        int icon = activeStatus.contentEquals("1") ? R.drawable.icon_active : R.drawable.icon_nonactive;
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(icon)));
        marker.setTag(index);

    }

    @Override
    public void showToast() {
        Toast.makeText(this, "Still on developing", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int index = (int) marker.getTag();

        centeredMarker(marker.getPosition());
        initTerminalListCard(index);
        Utils.showSnackBar(llMap,"Terminal click : " + terminalList.get(index).getTerminalName());
//        Toast.makeText(this, "Terminal click : " + terminalList.get(index).getTerminalName(), Toast.LENGTH_SHORT).show();

        return true;
    }

    private void centeredMarker(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15).tilt(75)
                .build();

        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cu);
    }

    private void initTerminalListCard(final int index) {
        llTerminal.setVisibility(View.VISIBLE);
        tvTerminalName.setText(terminalList.get(index).getTerminalName());
        tvTerminalTypeId.setText(terminalList.get(index).getTerminalId());
        tvTerminalAddress.setText(terminalList.get(index).getTerminalAddress());
//        tvTerminalLatitude.setText(String.valueOf(terminalList.get(index).getTerminalLatitude()));
//        tvTerminalLongitude.setText(String.valueOf(terminalList.get(index).getTerminalLatitude()));
        tvTerminalOpen.setText(terminalList.get(index).getTerminalOpenTime());
        tvTerminalClosed.setText(terminalList.get(index).getTerminalClosedTime());

        Glide.with(this)
                .load(terminalList.get(index).getAvatarPicture())
                .dontAnimate()
                .placeholder(R.drawable.recharge_logo)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivTerminal);

        if (terminalList.get(index).getTerminalActiveStatus().equals("0")) {
            linearInactive.setVisibility(View.VISIBLE);
        } else {
            linearInactive.setVisibility(View.GONE);
        }

        btnGetDirection.setVisibility(View.VISIBLE);
        onListClicked(index);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                //KALAU USER ALLOW PADA PERMISSION ACCESS LOCATION
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//                        boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//                        Location location;
//
//                        if (network_enabled){
//                            location  = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                            if (location!= null){
//
//                            }
//                        }
                    }
                } else {
                    onBackPressed();
                }
                return;
            }
        }
    }


    private void onListClicked(final int index) {
        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.com/maps/dir/?api=1&destination=" + terminalList.get(index).getTerminalLatitude() + "," + terminalList.get(index).getTerminalLongitude() + "&travelmode=driving";
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}
