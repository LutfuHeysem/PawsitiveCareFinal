package com.example.pawsitive.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.MyViewHolder;

import java.util.List;

import com.example.pawsitive.acitvities.Temp;
import com.example.pawsitive.classes.User;

public class HomePageDisplayAdapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List <Temp> users;

    public HomePageDisplayAdapter(Context applicationContext, List<Temp> users) {
        System.out.println("aaauu");
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_view,parent,false));
    }

    //    public ImageView profileView, heartView;
    //    public TextView nameView, genderAgeView, locationView, priceView;
    //    public RatingBar review;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(users.get(position).getName());
        holder.genderAgeView.setText(users.get(position).getGender());
        //holder.locationView.setText(users.get(position).getLocation());
        //price view will be added
        //String profileImage = users.get(position).getImage();
        //byte[] decodedString = Base64.decode(profileImage, Base64.DEFAULT);
        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //holder.profileView.setImageBitmap(decodedByte);
        holder.heartView.setImageResource(R.drawable.heart_3510);
       // holder.review.setRating(users.get(position)); (rating)
    }

    @Override
    public int getItemCount() {
        System.out.println(users.size());
        return users.size();
    }
}
