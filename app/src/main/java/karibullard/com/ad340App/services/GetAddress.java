package karibullard.com.ad340App.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import karibullard.com.ad340App.R;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

/**
 * Created by karibullard on 6/1/17.
 */

public class GetAddress extends IntentService{

    private static final String TAG = "FetchAddressIS";
    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";
    protected static final int SUCCESS_RESULT = 0;
    protected static final int FAILURE_RESULT = 1;
    protected static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    protected static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    protected static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
        /**
         * The receiver where results are forwarded from this service.
         */
        protected ResultReceiver mReceiver;

        /**
         * This constructor is required, and calls the super IntentService(String)
         * constructor with the name for a worker thread.
         */
        public GetAddress() {
            // Use the TAG to name the worker thread.
            super(TAG);
        }

        /**
         * Tries to get the location address using a Geocoder. If successful, sends an address to a
         * result receiver. If unsuccessful, sends an error message instead.
         * Note: We define a {@link android.os.ResultReceiver} in * MainActivity to process content
         * sent from this service.
         *
         * This service calls this method from the default worker thread with the intent that started
         * the service. When this method returns, the service automatically stops.
         */
        @Override
        protected void onHandleIntent(Intent intent) {
            String errorMessage = "";

            mReceiver = intent.getParcelableExtra(RECEIVER);

            // Check if receiver was properly registered.
            if (mReceiver == null) {
                Log.wtf(TAG, "No receiver received. There is nowhere to send the results.");
                return;
            }

            // Get the location passed to this service through an extra.
            Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);

            // Make sure that the location data was really sent over through an extra. If it wasn't,
            // send an error error message and return.
            if (location == null) {
                errorMessage = getString(R.string.no_location_data);
                Log.wtf(TAG, errorMessage);
                deliverResultToReceiver(FAILURE_RESULT, errorMessage);
                return;
            }

            // Errors could still arise from using the Geocoder (for example, if there is no
            // connectivity, or if the Geocoder is given illegal location data). Or, the Geocoder may
            // simply not have an address for a location. In all these cases, we communicate with the
            // receiver using a resultCode indicating failure. If an address is found, we use a
            // resultCode indicating success.

            // The Geocoder used in this sample. The Geocoder's responses are localized for the given
            // Locale, which represents a specific geographical or linguistic region. Locales are used
            // to alter the presentation of information such as numbers or dates to suit the conventions
            // in the region they describe.
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            // Address found using the Geocoder.
            List<Address> addresses = null;

            try {
                // Using getFromLocation() returns an array of Addresses for the area immediately
                // surrounding the given latitude and longitude. The results are a best guess and are
                // not guaranteed to be accurate.
                addresses = geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        // In this sample, we get just a single address.
                        1);
            } catch (IOException ioException) {
                // Catch network or other I/O problems.
                errorMessage = getString(R.string.service_not_available);
                Log.e(TAG, errorMessage, ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                // Catch invalid latitude or longitude values.
                errorMessage = getString(R.string.invalid_lat_long_used);
                Log.e(TAG, errorMessage + ". " +
                        "Latitude = " + location.getLatitude() +
                        ", Longitude = " + location.getLongitude(), illegalArgumentException);
            }

            // Handle case where no address was found.
            if (addresses == null || addresses.size()  == 0) {
                if (errorMessage.isEmpty()) {
                    errorMessage = getString(R.string.no_address_found);
                    Log.e(TAG, errorMessage);
                }
                deliverResultToReceiver(FAILURE_RESULT, errorMessage);
            } else {
                Address address = addresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();
                for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressFragments.add(address.getAddressLine(i));
                }
                Log.i(TAG, getString(R.string.address_found));
                deliverResultToReceiver(SUCCESS_RESULT,
                        TextUtils.join(System.getProperty("line.separator"), addressFragments));
            }
        }

        /**
         * Sends a resultCode and message to the receiver.
         */
        private void deliverResultToReceiver(int resultCode, String message) {
            Bundle bundle = new Bundle();
            bundle.putString(RESULT_DATA_KEY, message);
            mReceiver.send(resultCode, bundle);
        }
    }

