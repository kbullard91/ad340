package karibullard.com.ad340App;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by karibullard on 4/26/17.
 */

public class ButtonAdapter extends BaseAdapter {

    Context context;
    Button buttons[];
    LayoutInflater inflter;

    String[] dialogItems = {"I like Lebron.", "I like Durant", "I like Curry", "I like turtles."};

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
                } else if (position == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(android.R.drawable.sym_def_app_icon);
                    builder.setCancelable(false);
                    builder.setItems(dialogItems, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0:
                                    Log.d(TAG, "You like Lebron.");
                                    break;
                                case 1:
                                    Log.d(TAG, "You like Durant.");
                                    break;
                                case 2:
                                    Log.d(TAG, "You like Curry.");
                                    break;
                                case 3:
                                    Log.d(TAG, "You like Turtles.");
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    builder.setTitle("Pick your Favorite");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Log.d(TAG, "OK was clicked.");
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Log.d(TAG, "Cancel was clicked.");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(context, "" + position,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        return button;
    }

}
