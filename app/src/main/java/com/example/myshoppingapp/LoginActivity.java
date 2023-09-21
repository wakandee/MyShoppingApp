package com.example.myshoppingapp;

//package com.example.logsignsqlpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.logsignsqlpractice.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

//    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;
    Button login_button,Anonymous_btn;
    TextView signupRedirectText;
    EditText str_email,str_pwd;

    public SharedPreferences preferences;// = PreferenceManager.getDefaultSharedPreferences(this);//null;
//    SharedPreferences pref = getSharedPreferences("PREFS", MODE_PRIVATE);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        binding = ActivityLoginBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // User is logged in, go to the main content

            Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }  // User is not logged in, redirect to the login page


        login_button = (Button)findViewById(R.id.login_button);
        signupRedirectText = (TextView)findViewById(R.id.signupRedirectText);
        str_email = (EditText)findViewById((R.id.login_email));
        str_pwd = (EditText)findViewById((R.id.login_password));

        Anonymous_btn = (Button)findViewById(R.id.Anonymous_btn);

        databaseHelper = new DatabaseHelper(this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = str_email.getText().toString();
                String password = str_pwd.getText().toString();

                if(email.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    if(checkCredentials == true){

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();


                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        Anonymous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();


                Toast.makeText(LoginActivity.this, "Anonymous Loggedin Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}