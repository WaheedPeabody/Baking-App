package com.example.waheed.bakingapp.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = RecipesProvider.AUTHORITY, database = RecipesDatabase.class)
public final class RecipesProvider {

    public static final String AUTHORITY = "com.example.waheed.bakingapp.provider";


    @TableEndpoint(table = RecipesDatabase.recipes)
    public static class Recipes {

        @ContentUri(
                path = "recipes",
                type = "vnd.android.cursor.dir/recipe"
        )
        public static final Uri RECIPES_URI = Uri.parse("content://" + AUTHORITY + "/recipes");
    }

}
