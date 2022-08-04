package com.example.myproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholder extends RecyclerView.ViewHolder {
    TextView title_tv,data_tv;
    View oview;
    public Viewholder(@NonNull View itemView) {
        super(itemView);
        oview=itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListter.onItemchick(view,getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListter.onItemlongclick(view,getAdapterPosition());
                return true;
            }
        });
        //title_tv=itemView.findViewById(R.id.show_title);
        //data_tv=itemView.findViewById(R.id.show_data);
    }
    private Viewholder.ClickLister mClickListter;
    public interface ClickLister{
        void onItemchick(View view,int position);
        void onItemlongclick(View view,int position);
    }
    public void setOnclickList(Viewholder.ClickLister clickLister){
        mClickListter=clickLister;
    }
}
