package com.example.pawsitive.classes;

import com.example.pawsitive.acitvities.ReviewListActivity;
import com.example.pawsitive.utilities.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.TaskCompletionSource;

import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private static String name, gender, age, image;
    private static String password;
    private static String email;
    public static String location;
    private static String nameOnTheCard;
    private static String cardNumber;
    private static String cvv;
    private static String expirationDate;
    private static ArrayList<Review> reviewArrayList;
    private ArrayList<Pet> pets;
    private static ArrayList<String> chatUsers;
    public static ArrayList<Float> stars = new ArrayList<>();;

    public User(String email, OnUserLoadListener listener) {
        User.email = email;
        getChatUsersFromDatabase();

        ReviewListActivity.getReviews(email, new OnSuccessListener<ArrayList<Review>>() {
            @Override
            public void onSuccess(ArrayList<Review> reviews) {
                setReviewArrayList(reviews);
                listener.onUserLoaded(User.this);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle failure if needed
            }
        });

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
                        gender = document.getString("Gender");
                        age = document.getString("Age");
                        image = document.getString("Profile Photo");


                    } else {
                        Log.d("User", "No such document");
                    }
                } else {
                    Log.d("User", "get failed with ", task.getException());
                }
            }

        });

//        setReviewArrayList(ReviewListActivity.getReviews(email));
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

     static void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        User.reviewArrayList = reviewArrayList;
    }
    public static ArrayList<Review> getReviewArrayList() {
        return User.reviewArrayList;
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

    public static String getPassword() {
        return password;
    }

    public static String getEmail() {
        return email;
    }

    public static ArrayList<Review> getFilledAL(){ return reviewArrayList; }

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

    public String getGender() {
        return gender;
    }
    public String getAge(){
        return age;
    }
    public String getImage(){return image;}

    public interface OnUserLoadListener {
        void onUserLoaded(User user);
    }
}
