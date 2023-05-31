package com.example.expensemodule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RentAndElectricityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentAndElectricityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RentAndElectricityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RentAndElectricityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RentAndElectricityFragment newInstance(String param1, String param2) {
        RentAndElectricityFragment fragment = new RentAndElectricityFragment();
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

        View v = inflater.inflate(R.layout.fragment_rent_and_electricity, container, false);
        Button btnRentElectricity = v.findViewById(R.id.BtnRentElectricity);
        EditText rentET = (EditText) v.findViewById(R.id.RentET);

        EditText electricityET = (EditText) v.findViewById(R.id.ElectricityET);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getContext());
        List<User> userList = databaseHelper.userDao().getAllUsersWithName(MainActivity.USER_NAME);
        rentET.setHint("RM "+userList.get(0).getRent());
        electricityET.setHint("RM "+userList.get(0).getElectricity());




        btnRentElectricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rent = rentET.getText().toString();
                String electricity = electricityET.getText().toString();

                if (rent.equals("") || electricity.equals("")) {
                    Toast.makeText(getActivity(), "You did not enter a field", Toast.LENGTH_SHORT).show();

                }else{
                    List<User> userList = databaseHelper.userDao().getAllUsersWithName(MainActivity.USER_NAME);
                    userList.get(0).setRent(Double.parseDouble(rent));
                    userList.get(0).setElectricity(Double.parseDouble(electricity));
                    databaseHelper.userDao().updateUser(userList.get(0));
                    Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();

                }

            }
        });




        // Inflate the layout for this fragment
        return v;
    }
}