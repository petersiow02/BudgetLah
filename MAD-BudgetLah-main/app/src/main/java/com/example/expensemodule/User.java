package com.example.expensemodule;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity(tableName = "user")
public class User {
    static List<User>  UsersList;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_name")
    private String user;

    @ColumnInfo(name = "user_email")
    private String email;

    @ColumnInfo(name = "user_password")
    private String password;

    @ColumnInfo(name = "user_rent")
    private Double rent;

    @ColumnInfo(name = "user_electricity")
    private Double electricity;

    @ColumnInfo(name = "user_planned_income")
    private Double planned_income;

    public User(String user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.rent = 0.0;
        this.electricity = 0.0;
        this.planned_income = 0.0;
    }

    @Ignore
    public User(String user, String email, String password, Double rent, Double electricity, Double planned_income) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.rent = rent;
        this.electricity = electricity;
        this.planned_income = planned_income;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Double getElectricity() {
        return electricity;
    }

    public void setElectricity(Double electricity) {
        this.electricity = electricity;
    }

    public Double getPlanned_income() {
        return planned_income;
    }

    public void setPlanned_income(Double planned_income) {
        this.planned_income = planned_income;
    }
}
