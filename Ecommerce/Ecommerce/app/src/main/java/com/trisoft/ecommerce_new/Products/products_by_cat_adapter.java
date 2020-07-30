package com.trisoft.ecommerce_new.Products;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.Utils.Dialogs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class products_by_cat_adapter extends RecyclerView.Adapter<products_by_cat_adapter.MyViewHolder> {

    List<items_list_model> itemList = new ArrayList<>();
    Context context;

    Dialogs dialogs;

    public products_by_cat_adapter(List<items_list_model> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public products_by_cat_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new products_by_cat_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull products_by_cat_adapter.MyViewHolder holder, int position) {
//        holder.tv_offer_value.setText(itemList.get(position).getDiscount_value()+"%");
//        holder.tv_product_name.setText(itemList.get(position).getName());
//        holder.tv_product_short_desc.setText(itemList.get(position).getShortDescription());
//        holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getPrice());
//        holder.tv_discounted_price.setText("Our Price : \u20B9" + itemList.get(position).getSavePrice());
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

        holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotsDialog spotsDialog = new SpotsDialog(context);
                spotsDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"cart_add",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    spotsDialog.dismiss();

                                    Log.e("response","response");
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");

                                    if (status.equalsIgnoreCase("0")) {

                                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                                        sweetAlertDialog.setTitleText("Add to cart")
                                                .setContentText(object.getString("message"))
                                                .setConfirmText("Ok")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismiss();
                                                    }
                                                }).setCancelable(false);
                                        sweetAlertDialog.show();

                                    }
                                    else {

                                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                        sweetAlertDialog.setTitleText("Add to cart")
                                                .setContentText(object.getString("Product added to cart successfully"))
                                                .setConfirmText("Ok")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismiss();


                                                    }
                                                }).setCancelable(false);
                                        sweetAlertDialog.show();


                                    }
                                } catch (JSONException e) {

                                    spotsDialog.dismiss();
                                    Log.e("JSONException",e.toString());
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                spotsDialog.dismiss();

                                if (error instanceof com.android.volley.NetworkError) {

                                } else if (error instanceof ServerError) {

                                } else if (error instanceof AuthFailureError) {

                                } else if (error instanceof ParseError) {

                                } else if (error instanceof NoConnectionError) {

                                } else if (error instanceof TimeoutError) {

                                }
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<String, String>();
//                        map.put("product_id",itemList.get(position).getId());
//                        map.put("quantity",holder.et_qty.getText().toString().replace("Qty:",""));
//                        map.put("user_id",context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));

                        return map;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);

            }
        });


        holder.et_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogs = new Dialogs(context);

                List<String> qty =new ArrayList<>();
                qty.add("1");
                qty.add("2");
                qty.add("3");
                qty.add("4");
                qty.add("5");
                qty.add("6");
                qty.add("7");
                qty.add("8");
                qty.add("9");
                qty.add("10");

                String selectedposition="0";

                dialogs.singleSelectDialog(qty, holder.et_qty, "Select Quantity", selectedposition);
                String quantity = holder.et_qty.getText().toString().replace("Qty:","");

            }
        });


    }

    @Override
    public int getItemCount() {

        return itemList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_offer_value, tv_product_name, tv_product_short_desc, tv_price, tv_discounted_price,tv_add_to_cart;
        LinearLayout ll_product;
        ImageView iv_product;
        EditText et_qty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            et_qty = itemView.findViewById(R.id.et_qty);
            tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);

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
