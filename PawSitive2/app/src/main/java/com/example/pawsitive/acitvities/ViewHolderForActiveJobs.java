package com.example.pawsitive.acitvities;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;

public class ViewHolderForActiveJobs extends RecyclerView.ViewHolder{

    public TextView startDate, endDate;
    public Button chatButton;
    public ViewHolderForActiveJobs(@NonNull View itemView) {
        super(itemView);

        startDate = itemView.findViewById(R.id.startDate);
        endDate = itemView.findViewById(R.id.endDate);
        chatButton = itemView.findViewById(R.id.startChatButton);
    }
}
