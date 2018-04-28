package com.example.waheed.bakingapp.ui.details;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;

import java.io.Serializable;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();

        if (savedInstanceState == null) {
            Recipe recipe = (Recipe) intent.getSerializableExtra(Recipe.EXTRA_RECIPE);
            displayRecipeDetailsFragment(recipe);
        }
    }

    private void displayRecipeDetailsFragment(Recipe recipe) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        RecipeDetailsFragment fragment = RecipeDetailsFragment.newInstance(recipe);
        fragmentTransaction.add(R.id.recipeDetailsFragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
