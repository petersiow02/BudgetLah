package com.example.expensemodule;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TableLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

import org.w3c.dom.Text;

public class WeeklyReport extends Fragment {

    private Button button;
    private PieChart pieChart;
    //private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weekly_report, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button History = (Button) getView().findViewById(R.id.History);


        pieChart = getView().findViewById(R.id.weeklypiechart);
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

        c.add(Calendar.DAY_OF_MONTH, -7);


        Double totalExpense = databaseHelper.spendingBillDao().getSumOfExpensesFromTo(MainActivity.USER_NAME,c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), day, month+1, year);
        Double totalIncome = databaseHelper.incomeEntityDao().getSumOfIncomeFromTo(MainActivity.USER_NAME,c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), day, month+1, year);


        TextView expense_total_weekly = getView().findViewById(R.id.expense_total_weekly);
        expense_total_weekly.setText("-RM "+totalExpense.toString()+"  ");
        TextView income_total_monthly = getView().findViewById(R.id.income_total_weekly);
        income_total_monthly.setText("+RM"+totalIncome.toString()+"  ");
        TextView netEarnings_total_monthly = getView().findViewById(R.id.netEarnings_total_weekly);

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
        pieChart.getPaddingTop();
        pieChart.setEntryLabelTextSize(16);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("WEEKLY EXPENSE");
        pieChart.setCenterTextSize(18);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);
        l.setTextColor(Color.BLACK);
        l.setTextSize(10);
    }

    private void loadPieChartData() {


        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getActivity());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        c.add(Calendar.DAY_OF_MONTH, -7);


        Double totalExpense = databaseHelper.spendingBillDao().getSumOfExpensesFromTo(MainActivity.USER_NAME,c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), day, month+1, year);

        List<Double> categoriesSum =  databaseHelper.spendingBillDao().getCategorySumFromTo(MainActivity.USER_NAME,c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), day, month+1, year);
        List<String> categories =  databaseHelper.spendingBillDao().getCategoryFromTo(MainActivity.USER_NAME,c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), day, month+1, year);



        ArrayList<PieEntry> yValues = new ArrayList<>();
        int n = 0;
        for (Double i: categoriesSum){
            yValues.add(new PieEntry((float) (i/totalExpense), categories.get(n)));
            n++;
        }




        TextView noExpense = getView().findViewById(R.id.noExpense);
        if(yValues.size()>=1){
            noExpense.setVisibility(GONE);
        }else{
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