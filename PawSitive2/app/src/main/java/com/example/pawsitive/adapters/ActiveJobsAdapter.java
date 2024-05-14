package com.example.pawsitive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsitive.R;
import com.example.pawsitive.acitvities.ViewHolderForActiveJobs;
import com.example.pawsitive.classes.ActiveJobModel;
import com.example.pawsitive.listeners.ActiveJobListener;

import java.util.ArrayList;
import java.util.List;

public class ActiveJobsAdapter extends RecyclerView.Adapter<ViewHolderForActiveJobs> {

    private Context context;
    private final List<ActiveJobModel> activeJobModelList;
    private final ActiveJobListener activeJobListener;
    String clickedEmail;

    public ActiveJobsAdapter(Context context, ArrayList<ActiveJobModel> activeJobList, ActiveJobListener activeJobListener, String email) {
        this.activeJobListener = activeJobListener;
        this.context = context;
        this.activeJobModelList = activeJobList;
        this.clickedEmail = email;
    }

    @NonNull
    @Override
    public ViewHolderForActiveJobs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderForActiveJobs(LayoutInflater.from(context).inflate(R.layout.singular_items_active_job, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForActiveJobs holder, int position) {
        holder.startDate.setText(activeJobModelList.get(position).getStartDate());
        holder.endDate.setText(activeJobModelList.get(position).getEndDate());

        holder.chatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                activeJobListener.onChatClicked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
