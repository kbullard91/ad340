package karibullard.com.ad340App.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import karibullard.com.ad340App.DisplayInfoListActivity;
import karibullard.com.ad340App.model.Player;
import karibullard.com.ad340App.utils.HttpHelper;

public class GetCurrentPlayers extends IntentService {

    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    private static String TAG = DisplayInfoListActivity.class.getSimpleName();
    //The key used by Broadcast Receiver in DisplayInfoListActivity to access response data
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";

    public GetCurrentPlayers(){
        super("GetCurrentPlayers");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //Get Response Data
        Uri uri = intent.getData();
        String response;
        try {
            response = HttpHelper.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //Create Player Objects from Response Data
        try {
            JSONObject players = new JSONObject(response);
            JSONArray playerObjects = players.optJSONArray("active_players");
            Player[] playersArray = new Player[playerObjects.length()];
            for (int i = 0; i < playerObjects.length(); i++){
                JSONObject activePlayer = playerObjects.getJSONObject(i);
                int id = activePlayer.getInt("id");
                String firstName = activePlayer.getString("first_name");
                String lastName = activePlayer.getString("last_name");
                String phone = activePlayer.getString("phone");
                int playStatus = activePlayer.getInt("play_status");
                String image = activePlayer.getString("avatar");
                Player player = new Player(id, firstName, lastName,  phone, playStatus, image);
                playersArray[i] = player;
            }
            //Send data back to recycler view
            Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
            messageIntent.putExtra(MY_SERVICE_PAYLOAD, playersArray);
            LocalBroadcastManager manager =
                    LocalBroadcastManager.getInstance(getApplicationContext());
            manager.sendBroadcast(messageIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
