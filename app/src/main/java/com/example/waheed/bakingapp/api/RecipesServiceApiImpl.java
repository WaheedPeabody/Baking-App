package com.example.waheed.bakingapp.api;

import com.example.waheed.bakingapp.api.RecipesServiceApi;
import com.example.waheed.bakingapp.api.vo.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class RecipesServiceApiImpl implements RecipesServiceApi {


    private final RetrofitApi api;

    public RecipesServiceApiImpl() {
        api = RetrofitApi.create();
    }

    @Override
    public void getRecipes(RecipesServiceCallback<List<Recipe>> callback) {
        Call<List<Recipe>> call = api.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                callback.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}
