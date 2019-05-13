package com.example.myapp;

//import android.support.annotation.NonNull;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {
    Button btxoa;
    ListView list;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> listuser;
    ArrayAdapter<String> adap;
    ArrayList<String> keyList = new ArrayList<>();
     ArrayList<String> items = new ArrayList<>();
    User user;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listdata);
        user = new User();

        btxoa = findViewById(R.id.btxoa);
        list = findViewById(R.id.list);
//        btxoa.setEnabled(false);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("account");
        listuser = new ArrayList<>();
        adap = new ArrayAdapter<String>(this, R.layout.user_info, R.id.user_info,listuser);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user = ds.getValue(User.class);
                    listuser.add(user.getUsername());
//                    Log.d("abc", "onDataChange: " + dataSnapshot.child("user").getValue().toString());

                }
                list.setAdapter(adap);
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
        btxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaData();
            }
        });

    }
    public  void xoaData(){
        ref.getRoot().child("account")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot messages : dataSnapshot.getChildren()) {
                            keyList.add(messages.getKey());
                            items.add(messages.getValue(String.class));
                            btxoa.setEnabled(true);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        /*handle errors*/
                    }
                });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                adap.notifyDataSetChanged();
                //new code below
                ref.getRoot().child("account").child(keyList.get(position)).removeValue();
                keyList.remove(position);
            }
        });
    }
}
