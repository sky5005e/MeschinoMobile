package com.trisoft.ecommerce_new.barcodescanner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.trisoft.ecommerce_new.Activities.MainActivity;
import com.trisoft.ecommerce_new.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class Make_barcode_payment extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_barcode_payment);
        getSupportActionBar().hide();



        SharedPreferences sp = getSharedPreferences("cart_item", MODE_PRIVATE);
        String total_amt = sp.getString("total_amt","");
        String total_qty = sp.getString("total_qty","");


        final Activity activity = Make_barcode_payment.this;
        final com.razorpay.Checkout co = new com.razorpay.Checkout();
        try {


            JSONObject options = new JSONObject();
            options.put("name",getSharedPreferences("UserData",MODE_PRIVATE).getString("name",""));
            options.put("description", " Instant Shopping");
            //You can omit the image option to fetch the image from dashboard+-
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Double.parseDouble(total_amt) * 100);

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

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            SpotsDialog spotsDialog = new SpotsDialog(Make_barcode_payment.this);
            spotsDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"barcode_checkout",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                spotsDialog.dismiss();
                                Log.e("response","response");
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");

                                if (status.equalsIgnoreCase("0")) {
                                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Make_barcode_payment.this, SweetAlertDialog.WARNING_TYPE);
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

                                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Make_barcode_payment.this, SweetAlertDialog.SUCCESS_TYPE);
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
                    placeOrder.put("user_id", getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
                    placeOrder.put("amount",razorpayPaymentID);
                    placeOrder.put("method","Online");
                    Log.e("params_order",""+placeOrder);

                    return placeOrder;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
            Toast.makeText(getApplicationContext(), "Payment failed: " + code + " " + response, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("rzrpay", "Exception in onPaymentError", e);
        }
    }

}
