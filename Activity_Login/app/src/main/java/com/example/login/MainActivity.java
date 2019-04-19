package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity {
    EditText Username, Password;
    Button Login;
    DatabaseHelper db;
    AwesomeValidation awesomeValidation;
    TextView mTextViewForget;
    TextView mTextViewRegister;
    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();

    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;

    private SignInButton signin;
    private Button signout;
    private int RC_SIGN_IN;
    GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";



    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay2.setVisibility(View.VISIBLE);
            rellay1.setVisibility(View.VISIBLE);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());
        loginButton = (LoginButton) findViewById(R.id.fb_login_bn);
       // textView = (TextView) findViewById(R.id.textView);


        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(MainActivity.this, "Successfuly Login ", Toast.LENGTH_LONG).show();
                Intent lo = new Intent(MainActivity.this, MapsActivity2.class);
                startActivity(lo);

            }

            @Override
            public void onCancel() {
                textView.setText("Login Cancelled");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        signin = (SignInButton) findViewById(R.id.sign_in_button);
        signout = (Button) findViewById(R.id.signout);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        db = new DatabaseHelper(this);
        Password = (EditText) findViewById(R.id.Password_u);
        Username = (EditText) findViewById(R.id.Username_u);
        Login = (Button) findViewById(R.id.View_me);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String usr = Username.getText().toString();
                String pw = Password.getText().toString();
                Boolean insert = db.checkUser(usr, pw);


                if (insert == true) {

                    Toast.makeText(MainActivity.this, "Successfuly Login ", Toast.LENGTH_LONG).show();
                    Intent logincreen = new Intent(MainActivity.this, MapsActivity2.class);
                    startActivity(logincreen);
                    emptyinput();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();
                }
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        mTextViewRegister = (TextView) findViewById(R.id.vie_Signup);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, Register.class);
                startActivity(registerIntent);
            }
        });

        mTextViewForget = (TextView) findViewById(R.id.vie_Forget);

        mTextViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetIntent = new Intent(MainActivity.this, Forget.class);
                startActivity(forgetIntent);
            }
        });

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
     if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignResult(task);
        }
    }
    private void handleSignResult(Task<GoogleSignInAccount> completeTask) {

        try {

            GoogleSignInAccount account = completeTask.getResult(ApiException.class);
            startActivity(new Intent(MainActivity.this, MapsActivity2.class));
        } catch (ApiException e) {

            Log.w(TAG, "Google sign in failed", e);
        }
    }

    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            startActivity(new Intent(MainActivity.this, MapsActivity2.class));

        }
        super.onStart();

    }

private void emptyinput(){
        Username.setText("");
        Password.setText("");
}
}
