package com.example.waheed.bakingapp.api;

import com.example.waheed.bakingapp.api.vo.Recipe;

import java.util.List;

public interface RecipesServiceApi {

    void getRecipes(RecipesServiceCallback<List<Recipe>> callback);

    static RecipesServiceApi getInstance() {
        return new RecipesServiceApiImpl();
    }

    interface RecipesServiceCallback<T> {
        void onLoaded(T data);
    }

}
