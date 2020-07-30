package com.trisoft.ecommerce_new.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.trisoft.ecommerce_new.Cart.MyCart;
import com.trisoft.ecommerce_new.Intro.Login;
import com.trisoft.ecommerce_new.Products.items_list_model;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.Utils.Dialogs;
import com.trisoft.ecommerce_new.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class Search_product extends AppCompatActivity {

    EditText et_search;
    ImageView iv_search;
    RecyclerView rv_search_item;
    TextView tv_item_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        getSupportActionBar().hide();

        et_search = findViewById(R.id.et_search);
        iv_search = findViewById(R.id.iv_search);

        RelativeLayout rl_cart = findViewById(R.id.rl_cart);
        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MyCart.class));

            }
        });


        SharedPreferences sp = getSharedPreferences("cart_items",Context.MODE_PRIVATE);
        tv_item_counter = findViewById(R.id.tv_item_counter);

        if(new SessionManager(getApplicationContext()).isLoggedIn()) {

            tv_item_counter.setText(sp.getString("cartitemcount", "0"));

        }else{

            tv_item_counter.setText("0");

        }

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String keyword =et_search.getText().toString();

                if(keyword.isEmpty()){

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Search_product.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Search")
                            .setContentText("Type something to search")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(sweetAlertDialog1 -> {
                                sweetAlertDialog.dismiss();

                                finish();

                            }).show();

                    sweetAlertDialog.setCancelable(false);

                }else{

                    search_product(keyword);

                }

            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String keyword =et_search.getText().toString();

                    if(keyword.isEmpty()){

                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Search_product.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Search")
                                .setContentText("Type something to search")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(sweetAlertDialog1 -> {
                                    sweetAlertDialog.dismiss();
                                    finish();
                                }).show();

                        sweetAlertDialog.setCancelable(false);

                    }else{

                        search_product(keyword);

                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void search_product(String keyword) {

        SpotsDialog dialog = new SpotsDialog(Search_product.this);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "product_search",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            dialog.dismiss();

                            if (status.equalsIgnoreCase("0")) {


                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Search_product.this, SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText("Search")
                                        .setContentText("No Item found")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(sweetAlertDialog1 -> {
                                            sweetAlertDialog.dismiss();
                                            finish();
                                        }).show();

                                sweetAlertDialog.setCancelable(false);
                            } else {

                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<items_list_model> noticesList = new ArrayList<>();

                                noticesList.clear();

                                for (int i = 0; i < array.length(); i++) {

                                    items_list_model noticeList = gson.fromJson(array.getJSONObject(i).toString(), items_list_model.class);
                                    noticesList.add(noticeList);
                                }

                                Search_adapter completePackageAdapter = new Search_adapter(noticesList, Search_product.this);
                                rv_search_item = findViewById(R.id.rv_search_item);
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                rv_search_item.setLayoutManager(mLayoutManager);
                                rv_search_item.setAdapter(completePackageAdapter);

                            }
                        } catch (JSONException e) {
                            dialog.dismiss();

                            Log.e("JSONException", e.toString());
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.dismiss();

                        if (error instanceof com.android.volley.NetworkError) {

                        } else if (error instanceof ServerError) {

                        } else if (error instanceof AuthFailureError) {

                        } else if (error instanceof ParseError) {

                        } else if (error instanceof NoConnectionError) {

                        } else if (error instanceof TimeoutError) {

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("keyword",keyword);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }


    public class Search_adapter extends RecyclerView.Adapter<Search_adapter.MyViewHolder> {

        List<items_list_model> itemList = new ArrayList<>();
        Context context;

        Dialogs dialogs;

        public Search_adapter(List<items_list_model> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }

        @NonNull
        @Override
        public Search_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new Search_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull Search_adapter.MyViewHolder holder, int position) {

            holder.tv_offer_value.setVisibility(View.GONE);

            holder.tv_product_name.setText(itemList.get(position).getBrand());
            holder.tv_product_short_desc.setText(itemList.get(position).getProductType()+" - "+itemList.get(position).getProdName()+"\n"+itemList.get(position).getUnit());
            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getRetailPrice());
            holder.tv_discounted_price.setVisibility(View.GONE);
//            holder.tv_discounted_price.setText("Our Price : \u20B9" + itemList.get(position).getSavePrice());
//            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(itemList.get(position).getImages().size()>0) {


                Glide.with(context)
                        .load(itemList.get(position).getImages().get(0).getImagePath())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.pepsi_can)
                        .into(holder.iv_product);
            }


            holder.iv_product.setVisibility(View.VISIBLE);

            holder.ll_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (new SessionManager(getApplicationContext()).isLoggedIn()) {


                        SpotsDialog spotsDialog = new SpotsDialog(context);
                        spotsDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "add_cart",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            spotsDialog.dismiss();
                                            Log.e("response", "response");
                                            JSONObject object = new JSONObject(response);
                                            String status = object.getString("status");

                                            if (status.equalsIgnoreCase("0")) {

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
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

                                            } else {

                                                tv_item_counter.setText(String.valueOf(Integer.parseInt(tv_item_counter.getText().toString()) + 1));

                                                SharedPreferences sp = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putString("cartitemcount", tv_item_counter.getText().toString());
                                                editor.apply();

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                                sweetAlertDialog.setTitleText("Add to cart")
                                                        .setContentText("Item added to cart successfully")
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
                                            Log.e("JSONException", e.toString());
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
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("Prod_id", String.valueOf(itemList.get(position).getProdID()));
                                map.put("Qty", holder.et_qty.getText().toString().replace("Qty:", ""));
                                map.put("Loginid", context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id", ""));
                                map.put("Rate",itemList.get(position).getRetailPrice());
                                map.put("Total",String.valueOf(Double.parseDouble(holder.et_qty.getText().toString().replace("Qty:", ""))*Double.parseDouble(itemList.get(position).getRetailPrice())));
                                Log.e("Params_add_to_cart", "" + map);
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


                    } else {

                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Search_product.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Login")
                                .setContentText("To Add product in cart,you must Login")
                                .setConfirmText("Login")
                                .setCancelText("Cancel")
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        sweetAlertDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), Login.class));

                                    }
                                }).setCancelable(false);
                        sweetAlertDialog.show();
                    }
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
    }
