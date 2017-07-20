package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.android.bakingapp.MainActivity.mTwoPane;

public class StepsAndIngredientsActivity extends AppCompatActivity {
    public static int index=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_and_ingredients);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            if(mTwoPane) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.steps_and_ingredients_container, new StepsAndIngredientsFragment())
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.steps_and_ingredients_container_pane, new StepsDetailsFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.steps_and_ingredients_container, new StepsAndIngredientsFragment())
                        .commit();
            }
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_and_ingredients_container, new StepsAndIngredientsFragment())
                    .commit();
        }
    }
}
