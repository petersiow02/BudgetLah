package com.example.expensemodule;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateBudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CreateBudgetFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner dropdown;
    EditText amount;
    Switch alertStatus;
    Slider alertThreshold;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateBudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateBudgetFragment newInstance(String param1, String param2) {
        CreateBudgetFragment fragment = new CreateBudgetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CreateBudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_budget, container, false);

        dropdown = rootView.findViewById(R.id.spinner1);
        initspinnerfooter();

        amount = rootView.findViewById(R.id.editTextNumber);
        alertStatus = rootView.findViewById(R.id.switch1);
        alertThreshold = rootView.findViewById(R.id.discreteSlider);

        Button createBtn = (Button) rootView.findViewById(R.id.back_button);
        Button createBudgetBtn = (Button) rootView.findViewById(R.id.create_budget);

        createBudgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBudget();

            }
        });

        return rootView;
    }

    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.create_budget:
                createBudget();
                break;
        }
    }

    public void createBudget() {
        System.out.println("edit budget");
        System.out.println(MainActivity.USER_NAME);
        String amt = amount.getText().toString();
        boolean alertStat = alertStatus.isChecked();
        float range = alertThreshold.getValue();
        String category = (String) dropdown.getSelectedItem();

        //insert db
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getActivity());
        List<String> categoryList =  databaseHelper.spendingBillDao().getSpendingBillCategory(MainActivity.USER_NAME);
        databaseHelper.budgetDao().addBudget(
                new Budget(MainActivity.USER_NAME, category, Double.parseDouble(amt), alertStat));

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

    }

    private void initspinnerfooter() {
        String[] Categories = {"Foods & Drinks", "Bills & Fees", "Groceries", "Entertainment", "Shopping", "Investments", "Health", "Transport", "Others"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Categories);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}
