package com.example.expensemodule;

import androidx.room.*;

import java.util.Date;
import java.util.List;

@Entity(tableName = "spending_bills_table")
public class SpendingBill {
    public static List<SpendingBill> spendingBills;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    private String user_name;

    @ColumnInfo(name = "spending_bill_amount")
    private double spending_bill_amount;

    @ColumnInfo(name = "spending_bill_category")
    private String spending_bill_category;

    @ColumnInfo(name = "spending_bill_day")
    private int spending_bill_day;

    @ColumnInfo(name = "spending_bill_month")
    private int spending_bill_month;

    @ColumnInfo(name = "spending_bill_year")
    private int spending_bill_year;


    public SpendingBill(int id, String user_name, double spending_bill_amount, String spending_bill_category, int spending_bill_day, int spending_bill_month, int spending_bill_year) {
        this.id = id;
        this.user_name = user_name;
        this.spending_bill_amount = spending_bill_amount;
        this.spending_bill_category = spending_bill_category;
        this.spending_bill_day = spending_bill_day;
        this.spending_bill_month = spending_bill_month;
        this.spending_bill_year = spending_bill_year;
    }

    @Ignore
    public SpendingBill(String user_name, double spending_bill_amount, String spending_bill_category, int spending_bill_day, int spending_bill_month, int spending_bill_year) {
        this.user_name = user_name;
        this.spending_bill_amount = spending_bill_amount;
        this.spending_bill_category = spending_bill_category;
        this.spending_bill_day = spending_bill_day;
        this.spending_bill_month = spending_bill_month;
        this.spending_bill_year = spending_bill_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSpending_bill_amount() {
        return spending_bill_amount;
    }

    public void setSpending_bill_amount(double spending_bill_amount) {
        this.spending_bill_amount = spending_bill_amount;
    }

    public String getSpending_bill_category() {
        return spending_bill_category;
    }

    public void setSpending_bill_category(String spending_bill_category) {
        this.spending_bill_category = spending_bill_category;
    }

    public int getSpending_bill_day() {
        return spending_bill_day;
    }

    public void setSpending_bill_day(int spending_bill_day) {
        this.spending_bill_day = spending_bill_day;
    }

    public int getSpending_bill_month() {
        return spending_bill_month;
    }

    public void setSpending_bill_month(int spending_bill_month) {
        this.spending_bill_month = spending_bill_month;
    }

    public int getSpending_bill_year() {
        return spending_bill_year;
    }

    public void setSpending_bill_year(int spending_bill_year) {
        this.spending_bill_year = spending_bill_year;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}