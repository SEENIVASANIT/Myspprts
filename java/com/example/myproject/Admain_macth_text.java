package com.example.myproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class Admain_macth_text extends AppCompatActivity {
private EditText title,maths,stutes;
TextView select,data;
private Button save,show,colonter;
    ProgressDialog dialag;
double dudate;
int count=0;
Float a;
private FirebaseFirestore db;
private String uTitle,uMatch,uid;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admain_macth_text);

        title=findViewById(R.id.match_Title);
        maths=findViewById(R.id.matchss);
        save=findViewById(R.id.save);
        show=findViewById(R.id.show);
        select=findViewById(R.id.date);
        data=findViewById(R.id.date);
        colonter=findViewById(R.id.calunder);
        dialag=new ProgressDialog(this);
        db =FirebaseFirestore.getInstance();
        Bundle bundle=getIntent().getExtras();
        if(bundle !=null){
            save.setText("update");
            uTitle=bundle.getString("uTitle");
            uMatch=bundle.getString("uMacth");
            uid=bundle.getString("uid");
            title.setText(uTitle);
            maths.setText(uMatch);

        }else {
            save.setText("save");
        }

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int MONTH=calendar.get(Calendar.MONTH);
                int YEAR=calendar.get(Calendar.YEAR);
                int DAY=calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog=new DatePickerDialog(Admain_macth_text.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        data.setText(i2+"/"+i1+"/"+i);
                        dudate=(i*10000)+(i1*100)+i2;

                    }
                },YEAR,MONTH,DAY);
                datePickerDialog.show();
            }
        });

        MaterialDatePicker materialDatePicker=MaterialDatePicker.Builder.datePicker().setTitleText("Select").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
/*
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"Tag_pick");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {


                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        select.setText(materialDatePicker.getHeaderText());
                    }
                });
            }
        });*/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title1=title.getText().toString();
                String maths1=maths.getText().toString();

            //    String date12=select.getText().toString();
                Bundle bundle1=getIntent().getExtras();
                if(bundle1!=null){
                    String id=uid;
                    updatofierstore(id,title1,maths1);
                    Intent intent=new Intent(Admain_macth_text.this,Admain_show_all_match_data.class);

                    startActivity(intent);

                }else{
                    String id= UUID.randomUUID().toString();
                    saveToFierstore(id,title1,maths1);
                }

               // startActivity(new Intent(Admain_macth_text.this,Admain_show_all_match_data.class));
            }
        });


        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(Admain_macth_text.this,Admain_show_all_match_data.class));
                Toast.makeText(Admain_macth_text.this,"EMTE FIELD NOT ALLOW",Toast.LENGTH_SHORT).show();
                dialag.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Admain_show_all_match_data.class);
        startActivity(intent);
    }

    public void updatofierstore(String id, String title1, String match1){
        db.collection("MATHS_DATA").document(id).update("title",title1,"maths",match1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    dialag.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Admain_macth_text.this,"Updatefaild",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveToFierstore(String id,String title1,String maths1){

            dialag.setTitle("ADDING MATHS DATA...");
            dialag.show();
        if(title1.isEmpty()&&maths1.isEmpty() || title1.isEmpty() ||maths1.isEmpty()) {

            save.setEnabled(false);


            dialag.setTitle("ENTI FILED NOT ALLOW");
            dialag.show();

            Toast.makeText(this,"EMTE FIELD NOT ALLOW",Toast.LENGTH_SHORT).show();
            dialag.dismiss();


        }else {
            save.setEnabled(true);
            HashMap<String, Object> map = new HashMap<>();
            count++;
            map.put("id", id);
            map.put("title", title1);

            map.put("maths", maths1);
            map.put("stutes","Alltha best for matchs👍");
            map.put("date",dudate);
           // db.collection("MATHS_DATA").orderBy(title1, Query.Direction.ASCENDING);
            db.collection("MATHS_DATA").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    dialag.dismiss();
                    Toast.makeText(Admain_macth_text.this, "data  Enter Successfully", Toast.LENGTH_SHORT).show();
                    //db.collection("MATHS_DATA").orderBy(title1, Query.Direction.ASCENDING);
                    startActivity(new Intent(Admain_macth_text.this,Admain_show_all_match_data.class));
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialag.dismiss();
                    Toast.makeText(Admain_macth_text.this, "Data not save Tryagin", Toast.LENGTH_SHORT).show();

                }
            });

  /*
            db.collection("MATHS_DATA").orderBy(title1, Query.Direction.ASCENDING).set(map).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    dialag.dismiss();
                    Toast.makeText(Admain_macth_text.this, "data  Enter Successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Admain_macth_text.this,Admain_show_all_match_data.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Admain_macth_text.this, "Data not save Tryagin", Toast.LENGTH_SHORT).show();
                }
            });

    */    }

    }
}