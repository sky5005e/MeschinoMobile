package com.trisoft.ecommerce_new.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.razorpay.PaymentResultListener;
import com.trisoft.ecommerce_new.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class Checkout extends AppCompatActivity implements PaymentResultListener{

    RadioGroup radioGroup_payment;
    RadioButton radioButton_cod, radioButton_online;
    SharedPreferences sharedPreferences;
    Button button_placeorder;
    RecyclerView recycerView_timeSlot;
    SpotsDialog progressDialog;
    TextView textView_name, textView_mobile, textView_address, textView_qty, textView_orderAmount, textView_totalPayble;
    String name="",address="",societyid="",pincode="", email = "", mobile = "",altr_mob="", totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        progressDialog = new SpotsDialog(Checkout.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        getSupportActionBar().hide();

        radioGroup_payment = (RadioGroup) findViewById(R.id.rg_payment_method);
        radioButton_cod = (RadioButton) findViewById(R.id.rb_cod);
        radioButton_online = (RadioButton) findViewById(R.id.rb_payonline);

        button_placeorder = (Button) findViewById(R.id.btn_place_order);
        textView_name = (TextView) findViewById(R.id.tv_shipping_toName);
        textView_address = (TextView) findViewById(R.id.tv_shipping_toAddress);
        textView_mobile = (TextView) findViewById(R.id.tv_shipping_toMobile);

        textView_qty = (TextView) findViewById(R.id.tv_qty);
        textView_orderAmount = (TextView) findViewById(R.id.tv_order_amt);
        textView_totalPayble = (TextView) findViewById(R.id.tv_total_payble);
        recycerView_timeSlot = findViewById(R.id.recycerView_timeSlot);
        recycerView_timeSlot.setHasFixedSize(true);

        SharedPreferences sp = getSharedPreferences("Shipping_addr",Context.MODE_PRIVATE);
        name = sp.getString("str_name","");
        address = sp.getString("str_address","");
        societyid = sp.getString("str_city","");
        pincode = sp.getString("str_pincode","");
        email = sp.getString("str_email","");
        mobile = sp.getString("str_mobile","");
        altr_mob = sp.getString("str_alternate_mob","");

        SharedPreferences sp1 = getSharedPreferences("cart_item",Context.MODE_PRIVATE);
        totalAmount = sp1.getString("total_amt","");
        String qty = sp1.getString("total_qty","");

        textView_name.setText(name);
        textView_mobile.setText(mobile+"\n"+email);
        textView_address.setText(address+","+societyid+","+pincode);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        ImageView iv_back_arrow = findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textView_qty.setText(qty);
        textView_orderAmount.setText("\u20B9" + totalAmount);
        textView_totalPayble.setText("\u20B9" + totalAmount);

        button_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!radioButton_online.isChecked() && !radioButton_cod.isChecked()) {

                    Toast.makeText(getApplicationContext(), "Please select Payment method ", Toast.LENGTH_LONG).show();

                }
                else if (radioButton_cod.isChecked()) {

                    progressDialog.show();
                    SpotsDialog spotsDialog = new SpotsDialog(Checkout.this);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"order",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        spotsDialog.dismiss();
                                        Log.e("response","response");
                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {
                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Checkout.this, SweetAlertDialog.WARNING_TYPE);
                                            sweetAlertDialog.setTitleText("Place Order")
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

                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Checkout.this, SweetAlertDialog.SUCCESS_TYPE);
                                            sweetAlertDialog.setTitleText("Place Order")
                                                    .setContentText("Order Placed Sucessfully")
                                                    .setConfirmText("Ok")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismiss();
                                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

                            Map<String, String> placeOrder = new HashMap<>();
                            placeOrder.put("Loginid", getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
                            placeOrder.put("DeliverAddress", address);
                            placeOrder.put("City", societyid);
                            placeOrder.put("phone",mobile);
                            placeOrder.put("PinCode",pincode);
                            placeOrder.put("txnid","txn_cod");
                            placeOrder.put("method","COD");
                            Log.e("params_order",""+placeOrder);

                            return placeOrder;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Checkout.this);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            60000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    stringRequest.setShouldCache(false);
                    requestQueue.add(stringRequest);


                } else if (radioButton_online.isChecked()) {

                    final Activity activity = Checkout.this;
                    final com.razorpay.Checkout co = new com.razorpay.Checkout();
                    try {


                        JSONObject options = new JSONObject();
                        options.put("name", getSharedPreferences("UserData",MODE_PRIVATE).getString("name",""));
                        options.put("description", " Online Shoping");
                        //You can omit the image option to fetch the image from dashboard+-
                        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                        options.put("currency", "INR");
                        options.put("amount", Double.parseDouble(totalAmount) * 100);

                        JSONObject preFill = new JSONObject();
                        preFill.put("email", getSharedPreferences("UserData",MODE_PRIVATE).getString("email",""));
                        preFill.put("contact", getSharedPreferences("UserData",MODE_PRIVATE).getString("mobile",""));
                        options.put("prefill", preFill);
                        co.open(activity, options);

                    } catch (Exception e) {

                        Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }


                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            progressDialog.show();
            SpotsDialog spotsDialog = new SpotsDialog(Checkout.this);
            spotsDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"order",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                spotsDialog.dismiss();
                                Log.e("response","response");
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");

                                if (status.equalsIgnoreCase("0")) {
                                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Checkout.this, SweetAlertDialog.WARNING_TYPE);
                                    sweetAlertDialog.setTitleText("Place Order")
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

                                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Checkout.this, SweetAlertDialog.SUCCESS_TYPE);
                                    sweetAlertDialog.setTitleText("Place Order")
                                            .setContentText("Order Placed Sucessfully")
                                            .setConfirmText("Ok")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

                    Map<String, String> placeOrder = new HashMap<>();
                    placeOrder.put("Loginid", getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
                    placeOrder.put("DeliverAddress", address);
                    placeOrder.put("City", societyid);
                    placeOrder.put("phone",mobile);
                    placeOrder.put("PinCode",pincode);
                    placeOrder.put("txnid",razorpayPaymentID);
                    placeOrder.put("method","Online");
                    Log.e("params_order",""+placeOrder);

                    return placeOrder;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Checkout.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception e) {

            Log.e("rzrpay", "Exception in onPaymentSuccess", e);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            finish();
            Toast.makeText(Checkout.this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("rzrpay", "Exception in onPaymentError", e);
        }
    }


}