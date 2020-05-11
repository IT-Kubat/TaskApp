package com.example.taskapp.ui.onboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskapp.MainActivity;
import com.example.taskapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
//        1. В меню кнопка "Выйти", то при запуске приложения показать onBoard
//        2. Фон каждой страницы onboard чтоб была разного цвета
//        3. Вывести название файлов в recyclerView в GalleryFragment
//        4. Skip сделать
//        5. Открыть ProfileActivity по нажатии Header

public class BoardFragment extends Fragment {
    Button btnStart, btnSkip;

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textTitle = view.findViewById(R.id.textTitle);
        ImageView imageView = view.findViewById(R.id.imageView);
        btnStart = view.findViewById(R.id.btnStart);
        btnSkip = view.findViewById(R.id.btnSkip);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIsShown();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            private void saveIsShown(){
                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences("Settings", Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("IsShown", true).apply();
            }
        });
        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                btnStart.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.foto2);
                textTitle.setText("Hello, my friend!");
                break;
            case 1:
                btnStart.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.logo1);
                textTitle.setText("How are you?");
                break;
            case 2:
                btnStart.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.logo_gt);
                textTitle.setText("What are doing?");
                break;
        }
    }
}
