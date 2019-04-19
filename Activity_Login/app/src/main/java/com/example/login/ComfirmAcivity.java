package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class ComfirmAcivity extends AppCompatActivity {


    EditText mTextPassword1,mTextComfirmPassword1;
    AwesomeValidation awesomeValidation;
    Button Reset;
    private NestedScrollView nestedScrollView;
    DatabaseHelper databaseHelper;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_acivity);


        databaseHelper =new DatabaseHelper(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        mTextPassword1 = (EditText) findViewById(R.id.Password2);
        mTextComfirmPassword1 = (EditText) findViewById(R.id.ComfirmPassword2);
        Reset = (Button) findViewById(R.id.Reset);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(ComfirmAcivity.this,R.id.Password2,regexPassword ,R.string.Passworddd);
        awesomeValidation.addValidation(ComfirmAcivity.this,R.id.ComfirmPassword2,R.id.Password2,R.string.ComfirmPassworddd);


        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()) {
                    updatepassword();
                }
            }
        });
    }

    private void updatepassword() {


        String Password=mTextPassword1.getText().toString().trim();
        if(databaseHelper.chkusn(email)){
            Snackbar.make(nestedScrollView,"email doesn't exist",Snackbar.LENGTH_LONG).show();
            return;

        }else{
            databaseHelper.updatePassword(email,Password);
            Toast.makeText(this,"password reset successfully",Toast.LENGTH_SHORT).show();
            emptyinput();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void emptyinput() {
        mTextComfirmPassword1.setText("");
        mTextPassword1.setText("");
    }
}
