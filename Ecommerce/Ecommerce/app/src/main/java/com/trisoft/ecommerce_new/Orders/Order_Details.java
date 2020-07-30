package com.trisoft.ecommerce_new.Orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.trisoft.ecommerce_new.Intro.Login;
import com.trisoft.ecommerce_new.R;

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


public class Order_Details extends AppCompatActivity {

    RecyclerView recyclerView_Product;
    Button btn_cancel_order;
    int total=0;
    SpotsDialog spotsDialog;

    TextView tv_name,tv_mobile,tv_email,tv_address,tv_date,tv_shippingcarge,tv_newPayble,tv_orderTotal,tv_status,tv_time_slote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details);
        getSupportActionBar().setTitle("Order Id-"+getIntent().getStringExtra("orderId")+"'s Detail");

        spotsDialog=new SpotsDialog(this);
        spotsDialog.setTitle("Cancel Order");
        spotsDialog.setCancelable(false);
        tv_name=(TextView)findViewById(R.id.tv_Name);
        tv_mobile=(TextView)findViewById(R.id.tv_mobile);
        tv_email=(TextView)findViewById(R.id.tv_email);

        btn_cancel_order=findViewById(R.id.btn_cancel_order);
        tv_address=(TextView)findViewById(R.id.tv_shipping_toAddress);

        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_newPayble=(TextView)findViewById(R.id.tv_netPayble);
        tv_orderTotal=(TextView)findViewById(R.id.tv_orderTotal);
        tv_status=findViewById(R.id.tv_status);
        tv_time_slote=findViewById(R.id.tv_time_slote);

        recyclerView_Product = (RecyclerView)findViewById(R.id.recycler_Products);
        recyclerView_Product.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView_Product.setLayoutManager(linearLayoutManager);


//        ArrayList<order_list.Product> myList = (ArrayList<order_list.Product>) getIntent().getSerializableExtra("mylist");


//        SpotsDialog dialog = new SpotsDialog(Order_Details.this);
//        dialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "orderproducts",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject object = new JSONObject(response);
//                            String status = object.getString("status");
//
//                            dialog.dismiss();
//
//                            if (status.equalsIgnoreCase("0")) {
//
//
//                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Order_Details.this, SweetAlertDialog.WARNING_TYPE);
//                                sweetAlertDialog.setTitleText("Search")
//                                        .setContentText("No Item found")
//                                        .setConfirmText("Ok")
//                                        .setConfirmClickListener(sweetAlertDialog1 -> {
//                                            sweetAlertDialog.dismiss();
//                                            finish();
//                                        }).show();
//
//                                sweetAlertDialog.setCancelable(false);
//                            } else {
//
//                                Gson gson = new Gson();
//
////                                JSONArray array = object.getJSONArray("Data");
////                                List<order_list.Product> noticesList = new ArrayList<>();
//
//                                noticesList.clear();
//
//                                for (int i = 0; i < array.length(); i++) {
//
//                                    order_list.Product noticeList = gson.fromJson(array.getJSONObject(i).toString(), order_list.Product.class);
//                                    noticesList.add(noticeList);
//                                }
//
//
//                                Products_Adapter productsAdapter = new Products_Adapter(Order_Details.this,noticesList);
//                                recyclerView_Product.setAdapter(productsAdapter);
//
//                            }
//                        } catch (JSONException e) {
//                            dialog.dismiss();
//
//                            Log.e("JSONException", e.toString());
//                            e.printStackTrace();
//                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        dialog.dismiss();
//
//                        if (error instanceof com.android.volley.NetworkError) {
//
//                        } else if (error instanceof ServerError) {
//
//                        } else if (error instanceof AuthFailureError) {
//
//                        } else if (error instanceof ParseError) {
//
//                        } else if (error instanceof NoConnectionError) {
//
//                        } else if (error instanceof TimeoutError) {
//
//                        }
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("order_id",getIntent().getStringExtra("orderId"));
//                return map;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                60000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        stringRequest.setShouldCache(false);
//        requestQueue.add(stringRequest);
//
//        if(getIntent().getStringExtra("status").equals("Cancel") || getIntent().getStringExtra("status").equals("Delivered")  )
//        {
//            btn_cancel_order.setVisibility(View.GONE);
//        }
//
//        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Order_Details.this, SweetAlertDialog.WARNING_TYPE);
//                sweetAlertDialog.setTitleText("Cancel Order")
//                        .setContentText("Are you sure to Cancel\n Order?")
//                        .setConfirmText("Yes")
//                        .setCancelText("No").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//
//                    }
//                }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                        sweetAlertDialog.dismiss();
//                        SpotsDialog spotsDialog = new SpotsDialog(Order_Details.this);
//                        spotsDialog.show();
//
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"order_cancel",
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        try {
//
//                                            spotsDialog.dismiss();
//
//                                            JSONObject object = new JSONObject(response);
//                                            String status = object.getString("status");
//
//                                            if (status.equalsIgnoreCase("0")) {
//
//                                                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Order_Details.this, SweetAlertDialog.ERROR_TYPE);
//                                                sweetAlertDialog.setTitleText("Cancel Order")
//                                                        .setContentText(object.getString("message"))
//                                                        .setConfirmText("Ok")
//                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                                            @Override
//                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                                sweetAlertDialog.dismiss();
//                                                            }
//                                                        }).setCancelable(false);
//                                                sweetAlertDialog.show();
//
//                                            }
//                                            else {
//
//
//                                                JSONObject UserData = object.getJSONObject("data");
//
//                                                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Order_Details.this, SweetAlertDialog.SUCCESS_TYPE);
//                                                sweetAlertDialog.setTitleText("Cancel Order")
//                                                        .setContentText(object.getString("message"))
//                                                        .setConfirmText("Ok")
//                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                                            @Override
//                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                                sweetAlertDialog.dismiss();
//
//                                                                startActivity(new Intent(getApplicationContext(), Login.class));
//                                                                finish();
//                                                            }
//                                                        }).setCancelable(false);
//
//                                                sweetAlertDialog.show();
//
//
//                                            }
//                                        } catch (JSONException e) {
//
//                                            spotsDialog.dismiss();
//                                            Log.e("JSONException",e.toString());
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                },
//
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//
//                                        spotsDialog.dismiss();
//
//                                        if (error instanceof com.android.volley.NetworkError) {
//
//                                        } else if (error instanceof ServerError) {
//
//                                        } else if (error instanceof AuthFailureError) {
//
//                                        } else if (error instanceof ParseError) {
//
//                                        } else if (error instanceof NoConnectionError) {
//
//                                        } else if (error instanceof TimeoutError) {
//
//                                        }
//                                    }
//                                })
//                        {
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//
//                                Map<String, String> map = new HashMap<String, String>();
//
//                                map.put("order_id",getIntent().getStringExtra("orderId"));
//
//                                return map;
//                            }
//                        };
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                                60000,
//                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                        stringRequest.setShouldCache(false);
//                        requestQueue.add(stringRequest);
//
//                    }
//                }).setCancelable(false);
//                sweetAlertDialog.show();
//            }
//        });
//
//        Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE");
//
//
//        tv_newPayble.setText("\u20B9"+intent.getStringExtra("total"));
//        tv_name.setText(intent.getStringExtra("name"));
//        tv_mobile.setText(intent.getStringExtra("mobile"));
//        tv_email.setText(intent.getStringExtra("Email"));
//        tv_date.setText(intent.getStringExtra("date"));
//        tv_address.setText(intent.getStringExtra("address"));
//        tv_status.setText(intent.getStringExtra("status"));
//        tv_time_slote.setText(intent.getStringExtra("time_slot"));
    }



    }
//}
