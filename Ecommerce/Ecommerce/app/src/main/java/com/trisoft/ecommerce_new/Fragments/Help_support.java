package com.trisoft.ecommerce_new.Fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.trisoft.ecommerce_new.Activities.MainActivity;
import com.trisoft.ecommerce_new.BuildConfig;
import com.trisoft.ecommerce_new.Cart.MyCart;
import com.trisoft.ecommerce_new.Intro.Login;
import com.trisoft.ecommerce_new.Offers.Offer;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.Utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;


public class Help_support extends AppCompatActivity implements View.OnClickListener {

    View view;
    Button btn_send_feedback;
    int PERMISSION_ALL = 1;
    TextView tv_call_us, tv_mail_us;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_help_support);
        getSupportActionBar().setTitle("Help & Support");
        getSupportActionBar().hide();

        init_view();

    }

    private void init_view() {
        tv_call_us = findViewById(R.id.tv_call_us);

        tv_mail_us = findViewById(R.id.tv_mail_us);

        btn_send_feedback = findViewById(R.id.btnsend_feed_back);
        btn_send_feedback.setOnClickListener(this);

        tv_call_us.setOnClickListener(this);

        tv_mail_us.setOnClickListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id","");
        String name = sharedPreferences.getString("name","");
        String email = sharedPreferences.getString("email","");
        String mobile = sharedPreferences.getString("mobile","");
        String city = sharedPreferences.getString("city","");
        String pincode = sharedPreferences.getString("pincode","");
        String address = sharedPreferences.getString("address","");


        TextView tv_title = findViewById(R.id.tv_title);
        if(new SessionManager(getApplicationContext()).isLoggedIn()){

            tv_title.setText("Location\n"+address+","+city);

        }else{

            tv_title.setText("Location\nYour shipping address ");

        }

        ImageView iv_edit_loc = findViewById(R.id.iv_edit_loc);
        iv_edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), Account.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Help_support.this,SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Login")
                            .setContentText("To Change your locaion,you must Login")
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

        LinearLayout ll_cart = findViewById(R.id.ll_cart);
        ll_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), Help_support.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
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
                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), Account.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Help_support.this,SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Login")
                            .setContentText("To View your Account,you must Login")
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

                }            }
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

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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



       TextView tv_item_counter = findViewById(R.id.tv_item_counter);

        if(new SessionManager(getApplicationContext()).isLoggedIn()) {

            SharedPreferences sp1 = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
            tv_item_counter.setText(sp1.getString("cartitemcount", "0"));

        }else{
            tv_item_counter.setText("0");
        }

        ImageView iv_back = findViewById(R.id.iv_back);
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

                            startActivity(new Intent(getApplicationContext(), Help_support.class));
                            finish();
                        }else{

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
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

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
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

                            startActivity(new Intent(getApplicationContext(), Help_support.class));
                            finish();


                        }else{

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        if (v == tv_call_us) {

            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(
                    Manifest.permission.CALL_PHONE,
                    getPackageName());
            if (hasPerm != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);


            }else {
                try {
                    Intent my_callIntent = new Intent(Intent.ACTION_CALL);
                    my_callIntent.setData(Uri.parse("tel:7428232333"));
                    //here the word 'tel' is important for making a call...
                    startActivity(my_callIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error in your phone call" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }

        if(v== tv_mail_us){

            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{  "operations@paneka.in"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hello There");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Add Message here");


            emailIntent.setType("message/rfc822");

            try {
                startActivity(Intent.createChooser(emailIntent,
                        "Send email using..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "No email clients installed.",
                        Toast.LENGTH_SHORT).show();
            }


        }

        if(v==btn_send_feedback){

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.feed_back_popup, null);

            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            ImageView iv_close = popupView.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    popupWindow.dismiss();

                }
            });


            EditText et_name = popupView.findViewById(R.id.et_name);
            EditText et_email = popupView.findViewById(R.id.et_email);
            EditText et_contact = popupView.findViewById(R.id.et_contact);

            EditText et_message = popupView.findViewById(R.id.et_message);

            Button btn_send_feedback = popupView.findViewById(R.id.btn_send_feed_back);
            btn_send_feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String str_name = et_name.getText().toString();
                    String str_mobile = et_contact.getText().toString();
                    String str_email = et_email.getText().toString();
                    String str_message = et_message.getText().toString();

                    final String mobStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";


                    if(str_name.isEmpty()){

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Feedback")
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

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Feedback")
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

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Feedback")
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

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Feedback")
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

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Feedback")
                                .setContentText("Please Enter Valid Email id")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).setCancelable(false);
                        sweetAlertDialog.show();

                    }else if(str_message.isEmpty()){

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Feedback")
                                .setContentText("Please write message")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).setCancelable(false);
                        sweetAlertDialog.show();

                    }else{

                        SpotsDialog spotsDialog = new SpotsDialog(Help_support.this);
                        spotsDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"feedback",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            spotsDialog.dismiss();

                                            Log.e("response","response");
                                            JSONObject object = new JSONObject(response);
                                            String status = object.getString("status");

                                            if (status.equalsIgnoreCase("0")) {

                                                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.ERROR_TYPE);
                                                sweetAlertDialog.setTitleText("Feedback")
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

                                                popupWindow.dismiss();
                                                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Help_support.this, SweetAlertDialog.SUCCESS_TYPE);
                                                sweetAlertDialog.setTitleText("Feedback")
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

                                map.put("name",str_name);
                                map.put("email",str_email);
                                map.put("contact_num",str_mobile);
                                map.put("message",str_message);

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


            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });



        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidEmail(CharSequence target) {

        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
