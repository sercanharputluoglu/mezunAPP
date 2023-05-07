package com.example.minimezun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextInputEditText editTextName,editTextSurname, editTextYearOfEnrollment,
            editTextYearOfGraduation, editTextEmail, editTextPassword;
    Button buttonRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    ProgressBar progressBar;
    TextView textView;
    String userID;

//    Button buttonOpenCamera, buttonOpenGallery;
//
//    public static final int ALL_PERMS_CODE = 104;
//    public static final int CAMERA_PERM_CODE = 101;
//    public static final int CAMERA_REQUEST_CODE = 102;
//
//    StorageReference storageReference;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.name);
        editTextSurname = findViewById(R.id.surname);
        editTextYearOfEnrollment = findViewById(R.id.year_of_enrollment);
        editTextYearOfGraduation = findViewById(R.id.year_of_graduation);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        buttonRegister = findViewById(R.id.button_register);
        progressBar = findViewById(R.id.progressBar);

        textView = findViewById(R.id.loginNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name, surname, yearOfEnrollment, yearOfGraduation, email, password;

                name = String.valueOf(editTextName.getText());
                surname = String.valueOf(editTextSurname.getText());
                yearOfEnrollment = String.valueOf(editTextYearOfEnrollment.getText());
                yearOfGraduation = String.valueOf(editTextYearOfGraduation.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                firebaseFirestore = FirebaseFirestore.getInstance();

                if(TextUtils.isEmpty(name)){

                    editTextName.setError("Name is required");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if(TextUtils.isEmpty(surname)){

                    editTextSurname.setError("Surname is required");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if(TextUtils.isEmpty(yearOfEnrollment)){

                    editTextYearOfEnrollment.setError("Year of enrollment is required");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if(TextUtils.isEmpty(yearOfGraduation)){

                    editTextYearOfGraduation.setError("Year of graduation is required");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    //Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email is required");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    //Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Password is required");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if(password.length() < 6 ){
                    progressBar.setVisibility(View.GONE);
                    editTextPassword.setError("Password must be >= 6");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Account created",
                                        Toast.LENGTH_SHORT).show();

                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", name);
                                user.put("surname", surname);
                                user.put("year_of_enrollment", yearOfEnrollment);
                                user.put("year_of_graduation", yearOfGraduation);
                                user.put("email", email);
                                user.put("password", password);
                                user.put("bachelors", "");
                                user.put("masters", "");
                                user.put("doctorate", "");
                                user.put("country", "");
                                user.put("city", "");
                                user.put("company", "");
                                user.put("social_media_link1", "");
                                user.put("social_media_link2", "");
                                user.put("phone_number", "");

                                documentReference.set(user);

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

    }
}