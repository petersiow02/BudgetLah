package com.example.expensemodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
 * Use the {@link IncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeFragment newInstance(String param1, String param2) {
        IncomeFragment fragment = new IncomeFragment();
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
        View v =  inflater.inflate(R.layout.fragment_income, container, false);

        ListView income_LV = (ListView) v.findViewById(R.id.Income_LV);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getActivity());

        IncomeEntity.incomeEntityList = databaseHelper.incomeEntityDao().getAllIncomeEntities(MainActivity.USER_NAME);

        Collections.reverse(IncomeEntity.incomeEntityList);
        CustomAdapterIncome customAdapter = new CustomAdapterIncome(getContext(), IncomeEntity.incomeEntityList);

        income_LV.setAdapter(customAdapter);


        income_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), IncomeBillActivity.class);
                intent.putExtra("IncomeEntityPosition", position);
                startActivity(intent);


            }
        });

        FloatingActionButton fab_electricity_list = (FloatingActionButton) v.findViewById(R.id.fab_income_list);
        fab_electricity_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IncomeBillActivity.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }
}

class CustomAdapterIncome extends ArrayAdapter<IncomeEntity>
{
    public CustomAdapterIncome(Context context, List<IncomeEntity> notes)
    {
        super(context, 0, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        IncomeEntity incomeEntity = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        TextView amount  = convertView.findViewById(R.id.TV_List_Item1);
        TextView date = convertView.findViewById(R.id.TV_List_Item2);

        amount.setText("RM "+incomeEntity.getIncome_amount());
        date.setText(incomeEntity.getIncome_category()+" ("+incomeEntity.getIncome_day()+"-"+incomeEntity.getIncome_month()+"-"+incomeEntity.getIncome_year()+")");

        return convertView;
    }
}