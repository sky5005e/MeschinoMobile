package com.trisoft.ecommerce_new.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;
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
import com.trisoft.ecommerce_new.Activities.Add_address;
import com.trisoft.ecommerce_new.Activities.MainActivity;
import com.trisoft.ecommerce_new.BuildConfig;
import com.trisoft.ecommerce_new.Fragments.Account;
import com.trisoft.ecommerce_new.Fragments.Help_support;
import com.trisoft.ecommerce_new.Intro.Login;
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

public class MyCart extends AppCompatActivity {

    RecyclerView rv_my_cart;
    Button btn_proceed_now;
    Double total_price =0.0;
    TextView tv_total_amt;
    LinearLayout ll_bottom;

    TextView tv_title,tv_item_counter;
    RelativeLayout rl_cart;
    ImageView iv_back;
    String total_qty="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        getSupportActionBar().setTitle("My Cart(0)");

        getSupportActionBar().hide();
        rv_my_cart =findViewById(R.id.rv_my_cart);
        tv_total_amt =findViewById(R.id.tv_total_amt);
        ll_bottom =findViewById(R.id.ll_bottom1);


        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_item_counter = findViewById(R.id.tv_item_counter);
        rl_cart = findViewById(R.id.rl_cart);


        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id","");
        String name = sharedPreferences.getString("name","");
        String email = sharedPreferences.getString("email","");
        String mobile = sharedPreferences.getString("mobile","");
        String city = sharedPreferences.getString("city","");
        String pincode = sharedPreferences.getString("pincode","");
        String address = sharedPreferences.getString("address","");



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

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyCart.this,SweetAlertDialog.WARNING_TYPE);
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

                    startActivity(new Intent(getApplicationContext(), MyCart.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyCart.this, SweetAlertDialog.WARNING_TYPE);
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

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyCart.this,SweetAlertDialog.WARNING_TYPE);
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



        tv_item_counter = findViewById(R.id.tv_item_counter);

        if(new SessionManager(getApplicationContext()).isLoggedIn()) {

            SharedPreferences sp1 = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
            tv_item_counter.setText(sp1.getString("cartitemcount", "0"));

        }else{
            tv_item_counter.setText("0");
        }

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

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyCart.this, SweetAlertDialog.WARNING_TYPE);
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

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MyCart.this, SweetAlertDialog.WARNING_TYPE);
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

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyCart.this, SweetAlertDialog.WARNING_TYPE);
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



        btn_proceed_now =findViewById(R.id.btn_proceed_now);

        btn_proceed_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("cart_item",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("total_amt",""+total_price);
                editor.putString("total_qty",""+total_qty);
                editor.commit();
                editor.apply();
                startActivity(new Intent(getApplicationContext(), Add_address.class));

            }
        });


        getCartItem();

    }

    private void getCartItem() {

        SpotsDialog dialog = new SpotsDialog(MyCart.this);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "cart_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            dialog.dismiss();

                            if (status.equalsIgnoreCase("0")) {


                                SharedPreferences sp = getSharedPreferences("cart_items",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("cartitemcount",String.valueOf(0));
                                editor.apply();
                                editor.commit();
                                getSupportActionBar().setTitle("My Cart(0)");

                                tv_item_counter.setText(String.valueOf(0));


                                rv_my_cart.setVisibility(View.GONE);
                                ll_bottom.setVisibility(View.GONE);

                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyCart.this, SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText("My Cart")
                                        .setContentText("No item found")
                                        .setConfirmText("Shop now")
                                        .setConfirmClickListener(sweetAlertDialog1 -> {
                                            sweetAlertDialog.dismiss();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            finish();
                                        }).show();

                                sweetAlertDialog.setCancelable(false);

                            } else {

                                rv_my_cart.setVisibility(View.VISIBLE);
                                ll_bottom.setVisibility(View.VISIBLE);

                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<cart_list> cartLists = new ArrayList<>();
                                cartLists.clear();
                                total_price =0.0;

                                for (int i = 0; i < array.length(); i++) {
                                    cart_list cartList = gson.fromJson(array.getJSONObject(i).toString(), cart_list.class);
                                    cartLists.add(cartList);
                                    total_price+= Double.parseDouble(cartList.getRate())*Double.parseDouble(cartList.getQty());

                                }

//                                getSupportActionBar().setTitle("My Cart("+String.valueOf(noticesList.size())+")");


                                tv_item_counter.setText(String.valueOf(cartLists.size()));
                                SharedPreferences sp = getSharedPreferences("cart_items",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("cartitemcount",String.valueOf(cartLists.size()));
                                editor.apply();

                                tv_total_amt.setText("Total Amount : \u20B9"+String.valueOf(total_price));

                                total_qty = String.valueOf(cartLists.size());
                                Cart_adapter completePackageAdapter = new Cart_adapter(cartLists,MyCart.this);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
                                rv_my_cart.setLayoutManager(mLayoutManager);
                                rv_my_cart.setAdapter(completePackageAdapter);

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


    public class Cart_adapter extends RecyclerView.Adapter<Cart_adapter.MyViewHolder> {

        List<cart_list> itemList = new ArrayList<>();
        Context context;

        Dialogs dialogs;
        
        public Cart_adapter(List<cart_list> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }
        @NonNull
        @Override
        public Cart_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new Cart_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull Cart_adapter.MyViewHolder holder, int position) {

//            holder.tv_offer_value.setText(itemList.get(position).getDiscountValue()+"%");

            holder.tv_offer_value.setVisibility(View.GONE);
            holder.tv_product_name.setText(itemList.get(position).getProdDetail().getProdName());
            holder.tv_product_short_desc.setText(itemList.get(position).getProdDetail().getProdDesc());
            holder.tv_price.setText("MRP : \u20B9"+itemList.get(position).getRate()+" x "+itemList.get(position).getQty()+" = "+String.valueOf(Double.parseDouble(itemList.get(position).getQty())*Double.parseDouble(itemList.get(position).getProdDetail().getRetailPrice())));
//            holder.tv_discounted_price.setText("Our Price : \u20B9"+itemList.get(position).getDiscountPrice());
//            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.tv_discounted_price.setVisibility(View.GONE);

            holder.et_qty.setText("Qty : "+itemList.get(position).getQty());

            if(itemList.get(position).getImages().size()>0) {
                Glide.with(context)
                        .load("http://43.230.198.115/paneka" + itemList.get(position).getImages().get(0).getImagePath().replace("~",""))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(holder.iv_product);

            }
            holder.ll_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SpotsDialog spotsDialog = new SpotsDialog(context);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"remove_cart",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        spotsDialog.dismiss();
                                        Log.e("response","response");
                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {

                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                                            sweetAlertDialog.setTitleText("My Cart")
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

                                          getCartItem();

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
                            map.put("Saleid",""+itemList.get(position).getSaleid());
                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            60000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    stringRequest.setShouldCache(false);
                    requestQueue.add(stringRequest);

                }
            });


            holder.et_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogs = new Dialogs(context);
                    List<String> qty =new ArrayList<>();
                    qty.add("1");
                    qty.add("2");
                    qty.add("3");
                    qty.add("4");
                    qty.add("5");
                    qty.add("6");
                    qty.add("7");
                    qty.add("8");
                    qty.add("9");
                    qty.add("10");

                    String selectedposition="0";
                    dialogs.singleSelectDialog(qty, holder.et_qty, "Select Quantity", selectedposition);
                    String quantity = holder.et_qty.getText().toString();

                }
            });


            holder.iv_add_to_fvrt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SpotsDialog spotsDialog = new SpotsDialog(context);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"Favorite_add",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        spotsDialog.dismiss();

                                        Log.e("response",""+response);
                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {

                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                                            sweetAlertDialog.setTitleText("Add to Favourite")
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

                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                            sweetAlertDialog.setTitleText("Add to Favourite")
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
                            map.put("Login_Id",getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
                            map.put("Prod_ID",""+itemList.get(position).getProdDetail().getProdID());
                            map.put("Qty",holder.et_qty.getText().toString().replace("Qty : ",""));
                            map.put("Rate",itemList.get(position).getProdDetail().getRetailPrice());
                            map.put("Total",String.valueOf(Double.parseDouble(itemList.get(position).getQty())*Double.parseDouble(itemList.get(position).getProdDetail().getRetailPrice())));

                            Log.e("add_to_fvrt",""+map);

                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            60000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    stringRequest.setShouldCache(false);
                    requestQueue.add(stringRequest);


                }
            });


            holder.et_qty.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {}

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {

                    SpotsDialog spotsDialog = new SpotsDialog(context);
                    spotsDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"add_cart",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        spotsDialog.dismiss();

                                        Log.e("response",""+response);
                                        JSONObject object = new JSONObject(response);
                                        String status = object.getString("status");

                                        if (status.equalsIgnoreCase("0")) {

                                            SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                                            sweetAlertDialog.setTitleText("Add to cart")
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

                                          getCartItem();
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
                            map.put("Saleid",""+itemList.get(position).getSaleid());
                            map.put("Qty",holder.et_qty.getText().toString().replace("Qty:",""));
                            map.put("Rate",itemList.get(position).getProdDetail().getRetailPrice());
                            map.put("Total",String.valueOf(Double.parseDouble(holder.et_qty.getText().toString().replace("Qty:", ""))*Double.parseDouble(itemList.get(position).getProdDetail().getRetailPrice())));
                            Log.e("Params_update_cart",""+map);
                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            60000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    stringRequest.setShouldCache(false);
                    requestQueue.add(stringRequest);

                }
            });

        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_offer_value, tv_product_name, tv_product_short_desc, tv_price, tv_discounted_price,tv_delete;
            LinearLayout ll_product;
            ImageView iv_product,iv_add_to_fvrt;
            EditText et_qty;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_offer_value = itemView.findViewById(R.id.tv_offer_value);
                iv_add_to_fvrt = itemView.findViewById(R.id.iv_add_to_fvrt);

                et_qty = itemView.findViewById(R.id.et_qty);

                tv_product_name = itemView.findViewById(R.id.tv_product_name);
                tv_delete = itemView.findViewById(R.id.tv_delete);

                tv_product_short_desc = itemView.findViewById(R.id.tv_product_short_desc);
                tv_price = itemView.findViewById(R.id.tv_price);

                tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
                ll_product = itemView.findViewById(R.id.ll_product);
                iv_product = itemView.findViewById(R.id.iv_product);

            }
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
