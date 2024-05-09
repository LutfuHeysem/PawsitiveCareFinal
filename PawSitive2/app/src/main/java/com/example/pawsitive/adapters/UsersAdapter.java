package com.example.pawsitive.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.User;
import com.example.pawsitive.UserForChat;
import com.example.pawsitive.databinding.ItemContainerChatBinding;

import java.util.List;
public class UsersAdapter extends  RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private final List<UserForChat> users;

    public UsersAdapter(List<UserForChat> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerChatBinding itemContainerChatBinding = ItemContainerChatBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(itemContainerChatBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
    holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        ItemContainerChatBinding itemContainerChatBinding;

        public UserViewHolder(View itemView) {
            super(itemView);
        }

        ItemContainerChatBinding binding;
        UserViewHolder(ItemContainerChatBinding itemContainerChatBinding) {
            super(itemContainerChatBinding.getRoot());
            binding = itemContainerChatBinding;

        }

        void setUserData(UserForChat user){
            binding.textName.setText(user.name);
            binding.textEmail.setText(user.email);

        }
    }

    private Bitmap getUserImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
