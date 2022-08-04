package com.example.myproject;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Macth_show_admain extends AppCompatActivity {
private RecyclerView recyclerView;
private FloatingActionButton floatingActionButton;
FirebaseFirestore firestore;
private Adapter adapter;
private List<Mathsnodule> mlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macth_show_admain);
        recyclerView=findViewById(R.id.maths_show_admain);
        floatingActionButton=findViewById(R.id.add_float_match);
        firestore=FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Macth_show_admain.this));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewmatchs.newIntence().show(getSupportFragmentManager(),AddNewmatchs.Tag);
            }
        });
        mlist=new ArrayList<>();
        adapter=new Adapter(Macth_show_admain.this,mlist);
        recyclerView.setAdapter(adapter);
        shoedata();
    }
    private void shoedata(){
        firestore.collection("maths").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
           for(DocumentChange documentChange:value.getDocumentChanges()){
               if(documentChange.getType()== DocumentChange.Type.ADDED){
                   String id=documentChange.getDocument().getId();
                   //Mathsnodule mathsnodule=documentChange.getDocument().toObject(Mathsnodule.class).withId(id);
                   //mlist.add(mathsnodule);
                   adapter.notifyDataSetChanged();
               }
           }
                Collections.reverse(mlist);
            }
        });
    }
}