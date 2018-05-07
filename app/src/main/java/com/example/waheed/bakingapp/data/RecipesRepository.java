package com.example.waheed.bakingapp.data;

import com.example.waheed.bakingapp.api.RecipesServiceApi;
import com.example.waheed.bakingapp.api.vo.Recipe;

import java.util.List;

import static com.example.waheed.bakingapp.data.RecipesRepository.RecipesRepositories.*;

/**
 * Main entry point for accessing recipes data.
 */
public interface RecipesRepository {

    void loadRecipes(LoadRecipesCallback callback);

    static RecipesRepository getInstance(RecipesServiceApi api) {
        synchronized (sLock) {
            if (repository == null) {
                repository = new RecipesRepositoryImpl(api);
            }
            return repository;
        }
    }

    interface LoadRecipesCallback {

        void onRecipesLoaded(List<Recipe> recipes);

    }

    public static final Object sLock = new Object();

    class RecipesRepositories {
        static RecipesRepository repository = null;
    }

}
