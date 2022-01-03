package com.example.umgcteam3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023 ;
    TextView fullName,email,phone, changeProfile, deactivateAccount;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    public String userId;
    Button resendCode, resetPassLocal, changProfileImage;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;
    static long abs, upper, lower;
    static long[] numbers = {0,0,0};
    static BackgroundStatisticsWorker backgroundStatisticsWorker = LoginActivity.backgroundStatisticsWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        phone = findViewById(R.id.profilePhone);
        fullName = findViewById(R.id.fullName);
//        email    = findViewById(R.id.profileEmail);
        resetPassLocal = findViewById(R.id.resetPasswordLocal);
//
        profileImage = findViewById(R.id.profile_picture);
        changeProfile = findViewById(R.id.changeProfile);
        changProfileImage = findViewById(R.id.changeImageButton);
        deactivateAccount = findViewById(R.id.deactivate_account);
        changProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        try {
            storageReference = FirebaseStorage.getInstance().getReference();
        } catch (Exception e) {
            System.out.println("No storage for this user.");
        }

        try {
            StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profileImage);
                }
            });
        }catch (Exception e){
            Log.d("TrytoGetProfilePic: ", "\n\n\nCould not get Profile pic\n\n\n");
        }


        try{
            userId = fAuth.getCurrentUser().getUid();
            user = fAuth.getCurrentUser();
            populateHistory();
            checkUserInfo();
        } catch (Exception e){
            Log.d("TrytoGetUser: ", e.getMessage());
            finish();
        }

        try {
            fullName.setText(user.getDisplayName());
            System.out.println(user.getDisplayName());
        } catch (Exception e) {
            Log.d("TrytoDisplayName: ", e.getMessage());
            finish();
        }

        deactivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPassword = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Delete Account?");
                passwordResetDialog.setMessage("This cannot be undone.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Delete Account?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("DELETED", "User account deleted.");
                                            returnToAccountCreation(getCurrentFocus());
                                        }
                                    }
                                });
                    }
                });

                passwordResetDialog.setNegativeButton("Don't Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });
                passwordResetDialog.create().show();
            }
        });


        resetPassLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPassword = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });
                passwordResetDialog.create().show();
            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfileInfo(v);
            }
        });
    }

    public void buildInitialWorkouts(){
        System.out.println("Building initial workouts...\n\n\n\n");
        Intent get_maxes = new Intent(this, GetMaxesWorker.class);
        get_maxes.putExtra("counter", 0);
        get_maxes.putExtra("userid", userId);
        startActivity(get_maxes);
    }

    private void updateDisplayName() {
        try{
            DocumentReference documentReference = fStore.collection("users").document(userId);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        String fullName = documentSnapshot.getString("fName");
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullName)
                                .build();
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    private static final String TAG = "PROFILE";
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User profile updated.");
                                        }
                                    }
                                });
                    } else {
                        Log.d("tag", "onEvent: Document do not exists");
                    }
                }
            });
        } catch (Exception e) {
            Log.d("MainActivityProfilePic", e.getMessage());
        }
    }

    public void checkUserInfo() {
        if(user.getDisplayName()== null){
            updateDisplayName();
        }
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    try {
                        boolean workoutsBuilt = documentSnapshot.getBoolean("workoutsBuilt");
                        if(!workoutsBuilt){
                            buildInitialWorkouts();
                            System.out.println("Building initial workouts");
                        }
                        else{
                            System.out.println("The user has workouts");
                        }
                    } catch (NullPointerException exception){
                        buildInitialWorkouts();
                    }

                } else {
                    Log.d("tag", "onEvent: Document does not exist");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void populateHistory() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String user_id = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("users").document(user_id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    System.out.println("Updating textview");
                    try {
                        abs = (long) documentSnapshot.get("AbdominalWorkoutTotal");
                        upper = (long) documentSnapshot.get("UpperBodyWorkoutTotal");
                        lower = (long) documentSnapshot.get("LowerBodyWorkoutTotal");
                        System.out.println(upper + " is number of upper completed");
                        numbers[0] =abs;
                        numbers[1] = upper;
                        numbers[2] = lower;

                    } catch (Exception exception){
                        System.out.println("Caught exception"  + exception.getMessage());
                    }

                } else {
                    Log.d("tag", "onEvent: Document does not exist");
                }
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void proceedToWorkout(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

    }

    public void goToStatistics(View view){
        startActivity(new Intent(getApplicationContext(),StatisticsActivity.class));
    }

    public void goToHistory(View view){
        Intent history = new Intent(this, HistoryActivity.class);
        startActivity(history);
    }
    public void returnToDashboard(View view) {
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }
    public void changeProfileInfo(View view) {
        Intent profile = new Intent(this, ProfileUpdate.class);
        startActivity(profile);
    }
    public void returnToAccountCreation(View view) {
        Intent create = new Intent(this, RegisterActivity.class);
        startActivity(create);
    }

}