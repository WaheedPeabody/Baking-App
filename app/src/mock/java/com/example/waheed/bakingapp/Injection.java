package com.example.waheed.bakingapp;

import com.example.waheed.bakingapp.api.FakeRecipesServiceApiImpl;
import com.example.waheed.bakingapp.data.RecipesRepository;

public class Injection {

    public static RecipesRepository provideRecipesRepository() {
        return RecipesRepository.getInstance(new FakeRecipesServiceApiImpl());
    }

}
