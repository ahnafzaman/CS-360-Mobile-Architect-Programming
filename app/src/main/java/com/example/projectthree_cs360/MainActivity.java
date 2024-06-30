package com.example.projectthree_cs360;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button createAccountButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);

        loginButton.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginButton.setEnabled(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
    }

    public void onLogin(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (db.checkUser(user, pass)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DataDisplayActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreateAccount(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (db.insertUser(user, pass)) {
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Account creation failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestSmsPermission(View view) {
        Intent intent = new Intent(MainActivity.this, SMSPermissionActivity.class);
        startActivity(intent);
    }
}
