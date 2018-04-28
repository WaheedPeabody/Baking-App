package com.example.waheed.bakingapp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeStep {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;

}
