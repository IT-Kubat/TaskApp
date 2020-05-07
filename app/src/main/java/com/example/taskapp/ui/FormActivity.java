package com.example.taskapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.taskapp.R;
import com.example.taskapp.ui.models.Task;

public class FormActivity extends AppCompatActivity {

    EditText editTitle, editDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("New Task");

            editTitle = findViewById(R.id.editTitle);
            editDesc = findViewById(R.id.editDesc);
        }
    }

    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        Task task = new Task(title,desc);
        Intent intent = new Intent();
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        finish();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
