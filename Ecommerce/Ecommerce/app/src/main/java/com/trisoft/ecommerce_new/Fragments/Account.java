package com.trisoft.ecommerce_new.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.trisoft.ecommerce_new.Activities.Change_password;
import com.trisoft.ecommerce_new.BuildConfig;
import com.trisoft.ecommerce_new.Cart.MyCart;
import com.trisoft.ecommerce_new.Intro.Login;
import com.trisoft.ecommerce_new.Activities.MainActivity;
import com.trisoft.ecommerce_new.Offers.Offer;
import com.trisoft.ecommerce_new.Orders.MyOrders;
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

public class Account extends AppCompatActivity {

    TextInputEditText et_select_city,tie_name,tie_mobile,tie_email,tie_password,tie_pincode,tie_address,tie_username;
    List<String> citynamelist = new ArrayList<>();
    List<String> cityid = new ArrayList<>();
    Dialogs dialogs;
    final String mobStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";
    SessionManager sessionManager;
    ImageView iv_back;
    Button btn_update_profile,btn_change_password,btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account);
        getSupportActionBar().hide();


        init_view();


        LinearLayout ll_cart = findViewById(R.id.ll_cart);

        ll_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), MyCart.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Login")
                            .setContentText("To View your cart,you must Login")
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


        LinearLayout ll_account = findViewById(R.id.ll_account);
        ll_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(new SessionManager(getApplicationContext()).isLoggedIn()) {
//
//                    startActivity(new Intent(getApplicationContext(), Account.class));
//
//                }else{
//
//                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Account.this,SweetAlertDialog.WARNING_TYPE);
//                    sweetAlertDialog.setTitleText("Login")
//                            .setContentText("To View your Account,you must Login")
//                            .setConfirmText("Login")
//                            .setCancelText("Cancel")
//                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                                    sweetAlertDialog.dismiss();
//
//                                }
//                            })
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                                    sweetAlertDialog.dismiss();
//                                    startActivity(new Intent(getApplicationContext(), Login.class));
//
//                                }
//                            }).setCancelable(false);
//                    sweetAlertDialog.show();
//
//                }
            }
        });

        LinearLayout ll_help_support = findViewById(R.id.ll_help_support);
        ll_help_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Help_support.class));
            }
        });



        LinearLayout ll_home = findViewById(R.id.ll_hone);
        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


        LinearLayout ll_offers = findViewById(R.id.ll_offers);
        ll_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Offer.class));
            }
        });



//        tv_item_counter = findViewById(R.id.tv_item_counter);
//
//        if(new SessionManager(getApplicationContext()).isLoggedIn()) {
//
//            SharedPreferences sp1 = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
//            tv_item_counter.setText(sp1.getString("cartitemcount", "0"));
//
//        }else{
//            tv_item_counter.setText("0");
//        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_navigation_drawer, null);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(v, Gravity.START, 1, 1);
//                popupWindow.setAnimationStyle(R.style.alert_dialog_dark);
                TextView tv_build_version = popupView.findViewById(R.id.tv_app_version);
                TextView tv_name = popupView.findViewById(R.id.tv_user_name);
                TextView tv_email = popupView.findViewById(R.id.tv_email);
                TextView tv_logout = popupView.findViewById(R.id.tv_logout);

                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    tv_name.setText(getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("name", ""));
                    tv_email.setText(getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("email", ""));

                    tv_logout.setVisibility(View.VISIBLE);

                }else {
                    tv_logout.setVisibility(View.GONE);
                    tv_name.setText("Hi,Guest");
                    tv_email.setText("Login now");

                }

                tv_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!new SessionManager(getApplicationContext()).isLoggedIn()){

                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }
                    }
                });

                tv_build_version.setText("Version : "+ BuildConfig.VERSION_NAME);

                TextView tv_home = popupView.findViewById(R.id.tv_home);
                tv_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();

                    }
                });


                TextView tv_my_cart = popupView.findViewById(R.id.tv_my_cart);
                tv_my_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                            startActivity(new Intent(getApplicationContext(), MyCart.class));
                            finish();
                        }else{

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("Login")
                                    .setContentText("To View your cart,you must Login")
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

                TextView tv_support = popupView.findViewById(R.id.tv_support);
                tv_support.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Help_support.class));
                        finish();
                    }
                });

                TextView tv_share = popupView.findViewById(R.id.tv_share_app);
                tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                            String shareMessage= "\nRedifining grocery shopping experience open round the clock groceries on the wheels, an online grocery delivery experience that will make you choose us everyday.\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                        } catch(Exception e) {
                            //e.toString();
                        }
                    }
                });

                TextView tv_rate = popupView.findViewById(R.id.tv_rate_app);
                tv_rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                    }
                });


                tv_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Logout")
                                .setContentText("Are you sure want to Logout?")
                                .setConfirmText("Yes")
                                .setCancelText("No")
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

                                        SharedPreferences cartpreferences = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = cartpreferences.edit();
                                        editor.putString("cartitemcount", "0");
                                        editor.clear();
                                        editor.commit();

                                        SharedPreferences userpreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor usereditor = userpreferences.edit();
                                        usereditor.clear();
                                        usereditor.commit();

                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                        new SessionManager(getApplicationContext()).setLogin(false);
                                        finish();
                                    }
                                }).setCancelable(false);
                        sweetAlertDialog.show();

                    }
                });


                TextView tv_my_orders = popupView.findViewById(R.id.tv_my_orders);
                tv_my_orders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                            startActivity(new Intent(getApplicationContext(), MyOrders.class));
                            finish();


                        }else{

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("Login")
                                    .setContentText("To View your order history,you must Login")
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


                ImageView iv_close = popupView.findViewById(R.id.iv_close);
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                    }
                });

                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
//                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });




    }

    private void init_view() {

        sessionManager = new SessionManager(Account.this);
        iv_back = findViewById(R.id.iv_back);
        tie_name = findViewById(R.id.tie_name);
        tie_mobile = findViewById(R.id.tie_mobile);
        tie_username = findViewById(R.id.tie_username);
        tie_email = findViewById(R.id.tie_email);
        tie_password = findViewById(R.id.tie_password);
        tie_pincode = findViewById(R.id.tie_pincode);
        tie_address = findViewById(R.id.tie_address);
        et_select_city = findViewById(R.id.et_select_city);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                
            }
        });
        btn_update_profile = findViewById(R.id.btn_update_profile);
        btn_change_password = findViewById(R.id.btn_change_password);
        btn_logout = findViewById(R.id.btn_logout);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id","");
        String username = sharedPreferences.getString("username","");

        String name = sharedPreferences.getString("name","");
        String email = sharedPreferences.getString("email","");
        String mobile = sharedPreferences.getString("mobile","");
        String city = sharedPreferences.getString("city","");
        String pincode = sharedPreferences.getString("pincode","");
        String address = sharedPreferences.getString("address","");


        tie_name.setText(name);
        tie_username.setText(username);
        tie_email.setText(email);
        tie_mobile.setText(mobile);
        tie_pincode.setText(pincode);
        et_select_city.setText(city);
        tie_address.setText(address);

        dialogs = new Dialogs(Account.this);
        et_select_city = findViewById(R.id.et_select_city);
//        et_select_city.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String selectedposition="0";
//                dialogs.singleSelectDialog(citynamelist, et_select_city, "Select City", selectedposition);
//
//            }
//        });

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Change_password.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("Logout")
                        .setContentText("Are you sure want to Logout?")
                        .setConfirmText("Yes")
                        .setCancelText("No")
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

                                SharedPreferences cartpreferences = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = cartpreferences.edit();
                                editor.putString("cartitemcount", "0");
                                editor.clear();
                                editor.commit();

                                SharedPreferences userpreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor usereditor = userpreferences.edit();
                                usereditor.clear();
                                usereditor.commit();

                                startActivity(new Intent(getApplicationContext(), Login.class));
                                sessionManager.setLogin(false);
                                finish();

                            }
                        }).setCancelable(false);
                sweetAlertDialog.show();
            }
        });


        btn_update_profile.setOnClickListener(new View.OnClickListener() {
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

                if(str_name.isEmpty()){

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog
                            .setTitleText("My Account")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Account")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Account")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Account")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Account")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Account")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Account")
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

                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("My Account")
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

                    SpotsDialog spotsDialog = new SpotsDialog(Account.this);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"update_profile",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        spotsDialog.dismiss();

                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {


                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.ERROR_TYPE);
                                            sweetAlertDialog.setTitleText("My Account")
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
                                            
                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Account.this, SweetAlertDialog.SUCCESS_TYPE);
                                            sweetAlertDialog.setTitleText("My Account")
                                                    .setContentText(object.getString("message"))
                                                    .setConfirmText("Ok")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismiss();

                                                            startActivity(new Intent(Account.this, MainActivity.class));
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
                            map.put("LoginId",user_id);
                            map.put("UserName",str_username);
                            map.put("Emailid",str_email);
                            map.put("Mobile",str_mobile);
                            map.put("CustomerName",str_name);
                            map.put("City",str_city);
                            map.put("Pincode",str_pincode);
                            map.put("Address",str_address);
//                            update_profile [LoginId,UserName,CustomerName,Pincode,Address]
                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Account.this);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            60000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    stringRequest.setShouldCache(false);
                    requestQueue.add(stringRequest);

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
                        Toast.makeText(Account.this , error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(Account.this).add(stringRequest);
    }

}
