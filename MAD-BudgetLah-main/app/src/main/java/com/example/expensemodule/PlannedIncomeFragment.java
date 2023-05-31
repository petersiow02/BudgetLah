package com.example.expensemodule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlannedIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlannedIncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlannedIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlannedIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlannedIncomeFragment newInstance(String param1, String param2) {
        PlannedIncomeFragment fragment = new PlannedIncomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View v = inflater.inflate(R.layout.fragment_planned_income, container, false);
        EditText plannedIncomeET = v.findViewById(R.id.PlannedIncomeET);
        Button btnPlannedIncome = v.findViewById(R.id.BtnPlannedIncome);
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getContext());
        List<User> userList = databaseHelper.userDao().getAllUsersWithName(MainActivity.USER_NAME);
            plannedIncomeET.setHint("RM "+userList.get(0).getPlanned_income());



        btnPlannedIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plannedIncome = plannedIncomeET.getText().toString();
                if (plannedIncome.equals("")){
                    Toast.makeText(getActivity(), "You did not enter a field", Toast.LENGTH_SHORT).show();
                }else {

                    List<User> userList = databaseHelper.userDao().getAllUsersWithName(MainActivity.USER_NAME);
                    userList.get(0).setPlanned_income(Double.parseDouble(plannedIncome));
                    databaseHelper.userDao().updateUser(userList.get(0));
                    Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return v;
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }
}