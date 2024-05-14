package com.example.pawsitive.classes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.Calendar;
import java.util.HashMap;

public class MakeOfferDialog extends DialogFragment {

    private EditText etOfferAmount;
    private EditText etStartDate;
    private EditText etEndDate;
    private Button btnSendOffer;
    private String receiverUserEmail;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_make_offer, null);

        etOfferAmount = view.findViewById(R.id.etOfferAmount);
        etStartDate = view.findViewById(R.id.etStartDate);
        etEndDate = view.findViewById(R.id.etEndDate);
        btnSendOffer = view.findViewById(R.id.btnSendOffer);

        etStartDate.setOnClickListener(v -> showDatePickerDialog(etStartDate));
        etEndDate.setOnClickListener(v -> showDatePickerDialog(etEndDate));

        btnSendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String offerAmount = etOfferAmount.getText().toString().trim();
                String startDate = etStartDate.getText().toString().trim();
                String endDate = etEndDate.getText().toString().trim();
                addOfferToDb(offerAmount, startDate, endDate);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year1, month1, dayOfMonth) -> editText.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }

    public void addOfferToDb(String amount, String startDate, String endDate) {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String, String> userData = new HashMap<>();
            userData.put("amount", amount);
            userData.put("startDate", startDate);
            userData.put("endDate", endDate);

            db.collection("Users").document(receiverUserEmail)
                    .collection("Offers").document(auth.getCurrentUser().getEmail()).set(userData);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setReceiverUser(String receiverUser) {
        this.receiverUserEmail = receiverUser;
    }
}
