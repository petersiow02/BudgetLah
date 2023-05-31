package com.example.expensemodule;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BudgetDao {

    @Query("SELECT * FROM budget_table where username == :username")
    public List<Budget> getAllBudget(String username);

    @Insert
    public void addBudget(Budget budget);

    @Update
    public void updateBudget(Budget budget);

    @Delete
    public void deleteBudget(Budget budget);

}
