package com.example.expensemodule;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Income_table")
public class IncomeEntity {
    public static List<IncomeEntity> incomeEntityList;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    private String user_name;

    @ColumnInfo(name = "income_amount")
    private double income_amount;

    @ColumnInfo(name = "income_category")
    private String income_category;

    @ColumnInfo(name = "income_day")
    private int income_day;

    @ColumnInfo(name = "income_month")
    private int income_month;

    @ColumnInfo(name = "income_year")
    private int income_year;

    public IncomeEntity(int id, String user_name, double income_amount, String income_category, int income_day, int income_month, int income_year) {
        this.id = id;
        this.user_name = user_name;
        this.income_amount = income_amount;
        this.income_category = income_category;
        this.income_day = income_day;
        this.income_month = income_month;
        this.income_year = income_year;
    }

    @Ignore
    public IncomeEntity(String user_name, double income_amount, String income_category, int income_day, int income_month, int income_year) {
        this.user_name = user_name;
        this.income_amount = income_amount;
        this.income_category = income_category;
        this.income_day = income_day;
        this.income_month = income_month;
        this.income_year = income_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIncome_amount() {
        return income_amount;
    }

    public void setIncome_amount(double income_amount) {
        this.income_amount = income_amount;
    }

    public String getIncome_category() {
        return income_category;
    }

    public void setIncome_category(String income_category) {
        this.income_category = income_category;
    }

    public int getIncome_day() {
        return income_day;
    }

    public void setIncome_day(int income_day) {
        this.income_day = income_day;
    }

    public int getIncome_month() {
        return income_month;
    }

    public void setIncome_month(int income_month) {
        this.income_month = income_month;
    }

    public int getIncome_year() {
        return income_year;
    }

    public void setIncome_year(int income_year) {
        this.income_year = income_year;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
