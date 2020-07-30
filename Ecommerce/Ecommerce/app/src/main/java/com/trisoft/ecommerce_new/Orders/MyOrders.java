package com.trisoft.ecommerce_new.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import com.trisoft.ecommerce_new.Activities.MainActivity;
import com.trisoft.ecommerce_new.BuildConfig;
import com.trisoft.ecommerce_new.Fragments.Account;
import com.trisoft.ecommerce_new.Fragments.Help_support;
import com.trisoft.ecommerce_new.Intro.Forgot_password;
import com.trisoft.ecommerce_new.Intro.Login;
import com.trisoft.ecommerce_new.Navigation_drawer;
import com.trisoft.ecommerce_new.Offers.Offer;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.Utils.Dialogs;
import com.trisoft.ecommerce_new.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class MyOrders extends AppCompatActivity {

    RecyclerView rv_my_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        getSupportActionBar().setTitle("My Orders");

        getSupportActionBar().hide();


        LinearLayout ll_cart = findViewById(R.id.ll_cart);
        ll_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), MyOrders.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyOrders.this, SweetAlertDialog.WARNING_TYPE);
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

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyOrders.this,SweetAlertDialog.WARNING_TYPE);
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

                            startActivity(new Intent(getApplicationContext(), MyOrders.class));
                            finish();
                        }else{

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyOrders.this, SweetAlertDialog.WARNING_TYPE);
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

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MyOrders.this, SweetAlertDialog.WARNING_TYPE);
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

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyOrders.this, SweetAlertDialog.WARNING_TYPE);
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




        rv_my_orders = findViewById(R.id.rv_my_orders);

        SpotsDialog dialog = new SpotsDialog(MyOrders.this);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "order_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            dialog.dismiss();

                            if (status.equalsIgnoreCase("0")) {

                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyOrders.this, SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText("Products")
                                        .setContentText("No Product found")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(sweetAlertDialog1 -> {
                                            sweetAlertDialog.dismiss();
                                            finish();
                                        }).show();

                                sweetAlertDialog.setCancelable(false);

                            } else {

                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<order_list> orderLists = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {

                                    order_list orderList = gson.fromJson(array.getJSONObject(i).toString(), order_list.class);
                                    orderLists.add(orderList);
                                }

                                getSupportActionBar().setTitle("My Orders("+String.valueOf(orderLists.size())+")");


                                OrderList_Adapter completePackageAdapter = new OrderList_Adapter(MyOrders.this,orderLists);
                                rv_my_orders = findViewById(R.id.rv_my_orders);

                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                rv_my_orders.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
                                rv_my_orders.setAdapter(completePackageAdapter);
                                rv_my_orders.setNestedScrollingEnabled(false);
                                rv_my_orders.setHasFixedSize(true);

                            }
                        } catch (JSONException e) {
                            dialog.dismiss();

                            Log.e("JSONException", e.toString());
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.dismiss();

                        if (error instanceof com.android.volley.NetworkError) {

                        } else if (error instanceof ServerError) {

                        } else if (error instanceof AuthFailureError) {

                        } else if (error instanceof ParseError) {

                        } else if (error instanceof NoConnectionError) {

                        } else if (error instanceof TimeoutError) {

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("Loginid",getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
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

    public class OrderList_Adapter extends RecyclerView.Adapter<OrderList_Adapter.MyViewHolder> {

        List <order_list> order_history_modelsList=new ArrayList<>();
        Context context;

        public OrderList_Adapter(MyOrders myOrders, List<order_list> order_history_modelsList) {

            context=myOrders;
            this.order_history_modelsList=order_history_modelsList;

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);

            MyViewHolder myViewHolder=new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            holder.textView_orderid.setText(""+order_history_modelsList.get(position).getYorderID());
            holder.textView_orderstatus.setText(order_history_modelsList.get(position).getDeliverAddress());
            holder.textView_orderdate.setText(order_history_modelsList.get(position).getCity()+","+order_history_modelsList.get(position).getPinCode());
            holder.textView_orderPrice.setText("\u20B9"+String.valueOf(order_history_modelsList.get(position).getTotal()));

            holder.tv_ordstatus.setText(""+order_history_modelsList.get(position).getStatus());

            holder.rv_order_items.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

                                ArrayList<order_list.ProdDetail> myList = new ArrayList<>();
                                myList.add(order_history_modelsList.get(position).getProdDetail());

            holder.rv_order_items.setAdapter(new Products_Adapter(context,myList));


            holder.Pname.setText(order_history_modelsList.get(position).getProdDetail().getProdName());
            holder.Pqty.setText(order_history_modelsList.get(position).getQty());
            holder.Pprice.setText("\u20B9"+order_history_modelsList.get(position).getProdDetail().getMRP()+"x"+order_history_modelsList.get(position).getQty()+"= \u20B9"+String.valueOf(Double.parseDouble(order_history_modelsList.get(position).getProdDetail().getMRP())*Double.parseDouble(String.valueOf(order_history_modelsList.get(position).getQty()))));



            if(String.valueOf(order_history_modelsList.get(position).getStatus()).equalsIgnoreCase("cancle")){

                holder.tv_cancel_order.setVisibility(View.GONE);
            }

            holder.tv_cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MyOrders.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Cancel Order")
                            .setContentText("Are you sure want to Cancel order?")
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
                                    SpotsDialog spotsDialog = new SpotsDialog(MyOrders.this);
                                    spotsDialog.show();

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"cancle_order",
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {

                                                        spotsDialog.dismiss();

                                                        JSONObject object = new JSONObject(response);
                                                        String status = object.getString("status");

                                                        if (status.equalsIgnoreCase("0")) {


                                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MyOrders.this, SweetAlertDialog.ERROR_TYPE);
                                                            sweetAlertDialog.setTitleText("Cancel Order")
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

                                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MyOrders.this, SweetAlertDialog.SUCCESS_TYPE);
                                                            sweetAlertDialog.setTitleText("Cancel Order")
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

                                            map.put("order_id",""+order_history_modelsList.get(position).getYorderID());

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
                            }).setCancelable(false);
                    sweetAlertDialog.show();

                }
            });

            holder.tv_add_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.rating_review_popup, null);
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    popupWindow.showAtLocation(v, Gravity.CENTER, 1, 1);


                    ImageView iv_close = popupView.findViewById(R.id.iv_close);
                    RatingBar rb_product = popupView.findViewById(R.id.rb_product);
                    EditText et_comment = popupView.findViewById(R.id.et_comment);
                    Button btn_submit = popupView.findViewById(R.id.btn_submit);


                    iv_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            popupWindow.dismiss();
                        }
                    });


                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            float rating_val = rb_product.getRating();
                            String Comment = et_comment.getText().toString();


                            if(rating_val<1){

                                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MyOrders.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitleText("Add Rating")
                                        .setContentText("Please add rating")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).setCancelable(false);
                                sweetAlertDialog.show();
                            }else {


                                SpotsDialog spotsDialog = new SpotsDialog(MyOrders.this);
                                spotsDialog.show();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"Rating_submit",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {

                                                    spotsDialog.dismiss();

                                                    JSONObject object = new JSONObject(response);
                                                    String status = object.getString("status");

                                                    if (status.equalsIgnoreCase("0")) {


                                                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MyOrders.this, SweetAlertDialog.ERROR_TYPE);
                                                        sweetAlertDialog.setTitleText("Add Rating")
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

                                                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MyOrders.this, SweetAlertDialog.SUCCESS_TYPE);
                                                        sweetAlertDialog.setTitleText("Add Rating")
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

                                        map.put("product_id",""+order_history_modelsList.get(position).getProdDetail().getProdID());
                                        map.put("user_id",""+getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
                                        map.put("rating",""+rating_val);
                                        map.put("review",""+Comment);


                                        Log.e("params_rating",""+map);

//                                        Rating_submit [user_id,product_id,rating,review]
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
            });
//            holder.textView_paymentmethod.setText(order_history_modelsList.get(position).getMethod());

//            holder.ll_order.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    ArrayList<order_list.g> myList = new ArrayList<>();
//                    myList.addAll(order_history_modelsList.get(position).getProducts());
//
//                    Intent intent=new Intent(context,Order_Details.class);
//                    intent.putExtra("mylist", myList);
//                    intent.putExtra("name",order_history_modelsList.get(position).getFirstName());
//                    intent.putExtra("total",String.valueOf(order_history_modelsList.get(position).getTotal()));
//                    intent.putExtra("address",order_history_modelsList.get(position).getAddress1());
//                    intent.putExtra("mobile",order_history_modelsList.get(position).getPhone());
//                    intent.putExtra("Email",order_history_modelsList.get(position).getEmail());
//                    intent.putExtra("date",order_history_modelsList.get(position).getTime());
//                    intent.putExtra("orderId",order_history_modelsList.get(position).getId());
//                    intent.putExtra("status",order_history_modelsList.get(position).getStatus());
//                    intent.putExtra("payment",order_history_modelsList.get(position).getMethod());
//                    context.startActivity(intent);
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return order_history_modelsList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout ll_order;
            TextView tv_ordstatus,textView_orderid,textView_orderstatus,textView_orderdate,textView_orderPrice,textView_paymentmethod,tv_ordertime_slot,tv_cancel_order;
            RecyclerView rv_order_items;
            ImageView Pimg;
            TextView Pname,Pprice,Pqty,tv_add_review;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                rv_order_items= itemView.findViewById(R.id.rv_order_items);

                tv_cancel_order=(TextView)itemView.findViewById(R.id.tv_cancel_order);
                tv_ordstatus=(TextView)itemView.findViewById(R.id.tv_ordstatus);

                tv_add_review=(TextView)itemView.findViewById(R.id.tv_add_review);

                textView_orderid=(TextView)itemView.findViewById(R.id.tv_orderid);
                textView_orderstatus=(TextView)itemView.findViewById(R.id.tv_status);
                textView_orderdate=(TextView)itemView.findViewById(R.id.tv_orderdate);
                textView_orderPrice=(TextView)itemView.findViewById(R.id.tv_order_amt);
                textView_paymentmethod=(TextView)itemView.findViewById(R.id.tv_paymentmethod);
                tv_ordertime_slot=itemView.findViewById(R.id.tv_ordertime_slot);

                ll_order =itemView.findViewById(R.id.ll_order);

                Pimg=(ImageView)itemView.findViewById(R.id.img_Pimg);
                Pname=(TextView)itemView.findViewById(R.id.tv_Pname);
                Pprice=(TextView)itemView.findViewById(R.id.tv_Pprice);
                Pqty=(TextView)itemView.findViewById(R.id.tv_Pqty);
            }
        }
    }


    public class Products_Adapter extends RecyclerView.Adapter<Products_Adapter.MyViewHolder> {

        List<order_list.ProdDetail> object=new ArrayList<>();
        Context context;

        public Products_Adapter(Context context, List<order_list.ProdDetail> object) {

            this.object=object;
            context=context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productdetail_layout, parent, false);
            MyViewHolder myViewHolder=new MyViewHolder(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//            Glide.with(context)
//                    .load(object.get(position).getGroupName().replace(" ","_"))
//                    .placeholder(R.drawable.logo_paneka)
//                    .error(R.drawable.logo_paneka)
//                    .into(holder.Pimg);
            holder.Pname.setText(object.get(position).getProdName());
            holder.Pqty.setText(object.get(position).getQuantity()+"x"+object.get(position).getQuantity());
            holder.Pprice.setText("\u20B9"+object.get(position).getMRP()+"x"+object.get(position).getQuantity()+"= \u20B9"+String.valueOf(Double.parseDouble(object.get(position).getMRP())*Double.parseDouble(String.valueOf(object.get(position).getQuantity()))));
        }

        @Override
        public int getItemCount() {
            return object.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView Pimg;
            TextView Pname,Pprice,Pqty,tv_add_review;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                Pimg=(ImageView)itemView.findViewById(R.id.img_Pimg);
                Pname=(TextView)itemView.findViewById(R.id.tv_Pname);
                Pprice=(TextView)itemView.findViewById(R.id.tv_Pprice);
                Pqty=(TextView)itemView.findViewById(R.id.tv_Pqty);


            }
        }
    }


}
