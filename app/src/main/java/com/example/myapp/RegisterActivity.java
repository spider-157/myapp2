package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.Formatter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public static final String User_Email = "com.example.myapp.uemail";
    public static final String User_Pass = "com.example.myapp.upass";

    private EditText _nameText, _addrText, _emailText, _mobileText, _passwordText, _secretnoText;
    private Button _signupButton, _locationButton;
    private TextView _loginLink;
    private FirebaseAuth auth;
    private DatabaseReference databaseUser;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        auth = FirebaseAuth.getInstance();

        _nameText = findViewById(R.id.input_name);
        _addrText = findViewById(R.id.input_address);
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _mobileText = findViewById(R.id.input_mobile);
        _signupButton = findViewById(R.id.btn_signup);
        _secretnoText = findViewById(R.id.input_secretkey);
        _locationButton = findViewById(R.id.btn_loc);

        _locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                _addrText.setText(""+locationManager);

            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = _nameText.getText().toString().trim();
                final String addr = _addrText.getText().toString().trim();
                final String email = _emailText.getText().toString().trim();
                final String password = _passwordText.getText().toString().trim();
                final String mobile = _mobileText.getText().toString().trim();
                final String secretbit = _secretnoText.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(addr)) {
                    Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "Enter mobile number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mobile.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Number incorrect!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(secretbit)) {
                    Toast.makeText(getApplicationContext(), "Enter secret number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                String id = databaseUser.push().getKey();
                                User user = new User(id, name, addr, email, mobile, password, secretbit);
                                databaseUser.child(id).setValue(user);

                                Toast.makeText(RegisterActivity.this, "Successfully registered" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, SecretActivity.class));
                                    finish();
                                }
                            }
                        });

              /*  if ((!TextUtils.isEmpty(name))||(!TextUtils.isEmpty(addr))||(!TextUtils.isEmpty(email))||(!TextUtils.isEmpty(password))||(!TextUtils.isEmpty(mobile))) {
                    String id = databaseUser.push().getKey();

                    User user = new User(id, name, addr, email, password, mobile);

                    databaseUser.child(id).setValue(user);

                    Toast.makeText(getApplicationContext(),"User Added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_LONG).show();
                } */
            }
        });

       /* _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }); */

    }
}
