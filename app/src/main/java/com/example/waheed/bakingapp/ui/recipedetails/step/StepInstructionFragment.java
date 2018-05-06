package com.example.waheed.bakingapp.ui.recipedetails.step;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.api.vo.RecipeStep;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepInstructionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepInstructionFragment extends Fragment {

    private static final String ARG_STEP = "step";

    private RecipeStep step;

    public StepInstructionFragment() {
        // Required empty public constructor
    }

    public static StepInstructionFragment newInstance(RecipeStep step) {
        StepInstructionFragment fragment = new StepInstructionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = (RecipeStep) getArguments().getSerializable(ARG_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_step_instruction, container, false);
        TextView descriptionTextView =
                view.findViewById(R.id.stepDescriptionTextView);
        descriptionTextView.setText(step.getDescription());
        return view;
    }
}
