package com.trisoft.ecommerce_new.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;
import com.trisoft.ecommerce_new.Activities.MainActivity;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class Login extends AppCompatActivity {


    TextView tv_login,tv_forgot_password;
    Button btn_login;
    TextInputEditText tie_username,tie_password;
    SessionManager sessionManager;
    final String mobStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(getApplicationContext());


        tie_username = findViewById(R.id.tie_username);
        tie_password = findViewById(R.id.tie_password);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_mobile =tie_username.getText().toString();
                String str_password =tie_password.getText().toString();

                if(str_mobile.isEmpty()){
                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Login")
                            .setContentText("Please Enter Mobile number")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();

                                }
                            }).setCancelable(false);

                    sweetAlertDialog.show();

                }else if(!str_mobile.matches(mobStr)){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Login")
                            .setContentText("Please Enter Valid Mobile number")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();

                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else if(str_password.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Login")
                            .setContentText("Please Enter Password")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else{


                    SpotsDialog spotsDialog = new SpotsDialog(Login.this);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"login",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        spotsDialog.dismiss();

                                        Log.e("response",""+response);
                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {


                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE);
                                            sweetAlertDialog.setTitleText("Login")
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
                                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("user_id",UserData.getString("LoginId"));
                                            editor.putString("name",UserData.getString("CustomerName"));
                                            editor.putString("username",UserData.getString("UserName"));
                                            editor.putString("email",UserData.getString("Emailid"));
                                            editor.putString("mobile",UserData.getString("Mobile"));
                                            editor.putString("city",UserData.getString("City"));
                                            editor.putString("pincode",UserData.getString("Pincode"));
                                            editor.putString("address",UserData.getString("Address"));
                                            editor.commit();
                                            editor.apply();

                                            sessionManager.setLogin(true);

                                            SharedPreferences sp = getSharedPreferences("cart_items",Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor1 = sp.edit();
                                            editor1.putString("cartitemcount","0");
                                            editor1.apply();

                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Login.this, SweetAlertDialog.SUCCESS_TYPE);
                                            sweetAlertDialog.setTitleText("Login")
                                                    .setContentText(object.getString("message"))
                                                    .setConfirmText("Ok")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismiss();

                                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                            
                            map.put("Mobile",str_mobile);
                            map.put("password",str_password);
                          
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


        tv_login = findViewById(R.id.tv_create_accont);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Forgot_password.class));
            }
        });



    }
}
