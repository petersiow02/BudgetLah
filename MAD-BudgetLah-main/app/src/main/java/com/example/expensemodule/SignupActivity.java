package com.example.expensemodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SignupActivity extends AppCompatActivity {

    TextView btn;

    private EditText inputUsername,inputPassword,inputEmail,inputRepassword;
    Button btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn=findViewById(R.id.alreadyHaveAccount);
        inputUsername=findViewById(R.id.inputUsername);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputRepassword=findViewById(R.id.inputRepassword);

        btnSignup=findViewById(R.id.btnsignup);
        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        checkCredentials();

        btn.setOnClickListener ((v) ->  {
            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
        });
    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String rePassword = inputRepassword.getText().toString();

        if (username.isEmpty())
        {
            showError(inputUsername, "Please enter your username");
        }
        else if  (username.length() < 8)
        {
            showError(inputUsername, "Your username must be at least 8 characters");
        }
        else if (email.isEmpty())
        {
            showError(inputEmail,"Please enter your email");
        }
        else if (!email.isEmpty() && !email.contains("@"))
        {
            showError(inputEmail,"Email is not valid");
        }
        else if (password.isEmpty())
        {
            showError(inputPassword, "Please enter your password");
        }
        else if (password.length()<8)
        {
            showError(inputPassword, "Password must be 8 characters");
        }
        else if (rePassword.isEmpty() || !rePassword.equals(password))
        {
            showError(inputRepassword, "Passwords do not match!");
        }
        else
        {
            DatabaseHelper databaseHelper = DatabaseHelper.getDB(getApplicationContext());
            List<User> Userslist = databaseHelper.userDao().getAllUsersWithName(username);
            if (Userslist.isEmpty()){
                databaseHelper.userDao().addUser(
                        new User(username, email, rePassword)
                );
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }else{
                showError(inputUsername, "Username already taken");
            }

        }
    }

    private void showError(EditText input, String your_username_is_not_valid) {
        input.setError(your_username_is_not_valid);
        input.requestFocus();
    }
}