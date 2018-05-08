package com.example.waheed.bakingapp.ui.recipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.waheed.bakingapp.api.vo.Recipe;
import com.example.waheed.bakingapp.data.RecipesRepository;

import java.util.List;

public class RecipesViewModel extends ViewModel {

    private RecipesRepository repository;

    private MutableLiveData<List<Recipe>> recipesLive;

    public RecipesViewModel(RecipesRepository repository) {
        this.repository = repository;
        recipesLive = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipesLive() {
        if (recipesLive.getValue() == null) {
            repository.loadRecipes(recipes -> {
                recipesLive.setValue(recipes);
            });
        }
        return recipesLive;
    }

}
