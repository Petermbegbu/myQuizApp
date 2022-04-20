package myquizapp.ica;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText username, email, password, confirmPwd;
    Button buttonRegister, buttonSignin;
    DBHelper myDb;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setTitle("myQuizApp");

        username = (EditText) findViewById(R.id.editText_register_full_name);
        email = (EditText) findViewById(R.id.editText_register_email);
        password = (EditText) findViewById(R.id.editText_register_password);
        confirmPwd = (EditText) findViewById(R.id.editText_register_confirm_password);
        progressBar = findViewById(R.id.progressBar);

        buttonRegister = (Button) findViewById(R.id.button_register);
        buttonSignin = (Button) findViewById(R.id.button_signin);

        myDb = new DBHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUsername = username.getText().toString();
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                String textConfirmPwd = confirmPwd.getText().toString();


                if(TextUtils.isEmpty(textUsername)){
                    Toast.makeText(MainActivity.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    username.setError("Full Name is required");
                    username.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(MainActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    email.setError("Email is required");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(MainActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    email.setError("Valid email is required");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(MainActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    password.setError("Password is required");
                    password.requestFocus();
                }  else if (textPassword.length() < 5){
                    Toast.makeText(MainActivity.this, "Password should be at least 5 digits", Toast.LENGTH_LONG).show();
                    password.setError("Password too weak");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)){
                    Toast.makeText(MainActivity.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    confirmPwd.setError("Password confirmation is required");
                    confirmPwd.requestFocus();
                } else if (!textPassword.equals(textConfirmPwd)){
                    Toast.makeText(MainActivity.this, "Please enter same password", Toast.LENGTH_LONG).show();
                    confirmPwd.setError("Password did not match");
                    confirmPwd.requestFocus();
                    //Clear confirm password
                    confirmPwd.clearComposingText();
                } else {
                    //Register User
                    //registerUser(textUsername, textEmail, textPassword);

                    progressBar.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.VISIBLE);

                    Boolean checkEmail = myDb.checkEmail(textEmail);

                    if(checkEmail == false) {
                        Boolean registerResult = myDb.insertData(textUsername, textEmail, textPassword);

                        if(registerResult == true){
                            Toast.makeText(MainActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                            //Move to login Page
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }else {
                        Toast.makeText(MainActivity.this, "User already Exist", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });






        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

//
//    void registerUser(String textUsername, String textEmail, String textPassword){
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();
//                    //FirebaseUser firebaseUser = auth.getCurrentUser();
//
//                    //Send Verification Email
//                    //firebaseUser.sendEmailVerification();
//
//                    /*//Open User profile after successful registration
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//
//                    //To prevent User from returning back to Register Activity on pressing back button after registration
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();*/
//
//                }
//            }
//        });
//    }
}

