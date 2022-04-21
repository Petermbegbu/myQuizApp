package myquizapp.ica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    EditText email, password, confirmPwd;
    Button buttonRegister, buttonSignin;
    DBHelper myDb;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editText_login_email);
        password = (EditText) findViewById(R.id.editText_login_password);
        confirmPwd = (EditText) findViewById(R.id.editText_login_confirm_password);
        progressBar = findViewById(R.id.progressBar);

        buttonRegister = (Button) findViewById(R.id.btn_register);
        buttonSignin = (Button) findViewById(R.id.btn_signin);

        myDb = new DBHelper(this);

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                String textConfirmPwd = confirmPwd.getText().toString();


                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    email.setError("Email is required");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LoginActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    email.setError("Valid email is required");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    password.setError("Password is required");
                    password.requestFocus();
                }  else if (textPassword.length() < 5){
                    Toast.makeText(LoginActivity.this, "Password should be at least 5 digits", Toast.LENGTH_LONG).show();
                    password.setError("Password too weak");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)){
                    Toast.makeText(LoginActivity.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    confirmPwd.setError("Password confirmation is required");
                    confirmPwd.requestFocus();
                } else if (!textPassword.equals(textConfirmPwd)){
                    Toast.makeText(LoginActivity.this, "Please enter same password", Toast.LENGTH_LONG).show();
                    confirmPwd.setError("Password did not match");
                    confirmPwd.requestFocus();
                    //Clear confirm password
                    confirmPwd.clearComposingText();
                } else {
                    //Register User
                    progressBar.setVisibility(View.VISIBLE);

                    Boolean checkEmailPassword = myDb.checkEmailPassword(textEmail, textPassword);

                    if(checkEmailPassword) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                        //Move to Home Page
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                        //Pass the email address to the home screen
                        intent.putExtra(HomeActivity.EMAIL, textEmail);

                        startActivity(intent);

                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}