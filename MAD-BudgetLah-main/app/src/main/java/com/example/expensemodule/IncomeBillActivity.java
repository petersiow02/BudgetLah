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

import java.util.Calendar;
import java.util.Collections;

public class IncomeBillActivity extends AppCompatActivity {

    String[] Categories = {"Passive Income", "Pocket Money", "Side-Hustle", "Gigs", "Sales", "Refunds", "Others"};
    String CategoryChosen;
    ArrayAdapter<String> adapterCategories;
    AutoCompleteTextView category_drop_down_incomeBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_bill);
        Intent intent = getIntent();
        int position = intent.getIntExtra("incomeEntityPosition", -1);




        category_drop_down_incomeBill = findViewById(R.id.Category_drop_down_incomeBill);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        adapterCategories = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Categories);
        category_drop_down_incomeBill.setAdapter(adapterCategories);

        EditText incomeAmountEditText = findViewById(R.id.IncomeAmountEditText);
        Button saveBtnincomeBill = findViewById(R.id.SaveBtnIncomeBill);
        TextView incomeDateTV = findViewById(R.id.IncomeDateTV);

        if (position != -1){
            IncomeEntity.incomeEntityList = databaseHelper.incomeEntityDao().getAllIncomeEntities(MainActivity.USER_NAME);

            Collections.reverse(IncomeEntity.incomeEntityList);
            IncomeEntity incomeBill = IncomeEntity.incomeEntityList.get(position);
            incomeAmountEditText.setText(incomeBill.getIncome_amount()+"");
            incomeDateTV.setText(incomeBill.getIncome_day()+"-"+incomeBill.getIncome_month()+"-"+incomeBill.getIncome_year());
            category_drop_down_incomeBill.setCompletionHint(incomeBill.getIncome_category());
        }


        category_drop_down_incomeBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CategoryChosen = adapterView.getItemAtPosition(position).toString();
            }
        });

        saveBtnincomeBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Double amount = Double.parseDouble(incomeAmountEditText.getText().toString());
                String date = incomeDateTV.getText().toString();
                String[] newdate = date.split("-");
                int day = Integer.parseInt(newdate[0]);
                int month = Integer.parseInt(newdate[1]);
                int year = Integer.parseInt(newdate[2]);

                if (position != -1){
                    IncomeEntity incomeBill = IncomeEntity.incomeEntityList.get(position);
                    incomeBill.setIncome_amount(amount);
                    incomeBill.setIncome_category(CategoryChosen);
                    incomeBill.setIncome_day(day);
                    incomeBill.setIncome_month(month);
                    incomeBill.setIncome_year(year);
                    databaseHelper.incomeEntityDao().updateIncomeEntity(incomeBill);
                    Intent intent = new Intent(getApplicationContext(), IncomeModule.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }
                else if (amount != null && !date.equalsIgnoreCase( "Date") && CategoryChosen != null){
                    databaseHelper.incomeEntityDao().addIncomeEntity(
                            new IncomeEntity(MainActivity.USER_NAME,amount, CategoryChosen, day, month, year)
                    );


                    Intent intent = new Intent(getApplicationContext(), IncomeModule.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }

            }
        });

        ImageButton backBtnElectricityBill = findViewById(R.id.BackBtnIncomeBill);
        backBtnElectricityBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button deleteBtn = findViewById(R.id.DeleteIncomeBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != -1){
                    IncomeEntity incomeBill = IncomeEntity.incomeEntityList.get(position);
                    databaseHelper.incomeEntityDao().addIncomeEntity(incomeBill);
                    Intent intent = new Intent(getApplicationContext(), IncomeModule.class);
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
            ((TextView) getActivity().findViewById(R.id.IncomeDateTV)).setText(date);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new IncomeBillActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}