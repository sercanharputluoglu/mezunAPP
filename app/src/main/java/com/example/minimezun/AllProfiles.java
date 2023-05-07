package com.example.minimezun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AllProfiles extends AppCompatActivity implements RecyclerViewInterface{

    ArrayList<ProfileModel> profileList = new ArrayList<>();
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_profiles);
        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.allProfilesRecyclerView);

        setUpProfileList(recyclerView);


    }

    private void setUpProfileList(RecyclerView recyclerView){



        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getString("name") +
                                        document.getString("surname") +
                                        document.getString("year_of_graduation")+
                                        document.getString("company"));

                                System.out.println(document.getId());



                                profileList.add(new ProfileModel(
                                        document.getString("name"),
                                        document.getString("surname"),
                                        document.getString("year_of_graduation"),
                                        document.getString("company"),
                                        document.getString("bachelors"),
                                        document.getString("city"),
                                        document.getString("email"),
                                        document.getString("phone_number"),
                                        document.getId()));

                                System.out.println(profileList.get(0).toString());

                            }

                            AllProfiles_RecyclerViewAdapter adapter = new AllProfiles_RecyclerViewAdapter(AllProfiles.this, profileList, AllProfiles.this);

                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(AllProfiles.this));

                        } else {
                            System.out.println("data eklenmedi");
                        }
                    }
                });
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getApplicationContext(), ViewProfileFromAllProfiles.class);
        intent.putExtra("id", profileList.get(position).id);
        startActivity(intent);



    }
}
