package com.example.minimezun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfilePage extends AppCompatActivity {

    TextView viewName, viewSurname, viewYearOfEnrollment, viewYearOfGraduation, viewEmail,
            viewBachelors, viewMasters, viewDoctorate, viewCompanysCountry, viewCompanysCity,
            viewCompanyName, viewSocialMediaLink1, viewSocialMediaLink2, viewPhoneNumber;

    FirebaseAuth mAuth;

    FirebaseFirestore firebaseFirestore;

    String userID;

//    Button editProfile, editProfileImage;
    Button editProfile;
    ImageView profileImage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        profileImage = findViewById(R.id.profile_image);
        // gecici olarak durucak EMULATORE ORNEK FOTOGRAF KOYMAYI UNUTMA
//        editProfileImage = findViewById(R.id.edit_profile_image);
        editProfile = findViewById(R.id.edit_profile_details);

        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed To Load Image.", Toast.LENGTH_SHORT).show();
            }
        });

        viewName = findViewById(R.id.name);
        viewSurname = findViewById(R.id.surname);
        viewYearOfEnrollment = findViewById(R.id.year_of_enrollment);
        viewYearOfGraduation = findViewById(R.id.year_of_graduation);
        viewEmail = findViewById(R.id.email);
        viewBachelors = findViewById(R.id.bachelors);
        viewMasters = findViewById(R.id.masters);
        viewDoctorate = findViewById(R.id.doctorate);
        viewCompanysCountry = findViewById(R.id.country);
        viewCompanysCity= findViewById(R.id.city);
        viewCompanyName = findViewById(R.id.company);
        viewSocialMediaLink1 = findViewById(R.id.social_media_link1);
        viewSocialMediaLink2 = findViewById(R.id.social_media_link2);
        viewPhoneNumber = findViewById(R.id.phone_number);



        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                viewName.setText(value.getString("name"));
                viewSurname.setText(value.getString("surname"));
                viewYearOfEnrollment.setText(value.getString("year_of_enrollment"));
                viewYearOfGraduation.setText(value.getString("year_of_graduation"));
                viewEmail.setText(value.getString("email"));
                viewBachelors.setText(value.getString("bachelors"));
                viewMasters.setText(value.getString("masters"));
                viewDoctorate.setText(value.getString("doctorate"));
                viewCompanysCountry.setText(value.getString("country"));
                viewCompanysCity.setText(value.getString("city"));
                viewCompanyName.setText(value.getString("company"));
                viewSocialMediaLink1.setText(value.getString("social_media_link1"));
                viewSocialMediaLink2.setText(value.getString("social_media_link2"));
                viewPhoneNumber.setText(value.getString("phone_number"));
            }
        });

//        editProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryIntent,1000);
//            }
//        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditProfile.class);
                startActivity(intent);
            }
        });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1000){
//            if(resultCode == Activity.RESULT_OK){
//                Uri imageUri = data.getData();
//
//                //profileImage.setImageURI(imageUri);
//
//                uploadImageToFirebase(imageUri);
//
//
//            }
//        }
//
//    }
//
//    private void uploadImageToFirebase(Uri imageUri) {
//        // uplaod image to firebase storage
//        final StorageReference fileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
//        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri).into(profileImage);
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}