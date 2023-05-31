package com.example.expensemodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class SpendingBillActivity extends AppCompatActivity {


    String[] Categories = {"Foods & Drinks", "Bills & Fees", "Groceries", "Entertainment", "Shopping", "Investments", "Health", "Transport", "Others"};
    String CategoryChosen;
    ArrayAdapter<String> adapterCategories;
    AutoCompleteTextView category_drop_down_spendingBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_bill);
        Intent intent = getIntent();
        int position = intent.getIntExtra("SpendingEntityPosition", -1);




        category_drop_down_spendingBill = findViewById(R.id.Category_drop_down_spendingBill);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        adapterCategories = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Categories);
        category_drop_down_spendingBill.setAdapter(adapterCategories);

        EditText spendingAmountEditText = findViewById(R.id.SpendingAmountEditText);
        Button saveBtnSpendingBill = findViewById(R.id.SaveBtnSpendingBill);
        TextView spendingDateTV = findViewById(R.id.SpendingDateTV);

        if (position != -1){
            SpendingBill.spendingBills = databaseHelper.spendingBillDao().getAllSpendingBills(MainActivity.USER_NAME);

            Collections.reverse(SpendingBill.spendingBills);
            SpendingBill spendingBill = SpendingBill.spendingBills.get(position);
            spendingAmountEditText.setText(spendingBill.getSpending_bill_amount()+"");
            spendingDateTV.setText(spendingBill.getSpending_bill_day()+"-"+spendingBill.getSpending_bill_month()+"-"+spendingBill.getSpending_bill_year());
            category_drop_down_spendingBill.setCompletionHint(spendingBill.getSpending_bill_category());
        }


        category_drop_down_spendingBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CategoryChosen = adapterView.getItemAtPosition(position).toString();
            }
        });

        saveBtnSpendingBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Double amount = Double.parseDouble(spendingAmountEditText.getText().toString());
                String date = spendingDateTV.getText().toString();
                String[] newdate = date.split("-");
                int day = Integer.parseInt(newdate[0]);
                int month = Integer.parseInt(newdate[1]);
                int year = Integer.parseInt(newdate[2]);

                if (position != -1){
                    SpendingBill spendingBill = SpendingBill.spendingBills.get(position);
                    spendingBill.setSpending_bill_amount(amount);
                    spendingBill.setSpending_bill_category(CategoryChosen);
                    spendingBill.setSpending_bill_day(day);
                    spendingBill.setSpending_bill_month(month);
                    spendingBill.setSpending_bill_year(year);
                    databaseHelper.spendingBillDao().updateSpendingBill(spendingBill);
                    Intent intent = new Intent(getApplicationContext(), YourExpenses.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }
                else if (amount != null && !date.equalsIgnoreCase("Date") && CategoryChosen != null){
                    //adding a new spending bill
                    databaseHelper.spendingBillDao().addSpendingBill(
                            new SpendingBill(MainActivity.USER_NAME,amount, CategoryChosen, day, month, year)
                    );

                    Intent intent = new Intent(getApplicationContext(), YourExpenses.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }

            }
        });

        ImageButton backBtnElectricityBill = findViewById(R.id.BackBtnSpendingBill);
        backBtnElectricityBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button deleteBtn = findViewById(R.id.DeleteSpendingBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != -1){
                    SpendingBill spendingBill = SpendingBill.spendingBills.get(position);
                    databaseHelper.spendingBillDao().deleteSpendingBill(spendingBill);
                    Intent intent = new Intent(getApplicationContext(), YourExpenses.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }

            }
        });


    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String date = day+"-"+(month+1)+"-"+year;
            ((TextView) getActivity().findViewById(R.id.SpendingDateTV)).setText(date);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}