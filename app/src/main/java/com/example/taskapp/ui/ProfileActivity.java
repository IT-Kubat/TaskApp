package com.example.taskapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taskapp.R;
import com.example.taskapp.ui.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText editName;
    private ImageView imageView;
    private Uri mImageUri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    SharedPreferences preferences;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference = firebaseFirestore.document("users/My profile");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        imageView = findViewById(R.id.imageView);
//        getData();
        getData2();
        saveInFirestore();

        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String image = preferences.getString("pref", "");
        Glide.with(this).load(image).into(imageView);
    }


    private void getData2() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        User user = documentSnapshot.toObject(User.class);
                        editName.setText(user.getName());
                    }
                });
    }

    private void getData() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
//                            String name = task.getResult().getString("name");
                            editName.setText(user.getName());
                        }
                    }
                });

    }

    private void saveInFirestore() {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String nameDoc = documentSnapshot.getString("text");
                    editName.setText(nameDoc);
                } else {
                    Toast.makeText(ProfileActivity.this, "not exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2 && data != null) {
            mImageUri = data.getData();
            preferences.edit().putString("pref", String.valueOf(mImageUri)).apply();
            Glide.with(this).load(mImageUri).into(imageView);

            StorageReference sRef = storageReference.child("Photos").child(mImageUri.getLastPathSegment());
            sRef.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onSave(View view) {
        String text = editName.getText().toString();
        Map<String, Object> note = new HashMap<>();
        note.put("text", text);
        documentReference.set(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
//        String uid = FirebaseAuth.getInstance().getUid();
//        String name = editName.getText().toString().trim();
//        User user = new User(name, null, 30);
//
//
////        Map<String,Object> map = new HashMap<>();
////        map.put("name", "Kubat");
////        map.put("age", 30);
////        map.put("android", true);
//        FirebaseFirestore.getInstance().collection("users")
//                .document(uid)
//                .set(user)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(ProfileActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

