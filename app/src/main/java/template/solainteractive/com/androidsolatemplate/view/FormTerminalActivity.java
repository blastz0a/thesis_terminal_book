package template.solainteractive.com.androidsolatemplate.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Response;
import template.solainteractive.com.androidsolatemplate.BuildConfig;
import template.solainteractive.com.androidsolatemplate.Constants;
import template.solainteractive.com.androidsolatemplate.Contract.FormTerminalContract;
import template.solainteractive.com.androidsolatemplate.MyApplication;
import template.solainteractive.com.androidsolatemplate.Presenter.Terminal.FormTerminalPresenter;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.Utils.Utils;
import template.solainteractive.com.androidsolatemplate.base.BaseActivity;
import template.solainteractive.com.androidsolatemplate.connection.APIBody;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionCallback;
import template.solainteractive.com.androidsolatemplate.connection.ConnectionManager;
import template.solainteractive.com.androidsolatemplate.connection.RetrofitServices;
import template.solainteractive.com.androidsolatemplate.controller.RateAdapter;
import template.solainteractive.com.androidsolatemplate.model.MainResponse;
import template.solainteractive.com.androidsolatemplate.model.RateModel;
import template.solainteractive.com.androidsolatemplate.view.SignIn.SigninActivity;

public class FormTerminalActivity extends BaseActivity implements FormTerminalContract.View,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "TAG";
    private static int RESULT_LOAD_IMG = 1300;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1000;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1100;
    private static final int REQUEST_ACCESS_CAMERA = 1200;

    @BindView(R.id.rlForm)
    RelativeLayout form;
    @BindView(R.id.tvID)
    TextView tvID;
    @BindView(R.id.tvScanResult)
    TextView tvScanResult;
    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.tvLatitude)
    TextView tvLatitudeResult;
    @BindView(R.id.tvLongitude)
    TextView tvLongitudeResult;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.btnUpload)
    Button btnUpload;
    @BindView(R.id.ivCode)
    ImageView ivCode;
    @BindView(R.id.tvterminalName)
    TextView tvterminalName;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.tvTerminalType)
    TextView tvTerminalType;
    @BindView(R.id.rbTerminalType1)
    RadioButton rbTerminalType1;
    @BindView(R.id.rbTerminalType2)
    RadioButton rbTerminalType2;
    @BindView(R.id.rbTerminalType3)
    RadioButton rbTerminalType3;
    @BindView(R.id.rgTerminalType)
    RadioGroup rgTerminalType;
    @BindView(R.id.tvTerminalOpenTime)
    TextView tvTerminalOpenTime;
    @BindView(R.id.etTerminalOpen)
    EditText etTerminalOpen;
    @BindView(R.id.tvTerminaLCloseTime)
    TextView tvTerminaLCloseTime;
    @BindView(R.id.etTerminalClose)
    EditText etTerminalClose;
    @BindView(R.id.etLatitude)
    EditText etLatitude;
    @BindView(R.id.etLongitude)
    EditText etLongitude;
    @BindView(R.id.tvNetworkType)
    TextView tvNetworkType;
    @BindView(R.id.etNetworkType)
    EditText etNetworkType;
    @BindView(R.id.tvPostalCode)
    TextView tvPostalCode;
    @BindView(R.id.etPostalCode)
    EditText etPostalCode;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.etDescription)
    EditText etDescription;
    @BindView(R.id.tvMetadata)
    TextView tvMetadata;
    @BindView(R.id.etMetadata)
    EditText etMetadata;
    @BindView(R.id.btnMap)
    Button btnMap;
    @BindView(R.id.toolbarList)
    Toolbar toolbarList;
    @BindView(R.id.toolbar_title)
    ImageView toolbarTitle;
    @BindView(R.id.ic_terminal_active_status_id)
    ImageView icTerminalActiveStatusId;
    @BindView(R.id.tvTerminalActiveStatus)
    TextView tvTerminalActiveStatus;
    @BindView(R.id.rbTerminalActive1)
    RadioButton rbTerminalActive1;
    @BindView(R.id.rbTerminalActive2)
    RadioButton rbTerminalActive2;
    @BindView(R.id.rgTerminaLActiveStatus)
    RadioGroup rgTerminalActiveStatus;
    @BindView(R.id.ic_terminal_rate)
    ImageView icTerminalRate;
    @BindView(R.id.tvTerminalRate)
    TextView tvTerminalRate;
    @BindView(R.id.spinnerRate)
    Spinner spinnerRate;
    @BindView(R.id.ic_terminal_avatar)
    ImageView icTerminalAvatar;
    @BindView(R.id.tvterminalAvatar)
    TextView tvterminalAvatar;
    @BindView(R.id.iv_terminal_avatar)
    ImageView ivTerminalAvatar;

    private FormTerminalContract.Presenter formTerminalPresenter;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    public double latitude = 0.0, longitude = 0.0;
    double currLatitude, currLongitude;


    private static final int REQUEST_CODE_PERMISSION = 2;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String address, name, metatag, timeOpen, timeClose, network, postalCode, description, scanResult, activeStatus, avatarPicture;
    String encodedImage;
    String mCurrentPhotoPath, imageFileName, imgChosen;
    int typeID, choosenOptionID;
    boolean ivTerminal = false;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_form_terminal);
        ButterKnife.bind(this);

        formTerminalPresenter = new FormTerminalPresenter(this);

        Utils.setupAppToolbarForActivity(FormTerminalActivity.this,toolbarList,"Create New Terminal");
        tvScanResult.setText("");
        etTerminalOpen.setInputType(InputType.TYPE_NULL);
        etTerminalClose.setInputType(InputType.TYPE_NULL);
        ivCode.setVisibility(View.GONE);
        tvID.setVisibility(View.GONE);
        tvScanResult.setVisibility(View.GONE);
        rbTerminalActive1.setChecked(true);


        setSupportActionBar(toolbarList);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarList.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        formTerminalPresenter.showLocation();
        formTerminalPresenter.loadAvatarImage();
        formTerminalPresenter.btnScanClick();
        formTerminalPresenter.btnMapClick();

        setTimeOpen();
        setTimeClose();

        getRateAPI();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        if (this.getIntent().getExtras() != null) {
            Utils.setupAppToolbarForActivity(FormTerminalActivity.this,toolbarList,"Update Terminal");
            BundleExist();
            enableUpdateBtn();
            btnScan.setVisibility(View.GONE);
            btnUpload.setText("UPDATE");
        }

        etName.addTextChangedListener(new EditTextListener());
        etAddress.addTextChangedListener(new EditTextListener());
        etMetadata.addTextChangedListener(new EditTextListener());
        etTerminalOpen.addTextChangedListener(new EditTextListener());
        etTerminalClose.addTextChangedListener(new EditTextListener());
        etNetworkType.addTextChangedListener(new EditTextListener());
        etPostalCode.addTextChangedListener(new EditTextListener());
        etDescription.addTextChangedListener(new EditTextListener());
        radioGroupValidation();

        back();

    }

    public void BundleExist() {
        typeID = getIntent().getExtras().getInt("terminal_type_id");
        if (typeID == 1) {
            rbTerminalType1.setChecked(true);
        } else if (typeID == 2) {
            rbTerminalType2.setChecked(true);
        } else if (typeID == 3) {
            rbTerminalType3.setChecked(true);
        }

        activeStatus = getIntent().getExtras().getString("terminal_active_status");
        if (activeStatus.equals("0")) {
            rbTerminalActive1.setChecked(true);
        } else {
            rbTerminalActive2.setChecked(true);
        }

        address = getIntent().getExtras().getString("terminal_address");
        name = getIntent().getExtras().getString("terminal_name");
        scanResult = getIntent().getExtras().getString("terminal_id");
        tvScanResult.setVisibility(View.VISIBLE);
        ivCode.setVisibility(View.VISIBLE);
        timeOpen = getIntent().getExtras().getString("terminal_open");
        timeClose = getIntent().getExtras().getString("terminal_closed");
        currLatitude = getIntent().getExtras().getDouble("terminal_latitude");
        currLongitude = getIntent().getExtras().getDouble("terminal_longitude");
        network = getIntent().getExtras().getString("network_type");
        postalCode = getIntent().getExtras().getString("postal_code");
        description = getIntent().getExtras().getString("description");
        metatag = getIntent().getExtras().getString("metadata");

        etAddress.setText(address);
        etName.setText(name);
        tvScanResult.setText(scanResult);
        etTerminalOpen.setText(timeOpen);
        etTerminalClose.setText(timeClose);
        etLatitude.setText(String.valueOf(currLatitude));
        etLongitude.setText(String.valueOf(currLongitude));
        etNetworkType.setText(network);
        etPostalCode.setText(postalCode);
        etDescription.setText(description);
        etMetadata.setText(metatag);

        avatarPicture = getIntent().getExtras().getString("avatar_picture");
        Glide.with(this)
                .load(avatarPicture)
                .dontAnimate()
                .placeholder(R.drawable.recharge_logo)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivTerminalAvatar);
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
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                ivCode.setVisibility(View.VISIBLE);
                tvID.setVisibility(View.VISIBLE);
                tvScanResult.setVisibility(View.VISIBLE);
                tvScanResult.setText(result.getContents());
                formTerminalPresenter.showLocation();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                DecimalFormat df = new DecimalFormat("#.#######");

                latitude = data.getExtras().getDouble("latitude", 0.0);
                longitude = data.getExtras().getDouble("longitude", 0.0);

                //convert data to be send to backend
                currLatitude = Double.valueOf(df.format(latitude));
                currLongitude = Double.valueOf(df.format(longitude));

                String mlati = String.valueOf(df.format(latitude));
                String mLongi = String.valueOf(df.format(longitude));

                etLatitude.setText(mlati);
                etLongitude.setText(mLongi);
            } else if (requestCode == RESULT_LOAD_IMG) {
                try {
                    //compressImage();
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    encodedImage = gallerytoString(selectedImage);
                    Glide.with(this).load(imageUri).fitCenter().into(ivTerminalAvatar);
                    ivTerminal = true;
                    System.out.println("Base64 image selected from gallery : " + encodedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == CAMERA_REQUEST) {
                compressImage();
                System.out.println("Base64 image captured : " + encodedImage);
                Glide.with(this).load(mCurrentPhotoPath).fitCenter().into(ivTerminalAvatar);
                ivTerminal = true;
            }
        }
        if (getIntent().getExtras() != null) {
            enableUpdateBtn();
        } else {
            enableUploadBtn();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        String Latitude = String.valueOf(currentLatitude);
        String Longitude = String.valueOf(currentLongitude);

        tvLatitudeResult.setText(Latitude);
        tvLongitudeResult.setText(Longitude);
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    public void enableUploadBtn() {
        address = etAddress.getText().toString();
        name = etName.getText().toString();
        metatag = etMetadata.getText().toString();
        timeOpen = etTerminalOpen.getText().toString();
        timeClose = etTerminalClose.getText().toString();
        network = etNetworkType.getText().toString();
        postalCode = etPostalCode.getText().toString();
        description = etDescription.getText().toString();
        scanResult = tvScanResult.getText().toString();
        activeStatus = String.valueOf(rgTerminalActiveStatus.indexOfChild(findViewById(rgTerminalActiveStatus.getCheckedRadioButtonId())));

        if (address.isEmpty() || name.isEmpty() || metatag.isEmpty() || scanResult.isEmpty()
                || timeOpen.isEmpty() || timeClose.isEmpty() || network.isEmpty()
                || postalCode.isEmpty() || description.isEmpty() || postalCode.length() != 5 || ivTerminal == false) {
            btnUpload.setEnabled(false);
            btnUpload.setBackgroundResource(R.drawable.my_button_disabled);
        } else {
            btnUpload.setBackgroundResource(R.drawable.my_button);
            btnUpload.setEnabled(true);
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("id : " + scanResult);
                    System.out.println("typeID : " + typeID);
                    System.out.println("name : " + name);
                    System.out.println("address : " + address);
                    System.out.println("metatag : " + metatag);
                    System.out.println("open : " + timeOpen);
                    System.out.println("close : " + timeClose);
                    System.out.println("network : " + network);
                    System.out.println("postal : " + postalCode);
                    System.out.println("desc : " + description);
                    System.out.println("lat : " + currLatitude);
                    System.out.println("long : " + currLongitude);
                    System.out.println("active : " + activeStatus);
                    System.out.println("RateID : " + choosenOptionID);
                    postUploadTerminalAPI();
                }
            });
        }
    }

    public void enableUpdateBtn() {
        address = etAddress.getText().toString();
        name = etName.getText().toString();
        metatag = etMetadata.getText().toString();
        timeOpen = etTerminalOpen.getText().toString();
        timeClose = etTerminalClose.getText().toString();
        network = etNetworkType.getText().toString();
        postalCode = etPostalCode.getText().toString();
        description = etDescription.getText().toString();
        scanResult = tvScanResult.getText().toString();
        avatarPicture = getIntent().getExtras().getString("avatar_picture");
        if (avatarPicture != null){
            ivTerminal = true;
        }
        System.out.println("Avatar Picture : "+avatarPicture);
        System.out.println("Has Pict : "+ivTerminal);
        activeStatus = String.valueOf(rgTerminalActiveStatus.indexOfChild(findViewById(rgTerminalActiveStatus.getCheckedRadioButtonId())));

        if (address.isEmpty() || name.isEmpty() || metatag.isEmpty() || scanResult.isEmpty()
                || timeOpen.isEmpty() || timeClose.isEmpty() || network.isEmpty()
                || postalCode.isEmpty() || description.isEmpty() || postalCode.length() != 5 || ivTerminal == false) {
            btnUpload.setEnabled(false);
            btnUpload.setBackgroundResource(R.drawable.my_button_disabled);
        } else {
            btnUpload.setBackgroundResource(R.drawable.my_button);
            btnUpload.setEnabled(true);
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postUpdateTerminalAPI();
//                    System.out.println("id : " + scanResult);
//                    System.out.println("typeID : " + typeID);
//                    System.out.println("name : " + name);
//                    System.out.println("address : " + address);
//                    System.out.println("metatag : " + metatag);
//                    System.out.println("open : " + timeOpen);
//                    System.out.println("close : " + timeClose);
//                    System.out.println("network : " + network);
//                    System.out.println("postal : " + postalCode);
//                    System.out.println("desc : " + description);
//                    System.out.println("lat : " + currLatitude);
//                    System.out.println("long : " + currLongitude);
//                    System.out.println("active : " + activeStatus);
//                    System.out.println("RateID : " + choosenOptionID);
                }
            });
        }
    }

    @Override
    public void setShowLocation(){
        gps = new GPSTracker(this);

        if (gps.canGetLocation()) {
            currLatitude = gps.getLatitude();
            currLongitude = gps.getLongitude();

            String currentLatitude = String.valueOf(currLatitude);
            String currentLongitude = String.valueOf(currLongitude);

            etLatitude.setText(currentLatitude);
            etLongitude.setText(currentLongitude);
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public void setLoadAvatarImage() {
        ivTerminalAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(FormTerminalActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_change_avatar);

                // set the custom dialog components - text, image and button
                TextView text = dialog.findViewById(R.id.txt_dia);
                text.setText("Avatar must be in landscape mode!");

                Button btnCapture = dialog.findViewById(R.id.btn_capture);
                Button btnGallery = dialog.findViewById(R.id.btn_gallery);

                // capture
                btnCapture.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

                        System.out.println("OPEN CAMERA");
                        //takePicture();
                        checkAndRequestPermissions();
                        if(checkAndRequestPermissions()){
                            dispatchTakePictureIntent();
                            dialog.dismiss();
                        }

                    }
                });

                // gallery
                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAndRequestPermissions();
                        if (checkAndRequestPermissions()){
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.setType("image/*");
                            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                            dialog.dismiss();
                        }
                    }

                });
                dialog.show();

            }
        });
    }

    @Override
    public void setBtnScanClick() {
        btnScan.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(FormTerminalActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
                Utils.showSnackBar(form,"Scan");
                integrator.setOrientationLocked(false);
            }
        });
    }

    @Override
    public void setBtnMapClick() {
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INI DI COMMENT
//                mLocationRequest = LocationRequest.create()
//                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                        .setInterval(10 * 1000)        // 10 seconds, in milliseconds
//                        .setFastestInterval(1 * 1000); // 1 second, in milliseconds
//                try {
//                    if (ActivityCompat.checkSelfPermission(FormTerminalActivity.this, mPermission) != MockPackageManager.PERMISSION_GRANTED) {
//                        onBackPressed();
////                        ActivityCompat.requestPermissions(FormTerminalActivity.this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
//                    }
//                    else{
//                        onBackPressed();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                checkLocationPermission();
                Bundle b = new Bundle();
                b.putDouble("latitude", currLatitude);
                b.putDouble("longitude", currLongitude);
                Intent map = new Intent(FormTerminalActivity.this, MapsActivity.class);
                map.putExtras(b);
                startActivityForResult(map, 1);
            }
        });
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
    public void setOnSuccessGetRateAPI(final RateModel rateModel) {
        RateAdapter rateAdapter = new RateAdapter(FormTerminalActivity.this, R.layout.spinner_item, rateModel.getRateList());
        spinnerRate.setAdapter(rateAdapter);

        if (getIntent().getExtras() != null) {
            choosenOptionID = getIntent().getExtras().getInt("rate_id");
            for (int i = 0; i < rateModel.rateList.size(); i++) {
                if (rateModel.rateList.get(i).getRateId() == choosenOptionID) {
                    spinnerRate.setSelection(i);
                    break;
                }
            }
        }

        spinnerRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                choosenOptionID = rateModel.rateList.get(position).getRateId();
                if (getIntent().getExtras() != null) {
                    enableUpdateBtn();
                } else {
                    enableUploadBtn();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setOnFailedGetRateAPI(String message) {
        Utils.showSnackBar(spinnerRate, message);
    }

    @Override
    public void setOnFailureGetRateAPI(String message) {

    }

    @Override
    public void setOnSuccessPostTerminalAPI(MainResponse mainResponse) {
        dismissProgressDialog();
        Utils.showSnackBar(form, mainResponse.getMessage());
//        Toast.makeText(FormTerminalActivity.this, "Success Add Terminal", Toast.LENGTH_LONG).show();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent openIntent = new Intent(FormTerminalActivity.this,ShowTerminalActivity.class);
                openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getApplication().startActivity(openIntent);
            }
        },500);
//        Intent openIntent = new Intent(FormTerminalActivity.this, ShowTerminalActivity.class);
//        openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        getApplicationContext().startActivity(openIntent);
    }

    @Override
    public void setOnFailedPostTerminalAPI(String message) {
        dismissProgressDialog();
        Utils.showSnackBar(tvID, message);
    }

    @Override
    public void setOnFailurePostTerminalAPI(String message) {
        dismissProgressDialog();
        Utils.showSnackBar(tvID, message);
    }

    @Override
    public void setOnSuccessUpdateTerminalAPI(MainResponse mainResponse) {
        dismissProgressDialog();
//        Utils.showSnackBar(form, mainResponse.getMessage());
        Toast.makeText(FormTerminalActivity.this, "Success Update Terminal", Toast.LENGTH_LONG).show();
        Intent openIntent = new Intent(FormTerminalActivity.this, ShowTerminalActivity.class);
        openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getApplicationContext().startActivity(openIntent);
    }

    @Override
    public void setOnFailedUpdateTerminalAPI(String message) {
        dismissProgressDialog();
        Utils.showSnackBar(tvID, message);
        Toast.makeText(FormTerminalActivity.this, message, Toast.LENGTH_LONG).show();
        Intent openIntent = new Intent(FormTerminalActivity.this, ShowTerminalActivity.class);
        openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getApplicationContext().startActivity(openIntent);
    }

    @Override
    public void setOnFailureUpdateTerminalAPI(String message) {
        dismissProgressDialog();
        Utils.showSnackBar(tvID, message);
    }

    private class EditTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (getIntent().getExtras() != null) {
                enableUpdateBtn();
            } else {
                enableUploadBtn();
            }
        }
    }

    private void postUploadTerminalAPI() {
        showProgressDialog();
        formTerminalPresenter.postUploadTerminalAPI(scanResult, currLatitude, currLongitude, name, address, metatag, typeID,
                timeOpen, timeClose, network, postalCode, description, activeStatus, choosenOptionID, encodedImage);
    }

    private void postUpdateTerminalAPI() {
        System.out.println("url : " + Constants.URL_API.BASE_URL + Constants.URL_API.UPDATE);
        showProgressDialog();
        formTerminalPresenter.postUpdateTerminalAPI(scanResult, currLatitude, currLongitude, name, address, metatag, typeID,
                timeOpen, timeClose, network, postalCode, description, activeStatus, choosenOptionID, encodedImage);
    }

    public void setTimeOpen() {
        etTerminalOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTerminalOpen.getText().toString().isEmpty()) {
                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(getFragmentManager(), "TimeOpen");
                } else {
                    int hours = Integer.parseInt(etTerminalOpen.getText().toString().substring(0, 2));

                    String last = etTerminalOpen.getText().toString();
                    last = last.substring(last.length() - 2);
                    int minutes = Integer.parseInt(last);

                    DialogFragment newFragment = new TimePickerFragment().newInstance1(hours, minutes);
                    newFragment.show(getFragmentManager(), "TimeOpen");
                }
            }
        });
    }

    public void setTimeClose() {
        etTerminalClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTerminalClose.getText().toString().isEmpty()) {
                    DialogFragment newFragment2 = new TimePickerFragment2();
                    newFragment2.show(getFragmentManager(), "TimeClose");
                } else {
                    int hours = Integer.parseInt(etTerminalClose.getText().toString().substring(0, 2));

                    String last = etTerminalClose.getText().toString();
                    last = last.substring(last.length() - 2);
                    int minutes = Integer.parseInt(last);

                    DialogFragment newFragment2 = new TimePickerFragment2().newInstance2(hours, minutes);
                    newFragment2.show(getFragmentManager(), "TimeClose");
                }
            }
        });
    }


    public void getRateAPI() {
        System.out.println("tokenterminal : " + MyApplication.getInstance().getAccessToken());
        formTerminalPresenter.getRateAPI();
    }

    public void radioGroupValidation() {
        rgTerminalType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                typeID = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId())) + 1;
                System.out.println("type : " + typeID);
                if (typeID != 0) {
                    if (getIntent().getExtras() != null) {
                        enableUpdateBtn();
                    } else {
                        enableUploadBtn();
                    }
                }
            }
        });

        rgTerminalActiveStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                activeStatus = String.valueOf(radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId())));
                System.out.println("active status : " + activeStatus);
            }
        });

    }

    private String gallerytoString(Bitmap selectedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private String capturetoString(Bitmap photo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void dispatchTakePictureIntent() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePicture.resolveActivity(this.getPackageManager()) != null) {
            File photo = null;
            try {
                photo = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photo != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    try {
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
                        startActivityForResult(takePicture, CAMERA_REQUEST);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                    startActivityForResult(takePicture, CAMERA_REQUEST);
                }
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void compressImage() {
        File file = new File(Uri.parse(mCurrentPhotoPath).getPath());
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap original = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(is), 1000, 800, true);

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(Uri.parse(mCurrentPhotoPath).getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    original = rotateImage(original, 90);
                    encodedImage = capturetoString(original);

                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    original = rotateImage(original, 180);
                    encodedImage = capturetoString(original);

                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    original = rotateImage(original, 270);
                    encodedImage = capturetoString(original);

                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    encodedImage = capturetoString(original);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream stream = new FileOutputStream(file);
            original.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private  boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= 19) {
            int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "camera & storage permission granted");
                        // process the normal flow
                        // else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage permissions required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
            case MY_PERMISSIONS_REQUEST_LOCATION:{
                    //KALAU USER ALLOW PADA PERMISSION ACCESS LOCATION
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                            setShowLocation();
                        }
                    }else{

                    }
                    return;
            }

        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
}