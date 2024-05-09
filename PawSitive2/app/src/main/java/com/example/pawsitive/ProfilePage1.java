//package com.example.pawsitive;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class ProfilePage1 extends AppCompatActivity {
//    ImageView profileImage;
//    TextView profileUserName;
//    TextView stars;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_profile_page1);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        Button backButtonProfilePage = (Button) findViewById(R.id.backButtonProfilePage);
//        profileImage = (ImageView) findViewById(R.id.profileImage);
//        profileUserName = (TextView) findViewById(R.id.profileUserName);
//        //will be done when we learn how to get data from firebase
//        //profileUserName.setText(user.getUsername);
//        stars = (TextView) findViewById(R.id.ratingStars);
//
//
//
//
//        if(user.getStars() == 0.5)
//        {
//            stars.setBackgroundResource(R.drawable.halfStar);
//        }
//        if(user.getStars() == 1.5)
//        {
//            stars.setBackgroundResource(R.drawable.oneAndHalfStar);
//        }
//        if(user.getStars() == 2.5)
//        {
//            stars.setBackgroundResource(R.drawable.twoAndHalfStar);
//        }
//        if(user.getStars() == 3.5)
//        {
//            stars.setBackgroundResource(R.drawable.threeAndHalfStar);
//        }
//        if(user.getStars() == 4.5)
//        {
//            stars.setBackgroundResource(R.drawable.fourAndHalfStar);
//        }
//        if(user.getStars() == 0)
//        {
//            stars.setBackgroundResource(R.drawable.zeroStar);
//        }
//        if(user.getStars() == 1)
//        {
//            stars.setBackgroundResource(R.drawable.oneStar);
//        }
//        if(user.getStars() == 2)
//        {
//            stars.setBackgroundResource(R.drawable.twoStar);
//        }
//        if(user.getStars() == 3)
//        {
//            stars.setBackgroundResource(R.drawable.threeStar);
//        }
//        if(user.getStars() == 4)
//        {
//            stars.setBackgroundResource(R.drawable.fourStar);
//        }
//        if(user.getStars() == 5)
//        {
//            stars.setBackgroundResource(R.drawable.fiveStar);
//        }
//
//
//
//    }
//    private void changeProfilePhoto(ImageView newPhoto)
//    {
//        Drawable profileIcon = newPhoto.getDrawable();
//        profileImage.setImageDrawable(profileIcon);
//    }
//}