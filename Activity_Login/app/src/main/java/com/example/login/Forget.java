package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class Forget extends AppCompatActivity {

    EditText Email;
    Button Sent;
    AwesomeValidation awesomeValidation;
    TextView mTextViewSignUp;
    TextView mTextViewSignIn;
    private DatabaseHelper databaseHelper;
    private NestedScrollView nestedScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        databaseHelper=new DatabaseHelper(this);
        Sent=(Button)findViewById(R.id.Sent);
        Email=(EditText)findViewById(R.id.Mail);

        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        mTextViewSignIn=(TextView)findViewById(R.id.view_SignIn);

        mTextViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgetIntent=new Intent(Forget.this,MainActivity.class);
                startActivity(ForgetIntent);
            }
        });

        mTextViewSignUp=(TextView)findViewById(R.id.view_SingUp);
        mTextViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Forget1Intent=new Intent(Forget.this,Register.class);
                startActivity(Forget1Intent);
            }
        });
        updateUI2();
    }
    private void updateUI2 (){



        Sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerifyFromSQLite();

            }

        });
    }
    private void VerifyFromSQLite() {
        awesomeValidation.addValidation(Forget.this,R.id.Mail,android.util.Patterns.EMAIL_ADDRESS,R.string.Emailll);

        if (awesomeValidation.validate()) {
            if (databaseHelper.chkusn(Email.getText().toString().trim())) {
                Intent accountsIntent = new Intent(this, ComfirmAcivity.class);
                accountsIntent.putExtra("Email", Email.getText().toString().trim());
                startActivity(accountsIntent);
                emptyinputEditText();

            } else {
                Snackbar.make(nestedScrollView, "Wrong Email", Snackbar.LENGTH_LONG).show();
            }
        }
    }


    private  void emptyinputEditText(){
        Email.setText("");
    }
}
