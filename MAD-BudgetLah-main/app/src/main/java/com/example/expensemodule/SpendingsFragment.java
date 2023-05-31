package com.example.expensemodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpendingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpendingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpendingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpendingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpendingsFragment newInstance(String param1, String param2) {
        SpendingsFragment fragment = new SpendingsFragment();
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
        View v =  inflater.inflate(R.layout.fragment_spendings, container, false);

        ListView spending_LV = (ListView) v.findViewById(R.id.Spending_LV);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getActivity());

        SpendingBill.spendingBills = databaseHelper.spendingBillDao().getAllSpendingBills(MainActivity.USER_NAME);

        Collections.reverse(SpendingBill.spendingBills);
        CustomAdapterSpending customAdapter = new CustomAdapterSpending(getContext(), SpendingBill.spendingBills);

        spending_LV.setAdapter(customAdapter);


        spending_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), SpendingBillActivity.class);
                intent.putExtra("SpendingEntityPosition", position);
                startActivity(intent);
            }
        });

        FloatingActionButton fab_electricity_list = (FloatingActionButton) v.findViewById(R.id.fab_spending_list);
        fab_electricity_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpendingBillActivity.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }




}

class CustomAdapterSpending extends ArrayAdapter<SpendingBill>
{
    public CustomAdapterSpending(Context context, List<SpendingBill> notes)
    {
        super(context, 0, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        SpendingBill spendingBill = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        TextView amount  = convertView.findViewById(R.id.TV_List_Item1);
        TextView date = convertView.findViewById(R.id.TV_List_Item2);

        amount.setText("RM "+spendingBill.getSpending_bill_amount());
        date.setText(spendingBill.getSpending_bill_category()+" ("+spendingBill.getSpending_bill_day()+"-"+spendingBill.getSpending_bill_month()+"-"+spendingBill.getSpending_bill_year()+")");

        return convertView;
    }
}