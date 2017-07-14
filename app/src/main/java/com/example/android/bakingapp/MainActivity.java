package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.data.Recipes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static boolean mTwoPane=false;
    public static ArrayList<Recipes> recipesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if (findViewById(R.id.recipe_container_pane) != null) {
                mTwoPane = true;
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_container_pane, new RecipesFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_container, new RecipesFragment())
                        .commit();
            }
        }
    }
}
