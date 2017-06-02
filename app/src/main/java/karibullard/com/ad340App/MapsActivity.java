package karibullard.com.ad340App;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import karibullard.com.ad340App.services.GetAddress;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class MapsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    final static int REQUEST_LOCATION = 9;

    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";
    protected static final int SUCCESS_RESULT = 0;
    protected static final int FAILURE_RESULT = 1;
    protected static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    protected static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    protected static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";


    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private GoogleMap mMap;

    protected boolean mAddressRequested;
    protected String mAddressOutput;

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected TextView mLocationText;

    private AddressResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_info_contents);
//Main App Menu
        Toolbar mToolBar = (Toolbar) findViewById(R.id.app_menu);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mResultReceiver = new AddressResultReceiver(new Handler());
        mAddressRequested = true;
        mAddressOutput = "";

        mLatitudeText = (TextView) findViewById(R.id.textLatitude);
        mLongitudeText = (TextView) findViewById(R.id.textLongitude);
        mLocationText = (TextView) findViewById(R.id.textLocation);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // connect to google services
        createGoogleApiClient();

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                ==PackageManager.PERMISSION_GRANTED){
            mLastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (mLastLocation != null) {
            Log.d("LOCATION", mLastLocation.toString());

            setMap();
            if (mAddressRequested) {
                startIntentService();
            }

            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // store map object for use once location is available
        mMap = googleMap;
    }

    public void setMap() {
        LatLng myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.setMinZoomPreference(10); // zoom to city level
        mMap.addMarker(new MarkerOptions().position(myLocation)
                .title("My current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

    }


    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d("LOCATION", "connection failed: ");
        showToast("Connection failed.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            showToast("Disconnected. Please re-connect.");
        } else if (i == CAUSE_NETWORK_LOST) {
            showToast("Network lost. Please re-connect.");
        }
    }

    protected synchronized void createGoogleApiClient() {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, GetAddress.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(RESULT_DATA_KEY);
            mLocationText.setText(mAddressOutput);

            // Show a toast message if an address was found.
            if (resultCode == SUCCESS_RESULT) {
                showToast(getString(R.string.address_found));
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false;
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.about){
            Intent intent = new Intent(this, DisplayAbout.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.recycler_view){
            Intent intent = new Intent(this, DisplayInfoListActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.location){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.option_get_place) {
            Toast.makeText(MapsActivity.this, "Not available", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.start_game){
            Toast.makeText(MapsActivity.this, "You have clicked on start game", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.score_board){
            Toast.makeText(MapsActivity.this, "You have clicked on score board", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.find_players){
            Toast.makeText(MapsActivity.this, "You have clicked on find players", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }
}
