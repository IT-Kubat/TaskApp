package com.example.taskapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.ui.OnItemClickListener;
import com.example.taskapp.ui.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList <Task> list;
    private OnItemClickListener onItemClickListener;

    public void setList(List<Task> list) {
        this.list = (ArrayList<Task>) list;
    }

    public TaskAdapter(ArrayList<Task> list) {
        this.list = list;
//        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task,parent,false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        holder.setIsRecyclable(true);
        if (position % 2 == 1) {
            holder.itemView.setBackgroundResource(R.color.colorWhite);
        } else {
            holder.itemView.setBackgroundResource(R.color.colorGrey);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;
        private TextView textDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
         itemView.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View v) {
                onItemClickListener.itemLongClick(getAdapterPosition());
                 return false;
             }
         });
        }

        public void bind(Task task) {
            textTitle.setText(task.getTitle());
        }
    }
}
