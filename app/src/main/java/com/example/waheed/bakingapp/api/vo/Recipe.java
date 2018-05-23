package com.example.waheed.bakingapp.api.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Recipe implements Serializable {

    public static final String EXTRA_RECIPE = "extra_recipe";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    @Expose
    private List<RecipeStep> steps;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Recipe(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getServings() {
        return servings;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public String getImage() {
        return image;
    }

    public String getIngredientsAsString() {
        return ingredients.toString();
    }

}
