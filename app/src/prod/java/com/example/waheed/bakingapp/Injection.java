package com.example.waheed.bakingapp;

import com.example.waheed.bakingapp.api.RecipesServiceApi;
import com.example.waheed.bakingapp.data.RecipesRepository;

public class Injection {

    public static RecipesRepository provideRecipesRepository() {
        return RecipesRepository.getInstance(RecipesServiceApi.getInstance());
    }

}
