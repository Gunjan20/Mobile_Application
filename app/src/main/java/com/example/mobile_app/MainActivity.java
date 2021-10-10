package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //EditText regName, regEmailId, regPassword,regPhoneNo;
        //Button RegisterButton;
        //FirebaseAuth fAuth;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Define Action Bar
        Button Login_button = (Button) findViewById(R.id.LoginButton);
        Login_button.setOnClickListener(v -> OpenActivityLogin());
        Button Registration_Button = (Button) findViewById(R.id.RegistrationButton);
        Registration_Button.setOnClickListener(v -> OpenActivityRegistration());

        /*regName = findViewById(R.id.Name);
        regEmailId = findViewById(R.id.EmailAddress);
        regPhoneNo = findViewById(R.id.PhoneNumber);
        regPassword = findViewById(R.id.editPassword);
        RegisterButton = findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(v -> {

        });*/
        //Toast.makeText(MainActivity.this, "Firebase connection is sucess", Toast.LENGTH_SHORT).show();
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    private void OpenActivityRegistration()
    {
        Intent intent = new Intent(this, registration_page.class);
        startActivity(intent);
    }

    public void OpenActivityLogin()
    {
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
           R.id.miSettings -> Toast.makeText(this, "You have clicked on Settings Option", Toast.LENGTH_SHORT).show();
           R.id.miHome ->Toast.makeText(this, "You have clicked on Home Option", Toast.LENGTH_SHORT).show();
           R.id.miEdit ->Toast.makeText(this, "You have clicked on Edit Option", Toast.LENGTH_SHORT).show();
     }
        return true;*/
}
