package karibullard.com.ad340App;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import karibullard.com.ad340App.model.Player;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

//    xmlns:app="http://schemas.android.com/apk/res-auto"

    private static String TAG = DisplayInfoListActivity.class.getSimpleName();
    public static final String ITEM_ID_KEY = "item_id_key";
    static final String ITEM_KEY = "item_key";
    private List<Player> players;
    private Context context;
    private Map<String, Bitmap> playerAvatars = new HashMap<>();

    /**
     * The constructor
     * @param context application context
     * @param players data list to be displayed
     */
    RecyclerViewAdapter(Context context, List<Player> players){
        this.players = players;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        LayoutInflater inflater = LayoutInflater.from(context);
        View playerView =  inflater.inflate(R.layout.active_player_list_items, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(playerView);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position){

        final Player activePlayer = players.get(position);
        try {
            holder.playerName.setText(activePlayer.getFirstName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerDetailActivity.class);
                intent.putExtra(ITEM_KEY, activePlayer);
                context.startActivity(intent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView playerName;
        TextView playerStatus;
        View myView;

        ViewHolder(View playerView){
            super(playerView);

            playerName = (TextView)playerView.findViewById(R.id.playerNameText);
            playerStatus = (TextView) itemView.findViewById(R.id.playerStatusText);
            myView = itemView;
        }
    }

    @Override
    public int getItemCount(){
        return players.size();
    }
}
