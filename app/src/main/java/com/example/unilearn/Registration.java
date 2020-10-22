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

    TextInputLayout registrationName, registrationUser, registrationEmail, registrationPhone, registrationPassword;
    Button btnLogIn, btnSignUp;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        registrationName = findViewById(R.id.reg_name);
        registrationUser = findViewById(R.id.reg_username);
        registrationEmail = findViewById(R.id.reg_email);
        registrationPhone = findViewById(R.id.reg_phone);
        registrationPassword = findViewById(R.id.reg_password);

        btnLogIn = findViewById(R.id.btnLog);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });

        btnSignUp = findViewById(R.id.btnSign);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                //Get all the value
                String name = registrationName.getEditText().getText().toString();
                String username = registrationUser.getEditText().getText().toString();
                String email = registrationEmail.getEditText().getText().toString();
                String phoneNo = registrationPhone.getEditText().getText().toString();
                String password = registrationPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                reference.child(email).setValue(helperClass);
            }
        });

    }

    //Save the data in Firebase on button click
    public void registerUser (View view) {

        //Get all the value
        String name = registrationName.getEditText().getText().toString();
        String username = registrationUser.getEditText().getText().toString();
        String email = registrationEmail.getEditText().getText().toString();
        String phoneNo = registrationPhone.getEditText().getText().toString();
        String password = registrationPassword.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
        reference.child(username).setValue(helperClass);

    }
}
