package com.example.pawsitive.classes;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.pawsitive.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MakeOfferDialog extends DialogFragment {

    private EditText etOfferAmount;
    private Button btnSendOffer;
    private String receiverUserEmail;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_make_offer, null);

        etOfferAmount = view.findViewById(R.id.etOfferAmount);
        btnSendOffer = view.findViewById(R.id.btnSendOffer);

        btnSendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String offerAmount = etOfferAmount.getText().toString().trim();
                addOfferToDb(offerAmount);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
    public void addOfferToDb(String amount){
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String, String> userData = new HashMap<>();
            userData.put(User.getEmail(), amount);

            db.collection("Users").document(receiverUserEmail)
                    .collection("Offers").document(User.getEmail()).set(userData);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setReceiverUser(String receiverUser) {
        this.receiverUserEmail = receiverUser;
    }

}
