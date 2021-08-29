package com.example.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyRecyclerViewAdapter extends  RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder>{

    private List<Getterforview> list;
    private Context context;
    private OnInventoryListener mOnInventoryListener;

    public MyRecyclerViewAdapter(List<Getterforview> list, Context context,OnInventoryListener onInventoryListener) {
        this.list = list;
        this.context = context;
        this.mOnInventoryListener=onInventoryListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyHolder(v,mOnInventoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.MyHolder holder, int position) {

        holder.itempid.setText(list.get(position).getId()+"");
        holder.itempname.setText(list.get(position).getProductName()+"");
        holder.itempamount.setText(list.get(position).getProductAmount()+"");
        holder.itempdoe.setText(list.get(position).getProductDoe()+"");
     Glide.with(holder.itemimage.getContext())
             .load(list.get(position).getProductImageUrl())
             .placeholder(R.drawable.common_google_signin_btn_icon_dark)
             .error(R.drawable.common_google_signin_btn_icon_dark_normal)
             .into(holder.itemimage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itempid,itempname,itempamount,itempdoe;
        private ImageView itemimage;
        OnInventoryListener onInventoryListener;
        public MyHolder(@NonNull View itemView,OnInventoryListener onInventoryListener) {
            super(itemView);

            itempid=itemView.findViewById(R.id.itempid);
            itempname=itemView.findViewById(R.id.itempname);
            itempamount=itemView.findViewById(R.id.itempamount);
            itempdoe=itemView.findViewById(R.id.itempdoe);
            itemimage=itemView.findViewById(R.id.itemimage);
            this.onInventoryListener=onInventoryListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onInventoryListener.onInventoryClick(getAdapterPosition());

        }
    }

    public interface OnInventoryListener{
        void onInventoryClick(int position);
    }
}
