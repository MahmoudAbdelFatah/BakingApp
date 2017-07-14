package com.example.android.bakingapp.data;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 6/21/2017.
 */

public class Recipes {
    private String name;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Steps> steps;
    private String servings;
    private String image;

    public Recipes() {
    }

    public String getName() {
        return name;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setIngredients(ArrayList<Ingredients> mIngredients) {
        this.ingredients = mIngredients;
    }

    public void setSteps(ArrayList<Steps> mSteps) {
        this.steps = mSteps;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
