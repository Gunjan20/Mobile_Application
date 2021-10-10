package com.example.mobile_app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;
import static androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM;

public class profile_page extends AppCompatActivity implements View.OnClickListener{

    ImageButton left_icon_2, right_icon_2, right_icon_3, right_icon_4;
    TextView PName,PRollNo,PFaculty,PPhoneNo;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#10496D"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.profile_title_layout);
        PName = findViewById(R.id.name);
        PRollNo = findViewById(R.id.roll_number);
        PFaculty = findViewById(R.id.faculty);
        PPhoneNo = findViewById(R.id.PhoneNo);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        //Retrieving the data from the Database and assigning it to the variable
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, (documentSnapshot, error) -> {
            assert documentSnapshot != null;
            PName.setText(documentSnapshot.getString("Full_Name"));
            PRollNo.setText(documentSnapshot.getString("Roll_Number"));
            PFaculty.setText(documentSnapshot.getString("Faculty"));
            PPhoneNo.setText(documentSnapshot.getString("Phone_Number"));
        });

        left_icon_2 = (ImageButton) findViewById(R.id.left_icon_2);
        left_icon_2.setOnClickListener(this);
        right_icon_2 = (ImageButton) findViewById(R.id.right_icon_2);
        right_icon_2.setOnClickListener(this);
        right_icon_3 = (ImageButton) findViewById(R.id.right_icon_3);
        right_icon_3.setOnClickListener(this);
        right_icon_4 = (ImageButton) findViewById(R.id.right_icon_4);
        right_icon_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_icon_2) {
            Toast.makeText(profile_page.this, "You have clicked the back button", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, activity_home.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.right_icon_2)
        {
            Toast.makeText(profile_page.this, "You have clicked on Profile button", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, activity_home.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.right_icon_3)
        {
            Toast.makeText(profile_page.this, "You have clicked on Settings Button", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId()==R.id.right_icon_4)
        {
            Toast.makeText(profile_page.this, "You have clicked on Edit Button", Toast.LENGTH_SHORT).show();
        }
    }
}