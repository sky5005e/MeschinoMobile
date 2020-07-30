package com.trisoft.ecommerce_new.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

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
import com.google.android.material.textfield.TextInputEditText;
import com.trisoft.ecommerce_new.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class Forgot_password extends AppCompatActivity {

    TextInputEditText tie_mobile_num,tie_email_id;
    Button btn_send_email;
    final String mobStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();


        tie_email_id = findViewById(R.id.tie_email_id);
        tie_mobile_num = findViewById(R.id.tie_mobile_num);
        btn_send_email = findViewById(R.id.btn_send_email);




        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_mobile = tie_mobile_num.getText().toString();
                String str_email = tie_email_id.getText().toString();


                    if(str_email.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Forgot_password")
                            .setContentText("Please Enter Email id")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else if(!isValidEmail(str_email)){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Forgot_password")
                            .setContentText("Please Enter Valid Email id")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else{


                    SpotsDialog spotsDialog = new SpotsDialog(Forgot_password.this);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"forget_password",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        spotsDialog.dismiss();

                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {


                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.ERROR_TYPE);
                                            sweetAlertDialog.setTitleText("Forgot_password")
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

                                            JSONObject UserData = object.getJSONObject("data");
                                            
                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Forgot_password.this, SweetAlertDialog.SUCCESS_TYPE);
                                            sweetAlertDialog.setTitleText("Forgot_password")
                                                    .setContentText(object.getString("message"))
                                                    .setConfirmText("Ok")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismiss();

                                                            startActivity(new Intent(getApplicationContext(), Login.class));
                                                            finish();
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

                            map.put("email",str_email);

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

            }
        });

    }


    public static boolean isValidEmail(CharSequence target) {

        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
