package com.example.mobile_app;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AUIDUsers
{
    private final DatabaseReference databaseReference;
    public AUIDUsers()
    {
        FirebaseDatabase fbd = FirebaseDatabase.getInstance();
        databaseReference = fbd.getReference(Users.class.getSimpleName());
    }
    public Task<Void> add(Users user)
    {
        return databaseReference.push().setValue(user);
        //This push method will generate a unique Key in the node and it is generated from timestamp, string value.
        //Also when we design a class like this, its easy to maintain and validating before we can access or send the data to our server.
    }
}
