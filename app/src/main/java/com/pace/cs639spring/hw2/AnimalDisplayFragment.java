package com.pace.cs639spring.hw2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Chuyu on 2/26/19.
 */

public class AnimalDisplayFragment extends Fragment {

    ArrayList<AnimalItem> animalItemArrayList;
    View view;
    RecyclerView animalRecyclerView;
    ExampleAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    EditText factInput;
    Button addFactButton;
    int currentPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.animal_display, container, false);

        createAnimalItemList();
        buildRecyclerView();
        factInput = view.findViewById(R.id.factInput);
        addFactButton = view.findViewById(R.id.addFactButton);
        currentPosition = -1;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       //attach on item click listeners for item and buttons within item
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mAdapter.togglePosition(position);
                currentPosition = mAdapter.getSelectedPosition();
            }

            @Override
            public void onNextFactClick(int position) {
                animalItemArrayList.get(position).goNextFact();
                mAdapter.notifyItemChanged(position);
            }

            @Override
            public void onDeleteFactClick(int position) {
                if(!animalItemArrayList.get(position).deleteFact())
                    Toast.makeText(getActivity(), R.string.animal_need_at_lease_one_fact, Toast.LENGTH_LONG).show();
                mAdapter.notifyItemChanged(position);
            }
        });


        addFactButton.setOnClickListener(view1 ->  {
                if (isInputSelected()){
                    if (factInput.getText().length() == 0)
                        Toast.makeText(getActivity(), R.string.please_enter_a_fact, Toast.LENGTH_LONG).show();
                    else {
                        animalItemArrayList.get(currentPosition).addFact(factInput.getText().toString());
                        factInput.setText("");
                        Toast.makeText(getActivity(), R.string.new_fact_added, Toast.LENGTH_LONG).show();
                    }
                }
        });

        mAdapter.get10dpPX(dpToPx(10));
        addTransformButtonClickListeners();

    }

    public void createAnimalItemList() {
        animalItemArrayList = new ArrayList<>();
        animalItemArrayList.add(new AnimalItem(R.drawable.bird, getString(R.string.bird), getString(R.string.bird_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.cat, getString(R.string.cat), getString(R.string.cat_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.dog, getString(R.string.dog), getString(R.string.dog_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.giraffe, getString(R.string.giraffe), getString(R.string.giraffe_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.elephant, getString(R.string.elephant), getString(R.string.giraffe_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.fox, getString(R.string.fox), getString(R.string.fox_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.horse, getString(R.string.horse), getString(R.string.horse_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.deer, getString(R.string.deer), getString(R.string.horse_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.rabbit, getString(R.string.rabbit), getString(R.string.rabbit_description)));
        animalItemArrayList.add(new AnimalItem(R.drawable.butterfly, getString(R.string.butterfly), getString(R.string.butterfly_description)));

    }

    public void buildRecyclerView() {
        animalRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new ExampleAdapter(animalItemArrayList);
        animalRecyclerView.setLayoutManager(mLayoutManager);
        animalRecyclerView.setHasFixedSize(true);
        animalRecyclerView.setAdapter(mAdapter);

    }

    private void addTransformButtonClickListeners() {
        changeButtonStateAddOnClickListener(R.id.rotateButton);
        changeButtonStateAddOnClickListener(R.id.flipButton);
        changeButtonStateAddOnClickListener(R.id.arrowBack);
        changeButtonStateAddOnClickListener(R.id.arrowForward);
        changeButtonStateAddOnClickListener(R.id.arrowUpward);
        changeButtonStateAddOnClickListener(R.id.arrowDownward);
        changeButtonStateAddOnClickListener(R.id.vectorCenter);
    }

    private void changeButtonStateAddOnClickListener(int buttonID){
        getView().findViewById(buttonID).setOnClickListener(view -> {
            if (isInputSelected()) mAdapter.changeButtonState(buttonID);
        });

    }

    private boolean isInputSelected() {
        if (currentPosition == -1) {
            Toast.makeText(getActivity(), R.string.please_select_an_animal_image,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private float dpToPx(int dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
