package com.example.onlinetestexam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText LoginEmaiText,LoginPasswordText;
    Button LogInButton;
    TextView SignUpText;
    private FirebaseAuth mAuth;
    ProgressBar LoginProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        LoginProgress=findViewById(R.id.LoginProgressBar);
        LoginEmaiText=findViewById(R.id.EmailEdit);
        LoginPasswordText=findViewById(R.id.passwordEdit);
        LogInButton=findViewById(R.id.loginButton);
        SignUpText=findViewById(R.id.SignUpTextView);
        SignUpText.setOnClickListener(this);
        LogInButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {



        switch(v.getId())
        {

            case R.id.SignUpTextView:
                Intent SignUpLayout=new Intent(MainActivity.this,SignUp.class);
                startActivity(SignUpLayout);
                break;

            case R.id.loginButton:
                UserLogin();
                break;
        }

    }

    public void UserLogin() {

        String email=LoginEmaiText.getText().toString().trim();
        String Password=LoginPasswordText.getText().toString();



        if(email.isEmpty())
        {
            LoginEmaiText.setError("Enter an email address");
            LoginEmaiText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            LoginEmaiText.setError("Enter a valid email address");
            LoginEmaiText.requestFocus();
            return;
        }
        if(Password.isEmpty())
        {
            LoginPasswordText.setError("Enter a password");
            LoginPasswordText.requestFocus();
            return;
        }
        if(Password.length()<8)
        {
            LoginPasswordText.setError("Enter Atleast 8 character");
            LoginPasswordText.requestFocus();
            return;

        }

        LoginProgress.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful())
                {
                    LoginProgress.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(),"hiii",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,NavigationDrawer.class);
                    startActivity(intent);
                    clearAll();


                }
                else{
                    LoginProgress.setVisibility(View.GONE);


                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Login Message")
                            .setMessage("Email and Password Are Incorrect")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            clearAll();

                                        }
                                    }).show();

                }

            }
        });


    }
    public void clearAll(){
        LoginEmaiText.setText("");
        LoginPasswordText.setText("");
    }

}
