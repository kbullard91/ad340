package karibullard.com.ad340App;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

class ButtonAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private String[] dialogItems = {"I like Lebron.", "I like Durant", "I like Curry", "I like turtles."};
    private String[] buttonTitles = {"Player Pairings", "New Game", "Scoreboard", "Submit Question"};
    Button[] buttons = new Button[4];
    private static String TAG = MainActivity.class.getSimpleName();

    ButtonAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return buttons.length;
    }

    @Override
    public Object getItem(int i) {
        return buttons[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int i = position;
        Button button;
        if (convertView == null){
            button = new Button(context);
            button.setId(position);
            button.setText(buttonTitles[button.getId()]);
            buttons[position] = button;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(i == 0){
                        Intent intent = new Intent(context, DisplayInfoListActivity.class);
                        context.startActivity(intent);
                    } else if (i == 1) {
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
                        Toast.makeText(context, "" + i,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            button = (Button) convertView;
        }

        return button;
    }
}
