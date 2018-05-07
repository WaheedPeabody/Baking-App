package com.example.waheed.bakingapp.data;

import com.example.waheed.bakingapp.api.RecipesServiceApi;

class RecipesRepositoryImpl implements RecipesRepository {

    private final RecipesServiceApi api;

    public RecipesRepositoryImpl(RecipesServiceApi api) {
        this.api = api;
    }

    @Override
    public void loadRecipes(LoadRecipesCallback callback) {
        api.getRecipes(data -> callback.onRecipesLoaded(data));
    }
}
