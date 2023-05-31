package com.example.expensemodule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ChangePasswordActivity extends AppCompatActivity {

    Activity context;
    EditText inputUsername,inputNewpassword,inputConfirmnewpassword;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btnConfirm = findViewById(R.id.btnconfirm);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        inputUsername = findViewById(R.id.inputUsernamePS);
        inputNewpassword=findViewById(R.id.inputnewpassword);
        inputConfirmnewpassword=findViewById(R.id.inputconfirmnewpassword);
        btnConfirm=findViewById(R.id.btnconfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });
    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String newpassword = inputNewpassword.getText().toString();
        String confirmnewpassword = inputConfirmnewpassword.getText().toString();

        if (newpassword.isEmpty() || newpassword.length() < 8)
        {
            showError(inputNewpassword, "Your password is not valid!");
        }
        else if (confirmnewpassword.isEmpty() || confirmnewpassword.length()<8 || !confirmnewpassword.equals(newpassword))
        {
            showError(inputConfirmnewpassword, "Your passwords don't match");
        }
        else
        {
            DatabaseHelper databaseHelper = DatabaseHelper.getDB(getApplicationContext());
            List<User> userList= databaseHelper.userDao().getAllUsersWithName(username);
            if (userList.isEmpty()){
                showError(inputUsername, "User does not exist");
            }else{
                userList.get(0).setPassword(confirmnewpassword);
                databaseHelper.userDao().updateUser(userList.get(0));
                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Password Changed", Toast.LENGTH_SHORT).show();
            }


        }
    }
    private void showError(EditText input, String your_password_is_not_valid) {
        input.setError(your_password_is_not_valid);
        input.requestFocus();
    }
}