package com.trisoft.ecommerce_new.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;
import com.trisoft.ecommerce_new.Activities.MainActivity;
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

public class Register extends AppCompatActivity {


    Button btn_register;
    TextView tv_login;

    TextInputEditText et_select_city,tie_name,tie_mobile,tie_email,tie_password,tie_pincode,tie_address,tie_username;
    List<String> citynamelist=new ArrayList<>();
    List<String> cityid=new ArrayList<>();
    String url="http://fvcart.com/Api/city";
    Dialogs dialogs;
    final String mobStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(getApplicationContext());

        tie_name = findViewById(R.id.tie_name);
        tie_username = findViewById(R.id.tie_username);

        tie_mobile = findViewById(R.id.tie_mobile);
        tie_email = findViewById(R.id.tie_email);
        tie_password = findViewById(R.id.tie_password);
        tie_pincode = findViewById(R.id.tie_pincode);
        tie_address = findViewById(R.id.tie_address);


        dialogs = new Dialogs(Register.this);
        et_select_city = findViewById(R.id.et_select_city);
//        et_select_city.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String selectedposition="0";
//                dialogs.singleSelectDialog(citynamelist, et_select_city, "Select City", selectedposition);
//            }
//        });


//        get_city_list();


        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_username = tie_username.getText().toString();

                String str_name = tie_name.getText().toString();
                String str_mobile = tie_mobile.getText().toString();
                String str_email = tie_email.getText().toString();
                String str_password = tie_password.getText().toString();
                String str_city = et_select_city.getText().toString();
                String str_pincode = tie_pincode.getText().toString();
                String str_address = tie_address.getText().toString();

                if(str_username.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
                            .setContentText("Please Enter  username")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();



                } else if(str_name.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
                            .setContentText("Please Enter your name")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();



                }else if(str_mobile.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
                            .setContentText("Please Enter Valid Mobile number")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else if(str_email.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
                            .setContentText("Please Enter Valid Email id")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else if(str_password.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
                            .setContentText("Please Enter password")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }
                else if(str_city.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
                            .setContentText("Please Enter your city")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }
                else if(str_pincode.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
                            .setContentText("Please Enter pincode")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else if(str_address.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Register")
                            .setContentText("Please Enter your address")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else{

                    SpotsDialog spotsDialog = new SpotsDialog(Register.this);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"registration",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        Log.e("registeration_resp",""+response);

                                        spotsDialog.dismiss();

                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {


                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.ERROR_TYPE);
                                            sweetAlertDialog.setTitleText("Register")
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
                                            editor.putString("username",UserData.getString("UserName"));
                                            editor.putString("name",UserData.getString("CustomerName"));
                                            editor.putString("email",UserData.getString("Emailid"));
                                            editor.putString("mobile",UserData.getString("Mobile"));
                                            editor.putString("city",UserData.getString("City"));
                                            editor.putString("pincode",UserData.getString("Pincode"));
                                            editor.putString("address",UserData.getString("Address"));
                                            editor.commit();
                                            editor.apply();



//                                            {"status":"1","message":"successfully registered","data":
//                                                {"LoginId":5,"UserName":"Nishant","Password":"da39a3ee5e6b4b0d3255bfef95601890afd80709","UserType":"User",
//                                                        "CustomerName":"Nishant","Emailid":"mrnishant4@gmail.com","Mobile":"9057357829","City":null,
//                                                        "Pincode":"302017","Address":"Malviyanagar",
//                                                        "Createdate":null,"Modified_Date":null,"Active_Status":1}}


                                            sessionManager.setLogin(true);


                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Register.this, SweetAlertDialog.SUCCESS_TYPE);
                                            sweetAlertDialog.setTitleText("Register")
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
                            map.put("CustomerName",str_name);
                            map.put("UserName",str_username);
                            map.put("Emailid",str_email);
                            map.put("Mobile",str_mobile);
                            map.put("password",str_password);
                            map.put("City","Jaipur");
                            map.put("Pincode",str_pincode);
                            map.put("Address",str_address);

                            Log.e("params_regist",""+map);

//                            http://test.oakbells.in/Api/registration [Emailid,Mobile,UserName,CustomerName,City,Pincode,Address]
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

        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();

            }
        });

    }

    public static boolean isValidEmail(CharSequence target) {

        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void get_city_list() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrlApi+"get_city",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",""+response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray city = obj.getJSONArray("Data");

                            citynamelist.clear();
                            cityid.clear();
                            for(int i = 0; i<city.length(); i++)
                            {
                                JSONObject nameObject = city.getJSONObject(i);
                                citynamelist.add(nameObject.getString("name"));
                                cityid.add(nameObject.getString("id"));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext() , error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
