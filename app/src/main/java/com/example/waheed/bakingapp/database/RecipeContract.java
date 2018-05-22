package com.example.waheed.bakingapp.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.UniqueConstraint;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

@UniqueConstraint(columns = {RecipeContract.COLUMN_RECIPE_ID}, onConflict = ConflictResolutionType.IGNORE)
public interface RecipeContract {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(INTEGER) @NotNull String COLUMN_RECIPE_ID = "recipe_id";
    @DataType(TEXT) @NotNull String COLUMN_NAME = "name";
    @DataType(TEXT) @NotNull String COLUMN_INGREDIENTS = "ingredients";

}
