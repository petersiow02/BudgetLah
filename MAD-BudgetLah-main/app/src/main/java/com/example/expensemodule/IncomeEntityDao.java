package com.example.expensemodule;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IncomeEntityDao {

    @Query("SELECT * FROM Income_table where user_name == :user_name order by income_year, income_month, income_day")
    public List<IncomeEntity> getAllIncomeEntities(String user_name);

    @Insert
    public void addIncomeEntity(IncomeEntity incomeEntity);

    @Update
    public void updateIncomeEntity(IncomeEntity incomeEntity);

    @Delete
    public void deleteIncomeEntity(IncomeEntity incomeEntity);

    @Query("select sum(income_amount) from Income_table where user_name == :user_name and income_day between :startDay and :endDay and income_month between :startMonth and :endMonth and income_year between :startYear and :endYear ")
    public double getSumOfIncomeFromTo(String user_name,int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear);

    @Query("select income_category from Income_table where user_name == :user_name and income_day between :startDay and :endDay and income_month between :startMonth and :endMonth and income_year between :startYear and :endYear group by income_category order by income_amount")
    public List<String> getIncomeCategoryFromTo(String user_name,int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear);

    @Query("select sum(income_amount) from Income_table where user_name == :user_name and income_day between :startDay and :endDay and income_month between :startMonth and :endMonth and income_year between :startYear and :endYear group by income_category order by income_amount")
    public List<Double> getIncomeCategorySumFromTo(String user_name,int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear);


}
