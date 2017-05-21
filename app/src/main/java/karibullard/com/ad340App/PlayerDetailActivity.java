package karibullard.com.ad340App;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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


}
