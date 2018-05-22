package com.example.waheed.bakingapp.database;


import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = RecipesDatabase.VERSION)
public final class RecipesDatabase {

    public static final int VERSION = 1;

    @Table(RecipeContract.class) public static final String recipes = "recipes";

}
