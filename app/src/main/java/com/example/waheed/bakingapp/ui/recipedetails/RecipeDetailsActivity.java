package com.example.waheed.bakingapp.ui.recipedetails;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;
import com.example.waheed.bakingapp.api.vo.RecipeStep;
import com.example.waheed.bakingapp.ui.recipedetails.step.StepDetailsActivity;
import com.example.waheed.bakingapp.ui.recipedetails.step.StepInstructionFragment;
import com.example.waheed.bakingapp.ui.recipedetails.step.StepVideoFragment;

public class RecipeDetailsActivity extends AppCompatActivity implements OnStepClickListener{

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();

        recipe = (Recipe) intent.getSerializableExtra(Recipe.EXTRA_RECIPE);

        if (savedInstanceState == null) {
            displayRecipeDetailsFragment(recipe);
            if (isTabletInLandscape()) {
                RecipeStep step = recipe.getSteps().get(1);
                displayStepVideoFragment(step);
                displayStepInstructionFragment(step);
            }
        }
    }

    private boolean isTabletInLandscape() {
        return findViewById(R.id.w900dpRootView) != null;
    }

    private void displayRecipeDetailsFragment(Recipe recipe) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(recipe);
        fragmentTransaction.add(R.id.recipeDetailsFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void displayStepVideoFragment(RecipeStep step) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        StepVideoFragment fragment = StepVideoFragment.newInstance(step);
        fragmentTransaction.replace(R.id.stepVideoFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void displayStepInstructionFragment(RecipeStep step) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        StepInstructionFragment fragment = StepInstructionFragment.newInstance(step);
        fragmentTransaction.replace(R.id.stepInstructionFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStepClick(int stepOrder) {
        if (isTabletInLandscape()) {
            RecipeStep step = recipe.getSteps().get(stepOrder);
            displayStepInstructionFragment(step);
            displayStepVideoFragment(step);
        } else {
            openStepDetailsActivity(recipe, stepOrder);
        }
    }

    private void openStepDetailsActivity(Recipe recipe, int stepOrder) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(Recipe.EXTRA_RECIPE, recipe);
        intent.putExtra(RecipeStep.EXTRA_STEP_ORDER, stepOrder);
        startActivity(intent);
    }
}
