package com.example.myproject;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Image_adapter extends RecyclerView.Adapter<Image_adapter.ImageViewHolder> {
    public Context mContext;
    public List<Upload> mUpload;
    private FirebaseFirestore db;
//    private Object Glide;

    public Image_adapter(Context context,List<Upload>uploads){
        mContext=context;
        mUpload=uploads;
    }
    public void downloadurl(Context context, String url){
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request reques=new DownloadManager.Request(uri);
        reques.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
       // reques.setDestinationInExternalFilesDir(context,name,s,url,imageuri);
        downloadManager.enqueue(reques);

    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
Upload uploadc=mUpload.get(position);
holder.TextTitle.setText(uploadc.getName());
Picasso.with(mContext).load(uploadc.getImageuri()).fit().centerCrop().into(holder.imageView);
holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String currenttitle=uploadc.getName();
        String currenturl=uploadc.getImageuri();
        db.collection("Image_upload").document(uploadc.getName()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(mContext, "Success!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

});

holder.button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        downloadurl(Uri.parse(uploadc.getImageuri()),".Img",DIRECTORY_DOWNLOADS);
    }


    private void downloadurl(Uri imageuri, String s, String directoryDownloads) {
        DownloadManager downloadManager=(DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(String.valueOf(imageuri));
        DownloadManager.Request reques=new DownloadManager.Request(uri);
        reques.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // reques.setDestinationInExternalFilesDir(context,name,s,url,imageuri);
        downloadManager.enqueue(reques);
    }
});


    }

    private void imagedelect(int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        Upload upload=new Upload();

        builder.setTitle("Delect image"+upload.getName());
        builder.setMessage("Are you sure delete image");
        Upload uploadc=mUpload.get(position);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Query
                db.collection("Image_upload").document(uploadc.getName()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            data_remove(position);
                        }
                    }


                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    public void data_remove(int position){
        mUpload.remove(position);
        notifyItemRemoved(position);
        //activity.show_maths_data();

    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
public TextView TextTitle;
public ImageView imageView;
Button button,delete;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            TextTitle=itemView.findViewById(R.id.imagetitle);
            imageView=itemView.findViewById(R.id.image_views);
            button=itemView.findViewById(R.id.down);
            delete=itemView.findViewById(R.id.deleteimage);
        }
    }
}
