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
import com.example.pawsitive.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ReviewMain extends AppCompatActivity {

    private String reviewString;
    private HashMap<String, Object> userComment, userStar;
    private float rating;
    private RatingBar star;
    private EditText comment;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private Button saveReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_main);

        saveReview = findViewById(R.id.button7);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        comment = findViewById(R.id.editTextText2);
        star = findViewById(R.id.ratingBar);

        userComment = new HashMap<>();
        userStar = new HashMap<>();
        saveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewString = comment.getText().toString();
                rating = star.getRating();

                userStar.put("Stars", rating);
                userComment.put("Comment", reviewString);

                fStore.collection("Users").document(auth.getCurrentUser().getEmail()).collection("Reviews").document("Comments")
                        .set(userComment).addOnCompleteListener(ReviewMain.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    fStore.collection("Users").document(auth.getCurrentUser().getEmail()).collection("Reviews").document("Stars")
                                            .set(userStar).addOnCompleteListener(ReviewMain.this, new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(ReviewMain.this, "Your review is saved successfully!", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(ReviewMain.this, ResetPassword.class));
                                                    }
                                                    else
                                                        Toast.makeText(ReviewMain.this, "Error in adding stars! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                else
                                    Toast.makeText(ReviewMain.this, "Error in adding comment! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
    public float getNoOfStar() {
        return rating;
    }

    public String getReviewString() {
        return reviewString;
    }

    public static double calculateStarAverage(User user)
    {
        if(user.getReviews().size() == 0)
        {
            return 0;
        }
        double sumOfStars = 0;
        for(int i = 0; i < user.getReviews().size(); i++)
        {
            sumOfStars+= user.getStars().get(i);
        }
        double sumOfStarsTimesTwo = 2 * sumOfStars;
        double averageStarsTimesTwo = sumOfStarsTimesTwo / user.getReviews().size();
        double averageStarsTimesTwoRounded = Math.round(averageStarsTimesTwo);
        return  averageStarsTimesTwoRounded/2;
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