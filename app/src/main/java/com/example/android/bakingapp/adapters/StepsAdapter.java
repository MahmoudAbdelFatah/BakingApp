package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepsDetailsActivity;
import com.example.android.bakingapp.data.Steps;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 6/28/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Steps> mSteps;


    public StepsAdapter(Context context, ArrayList<Steps> steps) {
        this.mSteps = steps;
        mContext = context;
        Log.v("adapterSteps", "steps contractor : " + mSteps.size());
        Log.v("sizeSteps", steps.size() +"");
    }


    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("ViewHolder", "steps contractor");

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_item_steps, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder viewHolder, final int position) {
        Log.v("ViewHolder", "steps contractor");

        final Steps steps =mSteps.get(position);

        viewHolder.txtDescription.setText(steps.getShortDescription());
        Log.v("steps adapter", "item1 : " + steps.getShortDescription());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StepsDetailsActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
                Toast.makeText(mContext, "the position clicked is: " + position, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView txtDescription;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.background_non_image);
            txtDescription = (TextView) itemView.findViewById(R.id.description);
            cardView = (CardView)itemView.findViewById(R.id.cardView1);
        }
    }
}
