package com.example.mobile_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import static android.content.ContentValues.TAG;
import static androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM;
//import android.text.method.HideReturnsTransformationMethod;

public class registration_page extends AppCompatActivity implements View.OnClickListener
{
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
    ImageButton Show;
    Button Upload_Image;
    EditText regName,regEmailId,regPassword,regPhoneNo,regRollNumber,regFaculty;
    RadioButton regFemale,regMale;
    //ProgressBar regProgressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String userID;
    Uri selectedImage;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        Button Register_button = findViewById(R.id.RegisterButton);
        Register_button.setOnClickListener(v -> OpenActivity());
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#10496D"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
        imageView = findViewById(R.id.imageView);
        Upload_Image = findViewById(R.id.Upload_Image);
        imageView.setOnClickListener(this);
        Upload_Image.setOnClickListener(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.registration_title_layout);

        regName=findViewById(R.id.Name);
        regEmailId=findViewById(R.id.EmailAddress);
        regPhoneNo=findViewById(R.id.PhoneNumber);
        regPassword=findViewById(R.id.editPassword);
        regRollNumber=findViewById(R.id.Roll_Number);
        regFaculty = findViewById(R.id.Faculty);
        Show = findViewById(R.id.Show_Button);
        Show.setOnClickListener(v -> {

        });
        imageView= findViewById(R.id.imageView);
        regFemale=findViewById(R.id.Female);
        regMale=findViewById(R.id.Male);
        //regProgressBar=findViewById(R.id.progressBar);


        fAuth=FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),activity_home.class));
            finish();
        }

        //Save data in Firebase Database on clicking Register Button
        Register_button.setOnClickListener(vi->{

            //Get all the values
            String Name=regName.getText().toString().trim();
            String Password=regPassword.getText().toString().trim();
            String EmailId=regEmailId.getText().toString().trim();
            String PhoneNo = regPhoneNo.getText().toString().trim();
            String RollNo = regRollNumber.getText().toString().trim();
            String faculty = regFaculty.getText().toString().trim();

            //Validating the Email and Password
            if(TextUtils.isEmpty(EmailId))
            {
                regEmailId.setError("Email is required.");
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
            //regProgressBar.setVisibility(View.VISIBLE);

            //Registering the User

            fAuth.createUserWithEmailAndPassword(EmailId,Password)
                .addOnCompleteListener(task->{
            //Here we will check if the registration is successfull or not!
            if(task.isSuccessful())
            {
                FirebaseUser fuser = fAuth.getCurrentUser();
                fuser.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(registration_page.this, "Verification Email has been sent!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Log.d(TAG,"OnFailure: Email not Sent!"+e.getMessage()));
                userID =fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("Full_Name",Name);
                user.put("Email_Id",EmailId);
                user.put("Phone_Number",PhoneNo);
                user.put("Roll_Number",RollNo);
                user.put("Faculty",faculty);
                documentReference.set(user).addOnSuccessListener(aVoid -> Log.d(TAG,"onSuccess: User Profile is Created for"+userID));
                Toast.makeText(registration_page.this,"UserCreated!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),activity_home.class));
            }
            else
            {
                Toast.makeText(registration_page.this,"User not created,Error has occured!"+Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        });
    }

    private void OpenActivity()
    {
        Intent intent = new Intent(this, activity_home.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.Upload_Image)
        {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode ==RESULT_OK && data!=null)
        {
            selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            Upload_Image_To_Storage();
        }
    }
    private void Upload_Image_To_Storage()
    {
        //Naming the Image with Simple Date Formatter
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        //Helping to organizing images in a single folder
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
        storageReference.putFile(selectedImage)
                .addOnSuccessListener(taskSnapshot -> Toast.makeText(registration_page.this, "Image Uploaded!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(registration_page.this, "Failed To Upload!"+e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}