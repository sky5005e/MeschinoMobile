package com.trisoft.ecommerce_new.Products;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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
import com.trisoft.ecommerce_new.R;

import java.util.ArrayList;
import java.util.List;

public class items_adapter extends RecyclerView.Adapter<items_adapter.MyViewHolder> {

    List<items_list_model> itemList = new ArrayList<>();
    Context context;

    public items_adapter(List<items_list_model> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public items_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new items_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull items_adapter.MyViewHolder holder, int position) {

//        holder.tv_product_name.setText(itemList.get(position).getName());
//        holder.tv_product_short_desc.setText(itemList.get(position).getShortDescription());
//        holder.tv_price.setText("MRP : \u20B9"+itemList.get(position).getPrice());
//        holder.tv_discounted_price.setText("Our Price : \u20B9"+itemList.get(position).getSavePrice());
//        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//
//        Glide.with(context)
//                .load(itemList.get(position).getImage())
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.iv_product);

        holder.ll_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences sp = context.getSharedPreferences("Subcategories_detail", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("sub_cat_id", itemList.get(position).getId());
//                editor.putString("sub_cat_name", itemList.get(position).getName());
//                editor.commit();
//                editor.apply();
//
//                Intent in = new Intent(context, Child_subcategories.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {

        return itemList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_offer_value, tv_product_name, tv_product_short_desc,tv_price,tv_discounted_price;
        LinearLayout ll_product;
        ImageView iv_product;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_offer_value = itemView.findViewById(R.id.tv_offer_value);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_short_desc = itemView.findViewById(R.id.tv_product_short_desc);
            tv_price = itemView.findViewById(R.id.tv_price);

            tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
            ll_product = itemView.findViewById(R.id.ll_product);
            iv_product = itemView.findViewById(R.id.iv_product);

        }
    }
}

