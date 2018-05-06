package com.example.waheed.bakingapp.ui.recipes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.waheed.bakingapp.api.Api;
import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRecipeItemClickListener} interface
 * to handle interaction events.
 */
public class RecipesFragment extends Fragment {

    private OnRecipeItemClickListener mListener;
    private RecipesAdapter adapter;

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
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
        Api api = Api.create();
        Call<List<Recipe>> call = api.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                adapter.setRecipes(recipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
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
