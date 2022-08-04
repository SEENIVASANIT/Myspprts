package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustemAdapter extends RecyclerView.Adapter<Viewholder>{
    Admain_Show_maths admain_show_maths;
    List<Model> modelList;
    Context context;

    public CustemAdapter(Admain_Show_maths admain_show_maths, List<Model> modelList, Context context) {
        this.admain_show_maths = admain_show_maths;
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.module_layout,viewGroup,false);
        Viewholder viewholder=new Viewholder(itemView);
        viewholder.setOnclickList(new Viewholder.ClickLister() {
            @Override
            public void onItemchick(View view, int position) {

                //17.10
            }

            @Override
            public void onItemlongclick(View view, int position) {

            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
viewholder.title_tv.setText(modelList.get(position).getShow_title());
        viewholder.data_tv.setText(modelList.get(position).show_maths);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}

