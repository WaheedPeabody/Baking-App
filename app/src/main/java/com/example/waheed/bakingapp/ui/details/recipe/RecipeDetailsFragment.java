package com.example.waheed.bakingapp.ui.details.recipe;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;
import com.example.waheed.bakingapp.api.vo.RecipeStep;
import com.example.waheed.bakingapp.ui.details.recipe.step.StepDetailsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends Fragment {

    private static final String PARAM_RECIPE = "param_recipe";

    private Recipe recipe;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putSerializable(PARAM_RECIPE, recipe);
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            recipe = (Recipe) args.getSerializable(PARAM_RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView stepsRecyclerView = view.findViewById(R.id.recipeStepsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);
        StepAdapter adapter = new StepAdapter(recipe.getSteps(), new OnStepClickListener() {
            @Override
            public void onStepClick(int stepOrder) {
                openStepDetailsActivity(recipe, stepOrder);
            }
        });

        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(adapter);
    }

    private void openStepDetailsActivity(Recipe recipe, int stepOrder) {
        Intent intent = new Intent(getContext(), StepDetailsActivity.class);
        intent.putExtra(Recipe.EXTRA_RECIPE, recipe);
        intent.putExtra(RecipeStep.EXTRA_STEP_ORDER, stepOrder);
        getContext().startActivity(intent);
    }


}
