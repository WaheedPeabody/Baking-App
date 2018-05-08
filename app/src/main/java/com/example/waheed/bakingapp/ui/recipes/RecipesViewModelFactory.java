package com.example.waheed.bakingapp.ui.recipes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.waheed.bakingapp.data.RecipesRepository;

public class RecipesViewModelFactory implements ViewModelProvider.Factory {

    private final RecipesRepository recipesRepository;

    public RecipesViewModelFactory(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    /**
     * Creates a new instance of the given {@code Class}.
     * <p>
     *
     * @param modelClass a {@code Class} whose instance is requested
     * @return a newly created ViewModel
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {
            return (T) new RecipesViewModel(recipesRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");

        }
    }

}
