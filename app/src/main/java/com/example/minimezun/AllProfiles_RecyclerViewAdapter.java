package com.example.minimezun;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllProfiles_RecyclerViewAdapter extends RecyclerView.Adapter<AllProfiles_RecyclerViewAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<ProfileModel> profileList;

    StorageReference storageReference;

    public AllProfiles_RecyclerViewAdapter(Context context, ArrayList<ProfileModel> profileList, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.profileList = profileList;
        this.recyclerViewInterface = recyclerViewInterface;

    }


    @NonNull
    @Override
    public AllProfiles_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);


        return new AllProfiles_RecyclerViewAdapter.MyViewHolder(view , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProfiles_RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.viewName.setText(profileList.get(position).getName());
        holder.viewSurname.setText(profileList.get(position).getSurname());
        holder.viewYearOfGraduation.setText(profileList.get(position).getYearOfGraduation());
        holder.viewCompanyName.setText(profileList.get(position).getCompanyName());

        holder.viewBachelors.setText(profileList.get(position).getBachelors());
        holder.viewCity.setText(profileList.get(position).getCity());
        holder.viewEmail.setText(profileList.get(position).getEmail());
        holder.viewPhoneNumber.setText(profileList.get(position).getPhoneNumber());
        // image ekle
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+profileList.get(position).getId()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.profileImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Failed To Load Image.", Toast.LENGTH_SHORT).show();
                System.out.println("FOTO YUKLENMEDI");
            }
        });

        holder.viewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
                intent.setData(Uri.parse("mailto:" + holder.viewEmail.getText())); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
               context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {

        return profileList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImageView;
        TextView viewName, viewSurname, viewYearOfGraduation, viewCompanyName, viewBachelors,
        viewCity, viewEmail, viewPhoneNumber;


        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.imageView);
            viewName = itemView.findViewById(R.id.all_profiles_name);
            viewSurname = itemView.findViewById(R.id.all_profiles_surname);
            viewYearOfGraduation = itemView.findViewById(R.id.all_profiles_year_of_graduation);
            viewCompanyName = itemView.findViewById(R.id.all_profiles_company_name);

            viewBachelors = itemView.findViewById(R.id.all_profiles_bachelors);
            viewCity = itemView.findViewById(R.id.all_profiles_city);
            viewEmail = itemView.findViewById(R.id.all_profiles_email);
            viewPhoneNumber = itemView.findViewById(R.id.all_profiles_phone_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);

                        }
                    }
                }
            });


        }

    }


}
