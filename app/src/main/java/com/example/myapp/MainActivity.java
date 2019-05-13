package com.example.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText txtname;
    EditText txtpass;
    Button btlogin;
    TextView txtnew;
    CheckBox ckb;
//    DatabaseReference data;
    User user;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = firebaseAuth.getInstance();
        getSupportActionBar().hide();
        Anhxa();
        auth = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Toast.makeText(MainActivity.this, "thanh cong", Toast.LENGTH_LONG).show();
                }
            }
        };
        progressDialog = new ProgressDialog(this);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });
        create();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(auth);
    }

    private  void userlogin()
    {
        String us = txtname.getText().toString().trim();
        String pa = txtpass.getText().toString().trim();
        if(TextUtils.isEmpty(us) ||TextUtils.isEmpty(pa) ){
            Toast.makeText(MainActivity.this, "nhap thong tin", Toast.LENGTH_LONG).show();
        }else{
            firebaseAuth.signInWithEmailAndPassword(us, pa)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "thanhcong", Toast.LENGTH_LONG).show();
                               changeActivity();
                            }else {
                                Toast.makeText(MainActivity.this,"loi",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

        progressDialog.setMessage("loadding....");
        progressDialog.show();
    }

    private void changeActivity() {
        Intent huyen =new Intent(MainActivity.this,Main22Activity.class);
startActivity(huyen);
finish();
    }


    //    public void openDialog() {
//        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//        alert.setTitle("Thông tin");
//        alert.setMessage("Nhập sai thông tin");
//        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        alert.show();
//    }
//    private  void login(){
//        final String us = txtname.getText().toString();
//        final String pas = txtpass.getText().toString();
//        data = FirebaseDatabase.getInstance().getReference().child("account");
//        data.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                Log.d("abc", "onDataChange: " + dataSnapshot.child("user").getValue().toString());
//                Log.d("abc", "onDataChange: " + dataSnapshot.child("pass").getValue().toString());
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    user = ds.getValue(User.class);
////                    listuser.add(user.getUsername());
//                        user= new User();
////                    String user = dataSnapshot.child("user").getValue().toString();
////                    String pass = dataSnapshot.child("pass").getValue().toString();
////                    txtpass.setText(pass);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//            }
//        });
//
//    }


    private void create() {
        txtnew = findViewById(R.id.txtnew);
        txtnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,Main22Activity.class);
                startActivity(intent2);
//                setContentView(R.layout.activity_main22);
            }
        });
    }
    private  void  Anhxa(){
        txtname = findViewById(R.id.txtname);
        txtpass = findViewById(R.id.txtpass);
        btlogin = findViewById(R.id.btlogin);
        ckb = findViewById(R.id.ckb);
    }
}
