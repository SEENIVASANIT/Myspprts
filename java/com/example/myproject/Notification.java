package com.example.myproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        EditText title=findViewById(R.id.message_title);
        EditText message=findViewById(R.id.message_message);
        Button send_message=findViewById(R.id.sending);

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().isEmpty() && !message.getText().toString().isEmpty()){

                    FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all",title.getText().toString(),message.getText().toString(),getApplicationContext(),Notification.this);
                    notificationsSender.SendNotifications();
                    Toast.makeText(Notification.this, "Notification send successfull!", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(Notification.this, "Please Enter Message", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}