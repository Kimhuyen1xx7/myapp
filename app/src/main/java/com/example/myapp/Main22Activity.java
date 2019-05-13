package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Main22Activity extends AppCompatActivity {
    EditText user;
    EditText passw;
    Button dki;
    Button btxemds;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener auth;
//    DatabaseReference data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        firebaseAuth = FirebaseAuth.getInstance();
//        data = FirebaseDatabase.getInstance().getReference("account");


        user = findViewById(R.id.txtdk_user);
        passw = findViewById(R.id.txtdk_pass);


        dki = findViewById(R.id.btdki);
        auth = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Toast.makeText(Main22Activity.this, "thanh cong", Toast.LENGTH_LONG).show();
                }
            }
        };

        dki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
        Xem_DS();
    }
    public  void addUser(){
        String username = user.getText().toString();
        String pass = passw.getText().toString();

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pass)){
            firebaseAuth.createUserWithEmailAndPassword(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Main22Activity.this, "thanhcong", Toast.LENGTH_LONG).show();
                        changeActivity();
                    }else {
                        Toast.makeText(Main22Activity.this,"loi",Toast.LENGTH_LONG).show();
                    }
                }
            });
//            String userid = data.push().getKey();
//            User us = new User(username, pass);
//            data.child(userid).setValue(us);
//            user.setText("");
//            passw.setText("");
        }else {
            Toast.makeText(Main22Activity.this, "Nhập thông tin", Toast.LENGTH_LONG).show();
        }
    }
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(auth);
    }
    private void changeActivity() {
        Intent huyen =new Intent(Main22Activity.this,Main22Activity.class);
        startActivity(huyen);
        finish();
    }
    public  void Xem_DS(){
        btxemds = findViewById(R.id.btxemds);
        btxemds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Main22Activity.this,ListData.class);
                startActivity(intent2);
            }
        });
        }
}
