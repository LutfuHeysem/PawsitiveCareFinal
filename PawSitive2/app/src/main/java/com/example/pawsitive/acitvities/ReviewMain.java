package com.example.pawsitive.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawsitive.R;
import com.example.pawsitive.classes.Review;
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewMain extends AppCompatActivity {

    private HashMap<String, Object> review;
    private String reviewString;
    private float rating;
    private RatingBar star;
    private EditText comment;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private Button saveReview;
    private String feedbackReceiverUser;
    private double transactionAmountDouble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_main);
        feedbackReceiverUser = getIntent().getExtras().getString("email");
        saveReview = findViewById(R.id.button7);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        comment = findViewById(R.id.editTextText2);
        star = findViewById(R.id.ratingBar);

        saveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewString = comment.getText().toString();
                rating = star.getRating();

                review = new HashMap<>();
                review.put("Star", rating);
                review.put("Comment", reviewString);
                review.put("CareTaker", feedbackReceiverUser);

                fStore.collection("Reviews").document().set(review)
                        .addOnCompleteListener(ReviewMain.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(ReviewMain.this, "Your review is saved successfully!", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(ReviewMain.this, "Error in adding review", Toast.LENGTH_SHORT).show();
                            }
                        });
                    try {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference documentRef = db.collection("Users")
                                .document(User.getEmail())
                                .collection("AcceptedOffers")
                                .document(feedbackReceiverUser);

                        documentRef.delete()
                                .addOnSuccessListener(aVoid -> System.out.println("Document deleted successfully"))
                                .addOnFailureListener(e -> System.out.println("Error deleting document: " + e.getMessage()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }


//                    User feedbackReceiverUserUser = new User(feedbackReceiverUser);
//                    User feedbackGiverUser = new User(User.getEmail());
//                    double feedbackGiverBalance = User.getBalance();
//                    double feedbackReceiverUserBalance = feedbackReceiverUserUser.getBalance();
//                    FirebaseFirestore db =FirebaseFirestore.getInstance();
//
//                    DocumentReference careTakerJobData = db.collection("Users").
//                            document(User.getEmail()).
//                            collection("AcceptedOffers").
//                            document(feedbackReceiverUser);
//                    careTakerJobData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                            String transactionAmount = documentSnapshot.getString("amount");
//                            transactionAmountDouble = Double.parseDouble(transactionAmount);
//                        }
//                    });



//                    feedbackReceiverUserUser.setBalance(transactionAmountDouble + feedbackReceiverUserBalance, feedbackReceiverUserUser);
//                    feedbackGiverUser.setBalance(feedbackGiverBalance - transactionAmountDouble, feedbackGiverUser);


                    Intent intent = new Intent(ReviewMain.this, HomePage.class);
                    startActivity(intent);
            }
            });

}

    public static int getResource(double averageStar)
    {
        if(averageStar == 0.5)
        {
            return (R.drawable.halfstar);
        }
        if(averageStar == 1.5)
        {
            return (R.drawable.oneandhalfstar);
        }
        if(averageStar == 2.5)
        {
            return (R.drawable.twoandhalfstar);
        }
        if(averageStar == 3.5)
        {
            return (R.drawable.threeandhalfstar);
        }
        if(averageStar == 4.5)
        {
            return (R.drawable.fourandhalfstar);
        }
        if(averageStar == 0)
        {
            return (R.drawable.zerostar);
        }
        if(averageStar == 1)
        {
            return (R.drawable.onestar);
        }
        if(averageStar == 2)
        {
            return (R.drawable.twostar);
        }
        if(averageStar == 3)
        {
            return (R.drawable.threestar);
        }
        if(averageStar == 4)
        {
            return (R.drawable.fourstar);
        }
        if(averageStar == 5)
        {
            return (R.drawable.fivestar);
        }
        throw new RuntimeException("average can be an integer from 0 to 5 or an (integer + 0.5) < 5");
    }
}