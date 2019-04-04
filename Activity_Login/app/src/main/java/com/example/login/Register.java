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
        EditText Firstname,Username,Password,ComfirmPassword,Email;
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
        Firstname=(EditText)findViewById(R.id.Firsname);
        Username=(EditText)findViewById(R.id.Username1);
        Password=(EditText)findViewById(R.id.Password1);
        Email=(EditText)findViewById(R.id.Email);
        ComfirmPassword=(EditText)findViewById(R.id.ComfirmPassword);
        Register=(Button)findViewById(R.id.review_login);



        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

        awesomeValidation.addValidation(Register.this,R.id.Firsname,"[a-zA-Z\\s]+",R.string.Usernamee);
        awesomeValidation.addValidation(Register.this,R.id.Password1,regexPassword ,R.string.Passwordd);
        awesomeValidation.addValidation(Register.this,R.id.ComfirmPassword,R.id.Password1,R.string.ComfirmPasswordd);
        awesomeValidation.addValidation(Register.this,R.id.Email,android.util.Patterns.EMAIL_ADDRESS,R.string.Emaill);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String strFirstName=Firstname.getText().toString();
                    String strSurname = Username.getText().toString();
                    String strEmail = Email.getText().toString();
                    String strPassword=Password.getText().toString();
                Boolean chkusn=db.chkusn(strSurname);
                if(chkusn==true) {
                    Boolean insert = db.insert(strFirstName, strSurname, strEmail, strPassword);
                    if (insert == true) {

                        Toast.makeText(getApplicationContext(), "Successfully Saved!", Toast.LENGTH_SHORT).show();

                        Firstname.setText("");
                        Username.setText("");
                        Email.setText("");
                        Password.setText("");
                        ComfirmPassword.setText("");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong Username or Password",Toast.LENGTH_SHORT);
                }






            }

        });
    }
}
