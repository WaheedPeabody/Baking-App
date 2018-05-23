package com.example.waheed.bakingapp.ui.recipedetails;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;
import com.example.waheed.bakingapp.database.RecipeContract;
import com.example.waheed.bakingapp.database.generated.RecipesProvider;
import com.example.waheed.bakingapp.widget.RecipesAppWidget;

import static com.example.waheed.bakingapp.database.RecipesProvider.Recipes.RECIPES_URI;

/**
 * A simple {@link Fragment} subclass.
 * * Activities that contain this fragment must implement the
 * {@link OnStepClickListener} interface
 * to handle interaction events.
 */
public class RecipeDetailsFragment extends Fragment {

    private static final String PARAM_RECIPE = "param_recipe";

    private Recipe recipe;
    private OnStepClickListener mListener;
    private FloatingActionButton fab;
    private Toast toast;

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
        fab = view.findViewById(R.id.favoriteFab);
        fab.setOnClickListener(view1 -> addOrRemoveFavorite(recipe));
        fab.setImageResource(isFavorite(recipe) ?
                R.drawable.sharp_favorite_black_24dp :
                R.drawable.sharp_favorite_border_white_24dp);
        setupRecyclerView(view);

        TextView ingredientTextView = view.findViewById(R.id.ingredientsTextView);
        ingredientTextView.setText(recipe.getIngredientsAsString());
        return view;
    }

    /**
     * favorite the given recipe or remove it from favorites
     */
    private void addOrRemoveFavorite(Recipe recipe) {

        ContentResolver contentResolver = getContext().getContentResolver();
        String selection = RecipeContract.COLUMN_RECIPE_ID + "=?";
        String[] selectionArgs = {recipe.getId().toString()};

        if (isFavorite(recipe)) {
            contentResolver.delete(RECIPES_URI, selection, selectionArgs);

            // update ui to reflect the changes
            showToast(getString(R.string.removed_from_favorites));
            fab.setImageResource(R.drawable.sharp_favorite_border_white_24dp);
            updateWidget();
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RecipeContract.COLUMN_RECIPE_ID, recipe.getId());
            contentValues.put(RecipeContract.COLUMN_NAME, recipe.getName());
            contentValues.put(RecipeContract.COLUMN_INGREDIENTS, recipe.getIngredients().toString());
            contentResolver.insert(RECIPES_URI, contentValues);

            // update ui to reflect changes
            showToast(getString(R.string.added_to_favorites));
            fab.setImageResource(R.drawable.sharp_favorite_black_24dp);
            updateWidget();
        }

    }

    private boolean isFavorite(Recipe recipe) {
        ContentResolver contentResolver = getContext().getContentResolver();

        String selection = RecipeContract.COLUMN_RECIPE_ID + "=?";
        String[] selectionArgs = {recipe.getId().toString()};
        Cursor cursor = contentResolver.query(RECIPES_URI, null,
                selection, selectionArgs, null);
        boolean toReturn = cursor.getCount() > 0;
        cursor.close();
        return toReturn;
    }

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    // see https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
    private void updateWidget() {
        Intent intent = new Intent(getContext(), RecipesAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = (AppWidgetManager)
                getContext().getSystemService(Context.APPWIDGET_SERVICE);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getContext(), RecipesAppWidget.class));

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        getContext().sendBroadcast(intent);
    }

    private void setupRecyclerView(View view) {
        RecyclerView stepsRecyclerView = view.findViewById(R.id.recipeStepsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);
        StepAdapter adapter = new StepAdapter(recipe.getSteps(), mListener);

        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepClickListener) {
            mListener = (OnStepClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
