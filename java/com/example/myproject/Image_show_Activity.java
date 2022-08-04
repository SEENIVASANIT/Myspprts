package com.example.myproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Image_show_Activity extends AppCompatActivity {
public RecyclerView mrecyclerview;
public Image_adapter image_adapter;
    private FirebaseFirestore db;
private DatabaseReference databaseReference;
private List<Upload> mupload;
    ProgressDialog dialag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        mrecyclerview=findViewById(R.id.show_image_view);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new  LinearLayoutManager(this));

        mupload=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        db=FirebaseFirestore.getInstance();
        db.collection("upload").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()) {
                    Upload module = new Upload(snapshot.getString("name"), snapshot.getString("uri"));
                    mupload.add(module);
                    image_adapter=new Image_adapter(Image_show_Activity.this,mupload);
                    mrecyclerview.setAdapter(image_adapter);
                    Toast.makeText(Image_show_Activity.this, "SHOW FIERBASE", Toast.LENGTH_SHORT).show();
                }

                //image_adapter=new Image_adapter(Image_show_Activity.this,mupload);
                //mrecyclerview.setAdapter(image_adapter);
                image_adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}