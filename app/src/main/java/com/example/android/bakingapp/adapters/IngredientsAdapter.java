package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Ingredients;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 6/28/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Ingredients> mIngredients;

    public IngredientsAdapter(Context context, ArrayList<Ingredients> ingredients) {
        this.mIngredients = ingredients;
        mContext = context;
    }
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_item_ingredients, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Ingredients ingredients =mIngredients.get(position);
        viewHolder.txtName.setText(ingredients.getIngredient());
        viewHolder.txtMeasure.setText(ingredients.getMeasure());
        viewHolder.txtQuantity.setText(ingredients.getQuantity());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtMeasure;
        public TextView txtQuantity;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.ingredient_name);
            txtMeasure = (TextView) itemView.findViewById(R.id.measure);
            txtQuantity = (TextView) itemView.findViewById(R.id.quantity);
            cardView = (CardView)itemView.findViewById(R.id.cardView1);
        }
    }
}
