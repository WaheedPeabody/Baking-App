package com.example.waheed.bakingapp.ui.main;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;
import com.example.waheed.bakingapp.ui.details.recipe.RecipeDetailsActivity;

public class MainActivity extends AppCompatActivity implements OnRecipeItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            displayRecipesFragment();
        }
    }

    private void displayRecipesFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        RecipesFragment fragment = new RecipesFragment();
        fragmentTransaction.add(R.id.recipesFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Recipe.EXTRA_RECIPE, recipe);
        startActivity(intent);
    }
}
