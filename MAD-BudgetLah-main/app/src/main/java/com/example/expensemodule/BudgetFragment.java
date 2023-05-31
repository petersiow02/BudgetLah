package com.example.expensemodule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String tutorials[]
            = { "Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies" };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
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

        View v = inflater.inflate(R.layout.fragment_budget, container, false);

        Button createBtn = (Button) v.findViewById(R.id.createBtn);
        ListView lv = (ListView) v.findViewById(R.id.list);

//        ArrayList<Budget> arrayList = new ArrayList<>();
//        arrayList.add(new Budget(1,"investment", 500.00, false));
//        arrayList.add(new Budget(2,"shopping", 250.00, false));
//
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("hello world");
//        arrayList.add("hllo wsdfsdf");

//        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayList);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getActivity());
        List<Budget> budgetList = databaseHelper.budgetDao().getAllBudget(MainActivity.USER_NAME);

        CustomAdapterBudget customAdapterBudget = new CustomAdapterBudget(getContext(), budgetList);
        lv.setAdapter(customAdapterBudget);

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                // Remove item from backing list here
//                customAdapterBudget.notifyDataSetChanged();
//            }
//        });
//
//        itemTouchHelper.attachToRecyclerView(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                System.out.println("delete delete");
                AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete the budget?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.print("removed");
                    }});
                adb.show();
            }
        });

        createBtn.setOnClickListener((View.OnClickListener) this);

        return v;
    }

    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.createBtn:
                fragment = new CreateBudgetFragment();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

class CustomAdapterBudget extends ArrayAdapter<Budget>
{
    public CustomAdapterBudget(Context context, List<Budget> notes)
    {
        super(context, 0, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Budget budget = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.budget_list_item, parent, false);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getContext());
        List<Double> categorySum = databaseHelper.spendingBillDao().getSumOfCategory(MainActivity.USER_NAME);
        List<String> categoryList = databaseHelper.spendingBillDao().getSpendingBillCategory(MainActivity.USER_NAME);

        TextView category = convertView.findViewById(R.id.categoryview);
        TextView amount = convertView.findViewById(R.id.amount);
        ProgressBar progress = convertView.findViewById(R.id.progressBar);

        category.setText(budget.getCategory());
        amount.setText("Remaining RM " + budget.getAmount());

        int n = 0;
        for (String i : categoryList) {
            if (i.equalsIgnoreCase(budget.getCategory())) {
                Double difference =  categorySum.get(n) / budget.getAmount();

                progress.setProgress((int) (difference + 0));
            }

            n++;
        }

//        left.setText("RM 1200 of RM" + budget.getAmount());

//        amount.setText("RM "+incomeEntity.getIncome_amount());
//        date.setText(incomeEntity.getIncome_category()+" ("+incomeEntity.getIncome_day()+"-"+incomeEntity.getIncome_month()+"-"+incomeEntity.getIncome_year()+")");

        return convertView;
    }
}