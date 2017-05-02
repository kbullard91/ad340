package karibullard.com.ad340App;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by karibullard on 4/27/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    String[] Presidents;
    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView;

    public RecyclerViewAdapter(Context context1, String[] Presidents1){

        Presidents = Presidents1;
        context = context1;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(View v){

            super(v);

            textView = (TextView)v.findViewById(R.id.subject_textview1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view1 = LayoutInflater.from(context).inflate(R.layout.recyclerview_items, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        holder.textView.setText(Presidents[position]);
    }

    @Override
    public int getItemCount(){

        return Presidents.length;
    }
}
