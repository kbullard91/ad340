package karibullard.com.ad340App;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_MESSAGE = "karibullard.com.ad340App";
    Context context;
    Button[] buttons = new Button[4];
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alt);

        //Adds the button grid to the screen
        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new ButtonAdapter(this, buttons));

        //Keeps keyboard from autoloading
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Main App Menu
        Toolbar mToolBar = (Toolbar) findViewById(R.id.app_menu);
        setSupportActionBar(mToolBar);

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
            Toast.makeText(MainActivity.this, "You have clicked on start game", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.score_board){
            Toast.makeText(MainActivity.this, "You have clicked on score board", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.find_players){
            Toast.makeText(MainActivity.this, "You have clicked on find players", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }

    /** Sends Message to new Screen */
    public void sendMessage(View view) {
        String message_id;
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}
