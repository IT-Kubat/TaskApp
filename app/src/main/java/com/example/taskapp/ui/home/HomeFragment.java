package com.example.taskapp.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.ui.FormActivity;
import com.example.taskapp.ui.OnItemClickListener;
import com.example.taskapp.ui.models.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private static TaskAdapter adapter;
    private static ArrayList<Task> list = new ArrayList<>();
    private static List<Task> sortedList;
    private static List<Task> notSortedList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskAdapter(list);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(getActivity(), FormActivity.class);
                intent.putExtra("task", list.get(pos));
                Objects.requireNonNull(getActivity()).startActivityForResult(intent, 42);
                App.getDatabase().taskDao().delete(list.get(pos));
            }

            @Override
            public void itemLongClick(final int position) {
                list.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Do you want to delete?")
                        .setMessage("Delete")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.getDatabase().taskDao().delete(list.get(position));
                    }
                }).show();
            }
        });
        recyclerView.setAdapter(adapter);
        loadData();
    }


    public void loadData() {
        App.getInstance()
                .getDatabase()
                .taskDao()
                .getAllLive()
                .observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        notSortedList = tasks;
                        list.clear();
                        list.addAll(0, tasks);
                        Collections.reverse(list);
                        adapter.notifyDataSetChanged();
                    }
                });

        App.getDatabase().taskDao().getSortedList().observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                sortedList = tasks;

            }
        });

    }


    public static void setNotSortedList() {
        list.clear();
        list.addAll(notSortedList);
        adapter.notifyDataSetChanged();

    }


    public static void setSortedList() {
        list.clear();
        list.addAll(sortedList);
        adapter.notifyDataSetChanged();
    }
}
