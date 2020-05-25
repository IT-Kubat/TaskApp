package com.example.taskapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.example.taskapp.R.id.our_work;
import static com.example.taskapp.R.id.stay_home;

//        1. Нужно добавить окно для ввода кода из смс (другой edittext и button)
//        2. Показать это окно если была отправлена смска
//        3. Добавить +996 для ввода (не стираемый)
//        4. Дизайн
public class PhoneActivity extends AppCompatActivity {

    private EditText editPhone;
    private EditText editCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String codeFromInternet;
    LinearLayout numberField;
    LinearLayout numberCode;
    LottieAnimationView lottie1, lottie2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editPhone = findViewById(R.id.editPhone);
        editCode = findViewById(R.id.editCode);
        numberCode = findViewById(R.id.second_screen);
        numberField = findViewById(R.id.first_screen);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "onVerificationCompleted");
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    signIn(phoneAuthCredential);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "onVerificationFailed" + e.getMessage());

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeFromInternet = s;
                Log.e("Phone", "onCodeSent");
            }
        };
    }
    private void signIn (PhoneAuthCredential phoneAuthCredential){
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PhoneActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(PhoneActivity.this, MainActivity.class));
//                            finish();
                        } else {
                            Log.e("Phone", "Ошибка =" + task.getException().getMessage());
                            Toast.makeText(PhoneActivity.this, "Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT);

                        }
                    }
                });
    }

    public void onClick(View view) {
        String phone = editPhone.getText().toString().trim();
        if (phone == null) {
            editPhone.setError("Phone number is required");
            editPhone.requestFocus();
            return;
        } else {
            if (TextUtils.isEmpty(phone) && phone.length() > 9) {
                Toast.makeText(getApplicationContext(), "Enter your phone number", Toast.LENGTH_LONG).show();
                editPhone.setError("Phone number is required");
                editPhone.requestFocus();
                return;
            } else if (phone.length() == 9) {
                phone = "+996" + editPhone.getText().toString();
            }
            numberField.setVisibility(View.INVISIBLE);
            numberCode.setVisibility(View.VISIBLE);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    callbacks);

        }
    }
    public void onCodeClick(View view){
        String code = editCode.getText().toString().trim();

        if (TextUtils.isEmpty(code)) {
            Toast.makeText(getApplicationContext(), "Enter your code", Toast.LENGTH_LONG).show();
            editCode.setError("Code is required");
            editCode.requestFocus();
            return;
        } else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeFromInternet, code);
            signIn(credential);
            startActivity(new Intent(PhoneActivity.this, MainActivity.class));
            finish();
        }
    }
}



