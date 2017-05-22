package karibullard.com.ad340App;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import karibullard.com.ad340App.model.Player;

public class PlayerDetailActivity extends AppCompatActivity {

    private TextView firstName, lastName, bio;
    private ImageView itemImage;
    private String url = "http://kbullard.icoolshow.net/ad340/images/Profile_avatar_placeholder_large.png";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_detail_activity);

        Player player = getIntent().getExtras().getParcelable(RecyclerViewAdapter.ITEM_KEY);
        if (player == null) {
            throw new AssertionError("Null data item received!");
        }

        //Main App Menu
        Toolbar mToolBar = (Toolbar) findViewById(R.id.app_menu);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        TextView playerName = (TextView) findViewById(R.id.playerName);
        TextView playerStatus = (TextView) findViewById(R.id.playerStatus);
        TextView playerAction = (TextView) findViewById(R.id.pair_unpair_button);
        //set player name
        String fullName = player.getFirstName() + " " + player.getLastName();
        playerName.setText(fullName);

        //set button text based on current player status
        if(player.getPlayStatus() == 0){
            playerAction.setEnabled(true);
            playerAction.setText(R.string.player_status_action);
            playerStatus.setText(R.string.player_status_available);
        } else {
            playerAction.setEnabled(false);
            playerAction.setText(R.string.player_action_unavailabke);
            playerStatus.setText(R.string.player_action_unavailable);
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
            Toast.makeText(PlayerDetailActivity.this, "You have clicked on start game", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.score_board){
            Toast.makeText(PlayerDetailActivity.this, "You have clicked on score board", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.find_players){
            Toast.makeText(PlayerDetailActivity.this, "You have clicked on find players", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }


}
