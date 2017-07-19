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

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepsAndIngredientsActivity;
import com.example.android.bakingapp.data.Recipes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 6/21/2017.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private ArrayList<Recipes> mRecipes;
    private Context mContext;

    public RecipesAdapter(Context context, ArrayList<Recipes> recipesArrayList) {
        this.mRecipes = recipesArrayList;
        mContext = context;
    }

    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final Recipes recipes =mRecipes.get(position);
        viewHolder.txtName.setText(recipes.getName());
        viewHolder.txtServings.setText(recipes.getServings());
        if(recipes.getImage()!= null) {
            Picasso.with(mContext).load(recipes.getImage()).into(viewHolder.imageView);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(mContext, StepsAndIngredientsActivity.class);
            intent.putExtra("position", position);
            mContext.startActivity(intent);
            //Toast.makeText(mContext, "the position clicked is: "+ position, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView txtName;
        public TextView txtServings;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.background_image);
            txtName = (TextView) itemView.findViewById(R.id.name);
            txtServings = (TextView) itemView.findViewById(R.id.servings);
            cardView = (CardView)itemView.findViewById(R.id.cardView1);
        }
    }
}
