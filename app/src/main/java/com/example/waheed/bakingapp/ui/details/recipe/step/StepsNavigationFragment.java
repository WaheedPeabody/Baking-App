package com.example.waheed.bakingapp.ui.details.recipe.step;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.waheed.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link StepsNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsNavigationFragment extends Fragment {

    private static final String ARG_NUMBER_OF_STEPS = "number_of_steps";
    private static final String ARG_CURRENT_STEP_POSITION = "current_step_position";

    private int numberOfSteps;
    private int currentStepPosition;

    private OnStepNavigationButtonClickListener listener;

    public StepsNavigationFragment() {
        // Required empty public constructor
    }

    public static StepsNavigationFragment newInstance(int numberOfSteps, int currentStepPosition) {
        StepsNavigationFragment fragment = new StepsNavigationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER_OF_STEPS, numberOfSteps);
        args.putInt(ARG_CURRENT_STEP_POSITION, currentStepPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numberOfSteps = getArguments().getInt(ARG_NUMBER_OF_STEPS);
            currentStepPosition = getArguments().getInt(ARG_CURRENT_STEP_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_navigation, container, false);
        setupButtons(view);
        return view;
    }

    private void setupButtons(View view) {
        Button prevButton = view.findViewById(R.id.prevButton);
        Button nextButton = view.findViewById(R.id.nextButton);

        if (currentStepPosition == 0) {
            prevButton.setVisibility(View.INVISIBLE);
        }

        if (currentStepPosition == numberOfSteps - 1) {
            nextButton.setVisibility(View.INVISIBLE);
        }

        prevButton.setOnClickListener(view1 ->
                listener.onStepNavigationButtonClick(currentStepPosition -1, false));

        nextButton.setOnClickListener(view12 ->
                listener.onStepNavigationButtonClick(currentStepPosition + 1, true));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepNavigationButtonClickListener) {
            listener = (OnStepNavigationButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepNavigationButtonClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
