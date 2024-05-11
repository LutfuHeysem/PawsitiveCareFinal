package com.example.pawsitive.classes;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.TaskCompletionSource;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class User {
    private static String name;
    private static String password;
    private static String email;
    public static String location;
    private static String nameOnTheCard;
    private static String cardNumber;
    private static String cvv;
    private static String expirationDate;
    private ArrayList<String> reviews;
    private ArrayList<Float> stars;
    private ArrayList<Pet> pets;
    private static ArrayList<String> chatUsers;

    public User(String email) {
        User.email = email;
        getChatUsersFromDatabase();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TaskCompletionSource<DocumentSnapshot> source = new TaskCompletionSource<>();
        Task<DocumentSnapshot> task = source.getTask();

        db.collection("Users")
                .whereEqualTo("Email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                source.setResult(document);
                            }
                        } else {
                            source.setException(task.getException());
                        }
                    }
                });
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        name = document.getString("Name");
                        password = document.getString("Password");
                        location = document.getString("Location");
                        nameOnTheCard = document.getString("Name On The Credit Card");
                        cardNumber = document.getString("Card Number");
                        cvv = document.getString("CVV");
                        expirationDate = document.getString("Expiration Date");
                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
            }

        });

        db.collection("Users").document(email).collection("Reviews").document("Comments")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            String com = documentSnapshot.getString("Comment");
                            reviews.add(com);
                        }
                        else
                            Log.d("User", "No comments document exists!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("User", "Error fetching comments", e);
                    }
                });

        db.collection("Users").document(email).collection("Reviews").document("Stars")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            Float sta = documentSnapshot.getDouble("Stars").floatValue();
                            stars.add(sta);
                        }
                        else
                            Log.d("User", "No stars document exists!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("User", "Error fetching stars", e);
                    }
                });
    }
// dkjbvhy3wbhvr@yahoo.com
// 016956216532

public static boolean searchInsidefChat(String key) {
    for (int i = 0; i < chatUsers.size(); i++) {
        if (chatUsers.get(i).equals(key)) {
            return true;
        }
    }
    return false;
}

    public void getChatUsersFromDatabase() {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            CollectionReference usersForChatRef = db.collection("Users")
                    .document(User.getEmail())
                    .collection("UsersForChat");

            usersForChatRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    ArrayList<String> users = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String email = documentSnapshot.getString("email");
                        if (email != null) {
                            users.add(email);
                        }
                    }
                    chatUsers = users;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Failed to fetch users from Firebase: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addUserToChatPage(String user){
        chatUsers.add(user);
    }
    public static String getName() {
        return name;
    }

    public static void setChatUsers(ArrayList<String> chatUsers) {
        User.chatUsers = chatUsers;
    }

    public ArrayList<String> getReviews() {return reviews;}
    public ArrayList<Float> getStars() {return stars;}

    public static String getPassword() {
        return password;
    }

    public static String getEmail() {
        return email;
    }

    public static String getLocation() {
        return location;
    }

    public static String getNameOnTheCard() {
        return nameOnTheCard;
    }

    public static String getCardNumber() {
        return cardNumber;
    }

    public static String getCvv() {
        return cvv;
    }

    public static String getExpirationDate() {
        return expirationDate;
    }
    public ArrayList<Pet> getPets() {
        return pets;
    }

    public interface OnUserLoadListener {
        void onUserLoaded(User user);
    }
}
