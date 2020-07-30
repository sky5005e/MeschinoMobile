package com.trisoft.ecommerce_new.Brands;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.trisoft.ecommerce_new.Category.Child_subcategories;
import com.trisoft.ecommerce_new.Category.Subcategories;
import com.trisoft.ecommerce_new.Category.sub_cat_list;
import com.trisoft.ecommerce_new.R;

import java.util.ArrayList;
import java.util.List;

public class brands_list_adapter {

//    List<sub_cat_list> candidateList = new ArrayList<>();
//    Context context;
//
//    public brands_list_adapter(List<sub_cat_list> candidateList, Context context) {
//        this.candidateList = candidateList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public brands_list_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        return new brands_list_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.brands_layout, parent, false));
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull brands_list_adapter.MyViewHolder holder, int position) {
//
//        holder.tv_brand_name.setText(candidateList.get(position).getName());
//
//        Glide.with(context)
//                .load(candidateList.get(position).getImage())
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.iv_brand);
//
//        holder.ll_brand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SharedPreferences sp = context.getSharedPreferences("Subcategories_detail", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("sub_cat_id", candidateList.get(position).getId());
//                editor.putString("sub_cat_name", candidateList.get(position).getName());
//                editor.commit();
//                editor.apply();
//
//                Intent in = new Intent(context, Child_subcategories.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(in);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return candidateList.size();
//
//    }
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        TextView tv_brand_name;
//        LinearLayout ll_brand;
//        ImageView iv_brand;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            tv_brand_name = itemView.findViewById(R.id.tv_brand_name);
//            ll_brand = itemView.findViewById(R.id.ll_brand);
//            iv_brand = itemView.findViewById(R.id.iv_brand);
//
//        }
//    }
}

