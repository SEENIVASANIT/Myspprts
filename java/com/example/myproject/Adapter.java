package com.example.myproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Adapter extends RecyclerView.Adapter<Adapter.Myviewholder> {
    private List<Mathsnodule> mathslist;
    private Macth_show_admain activity;
    private FirebaseFirestore firestore;

    public Adapter(Macth_show_admain macth_show_admain,List<Mathsnodule> mathslist){
        this.mathslist=mathslist;
        activity=macth_show_admain;
    }
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SwitchCompat switchCompat = null;
       View view= LayoutInflater.from(activity).inflate(R.layout.admin_maths_idem,parent,false);
       SharedPreferences sharedPreferences= activity.getSharedPreferences("save",MODE_PRIVATE);
       switchCompat.setChecked(sharedPreferences.getBoolean("value",true));
       switchCompat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

       return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
Mathsnodule mathsnodule=mathslist.get(position);
SharedPreferences sharedPreferences= activity.getSharedPreferences("save", MODE_PRIVATE);

holder.switchCompat.setChecked(sharedPreferences.getBoolean("value",true));
holder.switchCompat.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {



    }
});

    }


    @Override
    public int getItemCount() {
        return mathslist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{
TextView date;
SwitchCompat switchCompat;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);

           switchCompat=itemView.findViewById(R.id.win_switch);
           SharedPreferences sharedPreferences= activity.getSharedPreferences("save", MODE_PRIVATE);
          switchCompat.setChecked(sharedPreferences.getBoolean("value",true));

        }
    }
}
