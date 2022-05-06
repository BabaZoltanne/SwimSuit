package com.example.swimsuitwebshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = String.valueOf(MainActivity.class.getPackage());
    private static final int SECRET_KEY = 23;

    EditText userNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passAgainEditText;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if(secret_key != 23) {
            finish();
        }

        EditText userNameEditText = findViewById(R.id.userNameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText passAgainEditText = findViewById(R.id.passAgainEditText);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        userNameEditText.setText(userName);
        passwordEditText.setText(password);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {
        String userName = userNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passAgain = passAgainEditText.getText().toString();

        if (!password.equals(passAgain)){
            Log.e(LOG_TAG, "Nem ugyanazt a jelszót adtad meg mindkét mezőben!");
            return;
        }

        Log.i(LOG_TAG,"Regisztrált: " + userName + ",email: " + email + ",jelszó: " + password + ",jelszó újra: " + passAgain);
        //startShopping();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new onCompleteListener<AuthResult>() {
            @Override
            public void OnComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(LOG_TAG, "User created successfully.");
                    startShopping();
                }else {
                    Log.d(LOG_TAG, "User wasn't created successfully.");
                    Toast.makeText(RegisterActivity.this, "User wasn't created successfully." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void cancel(View view) {
        finish();
    }

    private void startShopping() {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }
}