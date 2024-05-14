package com.example.pawsitive.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.AddEditPet;
import com.example.pawsitive.acitvities.ViewHolderForMyPets;
import com.example.pawsitive.classes.MyPetModel;
import com.example.pawsitive.acitvities.MyPets;
import com.example.pawsitive.listeners.PetsListener;

import java.util.ArrayList;
import java.util.List;

public class MyPetsAdapter extends RecyclerView.Adapter<ViewHolderForMyPets> {
    private Context context;
    private final List<MyPetModel> petsList;
    private final PetsListener petsListener;

    public MyPetsAdapter(Context context, ArrayList<MyPetModel> petsList, PetsListener petsListener) {
        this.context = context;
        this.petsList = petsList;
        this.petsListener = petsListener;
        System.out.println("MyPetAdapter " + petsList.size());
    }

    @NonNull
    @Override
    public ViewHolderForMyPets onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderForMyPets(LayoutInflater.from(context).inflate(R.layout.singular_items_pet_recycle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForMyPets holder, int position) {
        System.out.println("sa1");
        String imageStr = petsList.get(position).getImageStr();
        byte[] decodedString = Base64.decode(imageStr, Base64.DEFAULT);
        holder.petImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        System.out.println("sa2");
        holder.petName.setText(petsList.get(position).getName());
        System.out.println("sa3");
        holder.petAge.setText(String.valueOf(petsList.get(position).getAge()));
        System.out.println("sa4");
        holder.petWeight.setText(String.valueOf(petsList.get(position).getWeight()));
        System.out.println("sa5");
        holder.petType.setText(petsList.get(position).getType());
        System.out.println("sa6");
        holder.petFriendly.setText(petsList.get(position).getFriendly());
        System.out.println("sa7");
        holder.petNeutered.setText(petsList.get(position).getNeutered());
        System.out.println("sa8");
        holder.petMicrochipped.setText(petsList.get(position).getMicrochipped());
        System.out.println("sa9");
        holder.petFood.setText(petsList.get(position).getFood());
        System.out.println("sa10");
        holder.petWalks.setText(petsList.get(position).getWalks());
        holder.trainedText.setText(petsList.get(position).getTrained());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petsListener.onPetEditClicked(holder.petName.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return petsList.size();
    }
}
