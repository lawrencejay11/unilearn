package com.example.unilearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    TextInputLayout regName, regUser, regEmail, regPhone, regPassword;
    Button btnLogIn, btnSignUp;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        regName = findViewById(R.id.reg_name);
        regUser = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhone = findViewById(R.id.reg_phone);
        regPassword = findViewById(R.id.reg_password);
        btnSignUp = findViewById(R.id.btnSign);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                //Get all the value
                String name = regName.getEditText().getText().toString();
                String username = regUser.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhone.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                reference.child(email).setValue(helperClass);
            }
        });

        btnLogIn = findViewById(R.id.btnLog);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });
    }

    //Save the data in Firebase on button click
    public void registerUser (View view) {

        if (!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNo() | !validatePassword()) {
            return;
        }

        //Get all the value
        String name = regName.getEditText().getText().toString();
        String username = regUser.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
        reference.child(username).setValue(helperClass);

    }

    private boolean validateName(){
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername(){
        String val = regUser.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUser.setError("Field cannot be empty");
            return false;
        }
        else if (val.length() >= 15) {
            regUser.setError("Username is too long");
            return false;
        }
        else if (!val.matches(noWhiteSpace)) {
            regUser.setError("Space is not allowed");
            return false;
        }
        else {
            regUser.setError(null);
            regUser.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            regUser.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNo(){
        String val = regPhone.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhone.setError("Field cannot be empty");
            return false;
        }
        else {
            regPhone.setError(null);
            regUser.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\s+$)" +           //no spacing
                ".{4,} }" +              //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regUser.setErrorEnabled(false);
            return true;
        }
    }
}
