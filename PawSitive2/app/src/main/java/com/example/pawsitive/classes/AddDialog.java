package com.example.pawsitive.classes;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.AddEditPet;
import com.example.pawsitive.acitvities.EditProfilePage;
import com.example.pawsitive.acitvities.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDialog extends DialogFragment {

    private Button addPet;
    private Button addJob;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);

        addJob = view.findViewById(R.id.addJob);
        addPet = view.findViewById(R.id.addPet);

        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPetIntent = new Intent(getContext(), AddEditPet.class);
                startActivity(addPetIntent);
                dismiss();
            }
        });

        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addJobIntent = new Intent(getContext(), EditProfilePage.class);
                startActivity(addJobIntent);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

}
