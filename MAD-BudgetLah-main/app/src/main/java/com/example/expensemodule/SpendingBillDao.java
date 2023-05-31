package com.example.expensemodule;

import androidx.room.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Dao
public interface SpendingBillDao {

    @Query("SELECT * FROM spending_bills_table where user_name == :user_name order by spending_bill_year, spending_bill_month, spending_bill_day")
    public List<SpendingBill> getAllSpendingBills(String user_name);

    @Insert
    public void addSpendingBill(SpendingBill spendingBill);

    @Update
    public void updateSpendingBill(SpendingBill spendingBill);

    @Delete
    public void deleteSpendingBill(SpendingBill spendingBill);

    @Query("select sum(spending_bill_amount) from spending_bills_table where user_name == :user_name and spending_bill_day between :startDay and :endDay and spending_bill_month between :startMonth and :endMonth and spending_bill_year between :startYear and :endYear ")
    public double getSumOfExpensesFromTo(String user_name, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear);

    @Query("select spending_bill_category from spending_bills_table where user_name == :user_name and spending_bill_day between :startDay and :endDay and spending_bill_month between :startMonth and :endMonth and spending_bill_year between :startYear and :endYear group by spending_bill_category order by spending_bill_amount")
    public List<String> getCategoryFromTo(String user_name, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear);

    @Query("select sum(spending_bill_amount) from spending_bills_table where user_name == :user_name and spending_bill_day between :startDay and :endDay and spending_bill_month between :startMonth and :endMonth and spending_bill_year between :startYear and :endYear group by spending_bill_category order by spending_bill_amount")
    public List<Double> getCategorySumFromTo(String user_name, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear);

    @Query("select sum(spending_bill_amount) from spending_bills_table where user_name == :user_name and spending_bill_month == :month and spending_bill_year == :year group by spending_bill_category order by spending_bill_amount")
    public List<Double> getCategorySumMonth(String user_name, int month, int year);

    @Query("select spending_bill_category from spending_bills_table where user_name == :user_name and spending_bill_month == :month and spending_bill_year == :year group by spending_bill_category order by spending_bill_amount")
    public List<String> getCategoryMonth(String user_name, int month, int year);

    @Query("select sum(spending_bill_amount) from spending_bills_table where user_name == :user_name and spending_bill_month == :month and spending_bill_year == :year")
    public Double getSumOfExpensesMonth(String user_name, int month, int year);


    @Query("select sum(spending_bill_amount) from spending_bills_table where user_name == :user_name group by spending_bill_category order by spending_bill_category")
    public List<Double> getSumOfCategory(String user_name);

    @Query("select spending_bill_category from spending_bills_table where user_name == :user_name group by spending_bill_category order by spending_bill_category")
    public List<String> getSpendingBillCategory(String user_name);
}
