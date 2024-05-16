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

        petImage = itemView.findViewById(R.id.petImageView);

        petName = itemView.findViewById(R.id.nameText);
        petType = itemView.findViewById(R.id.typeText);
        petAge = itemView.findViewById(R.id.ageText);
        petWeight = itemView.findViewById(R.id.weightText);
        petFriendly = itemView.findViewById(R.id.friendlyText);
        petNeutered = itemView.findViewById(R.id.neuteredText);
        petMicrochipped = itemView.findViewById(R.id.microchippeText);
        petFood = itemView.findViewById(R.id.foodText);
        petWalks = itemView.findViewById(R.id.walksText);
        trainedText = itemView.findViewById(R.id.trainedText);

        editButton = itemView.findViewById(R.id.editButton);

    }
}
