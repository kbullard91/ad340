package karibullard.com.ad340App;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by karibullard on 4/30/17.
 */

public class DisplayAbout extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent intent = getIntent();

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
            Toast.makeText(DisplayAbout.this, "You have clicked on start game", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.score_board){
            Toast.makeText(DisplayAbout.this, "You have clicked on score board", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.find_players){
            Toast.makeText(DisplayAbout.this, "You have clicked on find players", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
