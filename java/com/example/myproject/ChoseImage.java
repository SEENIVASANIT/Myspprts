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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

public class ChoseImage extends AppCompatActivity {
Button uploadbtn,showallbtn,chosebtn;
ImageView imageView;
    EditText uploadTitle;
    TextView sametitle;
private Uri imageUri;
    private FirebaseFirestore db;
private DatabaseReference root= FirebaseDatabase.getInstance().getReference("Image");
StorageReference reference= FirebaseStorage.getInstance().getReference();
    ProgressDialog dialag;
String sametitLe=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_image);
        dialag=new ProgressDialog(this);

        db =FirebaseFirestore.getInstance();
        uploadbtn=findViewById(R.id.upload_to_fierbase);
        showallbtn=findViewById(R.id.show_image_recycle);
        chosebtn=findViewById(R.id.chose_dutton_image);
        imageView=findViewById(R.id.imageView_holder);
        uploadTitle=findViewById(R.id.upload_ImageTitle);
sametitle=findViewById(R.id.sametitle);
        Bundle bundle=getIntent().getExtras();
        if(bundle !=null){

            sametitLe=bundle.getString("uTitle");

            sametitle.setText(sametitLe);


        }else {
            sametitle.setText("NO Title");
        }

        chosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChose1();
               
            }

            
        });

        showallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("sametitle",sametitLe.toString());

                Intent intent=new Intent(ChoseImage.this,ShowActivity_image_viws.class);
                intent.putExtras(bundle);
               startActivity(intent);
               // startActivity(new Intent(ChoseImage.this,ShowActivity_image_viws.class));
            }
        });
        String finalSametitLe = sametitLe;
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri !=null){

                    uploadtofierdaseimage(imageUri);

                }else
                {
                    Toast.makeText(ChoseImage.this, "Plase select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openFileChose1(){
        Intent galleryIntent =new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        // mtphoto.launch("image/*");
        startActivityForResult(galleryIntent, 2);
        

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK && data !=null){

          imageUri=data.getData();
            //imageView.setImageURI(imageUri);
            Picasso.with(this).load(imageUri).into(imageView);


        }
    }

    private void uploadtofierdaseimage(Uri uri) {
       // ProgressDialog pd=new ProgressDialog(this);
        //pd.setTitle("upload Image");
        //pd.show();
        dialag.setTitle("Upload Image...");

        if (imageUri != null && uploadTitle !=null && (imageUri !=null || uploadTitle!=null)) {
            //System.err.println(uploadTitle);
            dialag.show();
        //    pd.dismiss();



            StorageReference fileRef = reference.child( System.currentTimeMillis()+ "." + getFileExtension(imageUri));
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
              //              ChoseImage choseImage=new ChoseImage();

                //            Module_image modul = new Module_image(uri.toString(),uploadTitle.getText().toString());
                            String id= UUID.randomUUID().toString();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("Image_id",id);
                            map.put("Image_Title",uploadTitle.getText().toString());
                            map.put("Image_url",uri.toString());
                            map.put("equaltitle",sametitLe);
                            //System.err.println(sametitle);
                            db.collection("Image_upload").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    dialag.dismiss();
                                    Toast.makeText(ChoseImage.this, "Upload", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChoseImage.this, "Error", Toast.LENGTH_LONG).show();
                                }
                            });

                           // String modelid = root.push().getKey();
                            //root.child(modelid).setValue(modul);
                            //dialag.dismiss();
                            //Toast.makeText(ChoseImage.this, "Upload success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ChoseImage.this, "Uplod fail", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    float a=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();


                    dialag.setMessage("Place wait!"+(int)a+"%");



                }
            });
        }else{
            Toast.makeText(this,"choseimage",Toast.LENGTH_SHORT).show();
        }
    }


    private String getFileExtension(Uri muri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }


}