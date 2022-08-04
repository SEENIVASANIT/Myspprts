package com.example.myproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewmatchs extends BottomSheetDialogFragment {
    public static final String Tag="AddNewTask";
    private TextView textView;
    private EditText editText;
    private Button save;
    private FirebaseFirestore firestore;
    private Context context;
    private String dudate;
    public static AddNewmatchs newIntence(){
        return new AddNewmatchs();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_add_maths,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView=view.findViewById(R.id.date);
        editText=view.findViewById(R.id.maths_titles);
        save=view.findViewById(R.id.button);
        firestore=FirebaseFirestore.getInstance();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
if(charSequence.toString().equals("")){
    save.setEnabled(false);
    save.setBackgroundColor(Color.GRAY);
}else {
    save.setEnabled(true);
    save.setBackgroundColor(getResources().getColor(R.color.black));

}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int MONTH=calendar.get(Calendar.MONTH);
                int YEAR=calendar.get(Calendar.YEAR);
                int DAY=calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        textView.setText(i2+"/"+i1+"/"+i);
                        dudate=i2+"/"+i1+"/"+i;
                    }
                },YEAR,MONTH,DAY);
                datePickerDialog.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String task =editText.getText().toString();
                if(task.isEmpty()){
                    Toast.makeText(context,"Empti not allow",Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String,Object> taskmap=new HashMap<>();
                    taskmap.put("Title",task);
                    taskmap.put("date",dudate);
                    taskmap.put("stutes",0);
                    firestore.collection("maths").add(taskmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context,"addd data",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context,"Erroe",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"addd data",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof ondilage){
            ((ondilage) activity).ondilage(dialog);
        }
    }
}
