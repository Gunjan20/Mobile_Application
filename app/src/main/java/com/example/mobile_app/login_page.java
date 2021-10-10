package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class login_page extends AppCompatActivity implements View.OnClickListener
{
    //public static final int GOOGLE_SIGN_IN_CODE=1005;
    EditText regEmail,regPassword;
    TextView L_ForgetPassword;
    Button Login;
    FirebaseAuth fAuth;
    SignInButton SignInWithGoogle;
    //GoogleSignInOptions gso;
    //GoogleSignInClient signInClient;
    //FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            regEmail = findViewById(R.id.Email_Id);
            regPassword = findViewById(R.id.Password);
            Login = findViewById(R.id.login);
            SignInWithGoogle = findViewById(R.id.Sign_in_google);

            fAuth = FirebaseAuth.getInstance();
            if (fAuth.getCurrentUser() != null) {
                startActivity(new Intent(getApplicationContext(), activity_home.class));
                finish();
            }
            Login.setOnClickListener(this);

            L_ForgetPassword = findViewById(R.id.ForgetPassword);
            L_ForgetPassword.setOnClickListener(v ->
            {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder PasswordResetDialog = new AlertDialog.Builder(v.getContext());
                PasswordResetDialog.setTitle("Reset Password?");
                PasswordResetDialog.setMessage("Enter Your Email to Receive Reset Link.");
                PasswordResetDialog.setView(resetMail);
                PasswordResetDialog.setPositiveButton("Yes", (dialog, which) ->
                {
                    // extract the email and send the reset link
                    String mail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail)
                            .addOnSuccessListener(aVoid -> Toast.makeText(login_page.this, "Reset Link Sent To Your Email!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(login_page.this, "Error! Reset Link Is Not Sent!"+e.getMessage(), Toast.LENGTH_SHORT).show());
                });
                PasswordResetDialog.setNegativeButton("No", (dialog, which) ->
                {
                    //Close the Dialoge
                });
                PasswordResetDialog.create().show();
            });
        }

        /*firebaseAuth =  FirebaseAuth.getInstance();
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1069975414383-ttuf1u77eg184n3j33aht0h3jc91e0me.apps.googleusercontent.com")
                .requestEmail()
                .build();

        signInClient=GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount signInAccount=GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount!=null){
            Toast.makeText(login_page.this,"User is already LoggedIn",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,activity_home.class));
        }
        SignInWithGoogle.setOnClickListener(v->
        {
            Intent sign=signInClient.getSignInIntent();
            startActivityForResult(sign,GOOGLE_SIGN_IN_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==GOOGLE_SIGN_IN_CODE)
        {
            Task<GoogleSignInAccount>signInTask=GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                GoogleSignInAccount SignInAcc=signInTask.getResult(ApiException.class);
                AuthCredential authCredential= GoogleAuthProvider.getCredential(SignInAcc.getIdToken(),null);
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                    Toast.makeText(getApplicationContext(),"Your Google Account is Connected to Our Application.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),activity_home.class));
                });
            }
            catch(ApiException e)
            {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void onClick(View v)
    {
            String Password=regPassword.getText().toString().trim();
            String Email=regEmail.getText().toString().trim();

            //Validating the Email and Password
            if(TextUtils.isEmpty(Email))
            {
                regEmail.setError("Email is required.");
                return;
            }
            if(TextUtils.isEmpty(Password))
            {
                regPassword.setError("Password is required.");
                return;
            }
            //Fixing the length of the password
            if(Password.length()<8)
            {
                regPassword.setError("Password must have 8 or more than 8 characters");
                return;
            }

            //Authenticate the User
            fAuth.signInWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(login_page.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),activity_home.class));
                        }
                        else
                        {
                            Toast.makeText(login_page.this, "Login Failed!, Error!"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
}

