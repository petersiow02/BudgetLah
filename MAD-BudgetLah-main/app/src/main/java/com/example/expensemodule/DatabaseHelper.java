package com.example.expensemodule;

import android.content.Context;

import androidx.room.*;

@Database(entities = {SpendingBill.class, IncomeEntity.class, User.class, Budget.class}, exportSchema = false, version = 7)
public abstract class DatabaseHelper extends RoomDatabase{

    private static final String DB_NAME = "Budget_Lah_DB";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract SpendingBillDao spendingBillDao();
    public abstract IncomeEntityDao incomeEntityDao();
    public abstract UserDao userDao();
    public abstract BudgetDao budgetDao();

}
