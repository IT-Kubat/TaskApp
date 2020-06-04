package com.example.taskapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskapp.R;
import com.example.taskapp.ui.fireStore.FirestoreRecyclerOptions;
import com.example.taskapp.ui.models.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class WorkFirestoreAdapter extends com.firebase.ui.firestore.FirestoreRecyclerAdapter<Task, WorkFirestoreAdapter.WorkHolder> {

    OnWorkListener listener;


    public WorkFirestoreAdapter(@NonNull FirestoreRecyclerOptions<Task> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull WorkHolder holder, int position, @NonNull Task model) {
        holder.onBind(model);

    }

    @NonNull
    @Override
    public WorkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_item, parent, false);
        WorkHolder workHolder = new WorkHolder(view);
        return workHolder;
    }

    class WorkHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title;
        TextView description;
        ImageView iv_picture;


        public WorkHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_firestore);
            description = itemView.findViewById(R.id.description_firestore);
            iv_picture = itemView.findViewById(R.id.iv_picture);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getSnapshots().getSnapshot(getAdapterPosition()), getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getSnapshots().getSnapshot(getAdapterPosition()), getAdapterPosition());
            return true;
        }

        public void onBind(Task task){
            title.setText(task.getTitle());
            description.setText(task.getDesc());
            Glide.with(itemView.getContext()).load(task.getImageUri()).into(iv_picture);
        }
    }

    public interface OnWorkListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

        void onItemLongClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnWorkListener(OnWorkListener listener) {
        this.listener = listener;
    }
}

