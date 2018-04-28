package com.example.waheed.bakingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private final OnRecipeItemClickListener listener;
    private List<Recipe> recipes;

    public RecipesAdapter(List<Recipe> recipes, OnRecipeItemClickListener listener) {
        this.recipes = recipes;
        this.listener = listener;
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
        return new RecipeViewHolder(itemView, listener);
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
        private final OnRecipeItemClickListener listener;

        public RecipeViewHolder(View itemView, OnRecipeItemClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            servingsNumberTextView = itemView.findViewById(R.id.servingsNumberTextView);
            this.listener = listener;
        }

        public void bindViews(Recipe recipe) {
            nameTextView.setText(recipe.getName());
            servingsNumberTextView.setText(recipe.getServings().toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRecipeItemClick(recipe);
                }
            });
        }
    }

}
