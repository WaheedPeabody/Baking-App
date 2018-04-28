package com.example.waheed.bakingapp.ui.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.RecipeStep;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<RecipeStep> steps;

    public StepAdapter(List<RecipeStep> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_recipe_step, parent, false);
        return new StepViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bindViews(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {

        private final TextView shortDescriptionTextView;

        public StepViewHolder(View itemView) {
            super(itemView);
            shortDescriptionTextView = itemView.findViewById(R.id.shortDescriptionTextView);
        }

        public void bindViews(RecipeStep step) {
            shortDescriptionTextView.setText(step.getShortDescription());
        }

    }
}
