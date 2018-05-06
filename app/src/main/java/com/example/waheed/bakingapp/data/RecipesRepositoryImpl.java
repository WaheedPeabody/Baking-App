package com.example.waheed.bakingapp.data;

import com.example.waheed.bakingapp.api.Api;
import com.example.waheed.bakingapp.api.vo.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RecipesRepositoryImpl implements RecipesRepository {

    private final Api api;

    public RecipesRepositoryImpl(Api api) {
        this.api = api;
    }

    @Override
    public void loadRecipes(LoadRecipesCallback callback) {
        Call<List<Recipe>> call = api.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                callback.onRecipesLoaded(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}
