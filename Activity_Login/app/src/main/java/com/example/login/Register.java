package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;


public class Register extends AppCompatActivity {

        DatabaseHelper db;
        EditText mTextFirstname,mTextUsername,mTextPassword,mTextComfirmPassword,mTextEmail;
        Button Register;
        AwesomeValidation awesomeValidation;
        TextView mTextViewLogin;
        TextView mTextViewForget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db=new DatabaseHelper(this);

            awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);
            mTextViewLogin=(TextView)findViewById(R.id.view_Singin);
            mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent LoginIntent=new Intent(Register.this,MainActivity.class);
                startActivity(LoginIntent);
            }
        });
            mTextViewForget=(TextView)findViewById(R.id.view_Forget);
            mTextViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetIntent=new Intent(Register.this,Forget.class);
                startActivity(forgetIntent);
            }
        });
            updateU();
        }
        private void updateU(){

            mTextFirstname=(EditText)findViewById(R.id.Firsname);
            mTextUsername=(EditText)findViewById(R.id.Username1);
            mTextPassword=(EditText)findViewById(R.id.Password1);
            mTextEmail=(EditText)findViewById(R.id.Email);
            mTextComfirmPassword=(EditText)findViewById(R.id.ComfirmPassword);


            String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

            awesomeValidation.addValidation(Register.this,R.id.Firsname,"[a-zA-Z\\s]+",R.string.Usernameee);
            awesomeValidation.addValidation(Register.this,R.id.Password1,regexPassword ,R.string.Passworddd);
            awesomeValidation.addValidation(Register.this,R.id.ComfirmPassword,R.id.Password1,R.string.ComfirmPassworddd);
            awesomeValidation.addValidation(Register.this,R.id.Email,android.util.Patterns.EMAIL_ADDRESS,R.string.Emailll);

            Register=(Button)findViewById(R.id.review_login);
            Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate()){



                    String strFirstName=mTextFirstname.getText().toString();
                    String strSurname = mTextUsername.getText().toString();
                    String strEmail = mTextEmail.getText().toString();
                    String strPassword=mTextPassword.getText().toString();

                    Boolean chkusn=db.chkusn(strSurname);

                    if(chkusn==true) {
                        Boolean insert = db.insert(strFirstName, strSurname, strEmail, strPassword);

                        if (insert == true) {

                            Toast.makeText(getApplicationContext(), "Successfully Saved!", Toast.LENGTH_SHORT).show();

                            mTextFirstname.setText("");
                            mTextUsername.setText("");
                            mTextEmail.setText("");
                            mTextPassword.setText("");
                            mTextComfirmPassword.setText("");

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                    }    }
                    else{

                    Toast.makeText(getApplicationContext(),"Wrong Username or Password",Toast.LENGTH_SHORT);
                }
            }

        });
                }
}
