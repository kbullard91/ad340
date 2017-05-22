package karibullard.com.ad340App;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import karibullard.com.ad340App.model.Player;
import karibullard.com.ad340App.services.GetCurrentPlayers;
import karibullard.com.ad340App.utils.NetworkHelper;

public class DisplayInfoListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
//    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private static String TAG = DisplayInfoListActivity.class.getSimpleName();
    private static final String url = "http://kbullard.icoolshow.net/ad340/single.json";


    RequestQueue requestQueue;
    List<Player> players;
    boolean networkOk;


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Player[] activePlayers = (Player[]) intent.getParcelableArrayExtra(GetCurrentPlayers.MY_SERVICE_PAYLOAD);
            //turn data received into a list
            players = new ArrayList<>(Arrays.asList(activePlayers));
            displayPlayers();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_info_list);

//Main App Menu
        Toolbar mToolBar = (Toolbar) findViewById(R.id.app_menu);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        //Setup view for RecyclerViewAdapter
        recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setHasFixedSize(true);

        //Check for network connection, and then tell it the service you'd like to run
        networkOk = NetworkHelper.hasNetworkAccess(this);
        if (networkOk) {
            Intent intent = new Intent(this, GetCurrentPlayers.class);
            intent.setData(Uri.parse(url));
            startService(intent);
        } else {
            Toast.makeText(DisplayInfoListActivity.this, "Network Not Available", Toast.LENGTH_SHORT).show();
        }

        //Registering Response listener
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(GetCurrentPlayers.MY_SERVICE_MESSAGE));
    }

    /**
     * Displays received data in the recycler view
     */
    private void displayPlayers(){
        if (players != null) {
            recyclerViewAdapter = new RecyclerViewAdapter(this, players);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    /**
     * Inflates the menu for the view
     * @param menu Our meny object
     * @return process completed flag
     */
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
        if(item.getItemId() == R.id.start_game){
            Toast.makeText(DisplayInfoListActivity.this, "You have clicked on start game", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.score_board){
            Toast.makeText(DisplayInfoListActivity.this, "You have clicked on score board", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.find_players){
            Toast.makeText(DisplayInfoListActivity.this, "You have clicked on find players", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
