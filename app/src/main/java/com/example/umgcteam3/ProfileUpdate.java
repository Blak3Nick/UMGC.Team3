package com.example.umgcteam3;


/*
This class is used to update the profile information
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileUpdate extends AppCompatActivity {
    //class variables for xml fields and Firebase User info
    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,mConfirmPassword, mPhone;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    public static String userID;
    AlertDialog.Builder dialogBuilder;

    //upon starting this class from another class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update_activity);

        //link class variables to fields in activity_main.xml file
        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mConfirmPassword   = findViewById(R.id.confirmPassword);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.registerBtn);

        //get firebase instance
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //create listener for the register button
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get all text from text fields and set them to strings
                System.out.println("Updating account...");;
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone    = mPhone.getText().toString();

                //check if all required fields are filled in
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(TextUtils.isEmpty(confirmPassword)){
                    mConfirmPassword.setError("Confirm Password Field is Required.");
                    return;
                }

                if(TextUtils.isEmpty(fullName)){
                    mFullName.setError("Name is required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                //confirm that the password and confirmpassword fields match
                if (!checkPassword(password, confirmPassword)){
                    mPassword.setError("Passwords must match.");
                    mConfirmPassword.setError("Passwords must match.");
                    return;
                }

                FirebaseUser fuser = fAuth.getCurrentUser();
                fuser.updatePassword(password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User password updated.");
                                }
                            }
                        });
                fuser.updateEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User email address updated.");
                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ProfileUpdate.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                        }
                                        //if register was unsuccessful, log the error
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                            dialogBuilder = new AlertDialog.Builder(ProfileUpdate.this);
                                            dialogBuilder.setMessage("Email already in use...")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            Toast.makeText(getApplicationContext(),"Please use another email or login",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                            AlertDialog alert = dialogBuilder.create();
                                            alert.setTitle("Email already used");
                                            alert.show();
                                            System.out.println("Error creating account");
                                        }
                                    });
                                }
                            }
                        });

                //display a popup to indicate that a user was updated
                Toast.makeText(ProfileUpdate.this, "User Updated.", Toast.LENGTH_SHORT).show();

                //get the userId of newly created user and update the user's details in a collection within Firebase
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("fName",fullName);
                user.put("email",email);
                user.put("phone",phone);

                //if storage was successful, log
                //if storage was unsuccessful, log
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user Profile is updated for "+ userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());

                    }
                });
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build();

                fuser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            private static final String TAG = "PROFILE";
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                    Toast.makeText(ProfileUpdate.this, "Updated Successfully.", Toast.LENGTH_LONG).show();
                                    returnToMain(getCurrentFocus());
                                }
                            }
                        });
            }
        });

    }

    private boolean checkPassword(String p, String c){
        if (p.equals(c)){
            return true;
        }
        return false;
    }
    public void returnToMain(View view) {
        Intent mainClass = new Intent(this, MainActivity.class);
        startActivity(mainClass);
    }

}
