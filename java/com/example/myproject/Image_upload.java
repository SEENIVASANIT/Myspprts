package com.example.myproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class Image_upload extends AppCompatActivity {
private static final int PICK_IMAGE_REQUEST =1;

    Button imagechose,imageuploade,imageshow;
ImageView image;
EditText uploadTitle;
public Uri imageuri;
Uri download;
public StorageTask mStorage;
    private StorageReference storageReference;
private DatabaseReference databaseReference;
    private FirebaseFirestore db;

    ProgressDialog dialag;
    ActivityResultLauncher<Intent>mtphoto=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                private Object Context;

                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    int result=activityResult.getResultCode();
                    Intent data=activityResult.getData();
                    if(result==RESULT_OK && data!=null &&data.getData()!=null){
                        imageuri=data.getData();
                        //Picasso.with().load(imageuri).into(image);


                        image.setImageURI(imageuri);
                    }

                }
            }
    );
    private StorageReference taskSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        dialag=new ProgressDialog(this);
        imagechose=findViewById(R.id.upload_image);
        imageuploade=findViewById(R.id.uploadfierbase);
        imageshow=findViewById(R.id.showimage);
        image=findViewById(R.id.imagehold);
        uploadTitle=findViewById(R.id.uploadtitle);
        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        db =FirebaseFirestore.getInstance();
        imagechose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChose();
            }
        });
        imageuploade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtofirebase();

            }
        });
        imageshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opeanimageactivity();
            }
        });

    }
    public void openFileChose(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
       // mtphoto.launch("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        mtphoto.launch(intent);

    }

   // @Override
    //protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null){
          //  imageuri=data.getData();
           //Picasso.with(this).load(imageuri).into(image);

           //image.setImageURI(imageuri);
        //}

    //}
    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    public void uploadtofirebase(){
        dialag.setTitle("Upload Image...");
        dialag.setMessage("Place wait!");

        if(imageuri!=null){
            dialag.show();
            Task<Uri> downloadUrl= taskSnapshot.getDownloadUrl();
            StorageReference fileReference=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageuri));
            if ((mStorage = fileReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Upload upload = new Upload(uploadTitle.getText().toString().trim(), downloadUrl.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            })) instanceof StreamDownloadTask) {
                mStorage = fileReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Upload upload = new Upload(uploadTitle.getText().toString().trim(), imageuri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }





            fileReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    String Down=downloadUrl.toString();


                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {

                            dialag.dismiss();
                            Upload upload=new Upload(uploadTitle.getText().toString().trim(),imageuri.toString());
                            String uploadid=databaseReference.push().getKey();
                            String id= UUID.randomUUID().toString();
                            imageuploade.setEnabled(true);

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("name",uploadTitle.getText().toString());
map.put("download",Down);
                           map.put("uri",imageuri.toString());
                            //map.put("name","vasan");
                            String uploadid1=db.toString();

                            db.collection("upload").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Image_upload.this, "Upload", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Image_upload.this, "not upload", Toast.LENGTH_LONG).show();
                                }
                            });
                                ;


                            databaseReference.child(uploadid).setValue(upload);
                            Toast.makeText(Image_upload.this, "Upload Successful...", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Image_upload.this, "upload Error", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this,"choseimage",Toast.LENGTH_SHORT).show();
        }
    }
    public void opeanimageactivity(){
        Intent intent =new Intent(this,Image_show_Activity.class);
        startActivity(intent);
    }
}