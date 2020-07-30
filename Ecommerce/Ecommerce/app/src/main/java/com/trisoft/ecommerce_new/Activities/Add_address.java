package com.trisoft.ecommerce_new.Activities;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.Utils.Dialogs;
import com.trisoft.ecommerce_new.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class Add_address extends AppCompatActivity {

    TextInputEditText et_select_city,tie_name,tie_mobile,tie_email,tie_password,tie_pincode,tie_address,tie_alt_mobile;
    List<String> citynamelist=new ArrayList<>();
    List<String> cityid=new ArrayList<>();
    Dialogs dialogs;
    final String mobStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";
    SessionManager sessionManager;
    ImageView iv_back;
    Button btn_continue,btn_change_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        getSupportActionBar().setTitle("Add Shipping Address");

        init_view();

    }

    private void init_view() {
        sessionManager = new SessionManager(Add_address.this);
        tie_name = findViewById(R.id.tie_name);
        tie_mobile = findViewById(R.id.tie_mobile);
        tie_email = findViewById(R.id.tie_email);
        tie_password = findViewById(R.id.tie_password);
        tie_pincode = findViewById(R.id.tie_pincode);
        tie_address = findViewById(R.id.tie_address);
        tie_alt_mobile = findViewById(R.id.tie_alt_mobile);
        btn_continue = findViewById(R.id.btn_continue);

        et_select_city = findViewById(R.id.et_select_city);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id","");
        String name = sharedPreferences.getString("name","");
        String email = sharedPreferences.getString("email","");
        String mobile = sharedPreferences.getString("mobile","");
        String city = sharedPreferences.getString("city","");
        String pincode = sharedPreferences.getString("pincode","");
        String address = sharedPreferences.getString("address","");

        tie_name.setText(name);
        tie_email.setText(email);
        tie_mobile.setText(mobile);
        tie_pincode.setText(pincode);
        et_select_city.setText(city);
        tie_address.setText(address);

        dialogs = new Dialogs(Add_address.this);
        et_select_city = findViewById(R.id.et_select_city);
        et_select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedposition="0";
                dialogs.singleSelectDialog(citynamelist, et_select_city, "Select City", selectedposition);
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_name = tie_name.getText().toString();
                String str_mobile = tie_mobile.getText().toString();
                String str_email = tie_email.getText().toString();
                String str_password = tie_password.getText().toString();
                String str_city = et_select_city.getText().toString();
                String str_pincode = tie_pincode.getText().toString();
                String str_address = tie_address.getText().toString();
                String str_alternate_mob = tie_alt_mobile.getText().toString();

                if(str_name.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Add_address.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Add address")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Add_address.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Add address")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Add_address.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Add address")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Add_address.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Add address")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Add_address.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Add address")
                            .setContentText("Please Enter Valid Email id")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else if(str_city.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Add_address.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Add address")
                            .setContentText("Please Select your city")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }else if(str_pincode.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Add_address.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Add_address")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Add_address.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Add_address")
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

                    SharedPreferences sp = getSharedPreferences("Shipping_addr",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("str_name",str_name);
                    editor.putString("str_mobile",str_mobile);
                    editor.putString("str_email",str_email);
                    editor.putString("str_city",str_city);
                    editor.putString("str_pincode",str_pincode);
                    editor.putString("str_address",str_address);
                    editor.putString("str_alternate_mob",str_alternate_mob);
                    editor.commit();
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(),Checkout.class));
                }
            }
        });

//        get_city_list();

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
                        Toast.makeText(Add_address.this , error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(Add_address.this).add(stringRequest);
    }

}

