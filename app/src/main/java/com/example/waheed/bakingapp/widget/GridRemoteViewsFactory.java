package com.example.waheed.bakingapp.widget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.database.RecipeContract;
import com.example.waheed.bakingapp.database.RecipesProvider;

public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor cursor;
    private Context context;

    public GridRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {
        if (cursor != null)
            cursor.close();

        ContentResolver contentResolver = context.getContentResolver();
        cursor = contentResolver.query(RecipesProvider.Recipes.RECIPES_URI,
                null, null, null, null);
    }

    @Override
    public RemoteViews getViewAt(int i) {
        cursor.moveToPosition(i);
        int nameColumnIndex = cursor.getColumnIndex(RecipeContract.COLUMN_NAME);
        String name = cursor.getString(nameColumnIndex);
        int ingredientsColumnIndex = cursor.getColumnIndex(RecipeContract.COLUMN_INGREDIENTS);
        String ingredients = cursor.getString(ingredientsColumnIndex);

        RemoteViews remoteViews =
                new RemoteViews(context.getPackageName(), R.layout.item_widget_recipe);
        remoteViews.setTextViewText(R.id.recipeNameTextView, name);
        remoteViews.setTextViewText(R.id.ingredientsTextView, ingredients);

        return remoteViews;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor != null ? cursor.getCount() : 0;
    }


}
