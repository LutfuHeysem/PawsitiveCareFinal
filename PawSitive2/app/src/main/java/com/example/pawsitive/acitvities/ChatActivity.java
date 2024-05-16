package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawsitive.classes.AcceptOfferDialog;
import com.example.pawsitive.classes.ChatMessage;
import com.example.pawsitive.classes.MakeOfferDialog;
import com.example.pawsitive.classes.User;
import com.example.pawsitive.classes.UserForChat;
import com.example.pawsitive.adapters.ChatAdapter;
import com.example.pawsitive.databinding.ActivityChatBinding;
import com.example.pawsitive.utilities.Constants;
import com.example.pawsitive.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private UserForChat receiverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore db;
    private double caretakerBalance, ownerBalance;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
        init();
        listenMessages();
        takeUsersBalance();
    }

    private void init() {

        preferenceManager = new PreferenceManager(getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                chatMessages,
                getBitmapFromEncodedString(receiverUser.img),
                auth.getCurrentUser().getEmail()
        );
        binding.chatRecyclerView.setAdapter(chatAdapter);
        db = FirebaseFirestore.getInstance();
    }

    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, auth.getCurrentUser().getEmail());
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
        message.put(Constants.KEY_MESSAGE, binding.inputMessage.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        db.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        binding.inputMessage.setText(null);
    }

    private void makeOffer() {
        MakeOfferDialog dialog = new MakeOfferDialog();
        dialog.setReceiverUser(receiverUser.email);
        dialog.show(getSupportFragmentManager(), "MakeOfferDialog");
    }

    private void listenMessages() {
        db.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, auth.getCurrentUser().getEmail())
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverUser.id)
                .addSnapshotListener(eventListener);
        db.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, receiverUser.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, auth.getCurrentUser().getEmail())
                .addSnapshotListener(eventListener);


        db.collection("Users")
                .document(auth.getCurrentUser().getEmail())
                .collection("Offers")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        try {
                            if (e != null) {
                                System.out.println("Listen failed: " + e.getMessage());
                                return;
                            }

                            for (DocumentChange dc : snapshot.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        DocumentSnapshot document = dc.getDocument();
                                        String userEmail = document.getId();
                                        String amount = document.getString("amount");
                                        String endDate = document.getString("endDate");
                                        String startDate = document.getString("startDate");
                                        AcceptOfferDialog dialog = new AcceptOfferDialog();
                                        dialog.setAmount(amount);
                                        dialog.setEndDate(endDate);
                                        dialog.setStartDate(startDate);
                                        dialog.setReceiverUserEmail(receiverUser.email);
                                        dialog.show(getSupportFragmentManager(), "AcceptOfferDialog");
                                        break;
                                    case MODIFIED:
                                        break;
                                    case REMOVED:
                                        break;
                                }
                            }
                        } catch (Exception ex) {
                            // Handle any exceptions here
                            System.out.println("Exception occurred: " + ex.getMessage());
                        }
                    }
                });


        db.collection("Users")
                .document(auth.getCurrentUser().getEmail())
                .collection("AcceptedOffers")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        try {
                            if (e != null) {
                                System.out.println("Listen failed: " + e.getMessage());
                                return;
                            }

                            for (DocumentChange dc : snapshot.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        DocumentSnapshot document = dc.getDocument();
                                        String userEmail = document.getId();
                                        String amount = document.getString("amount");
                                        String endDate = document.getString("endDate");
                                        String startDate = document.getString("startDate");

                                        LocalDate today = LocalDate.now();

                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        String formattedDate = today.format(formatter);
                                        String[] endDateArr = new String[3];
                                        endDateArr = endDate.split("/");
                                        int day = Integer.parseInt(endDateArr[0]);
                                        int month = Integer.parseInt(endDateArr[1]);
                                        int year = Integer.parseInt(endDateArr[2]);

                                        String[] todayDateArr = new String[3];
                                        todayDateArr = formattedDate.split("/");
                                        int day2 = Integer.parseInt(todayDateArr[0]);
                                        int month2 = Integer.parseInt(todayDateArr[1]);
                                        int year2 = Integer.parseInt(todayDateArr[2]); //receiveremail caretaker -*- auth
                                        if (day == day2 && month == month2 && year == year2 && userEmail.equals(receiverUser.email)) {

                                            //OWNER auth.getCurrentUser().getEmail()
                                            //CARETAKER receiverUser.email
                                            double amountD = Double.parseDouble(amount);
                                            HashMap<String, Object> updateBalanceCaretaker = new HashMap<>();
                                            HashMap<String, Object> updateBalanceOwner = new HashMap<>();

                                            //There are some confussions in the naming but works correctly
                                            ownerBalance -= amountD;
                                            caretakerBalance += amountD;
                                            updateBalanceCaretaker.put("Balance", ownerBalance);
                                            updateBalanceOwner.put("Balance", caretakerBalance);

                                            updateInfo(updateBalanceCaretaker, receiverUser.email);
                                            updateInfo(updateBalanceOwner, auth.getCurrentUser().getEmail());

                                            Intent intent = new Intent(ChatActivity.this, ReviewMain.class);
                                            intent.putExtra("email", userEmail);
                                            startActivity(intent);
                                        }
                                        break;
                                    case MODIFIED:
                                        break;
                                    case REMOVED:
                                        break;
                                }
                            }
                        } catch (Exception ex) {
                            // Handle any exceptions here
                            System.out.println("Exception occurred: " + ex.getMessage());
                        }
                    }
                });
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null)
            return;
        if (value != null) {
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessage.dateTime = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if (count == 0) {
                chatAdapter.notifyDataSetChanged();
            } else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            }
            binding.chatRecyclerView.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);
    };

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void loadReceiverDetails() {
        receiverUser = (UserForChat) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textName.setText(receiverUser.name);
    }

    private void backPressed() {
        Intent intent = new Intent(ChatActivity.this, UsersActivity.class);
        startActivity(intent);
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> backPressed());
        binding.layoutSend.setOnClickListener(v -> sendMessage());
        binding.layoutOffer.setOnClickListener(v -> makeOffer());
    }

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }
    //OWNER auth.getCurrentUser().getEmail()
    //CARETAKER receiverUser.email
    private void takeUsersBalance(){
        db.collection("Users").document(auth.getCurrentUser().getEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ownerBalance = documentSnapshot.getDouble("Balance");
                    }
                });
        db.collection("Users").document(receiverUser.email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        caretakerBalance = documentSnapshot.getDouble("Balance");
                    }
                });
    }

    private void updateInfo(HashMap<String, Object> hashMap, String email) {
        db.collection("Users")
                .document(email)
                .update(hashMap)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ChatActivity.this, "Balance updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChatActivity.this, "Failed to update balance", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}