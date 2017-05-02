package karibullard.com.ad340App;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import static karibullard.com.ad340App.MainActivity.EXTRA_MESSAGE;


public class DisplayMessageActivity extends AppCompatActivity {

    private static final String TAG = "DisplayMessageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        TextView display_message = (TextView)findViewById(R.id.textView);
        display_message.setText(message);

        //Main App Menu
        Toolbar mToolBar = (Toolbar) findViewById(R.id.app_menu);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

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
            Intent intent = new Intent(this, DisplayInfoList.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.start_game){
            Toast.makeText(DisplayMessageActivity.this, "You have clicked on start game", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.score_board){
            Toast.makeText(DisplayMessageActivity.this, "You have clicked on score board", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.find_players){
            Toast.makeText(DisplayMessageActivity.this, "You have clicked on find players", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
