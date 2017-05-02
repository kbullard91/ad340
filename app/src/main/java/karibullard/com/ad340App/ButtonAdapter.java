package karibullard.com.ad340App;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by karibullard on 4/26/17.
 */

public class ButtonAdapter extends BaseAdapter {

    Context context;
    Button buttons[];
    LayoutInflater inflter;

    public ButtonAdapter(Context applicationContext, Button[] buttons) {
        this.context = applicationContext;
        this.buttons = buttons;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return buttons.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int position = i;
        Button button = new Button(context);
        button.setId(i);
        button.setBackgroundColor(Color.parseColor("#00bcd4"));
        button.setLayoutParams(new GridView.LayoutParams(450,450));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Intent intent = new Intent(context, DisplayInfoList.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "" + position,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        return button;
    }

}
