package com.example.android.bakingapp.data;

/**
 * Created by Mahmoud on 6/21/2017.
 */

public class Ingredients {
    private String quantity;
    private String measure;
    private String ingredient;

    public Ingredients( ){

    }

    public String getQuantity(){
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure ) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
