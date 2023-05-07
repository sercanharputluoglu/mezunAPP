package com.example.minimezun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

public class ViewProfileFromAllProfiles extends AppCompatActivity {

    TextView viewName, viewSurname, viewYearOfEnrollment, viewYearOfGraduation, viewEmail,
            viewBachelors, viewMasters, viewDoctorate, viewCompanysCountry, viewCompanysCity,
            viewCompanyName, viewSocialMediaLink1, viewSocialMediaLink2, viewPhoneNumber;
    FirebaseFirestore firebaseFirestore;

    String userID;

    ImageView profileImage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_from_all_profiles);

        userID = getIntent().getStringExtra("id");

        firebaseFirestore = FirebaseFirestore.getInstance();

        profileImage = findViewById(R.id.profile_image_from_all_profiles);


        viewName = findViewById(R.id.name_from_all_profiles);
        viewSurname = findViewById(R.id.surname_from_all_profiles);
        viewYearOfEnrollment = findViewById(R.id.year_of_enrollment_from_all_profiles);
        viewYearOfGraduation = findViewById(R.id.year_of_graduation_from_all_profiles);
        viewEmail = findViewById(R.id.email_from_all_profiles);
        viewBachelors = findViewById(R.id.bachelors_from_all_profiles);
        viewMasters = findViewById(R.id.masters_from_all_profiles);
        viewDoctorate = findViewById(R.id.doctorate_from_all_profiles);
        viewCompanysCountry = findViewById(R.id.country_from_all_profiles);
        viewCompanysCity= findViewById(R.id.city_from_all_profiles);
        viewCompanyName = findViewById(R.id.company_from_all_profiles);
        viewSocialMediaLink1 = findViewById(R.id.social_media_link1_from_all_profiles);
        viewSocialMediaLink2 = findViewById(R.id.social_media_link2_from_all_profiles);
        viewPhoneNumber = findViewById(R.id.phone_number_from_all_profiles);


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



        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+userID+"/profile.jpg");
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

    }




}