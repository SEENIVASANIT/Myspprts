package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Admain_maths_detali_page extends AppCompatActivity {
EditText maths_Title,maths_data;
Button save_data;
ProgressDialog dialag;
FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admain_maths_detali_page);
        maths_Title=findViewById(R.id.match_Title);
        maths_data=findViewById(R.id.maths_place);
        save_data=findViewById(R.id.save_detali);
        dialag=new ProgressDialog(this);
        db=FirebaseFirestore.getInstance();
        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=maths_Title.getText().toString().trim();
                String mthsdata=maths_data.getText().toString().trim();
                String id= UUID.randomUUID().toString();
                upload_Maths_Data(id,title,mthsdata);

            }
        });
    }
private void upload_Maths_Data(String id,String title, String mthsdata){
        dialag.setTitle("ADDING MATHS DATA");
        dialag.show();
        //String id= UUID.randomUUID().toString();
        if(title.isEmpty()&&mthsdata.isEmpty()) {
            Map<String, Object> Map_obj = new HashMap<>();
            Map_obj.put("id", id);
            Map_obj.put("title", title);
            Map_obj.put("Data", mthsdata);
            db.collection("MATHS_DATA").document(id).set(Map_obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    dialag.dismiss();
                    Toast.makeText(Admain_maths_detali_page.this, "Upload Success...", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialag.dismiss();
                    Toast.makeText(Admain_maths_detali_page.this, "Tiry again", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else Toast.makeText(this,"EMTE FIELD NOT ALLOW",Toast.LENGTH_SHORT).show();


}
}