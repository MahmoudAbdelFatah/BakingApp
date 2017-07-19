package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Ingredients;
import com.example.android.bakingapp.data.Recipes;
import com.example.android.bakingapp.data.Steps;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import static com.example.android.bakingapp.MainActivity.recipesArrayList;

/**
 * Created by Mahmoud on 7/11/2017.
 */

public class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Recipes recipes;
    private Ingredients ingredients;
    private Steps steps;
    private static ArrayList<Ingredients> ingredientsArrayList;
    private static ArrayList<Steps> stepsArrayList;

    public BakingRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        //recipesArrayList = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {
        downloadFromInternet();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget_list_item);

        rv.setTextViewText(R.id.name, recipesArrayList.get(position).getName());
        rv.setTextViewText(R.id.servings, recipesArrayList.get(position).getServings());
        Log.v("sizeOfRec", recipesArrayList.size()+"");
        Log.v("sizeInView", recipesArrayList.get(position).getIngredients().size()+"");
        for (int i=0;i<recipesArrayList.get(position).getIngredients().size();i++){
            RemoteViews  remoteViews= new RemoteViews(mContext.getPackageName(), R.layout.item_ingredient);
            Log.v("measureInView", recipesArrayList.get(position).getIngredients().get(i).getMeasure());
            remoteViews.setTextViewText(R.id.measure,recipesArrayList.get(position).getIngredients().get(i).getMeasure());
            remoteViews.setTextViewText(R.id.ingredient,recipesArrayList.get(position).getIngredients().get(i).getIngredient());
            remoteViews.setTextViewText(R.id.measure,recipesArrayList.get(position).getIngredients().get(i).getMeasure());
            rv.addView(R.id.ingredient_list,remoteViews);
        }

        Intent intent = new Intent();
        intent.putExtra("position", position);
        rv.setOnClickFillInIntent(R.id.widget_layout, intent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void downloadFromInternet() {
        final String url= "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        Log.v("name", "hello before");
        Ion.with(mContext.getApplicationContext())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    String tmp;

                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e == null) {
                            recipesArrayList = new ArrayList<>();
                            ingredientsArrayList = new ArrayList<Ingredients>();
                            stepsArrayList = new ArrayList<Steps>();
                            for(int i=0; i<result.size(); i++) {
                                recipes = new Recipes();
                                ingredientsArrayList.clear();
                                stepsArrayList.clear();
                                tmp = result.get(i).getAsJsonObject().get("name").toString();
                                tmp = tmp.substring(1, tmp.length()-1);
                                recipes.setName(tmp);
                                recipes.setServings(result.get(i).getAsJsonObject().get("servings").toString());

                                JsonArray ingredientJsonArray = (JsonArray) result.get(i).getAsJsonObject().get("ingredients");
                                Log.v("jsonSizeIngredients",ingredientJsonArray.size() + "" );
                                for(int j=0; j<ingredientJsonArray.size(); j++) {
                                    ingredients = new Ingredients();
                                    ingredients.setQuantity(ingredientJsonArray.get(j).getAsJsonObject().get("quantity").toString());

                                    tmp = ingredientJsonArray.get(j).getAsJsonObject().get("measure").toString();
                                    tmp = tmp.substring(1, tmp.length()-1);
                                    ingredients.setMeasure(tmp);

                                    tmp=ingredientJsonArray.get(j).getAsJsonObject().get("ingredient").toString();
                                    tmp = tmp.substring(1, tmp.length()-1);
                                    ingredients.setIngredient(tmp);
                                    ingredientsArrayList.add(ingredients);
                                }
                                recipes.setIngredients(ingredientsArrayList);
                                JsonArray stepsJsonArray = (JsonArray) result.get(i).getAsJsonObject().get("steps");
                                Log.v("jsonSizeSteps",stepsJsonArray.size() + "" );
                                for(int j=0; j<stepsJsonArray.size(); j++) {
                                    steps = new Steps();
                                    steps.setId(Integer.parseInt(stepsJsonArray.get(j).getAsJsonObject().get("id").toString()));
                                    tmp = stepsJsonArray.get(j).getAsJsonObject().get("shortDescription").toString();
                                    tmp = tmp.substring(1, tmp.length()-1);
                                    steps.setShortDescription(tmp);

                                    tmp = stepsJsonArray.get(j).getAsJsonObject().get("description").toString();
                                    tmp = tmp.substring(1, tmp.length()-1);
                                    steps.setDescription(tmp);

                                    tmp = stepsJsonArray.get(j).getAsJsonObject().get("videoURL").toString();
                                    tmp = tmp.substring(1, tmp.length()-1);
                                    steps.setVideoURL(tmp);

                                    tmp = stepsJsonArray.get(j).getAsJsonObject().get("thumbnailURL").toString();
                                    tmp = tmp.substring(1, tmp.length()-1);
                                    steps.setThumbnailURL(tmp);
                                    stepsArrayList.add(steps);
                                }
                                recipes.setSteps(stepsArrayList);
                                recipesArrayList.add(recipes);
                                Log.v("content", recipes.getName());
                            }
                            Log.v("sizeInWidget", recipesArrayList.size()+"");

                        }
                    }
                });
    }
}
