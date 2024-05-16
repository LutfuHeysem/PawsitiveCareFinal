package com.example.pawsitive.classes;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.pawsitive.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AcceptOfferDialog extends DialogFragment {

    private TextView offerAmount;
    private TextView startDate;
    private TextView endDate;
    private TextView textOfferText;
    private Button acceptOffer;
    private Button denyOffer;
    private String receiverUserEmail;
    private String amount;
    private String startDateString;
    private String endDateString;
    private double ownerBalance, takerBalance;
    private FirebaseFirestore db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_accept_deny_offer, null);

        offerAmount = view.findViewById(R.id.offerAmount);
        startDate = view.findViewById(R.id.starDate);
        endDate = view.findViewById(R.id.endDate);
        acceptOffer = view.findViewById(R.id.btnAccept);
        denyOffer = view.findViewById(R.id.btnDeny);
        textOfferText = view.findViewById(R.id.textOffer);

        offerAmount.setText(String.format("%s%s$", getString(R.string.offer_amount), amount));
        startDate.setText(String.format("%s %s", "Start Date", startDateString));
        endDate.setText(String.format("%s %s", getString(R.string.end_date_view), endDateString));

        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(receiverUserEmail).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ownerBalance = documentSnapshot.getDouble("Balance");
                    }
                });
        acceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ownerBalance < Double.parseDouble(amount))
                {
                    acceptOffer.setVisibility(View.INVISIBLE);
                    startDate.setVisibility(View.INVISIBLE);
                    endDate.setVisibility(View.INVISIBLE);
                    textOfferText.setText(R.string.your_balance_is_not_sufficient);
                }
                else{
                    addOfferToDb(amount);
                    deleteDocument(receiverUserEmail);
                    dismiss();
                }
            }
        });

        denyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    deleteDocument(receiverUserEmail);
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
            userData.put("amount", amount);
            userData.put("startDate", startDateString);
            userData.put("endDate", endDateString);
            db.collection("Users").document(User.getEmail())
                    .collection("AcceptedOffers").document(receiverUserEmail).set(userData);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDocument(String userEmail) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference documentRef = db.collection("Users")
                    .document(User.getEmail())
                    .collection("Offers")
                    .document(userEmail);

            documentRef.delete()
                    .addOnSuccessListener(aVoid -> System.out.println("Document deleted successfully"))
                    .addOnFailureListener(e -> System.out.println("Error deleting document: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setReceiverUserEmail(String receiverUserEmail) {
        this.receiverUserEmail = receiverUserEmail;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public void setStartDate(String startDate) {
        this.startDateString = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDateString = endDate;
    }
}
