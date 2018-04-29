package com.example.waheed.bakingapp.ui.details.recipe.step;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;
import com.example.waheed.bakingapp.api.vo.RecipeStep;

public class StepDetailsActivity extends AppCompatActivity implements OnStepNavigationButtonClickListener {

    private Recipe recipe;
    private int currentStepPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Intent intent = getIntent();

        if (intent != null) {
            recipe = (Recipe) intent.getSerializableExtra(Recipe.EXTRA_RECIPE);
            currentStepPosition = intent.getIntExtra(RecipeStep.EXTRA_STEP_ORDER, 0);
            if (savedInstanceState == null) {
                RecipeStep step = recipe.getSteps().get(currentStepPosition);
                displayStepDetailsFragment(step);
                displayStepsNavigationFragment(recipe.getSteps().size(), currentStepPosition);
            }
        }

    }

    private void displayStepDetailsFragment(RecipeStep step) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        StepInstructionFragment fragment = StepInstructionFragment.newInstance(step);
        fragmentTransaction.add(R.id.stepDetailsFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void displayStepsNavigationFragment(int numberOfSteps, int currentStepPosition) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        StepsNavigationFragment fragment =
                StepsNavigationFragment.newInstance(numberOfSteps, currentStepPosition);
        fragmentTransaction.add(R.id.stepsNavigationFragmentContainer, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onStepNavigationButtonClick(int navigateToStepAtPosition, boolean goingForward) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        StepInstructionFragment stepInstructionFragment =
                StepInstructionFragment.newInstance(recipe.getSteps().get(navigateToStepAtPosition));
        StepsNavigationFragment stepsNavigationFragment =
                StepsNavigationFragment.newInstance(recipe.getSteps().size(), navigateToStepAtPosition);

        fragmentTransaction
                .replace(R.id.stepDetailsFragmentContainer, stepInstructionFragment)
                .replace(R.id.stepsNavigationFragmentContainer, stepsNavigationFragment);

        if (goingForward) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
        currentStepPosition = navigateToStepAtPosition;
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        if (currentStepPosition <= 0) {
            finish();
        } else {
            onStepNavigationButtonClick(currentStepPosition - 1, false);
        }
    }

}
