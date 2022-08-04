package com.example.myproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Admain_maths_adpater extends RecyclerView.Adapter<Admain_maths_adpater.Myviewholder>

{
    //private FirebaseFirestore db;
    ProgressDialog dialag;

public static String eqal;
public String e;
    private List<Admain_maths_module> mList;
    private Admain_show_all_match_data activity;
    private FirebaseFirestore db;
    //SwitchCompat switchCompat;
    public Admain_maths_adpater(Admain_show_all_match_data activity1,List<Admain_maths_module> mList){
        this.activity=activity1;
        this.mList=mList;

    }


    public void delet(int position){
        Admain_maths_module item=mList.get(position);
        db.collection("MATHS_DATA").document(item.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    data_remove(position);

                }
                else {

                }
            }
        });

    }
    public void data_remove(int position){
        //mList.remove(position);
        //notifyItemRemoved(position);
        //activity.show_maths_data();

    }
public void update(int position){

        Admain_maths_module item=mList.get(position);
    Bundle bundle=new Bundle();
    bundle.putString("title",item.getTitle());
    bundle.putString("maths",item.getMaths());
    Intent intent=new Intent(activity,Admain_macth_text.class);
    intent.putExtras(bundle);
    activity.startActivity(intent);
    activity.notifyAll();
}
public void dilod(){

}
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(activity).inflate(R.layout.admin_maths_idem,parent ,false);
        db=FirebaseFirestore.getInstance();
     return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, @SuppressLint("RecyclerView") int position) {
        FloatingActionButton win_or_not,win,notwin,noidea;
Button edit;
        Admain_maths_module admain_maths_module=mList.get(position);
        holder.title.setText(mList.get(position).getTitle());
        holder.maths.setText(mList.get(position).getMaths());
        holder.win_or_not.findViewById(R.id.win_or_not);
        holder.win.findViewById(R.id.win);
        holder.notwin.findViewById(R.id.notwin);
        holder.noidea.findViewById(R.id.nothing);
        holder.win_or_not_open= AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.win_forword);
       holder.win_or_not_close=AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.win_float_button_close);
        holder.win_or_not_forword=AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.win_forword);
        holder.win_or_not_backword=AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.win_backword);
        holder.edit_macth1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Admain_maths_module item=mList.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("uTitle",item.getTitle());
                bundle.putString("uMacth",item.getMaths());
                bundle.putString("uid",item.getId());
                Intent intent=new Intent(activity,Admain_macth_text.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                //final DialogPlus dialogPlus=DialogPlus.newDialog(edit_macth1.getContext()).setContentHolder(new ViewHolder(R.layout.admin_match_update)).setExpanded(true,950).create();
                //dialogPlus.show();
                //View vie=dialogPlus.getHeaderView();
                //EditText title09=dialogPlus.getHeaderView().findViewById(R.id.update_title1);
                //title09.setText(obj.getTitle());
            }
        });
        holder.delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Are you score!");
                builder.setMessage("Delect idem....");
                builder.setIcon(R.drawable.ic_outline_report_problem);
                builder.setPositiveButton("Delet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delet(2);

                                Admain_maths_module item1=mList.get(position);
                                db.collection("MATHS_DATA").document(item1.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                            Toast.makeText(activity,"Delect sucessfull...",Toast.LENGTH_SHORT).show();
                                            //Intent intent=new Intent(activity,Admain_show_all_match_data.class);
                                           // activity.startActivity(intent);
                                        activity.show_maths_data();

                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity,"Cancle",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

holder.win_or_not.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(holder.isopen){
            holder.win_or_not.startAnimation(holder.win_or_not_forword);
            holder.win.startAnimation(holder.win_or_not_close);
            holder.notwin.startAnimation(holder.win_or_not_close);
            holder.noidea.startAnimation(holder.win_or_not_close);
            holder.win.setClickable(false);
            holder.notwin.setClickable(false);
            holder.noidea.setClickable(false);
            holder.isopen=false;
        }else {
            holder.win_or_not.startAnimation(holder.win_or_not_backword);
            holder.win.startAnimation(holder.win_or_not_open);
            holder.notwin.startAnimation(holder.win_or_not_open);
            holder.noidea.startAnimation(holder.win_or_not_open);
            holder.win.setClickable(true);
            holder.notwin.setClickable(true);
            holder.noidea.setClickable(true);
            holder.isopen=true;

        }

    }
});
holder.win.setOnClickListener(view -> {
    dialag=new ProgressDialog(holder.edit_macth1.getContext());
    dialag.setTitle("Win tha match");
    dialag.setMessage("Please wait");

//dialag.show();

    if(holder.win.isClickable()){
        //Toast.makeText(holder.edit_macth.getContext(), "Win tha match",Toast.LENGTH_SHORT).show();

        db.collection("MATHS_DATA").document(admain_maths_module.getId()).update("title","win tha match");

        holder.win_or_not.startAnimation(holder.win_or_not_forword);
        holder.win.startAnimation(holder.win_or_not_close);
        holder.notwin.startAnimation(holder.win_or_not_close);
        holder.noidea.startAnimation(holder.win_or_not_close);
        holder.win.setClickable(false);
        holder.notwin.setClickable(false);
        holder.noidea.setClickable(false);
        holder.isopen=false;
        //Intent intent=new Intent(activity,Admain_show_all_match_data.class);
        //activity.startActivity(intent);
        activity.show_maths_data();

/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialag.dismiss();
            }
        },1500);         */   //db.collection("MATHS_DATA").document(admain_maths_module.getId()).update("stutes","lose tha match");
    }
    else {

        //Toast.makeText(holder.edit_macth1.getContext(), "Win tha match",Toast.LENGTH_SHORT).show();
        //db.collection("MATHS_DATA").document(admain_maths_module.getId()).update("stutes","lose tha match");
    }
});
holder.notwin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String number= holder.title.getText().toString();
       // Intent intent=new Intent(Intent.ACTION_CALL);
        //intent.setData(Uri.parse("tel:"+number));
        Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+Uri.encode(number)));
        activity.startActivity(intent);


    }
});

        //holder.checkBox.setChecked(update("jp_tech"));

        //holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          //  @Override
            //public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              //  if(b){
//
  //                  db.collection("MATHS_DATA").document(admain_maths_module.getId()).update("stutes","wintha match");
    //                saveinto("jp_tech",b);
      //          }
        //        else{
//
  //                db.collection("MATHS_DATA").document(admain_maths_module.getId()).update("stutes","lose tha match");
   //             }
      //      }
       // });
        //holder.redio1.setText(mList.get(position).getId());
  /*      //holder.redio2.setText(mList.get(position).getId());
holder.redio1.setChecked(update("jp_tech"));

       holder.redio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean a) {
               saveinto("jp_tech",a);
db.collection("MATHS_DATA").document(admain_maths_module.getId()).update("stutes","wintha match");


           }
       });

*/

}

public void call(){
        String call=activity.getTitle().toString();
    //    if(ContextCompat.checkSelfPermission)
}
public void botton(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        AlertDialog alertDialog=builder.create();
        alertDialog.setIcon(R.drawable.ic_outline_report_problem);
        alertDialog.setContentView(R.layout.admin_match_update);
        alertDialog.show();
}

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView title,maths;
//CheckBox checkBox;
//RadioButton redio1,redio2;
        FloatingActionButton win_or_not,win,notwin,noidea;
        Animation win_or_not_open,win_or_not_close,win_or_not_forword,win_or_not_backword;
        Button edit_macth1,delect,addimage;
        EditText title09;
        boolean isopen=false;//dufult false
        public Myviewholder(@NonNull View itemView) {

            super(itemView);
            title=itemView.findViewById(R.id.admain_title_text);
            maths=itemView.findViewById(R.id.admain_maths_text);
            edit_macth1=itemView.findViewById(R.id.edit_match);
            delect=itemView.findViewById(R.id.delet_match);
            addimage=itemView.findViewById(R.id.maths_image);
            win_or_not=itemView.findViewById(R.id.win_or_not);
            win=itemView.findViewById(R.id.win);
            notwin=itemView.findViewById(R.id.notwin);
            noidea=itemView.findViewById(R.id.nothing);
            title09=itemView.findViewById(R.id.update_title1);
            Admain_maths_module obj=new Admain_maths_module();
            edit_macth1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Admain_maths_module item=mList.get(getAdapterPosition());
                    Bundle bundle=new Bundle();
                    bundle.putString("uTitle",item.getTitle());
                    bundle.putString("uMacth",item.getMaths());
                    bundle.putString("uid",item.getId());
                    Intent intent=new Intent(activity,Admain_macth_text.class);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                    //final DialogPlus dialogPlus=DialogPlus.newDialog(edit_macth1.getContext()).setContentHolder(new ViewHolder(R.layout.admin_match_update)).setExpanded(true,950).create();
                    //dialogPlus.show();
                    //View vie=dialogPlus.getHeaderView();
                    //EditText title09=dialogPlus.getHeaderView().findViewById(R.id.update_title1);
                    //title09.setText(obj.getTitle());
                }
            });
            //Admain_maths_module items=mList.get(getAdapterPosition());

            addimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Admain_maths_module item = mList.get(getAdapterPosition());
                        eqal=item.getTitle();
                   // System.out.println(eqal+"frtfgyhujkljhgfewertyuiuytrertyuiuy");
                        Bundle bundle = new Bundle();
                        bundle.putString("uTitle", item.getTitle());
                        Intent intent = new Intent(activity, ChoseImage.class);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);


                }
            });


            //win_or_not_open= AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.win_forword);
            //win_or_not_close=AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.win_float_button_close);
            //win_or_not_forword=AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.win_forword);
            //win_or_not_backword=AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.win_backword);

            //redio1=itemView.findViewById(R.id.one);
          //redio2=itemView.findViewById(R.id.of);
            //checkBox=itemView.findViewById(R.id.chick_win);
           // checkBox.setChecked(update("jp_tech"));
//checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
  //  @Override
    //public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
      //  if(b){
        //    saveinto("jp_tech",b);
       // }
        //else {
            //db.collection("MATHS_DATA").document(admain_maths_module.getId()).update("stutes","wintha match");

        //}

    //}
//});






        }


    }
    public Admain_maths_adpater(){



    }
}





















