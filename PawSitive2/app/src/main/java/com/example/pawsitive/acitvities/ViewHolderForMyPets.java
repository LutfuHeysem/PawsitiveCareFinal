package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;

public class ViewHolderForMyPets extends RecyclerView.ViewHolder {
    public ImageView petImage;
    public TextView petName, petAge, petWeight, petType, petFriendly, petNeutered, petMicrochipped, petFood, petWalks, trainedText;
    public Button editButton;
    public ViewHolderForMyPets(@NonNull View itemView) {
        super(itemView);

        System.out.println("hell1");
        petImage = itemView.findViewById(R.id.petImageView);

        System.out.println("hell2");
        petName = itemView.findViewById(R.id.nameText);
        System.out.println("hell3");
        petType = itemView.findViewById(R.id.typeText);
        System.out.println("hell4");
        petAge = itemView.findViewById(R.id.ageText);
        System.out.println("hell5");
        petWeight = itemView.findViewById(R.id.weightText);
        System.out.println("hell6");
        petFriendly = itemView.findViewById(R.id.friendlyText);
        System.out.println("hell7");
        petNeutered = itemView.findViewById(R.id.neuteredText);
        System.out.println("hell8");
        petMicrochipped = itemView.findViewById(R.id.microchippeText);
        System.out.println("hell9");
        petFood = itemView.findViewById(R.id.foodText);
        System.out.println("hell10");
        petWalks = itemView.findViewById(R.id.walksText);
        trainedText = itemView.findViewById(R.id.trainedText);

        editButton = itemView.findViewById(R.id.editButton);

    }
}
