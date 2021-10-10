package com.example.mobile_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import java.util.Objects;
import static android.content.ContentValues.TAG;
import static androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM;

public class activity_home extends AppCompatActivity implements View.OnClickListener
{
    public static final int CAMERA_REQUEST=9999;
    ImageButton left_icon, right_icon;
    ImageView imageView3;
    TextView Email_not_verified;
    Button Logout,captureImageButton,verify;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#10496D"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.home_title_layout);
        left_icon = findViewById(R.id.left_icon);
        left_icon.setOnClickListener(this);
        right_icon = findViewById(R.id.right_icon);
        right_icon.setOnClickListener(this);
        imageView3 = findViewById(R.id.imageView3);

        Email_not_verified = findViewById(R.id.Email_Not_Verified);
        verify = findViewById(R.id.Verify);
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        if(!user.isEmailVerified())
        {
            Email_not_verified.setVisibility(View.VISIBLE);
            verify.setVisibility(View.VISIBLE);
            verify.setOnClickListener(v -> user.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(activity_home.this, "Verification Email has been sent!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Log.d(TAG,"OnFailure: Email not Sent!"+e.getMessage())));
        }
        captureImageButton = findViewById(R.id.captureImageButton);
        captureImageButton.setOnClickListener(v -> {
            if(v.getId()==R.id.captureImageButton)
            {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,CAMERA_REQUEST);
                Toast.makeText(activity_home.this, "You have clicked on Capture Image Button", Toast.LENGTH_SHORT).show();
            }
        });
        Logout = findViewById(R.id.Logout);
        Logout.setOnClickListener(this);
    }


    FaceDetectorOptions highAccuracyOpts = new FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                    .build();
/*    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode()== Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        assert data != null;
                        Bitmap bitmap= (Bitmap)data.getExtras().get("data");
                        imageView3.setImageBitmap(bitmap);
                    }
                }
            });
    public void captureImageButton(View view)
    {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launchSomeActivity.launch(i);
        Toast.makeText(activity_home.this, "You have clicked on Capture Image Button", Toast.LENGTH_SHORT).show();
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST)
        {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            imageView3.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.left_icon)
        {
            Toast.makeText(activity_home.this, "You have clicked the back button", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.right_icon)
            {
                Toast.makeText(activity_home.this, "You have clicked on Profile button", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, profile_page.class);
                startActivity(intent);
        }
        else if(v.getId()==R.id.Logout)
        {
            Toast.makeText(activity_home.this, "You have clicked on Logout Button", Toast.LENGTH_SHORT).show();
            Logout.setOnClickListener(v1 -> {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            });
        }
    }
}