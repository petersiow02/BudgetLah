package com.example.expensemodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView btn;
    EditText inputUsername,inputPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn = findViewById(R.id.textViewSignup);
        inputUsername=findViewById(R.id.inputusername);
        inputPassword=findViewById(R.id.inputpassword);
        btnLogin=findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });


        btn.setOnClickListener((v) -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        if (username.isEmpty())
        {
            showError(inputUsername, "Please enter your username");
        }
        else if  (username.length() < 8)
        {
            showError(inputUsername, "Your username must be 8 characters or more");
        }
        else if (password.isEmpty())
        {
            showError(inputPassword, "Please enter your password");
        }
        else if (password.length()<8)
        {
            showError(inputPassword, "Password must be at least 8 characters");
        }
        else
        {
            DatabaseHelper databaseHelper = DatabaseHelper.getDB(getApplicationContext());
            List<User> UsersList = databaseHelper.userDao().getAllUsersWithName(username);
            if (UsersList.isEmpty() || !UsersList.get(0).getPassword().equals(password)){
                showError(inputUsername, "Please check your username/password");
                showError(inputPassword, "Please check your username/password");
            }else{
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("UserName", username);
                startActivity(intent);
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void showError(EditText input, String your_username_is_not_valid) {
        input.setError(your_username_is_not_valid);
        input.requestFocus();
    }
}