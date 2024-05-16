package com.example.pawsitive.acitvities;

import com.example.pawsitive.classes.User;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UsersHomePageDisplay {
    private ArrayList<User> usersOnDisplay;
    public UsersHomePageDisplay(){
        this.usersOnDisplay = new ArrayList<>();
    }
    public ArrayList<User> getUsersOnDisplay(){
        return this.usersOnDisplay;
    }
}
