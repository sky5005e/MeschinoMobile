package com.trisoft.ecommerce_new.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class Change_password extends AppCompatActivity {


    EditText editText_currentPassword,editText_newPasword,editText_confirmNewPassword;
    Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setTitle("Change Password");

        editText_currentPassword=findViewById(R.id.et_currPassword);
        editText_newPasword=findViewById(R.id.et_newPassword);
        editText_confirmNewPassword=findViewById(R.id.et_confirmnewPasword);

        button_send = findViewById(R.id.btn_saveChanges);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText_currentPassword.getText().toString().isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Change_password.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Change Password")
                            .setContentText("Please Enter Current Password")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }
                else if(editText_newPasword.getText().toString().isEmpty())
                {
                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Change_password.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Change Password")
                            .setContentText("Please Enter New Password")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();
                }
                else if(editText_confirmNewPassword.getText().toString().isEmpty())
                {
                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Change_password.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Change Password")
                            .setContentText("Confirm New Password")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();
                }
                else if(!editText_confirmNewPassword.getText().toString().equals(editText_newPasword.getText().toString()))
                {

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Change_password.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Change Password")
                            .setContentText("New Password and Confirm Password Should be same")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();
                }
                else
                {
                    SpotsDialog spotsDialog = new SpotsDialog(Change_password.this);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"change_password",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        spotsDialog.dismiss();

                                        Log.e("response","response");
                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {
                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Change_password.this, SweetAlertDialog.ERROR_TYPE);
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


                                        } else {

                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Change_password.this, SweetAlertDialog.SUCCESS_TYPE);
                                            sweetAlertDialog.setTitleText("Password")
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

                            map.put("LoginId",getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
                            map.put("oldPassword",editText_currentPassword.getText().toString());
                            map.put("Password",editText_newPasword.getText().toString());

                            Log.e("param_change_password",""+map);
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
}
