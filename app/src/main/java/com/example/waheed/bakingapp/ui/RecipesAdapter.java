package com.example.waheed.bakingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecipesAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bindViews(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView servingsNumberTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            servingsNumberTextView = itemView.findViewById(R.id.servingsNumberTextView);
        }

        public void bindViews(Recipe recipe) {
            nameTextView.setText(recipe.getName());
            servingsNumberTextView.setText(recipe.getServings().toString());
        }
    }

}
