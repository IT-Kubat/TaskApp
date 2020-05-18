package com.example.taskapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.ui.models.Task;

public class FormActivity extends AppCompatActivity {

    private EditText editTitle, editDesc;
    Task myTask = new Task();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("New Task");
            editTitle = findViewById(R.id.editTitle);
            editDesc = findViewById(R.id.editDesc);
            edit();
        }

    }



    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        Task task = new Task(title, desc);
        Intent intent = new Intent();
//        intent.putExtra("title", title);
//        intent.putExtra("desc", desc);
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        if (editTitle.getText().toString().matches("") || editDesc.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Fill the Line", Toast.LENGTH_SHORT).show();
//        } else if (myTask != null) {
//            myTask.setTitle(title);
//            myTask.setDesc(desc);
//            App.getInstance().getDatabase().taskDao().insert(task);

        } else {
            Log.e("Ololo", desc);
            myTask = new Task(title, desc);
            App.getDatabase().taskDao().insert(myTask);
            finish();
        }
    }

        @Override
        public boolean onSupportNavigateUp() {
            onBackPressed();
            return true;
        }

        public void edit() {
            myTask = (Task) getIntent().getSerializableExtra("task");
            if (myTask != null) {
                editDesc.setText(myTask.getDesc());
                editTitle.setText(myTask.getTitle());
            }
        }
    }
