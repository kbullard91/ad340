package karibullard.com.ad340App;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by karibullard on 4/27/17.
 */

public class DisplayInfoList extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    ConnectionDetector cd;
    String url = "http://kbullard.icoolshow.net/ad340/single.json";
    ArrayList<String> users;
    private static String TAG = DisplayInfoList.class.getSimpleName();
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        users = new ArrayList<String>();
        requestQueue = Volley.newRequestQueue(this);
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "got it.");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });
        requestQueue.add(stringRequest);






        setContentView(R.layout.activity_info_list);

        context = getApplicationContext();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recylerViewLayoutManager = new GridLayoutManager(context, 2);

        recyclerView.setLayoutManager(recylerViewLayoutManager);


        recyclerViewAdapter = new RecyclerViewAdapter(context, users);

        recyclerView.setAdapter(recyclerViewAdapter);

        //Main App Menu
        Toolbar mToolBar = (Toolbar) findViewById(R.id.app_menu);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        cd = new ConnectionDetector(this);
        if (cd.isConnected()) {
            Toast.makeText(DisplayInfoList.this, "Connected!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DisplayInfoList.this, "Not Connected!", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Inflates the menu for the view
     *
     * @param menu Our meny object
     * @return process completed flag
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, DisplayAbout.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.recycler_view) {
            Intent intent = new Intent(this, DisplayInfoList.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.start_game) {
            Toast.makeText(DisplayInfoList.this, "You have clicked on start game", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.score_board) {
            Toast.makeText(DisplayInfoList.this, "You have clicked on score board", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.find_players) {
            Toast.makeText(DisplayInfoList.this, "You have clicked on find players", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }

}
