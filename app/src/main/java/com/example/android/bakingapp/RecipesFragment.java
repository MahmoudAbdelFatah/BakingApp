package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.adapters.RecipesAdapter;
import com.example.android.bakingapp.data.Ingredients;
import com.example.android.bakingapp.data.Recipes;
import com.example.android.bakingapp.data.Steps;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import static com.example.android.bakingapp.MainActivity.recipesArrayList;

/**
 * Created by Mahmoud on 6/21/2017.
 */

public class RecipesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private Recipes recipes;
    private Ingredients ingredients;
    private Steps steps;
    private static ArrayList<Ingredients> ingredientsArrayList;
    private static ArrayList<Steps> stepsArrayList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.v("create", "hello first");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns()));

        return rootView;
    }

    //i get this function from the review of the previous project
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 1;
        return nColumns;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("start", "hello from start");
        if(recipesArrayList!=null)
            recipesArrayList.clear();
        downloadFromInternet();
    }

    private void downloadFromInternet() {
        final String url= "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        Log.v("name", "hello before");
        Ion.with(getActivity())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    String tmp;
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e == null) {
                            recipesArrayList = new ArrayList<>();
                            for(int i=0; i<result.size(); i++) {
                                recipes = new Recipes();
                                ingredientsArrayList = new ArrayList<Ingredients>();
                                stepsArrayList = new ArrayList<Steps>();
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

                                    /*Log.v("cnt1", (j+1) +": "+ ingredientJsonArray.get(j).getAsJsonObject().get("quantity").toString()+"\n" +
                                            ingredientJsonArray.get(j).getAsJsonObject().get("measure").toString() + "\n"+
                                            ingredientJsonArray.get(j).getAsJsonObject().get("ingredient").toString()
                                    );
                                    Log.v("ingredients size", ingredientsArrayList.size()+"");
                                    */
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

                                    /*Log.v("cnt2", (j+1) +": "+ stepsJsonArray.get(j).getAsJsonObject().get("id").toString()+"\n" +
                                            stepsJsonArray.get(j).getAsJsonObject().get("shortDescription").toString() + "\n"+
                                            stepsJsonArray.get(j).getAsJsonObject().get("description").toString() + "\n"+
                                            stepsJsonArray.get(j).getAsJsonObject().get("videoURL").toString() + "\n"+
                                            stepsJsonArray.get(j).getAsJsonObject().get("thumbnailURL").toString()
                                    );
                                    Log.v("steps size", stepsArrayList.size()+"");
                                    */
                                    stepsArrayList.add(steps);
                                }
                                recipes.setSteps(stepsArrayList);
                                //Log.v("tmp size", jsonArrayTmp.size()+"");
                                //recipes.setIngredients();
                                //Log.v("cnt", (i+1) +": "+ result.get(i).getAsJsonObject().get("servings").toString()+"\n" +
                                  //      result.get(i).getAsJsonObject().get("image").toString() + "\n"+
                                    //    result.get(i).getAsJsonObject().get("name").toString()
                                //);
                                recipesArrayList.add(recipes);
                            }
                        }
                        if(recipesArrayList!= null) {
                            mRecipesAdapter = new RecipesAdapter(getActivity() , recipesArrayList);
                            mRecyclerView.setAdapter(mRecipesAdapter);
                        }
                    }
                });
    }
}
