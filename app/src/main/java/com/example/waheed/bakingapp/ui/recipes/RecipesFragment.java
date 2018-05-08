package com.example.waheed.bakingapp.ui.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.waheed.bakingapp.Injection;
import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;
import com.example.waheed.bakingapp.data.RecipesRepository;
import com.example.waheed.bakingapp.utils.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRecipeItemClickListener} interface
 * to handle interaction events.
 */
public class RecipesFragment extends Fragment {

    private OnRecipeItemClickListener mListener;
    private RecipesAdapter adapter;

    private View progressBar;

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        progressBar = view.findViewById(R.id.progressBar);

        setupRecyclerView(view);
        requestRecipes();
        return view;
    }

    public void setupRecyclerView(View view) {
        RecyclerView recipesRecyclerView = view.findViewById(R.id.recipesRecyclerView);
        RecyclerView.LayoutManager layoutManager;

        if (isTabletInLandscape(view)) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new LinearLayoutManager(
                    getContext(), LinearLayoutManager.VERTICAL, false);
        }

        this.adapter = new RecipesAdapter(new ArrayList<>(), mListener);
        recipesRecyclerView.setLayoutManager(layoutManager);
        recipesRecyclerView.setAdapter(adapter);
    }

    private boolean isTabletInLandscape(View view) {
        return view.findViewById(R.id.w900dpRootView) != null;
    }

    private void requestRecipes() {
        RecipesRepository repository = Injection.provideRecipesRepository();

        RecipesViewModelFactory viewModelFactory = new RecipesViewModelFactory(repository);
        RecipesViewModel recipesViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(RecipesViewModel.class);

        EspressoIdlingResource.increment();

        recipesViewModel.getIsLoadingLive()
                .observe(this, isLoading -> showLoading(isLoading));
        recipesViewModel.getRecipesLive()
        .observe(this, recipes -> {
            showData(recipes);

            EspressoIdlingResource.decrement();
        });
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.INVISIBLE);
    }

    private void showData(List<Recipe> recipes) {
        adapter.setRecipes(recipes);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeItemClickListener) {
            mListener = (OnRecipeItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
