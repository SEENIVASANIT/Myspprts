package com.example.myproject;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewholder>{
    public ArrayList<Module_image> mlist;
private Context context;
    private FirebaseFirestore db;
    FirebaseStorage storageReference;
    public ShowActivity_image_viws activity;
public Myadapter(Context context,ArrayList<Module_image> mlist){

    this.context=context;
    this.mlist=mlist;

}
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemi_mage,parent,false);
        db=FirebaseFirestore.getInstance();
        return new MyViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, @SuppressLint("RecyclerView") int position) {
    Module_image module_image=mlist.get(position);
    holder.TextTitle.setText(module_image.getTitle());
    Picasso.with(context).load(mlist.get(position).getImageuri()).fit().centerCrop().into(holder.imageView);
    holder.delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder=new AlertDialog.Builder(holder.TextTitle.getContext());
            builder.setTitle("Are you score!");
            builder.setMessage("Delect idem....");
            builder.setIcon(R.drawable.ic_outline_report_problem);
            System.err.println(module_image.getImageuri());
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Module_image module_image=mlist.get(position);


                    db.collection("Image_upload").document(module_image.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                               // mlist.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context, "Success!!!!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(context,ShowActivity_image_viws.class);

                                context.startActivity(intent);
                                //activity.show_Image_fierbase();
                            }
                            //StorageReference storageref= storageReference.getRoot();
                            storageReference= FirebaseStorage.getInstance();

                            StorageReference delectstorage=storageReference.getReferenceFromUrl(module_image.imageuri);
                            delectstorage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {




                                    Toast.makeText(context, "image deloct", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                        }
                    });
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(context, "cancle", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();


        }
    });
    holder.downlad.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String []permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                }else {
                    String geturl=module_image.getImageuri().toString();
                    DownloadManager.Request request=new DownloadManager.Request(Uri.parse(geturl));
                    //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,module_image.title);
                    String title= URLUtil.guessFileName(geturl,null,null);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.allowScanningByMediaScanner();
                    request.setTitle(module_image.getTitle()+" DOEMLOAD SECCESFULL!");
                    request.setDescription("DOWNLOADING....");
                    String cookie = CookieManager.getInstance().getCookie(geturl);
                    request.addRequestHeader("cookie",cookie);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
                    DownloadManager downloadManager=(DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                    Toast.makeText(context, "DOWNLOAD SUCCESSFULL!", Toast.LENGTH_SHORT).show();
                    //System.err.println(module_image.getImageuri()+"Download url..........");
                }
            }else {
                //downloadimage(holder.TextTitle.getContext(), module_image.getTitle(),"png",DIRECTORY_DOWNLOADS,module_image.getImageuri());
            }



            //System.err.println(module_image.getImageuri()+"Download url..........");
        }



    });

    }

    public void downloadimage(Context context,String filename,String fileExrension,String destionDirectory,String url){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        String title= URLUtil.guessFileName(filename,null,null);
        request.setDescription("DOWNLOADING....");
        String cookie = CookieManager.getInstance().getCookie(url);
        request.addRequestHeader("cookie",cookie);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
      //  request.setDestinationInExternalFilesDir(context,destionDirectory,filename+fileExrension);
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
        Toast.makeText(context, "DOWNLOADE cOMPLicte", Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public static class MyViewholder extends RecyclerView.ViewHolder {
ImageView imageView;
TextView downlad,delete;

        public TextView TextTitle;
        public List<Module_image> mlist;
        ShowActivity_image_viws activity;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            TextTitle=itemView.findViewById(R.id.image_title);
            imageView=itemView.findViewById(R.id.show_image_views);
            downlad=itemView.findViewById(R.id.downlods);
            delete=itemView.findViewById(R.id.deleteimage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Module_image module_image=mlist.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
  //                  System.err.println(module_image.getImageuri()+"sadsfxcvxcvxcvcvvcvcxvcxvcvcvvvvbvcbvbvcb");
    //                bundle.putString("url",module_image.getImageuri());
                    Intent intent = new Intent(activity, Image_tech_view.class);
      //              intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });

        }
    }
}
