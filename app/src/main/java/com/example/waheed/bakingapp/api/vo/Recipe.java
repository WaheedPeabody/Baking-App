package com.example.waheed.bakingapp.api.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
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

    public String getName() {
        return name;
    }

    public Integer getServings() {
        return servings;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }
}
