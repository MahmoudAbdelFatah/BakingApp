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

import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.data.Ingredients;
import com.example.android.bakingapp.data.Steps;

import java.util.ArrayList;

import static com.example.android.bakingapp.MainActivity.recipesArrayList;
import static com.example.android.bakingapp.StepsAndIngredientsActivity.index;

/**
 * Created by Mahmoud on 6/30/2017.
 */

public class StepsAndIngredientsFragment extends Fragment {
    private RecyclerView stepsRecyclerView;
    private RecyclerView ingredientsRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Steps> steps;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("create1", "hello");
        View rootView = inflater.inflate(R.layout.fragment_steps_and_ingredients , container, false);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.steps_recycler_view);
        stepsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns()));

        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredients_recycler_view);
        ingredientsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns()));

        index = getActivity().getIntent().getExtras().getInt("position");
        steps = recipesArrayList.get(index).getSteps();
        ingredients = recipesArrayList.get(index).getIngredients();
        Log.v("Ing", ingredients.size()+"");
        mIngredientsAdapter = new IngredientsAdapter(getActivity() , ingredients);
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        mStepsAdapter = new StepsAdapter(getActivity() , steps);
        stepsRecyclerView.setAdapter(mStepsAdapter);
        Log.v("index: " , index +" _ "+ steps.size());
        return rootView;
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 1;
        return nColumns;
    }
}
