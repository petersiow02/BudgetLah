package com.example.expensemodule;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;

public class MonthlyReport extends Fragment {

    private Button button;
    private PieChart pieChart;
    String[] Months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    int MonthChosen = 1;
    int YearChosen = 2023;
    ArrayAdapter<String> adapterMonths;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly_report, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /**AutoCompleteTextView month_drop_down = getView().findViewById(R.id.month_drop_down);

        adapterMonths = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Months);
        month_drop_down.setAdapter(adapterMonths);**/
        Button History = (Button) getView().findViewById(R.id.History);


        pieChart = getView().findViewById(R.id.monthlypiechart);
        setupPieChart();
        loadPieChartData();

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), YourExpenses.class);
                startActivity(intent);
            }
        });

        TableLayout table = getView().findViewById(R.id.tableLayout);
        table();



    }

    private void table(){
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getActivity());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        Double totalExpense1 = databaseHelper.spendingBillDao().getSumOfExpensesFromTo(MainActivity.USER_NAME,0, month+1, year, 31, month+1, year);
        Double totalIncome1 = databaseHelper.incomeEntityDao().getSumOfIncomeFromTo(MainActivity.USER_NAME,0, month+1, year, 31, month+1, year);

        List<User> userList = databaseHelper.userDao().getAllUsersWithName(MainActivity.USER_NAME);
        Double totalExpense2 = userList.get(0).getElectricity() + userList.get(0).getRent();
        Double totalIncome2 = userList.get(0).getPlanned_income();

        Double totalExpense = totalExpense1 + totalExpense2;
        Double totalIncome = totalIncome1 + totalIncome2;

        TextView expense_total_monthly = getView().findViewById(R.id.expense_total_monthly);
        expense_total_monthly.setText("-RM "+totalExpense.toString()+"  ");
        TextView income_total_monthly = getView().findViewById(R.id.income_total_monthly);
        income_total_monthly.setText("+RM"+totalIncome.toString()+"  ");
        TextView netEarnings_total_monthly = getView().findViewById(R.id.netEarnings_total_monthly);

        Double netE = totalIncome - totalExpense;

        if (netE<0){
            netEarnings_total_monthly.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }else if (netE>0){
            netEarnings_total_monthly.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        netEarnings_total_monthly.setText(netE+"  ");
    }


    private void setupPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(16);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("MONTHLY EXPENSE");
        pieChart.setCenterTextSize(18);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);
        l.setTextColor(Color.BLACK);
        l.setTextSize(10f);
    }

    private void loadPieChartData() {


        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getActivity());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        Double totalExpense1 = databaseHelper.spendingBillDao().getSumOfExpensesFromTo(MainActivity.USER_NAME,0, month+1, year, 31, month+1, year);

        List<User> userList = databaseHelper.userDao().getAllUsersWithName(MainActivity.USER_NAME);
        Double totalExpense2 = 0.00;
        totalExpense2 = userList.get(0).getElectricity() + userList.get(0).getRent();

        Double totalExpense = totalExpense1 + totalExpense2;

        List<Double> categoriesSum =  databaseHelper.spendingBillDao().getCategorySumFromTo(MainActivity.USER_NAME,0, month+1, year, 31, month+1, year);
        List<String> categories =  databaseHelper.spendingBillDao().getCategoryFromTo(MainActivity.USER_NAME,0, month+1, year, 31, month+1, year);



        ArrayList<PieEntry> yValues = new ArrayList<>();
        int n = 0;
        yValues.add(new PieEntry((float) ((userList.get(0).getElectricity())/totalExpense),"Electricity"));
        yValues.add(new PieEntry((float) (( userList.get(0).getRent())/totalExpense),"Rent"));
        for (Double i: categoriesSum){
            yValues.add(new PieEntry((float) (i/totalExpense), categories.get(n)));
            n++;
        }




        TextView noExpense = getView().findViewById(R.id.noExpense);
        if(yValues.size()>=1){
            //if there is more than 1 expense corresponding any expense category, erase the text saying that there is no expense
            noExpense.setVisibility(GONE);
        }else{
            //if there is no expense spent this month so far, erase the pie chart and show the text saying that there is no expense
            pieChart.setVisibility(GONE);
        }

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#F54F52"));
        colors.add(Color.parseColor("#378AFF"));
        colors.add(Color.parseColor("#FFEC21"));
        colors.add(Color.parseColor("#93F03B"));
        colors.add(Color.parseColor("#FFA32F"));
        colors.add(Color.parseColor("#9552EA"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(3f);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(18);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

}