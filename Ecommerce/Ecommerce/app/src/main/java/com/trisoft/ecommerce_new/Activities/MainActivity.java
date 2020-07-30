package com.trisoft.ecommerce_new.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.trisoft.ecommerce_new.BestSellers.bestseller_item_list;
import com.trisoft.ecommerce_new.BuildConfig;
import com.trisoft.ecommerce_new.Cart.MyCart;
import com.trisoft.ecommerce_new.Category.Subcategories;
import com.trisoft.ecommerce_new.Category.category_list_model;
import com.trisoft.ecommerce_new.Fragments.Account;
import com.trisoft.ecommerce_new.Fragments.Help_support;
import com.trisoft.ecommerce_new.Intro.Login;

import com.trisoft.ecommerce_new.Offers.Offer;
import com.trisoft.ecommerce_new.Orders.MyOrders;
import com.trisoft.ecommerce_new.Products.Product_by_cat;
import com.trisoft.ecommerce_new.Products.favourite_product_list;
import com.trisoft.ecommerce_new.Products.items_list_model;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.RecentlyAdded.recently_added_item_list;
import com.trisoft.ecommerce_new.Search.Search_product;
import com.trisoft.ecommerce_new.Utils.Dialogs;
import com.trisoft.ecommerce_new.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_cat, rv_offer_product,rv_favorite_products,rv_brands,rv_recently_added,rv_bestsellers;
    TextView tv_location,tv_item_counter,tv_favorite;
    ImageView iv_banner;
    RelativeLayout rl_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        EditText et_search = findViewById(R.id.et_search);
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Search_product.class));
            }
        });

        rv_cat = findViewById(R.id.rv_cat);
        rv_brands = findViewById(R.id.rv_recently_added);

        rv_favorite_products = findViewById(R.id.rv_favorite_products);
        tv_favorite = findViewById(R.id.tv_favorite);

        rv_recently_added = findViewById(R.id.rv_recently_added);
        rv_bestsellers = findViewById(R.id.rv_bestsellers);

        rl_cart = findViewById(R.id.rl_cart);

        rv_offer_product = findViewById(R.id.rv_offer_product);
        tv_location = findViewById(R.id.tv_location);
        iv_banner = findViewById(R.id.iv_banner);


        ImageView iv_bar_code_scan = findViewById(R.id.iv_bar_code_scan);
        iv_bar_code_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), com.trisoft.ecommerce_new.barcodescanner.activity.MainActivity.class));

                }else{
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Barcode Scan")
                            .setContentText("To Barcode scan shopping must login.")
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

        ImageView iv_edit_loc = findViewById(R.id.iv_edit_loc);
        iv_edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), Account.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE);
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



        tv_item_counter = findViewById(R.id.tv_item_counter);
        if(new SessionManager(getApplicationContext()).isLoggedIn()) {
            SharedPreferences sp = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
            tv_item_counter.setText(sp.getString("cartitemcount", "0"));

        }else{
            tv_item_counter.setText("0");
        }


        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), MyCart.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
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




        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id","");
        String name = sharedPreferences.getString("name","");
        String email = sharedPreferences.getString("email","");
        String mobile = sharedPreferences.getString("mobile","");
        String city = sharedPreferences.getString("city","");
        String pincode = sharedPreferences.getString("pincode","");
        String address = sharedPreferences.getString("address","");



        if(new SessionManager(getApplicationContext()).isLoggedIn()){

            tv_location.setText("Location\n"+address+","+city);

        }else{

            tv_location.setText("Location\nYour shipping address ");

        }


       LinearLayout ll_cart = findViewById(R.id.ll_cart);
        ll_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SessionManager(getApplicationContext()).isLoggedIn()) {

                    startActivity(new Intent(getApplicationContext(), MyCart.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
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

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE);
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
            }
        });


        LinearLayout ll_offers = findViewById(R.id.ll_offers);
        ll_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Offer.class));
            }
        });


//        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"banner_img",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//
//
//                            Log.e("response","response");
//                            JSONObject object = new JSONObject(response);
//                            String status = object.getString("status");
//
//                            if (status.equalsIgnoreCase("0")) {
//
//
//                                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
//                                sweetAlertDialog.setTitleText("Login")
//                                        .setContentText(object.getString("message"))
//                                        .setConfirmText("Ok")
//                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                sweetAlertDialog.dismiss();
//                                            }
//                                        }).setCancelable(false);
//                                sweetAlertDialog.show();
//
//                            }
//                            else {
//
//
//                                JSONObject UserData = object.getJSONObject("Data");
//                                Glide.with(MainActivity.this)
//                                        .load(UserData.getString("image"))
//                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                        .skipMemoryCache(true)
//                                        .into(iv_banner);
//
//                            }
//                        } catch (JSONException e) {
//
//                            Log.e("JSONException",e.toString());
//                            e.printStackTrace();
//                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        if (error instanceof com.android.volley.NetworkError) {
//
//                        } else if (error instanceof ServerError) {
//
//                        } else if (error instanceof AuthFailureError) {
//
//                        } else if (error instanceof ParseError) {
//
//                        } else if (error instanceof NoConnectionError) {
//
//                        } else if (error instanceof TimeoutError) {
//
//                        }
//                    }
//                })
//            {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<String, String>();
//
//                return map;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                60000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        stringRequest.setShouldCache(false);
//        requestQueue.add(stringRequest);

        ImageView iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
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

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
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

                        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
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

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
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




        getCategoryList();

        getOfferProduct();

        getBestsellerProduct();

        getRecentProduct();

        if(new SessionManager(getApplicationContext()).isLoggedIn()) {

            rv_favorite_products.setVisibility(View.VISIBLE);
            tv_favorite.setVisibility(View.VISIBLE);
            getFavouriteProduct();

        }else{

            rv_favorite_products.setVisibility(View.GONE);
            tv_favorite.setVisibility(View.GONE);
        }

    }


    private void getRecentProduct() {

        SpotsDialog dialog = new SpotsDialog(MainActivity.this);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "Recent_products",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");


                            dialog.dismiss();

                            if (status.equalsIgnoreCase("0")) {


                            } else {
                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<recently_added_item_list> noticesList = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {

                                    recently_added_item_list noticeList = gson.fromJson(array.getJSONObject(i).toString(), recently_added_item_list.class);
                                    noticesList.add(noticeList);

                                }


                                recently_added_prod_adapter completePackageAdapter = new recently_added_prod_adapter(noticesList, MainActivity.this);
                                rv_recently_added.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                                rv_recently_added.setAdapter(completePackageAdapter);

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

                map.put("Login_Id",getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
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



    private void getBestsellerProduct() {

        SpotsDialog dialog = new SpotsDialog(MainActivity.this);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "Best_sellers_product",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            Log.e("response_bestseller",""+response);


                            dialog.dismiss();

                            if (status.equalsIgnoreCase("0")) {


                            } else {
                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<bestseller_item_list> noticesList = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {

                                    bestseller_item_list noticeList = gson.fromJson(array.getJSONObject(i).toString(), bestseller_item_list.class);
                                    noticesList.add(noticeList);

                                }


                                Bestseller_prod_adapter completePackageAdapter = new Bestseller_prod_adapter(noticesList, MainActivity.this);
                                rv_bestsellers.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                                rv_bestsellers.setAdapter(completePackageAdapter);

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


    private void getFavouriteProduct() {

        SpotsDialog dialog = new SpotsDialog(MainActivity.this);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "Favorite_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");


                            dialog.dismiss();

                            if (status.equalsIgnoreCase("0")) {


                            } else {
                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<favourite_product_list> noticesList = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {

                                    favourite_product_list noticeList = gson.fromJson(array.getJSONObject(i).toString(), favourite_product_list.class);
                                    noticesList.add(noticeList);

                                }


                                favourite_prod_adapter completePackageAdapter = new favourite_prod_adapter(noticesList, MainActivity.this);
                                rv_favorite_products.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                                rv_favorite_products.setAdapter(completePackageAdapter);

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

                map.put("Login_Id",getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
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

    private void getOfferProduct() {

        SpotsDialog dialog = new SpotsDialog(MainActivity.this);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "Product_latest",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            dialog.dismiss();

                            if (status.equalsIgnoreCase("0")) {

//

                            } else {
                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<items_list_model> noticesList = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {

                                    items_list_model noticeList = gson.fromJson(array.getJSONObject(i).toString(), items_list_model.class);
                                    noticesList.add(noticeList);

                                }


                                items_adapter completePackageAdapter = new items_adapter(noticesList, MainActivity.this);
                                rv_offer_product.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                                rv_offer_product.setAdapter(completePackageAdapter);

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

    private void getCategoryList() {

        SpotsDialog dialog = new SpotsDialog(MainActivity.this);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "list_category",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            dialog.dismiss();

                            if (status.equalsIgnoreCase("0")) {

                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText("Category")
                                        .setContentText("No Category found")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(sweetAlertDialog1 -> {
                                            sweetAlertDialog.dismiss();
                                            finish();
                                        }).show();
                                sweetAlertDialog.setCancelable(false);

                            } else {
                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<category_list_model> noticesList = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {

                                    category_list_model noticeList = gson.fromJson(array.getJSONObject(i).toString(), category_list_model.class);
                                    noticesList.add(noticeList);

                                }


                                topics_adapter completePackageAdapter = new topics_adapter(noticesList, getApplicationContext());
                                rv_cat.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                                rv_cat.setAdapter(completePackageAdapter);

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



    public class items_adapter extends RecyclerView.Adapter<items_adapter.MyViewHolder> {

        List<items_list_model> itemList = new ArrayList<>();
        Context context;
        Dialogs dialogs;

        public items_adapter(List<items_list_model> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }

        @NonNull
        @Override
        public items_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new items_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull items_adapter.MyViewHolder holder, int position) {

            holder.tv_offer_value.setVisibility(View.GONE);

//            holder.tv_product_name.setText(itemList.get(position).getProdName());
//            holder.tv_product_short_desc.setText(itemList.get(position).getProdDesc()+"\n"+itemList.get(position).getUnit());
//            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getRetailPrice());
            holder.tv_product_name.setText(itemList.get(position).getBrand());
            holder.tv_product_short_desc.setText(itemList.get(position).getProductType()+" - "+itemList.get(position).getProdName()+"\n"+itemList.get(position).getUnit());
            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getRetailPrice());

            holder.tv_discounted_price.setVisibility(View.GONE);
//            holder.tv_discounted_price.setText("Our Price : \u20B9" + itemList.get(position).getSavePrice());
//            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(itemList.get(position).getImages().size()>0) {


                Glide.with(context)
                        .load("http://43.230.198.115/paneka"+itemList.get(position).getImages().get(0).getImagePath().replace("~",""))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.pepsi_can)
                        .into(holder.iv_product);
            }

            holder.iv_product.setVisibility(View.VISIBLE);

            holder.ll_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = getSharedPreferences("prod_detail",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("product_id",""+itemList.get(position).getProdID());
                    editor.putString("product_name",itemList.get(position).getProdName());
                    editor.putString("product_type",itemList.get(position).getProductType());
                    editor.putString("product_brand",itemList.get(position).getBrand());
                    editor.putString("product_unit",itemList.get(position).getUnit());
                    editor.putString("product_mrp",itemList.get(position).getRetailPrice());
                    editor.putString("product_desc",""+itemList.get(position).getProdDesc());
                    editor.commit();
                    editor.apply();


                    Intent in = new Intent(context,Product_detail.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);

                }
            });

            holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (new SessionManager(getApplicationContext()).isLoggedIn()) {

                        SpotsDialog spotsDialog = new SpotsDialog(context);
                        spotsDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "add_cart",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            spotsDialog.dismiss();
                                            Log.e("response", "response");
                                            JSONObject object = new JSONObject(response);
                                            String status = object.getString("status");

                                            if (status.equalsIgnoreCase("0")) {

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
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

                                            } else {

                                                tv_item_counter.setText(String.valueOf(Integer.parseInt(tv_item_counter.getText().toString()) + 1));

                                                SharedPreferences sp = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putString("cartitemcount", tv_item_counter.getText().toString());
                                                editor.apply();

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                                sweetAlertDialog.setTitleText("Add to cart")
                                                        .setContentText("Item added to cart successfully")
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
                                            Log.e("JSONException", e.toString());
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
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("Prod_id", String.valueOf(itemList.get(position).getProdID()));
                                map.put("Qty", holder.et_qty.getText().toString().replace("Qty:", ""));
                                map.put("Loginid", context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id", ""));
                                map.put("Rate",itemList.get(position).getRetailPrice());
                                map.put("Total",String.valueOf(Double.parseDouble(holder.et_qty.getText().toString().replace("Qty:", ""))*Double.parseDouble(itemList.get(position).getRetailPrice())));
                                Log.e("Params_add_to_cart", "" + map);
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

                    } else {

                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Login")
                                .setContentText("To Add product in cart,you must Login")
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

        }

        @Override
        public int getItemCount() {

            return itemList.size();

        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_offer_value, tv_product_name, tv_product_short_desc,tv_price,tv_discounted_price,tv_add_to_cart;
            LinearLayout ll_product;
            ImageView iv_product;
            EditText et_qty;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_offer_value = itemView.findViewById(R.id.tv_offer_value);
                et_qty = itemView.findViewById(R.id.et_qty);

                tv_product_name = itemView.findViewById(R.id.tv_product_name);
                tv_product_short_desc = itemView.findViewById(R.id.tv_product_short_desc);
                tv_price = itemView.findViewById(R.id.tv_price);
                tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);

                tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
                ll_product = itemView.findViewById(R.id.ll_product);
                iv_product = itemView.findViewById(R.id.iv_product);

            }
        }
    }


    public class favourite_prod_adapter extends RecyclerView.Adapter<favourite_prod_adapter.MyViewHolder> {

        List<favourite_product_list> itemList = new ArrayList<>();
        Context context;
        Dialogs dialogs;

        public favourite_prod_adapter(List<favourite_product_list> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }

        @NonNull
        @Override
        public favourite_prod_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new favourite_prod_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull favourite_prod_adapter.MyViewHolder holder, int position) {

            holder.tv_offer_value.setVisibility(View.GONE);

//            holder.tv_product_name.setText(itemList.get(position).getProdDetail().getProdName());
//            holder.tv_product_short_desc.setText(itemList.get(position).getProdDetail().getProdDesc()+"\n"+itemList.get(position).getProdDetail().getUnit());
//            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getProdDetail().getRetailPrice());
            holder.tv_discounted_price.setVisibility(View.GONE);

            holder.tv_product_name.setText(itemList.get(position).getProdDetail().getBrand());
            holder.tv_product_short_desc.setText(itemList.get(position).getProdDetail().getProductType()+" - "+itemList.get(position).getProdDetail().getProdName()+"\n"+itemList.get(position).getProdDetail().getUnit());
            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getProdDetail().getRetailPrice());

//            holder.tv_discounted_price.setText("Our Price : \u20B9" + itemList.get(position).getSavePrice());
//            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(itemList.get(position).getImages().size()>0) {


                Glide.with(context)
                        .load(""+"http://43.230.198.115/paneka"+itemList.get(position).getImages().get(0).getImagePath().replace("~",""))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.pepsi_can)
                        .into(holder.iv_product);
            }


            holder.iv_product.setVisibility(View.VISIBLE);

            holder.ll_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sp = getSharedPreferences("prod_detail",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("product_id",""+itemList.get(position).getProdID());
                    editor.putString("product_name",itemList.get(position).getProdDetail().getProdName());
                    editor.putString("product_type",itemList.get(position).getProdDetail().getProductType());
                    editor.putString("product_brand",itemList.get(position).getProdDetail().getBrand());
                    editor.putString("product_unit",itemList.get(position).getProdDetail().getUnit());
                    editor.putString("product_mrp",itemList.get(position).getProdDetail().getRetailPrice());
                    editor.putString("product_desc",""+itemList.get(position).getProdDetail().getProdDesc());
                    editor.commit();
                    editor.apply();


                    Intent in = new Intent(context,Product_detail.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
            });

            holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (new SessionManager(getApplicationContext()).isLoggedIn()) {


                        SpotsDialog spotsDialog = new SpotsDialog(context);
                        spotsDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "add_cart",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            spotsDialog.dismiss();
                                            Log.e("response", "response");
                                            JSONObject object = new JSONObject(response);
                                            String status = object.getString("status");

                                            if (status.equalsIgnoreCase("0")) {

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
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

                                            } else {

                                                tv_item_counter.setText(String.valueOf(Integer.parseInt(tv_item_counter.getText().toString()) + 1));

                                                SharedPreferences sp = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putString("cartitemcount", tv_item_counter.getText().toString());
                                                editor.apply();

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                                sweetAlertDialog.setTitleText("Add to cart")
                                                        .setContentText("Item added to cart successfully")
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
                                            Log.e("JSONException", e.toString());
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
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("Prod_id", String.valueOf(itemList.get(position).getProdID()));
                                map.put("Qty", holder.et_qty.getText().toString().replace("Qty:", ""));
                                map.put("Loginid", context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id", ""));
                                map.put("Rate",itemList.get(position).getProdDetail().getRetailPrice());
                                map.put("Total",String.valueOf(Double.parseDouble(holder.et_qty.getText().toString().replace("Qty:", ""))*Double.parseDouble(itemList.get(position).getProdDetail().getRetailPrice())));
                                Log.e("Params_add_to_cart", "" + map);
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


                    } else {

                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Login")
                                .setContentText("To Add product in cart,you must Login")
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
                    String quantity = holder.et_qty.getText().toString().replace("Qty:","");

                }
            });
        }

        @Override
        public int getItemCount() {

            return itemList.size();

        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_offer_value, tv_product_name, tv_product_short_desc,tv_price,tv_discounted_price,tv_add_to_cart;
            LinearLayout ll_product;
            ImageView iv_product;
            EditText et_qty;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_offer_value = itemView.findViewById(R.id.tv_offer_value);
                et_qty = itemView.findViewById(R.id.et_qty);

                tv_product_name = itemView.findViewById(R.id.tv_product_name);
                tv_product_short_desc = itemView.findViewById(R.id.tv_product_short_desc);
                tv_price = itemView.findViewById(R.id.tv_price);

                tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);


                tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
                ll_product = itemView.findViewById(R.id.ll_product);
                iv_product = itemView.findViewById(R.id.iv_product);

            }
        }
    }


    public class Bestseller_prod_adapter extends RecyclerView.Adapter<Bestseller_prod_adapter.MyViewHolder> {

        List<bestseller_item_list> itemList = new ArrayList<>();
        Context context;
        Dialogs dialogs;

        public Bestseller_prod_adapter(List<bestseller_item_list> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }

        @NonNull
        @Override
        public Bestseller_prod_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new Bestseller_prod_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull Bestseller_prod_adapter.MyViewHolder holder, int position) {

            holder.tv_offer_value.setVisibility(View.GONE);

//            holder.tv_product_name.setText(itemList.get(position).getProdDetail().getProdName());
//            holder.tv_product_short_desc.setText(itemList.get(position).getProdDetail().getProdDesc()+"\n"+itemList.get(position).getProdDetail().getUnit());
//            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getProdDetail().getRetailPrice());
            holder.tv_discounted_price.setVisibility(View.GONE);

            holder.tv_product_name.setText(itemList.get(position).getBrand());
            holder.tv_product_short_desc.setText(itemList.get(position).getProductType()+" - "+itemList.get(position).getProdName()+"\n"+itemList.get(position).getUnit());
            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getRetailPrice());

//            holder.tv_discounted_price.setText("Our Price : \u20B9" + itemList.get(position).getSavePrice());
//            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(itemList.get(position).getImages().size()>0) {


                Glide.with(context)
                        .load("http://43.230.198.115/paneka"+itemList.get(position).getImages().get(0).getImagePath().replace("~",""))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.pepsi_can)
                        .into(holder.iv_product);
            }


            holder.iv_product.setVisibility(View.VISIBLE);

            holder.ll_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = getSharedPreferences("prod_detail",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("product_id",""+itemList.get(position).getProdID());
                    editor.putString("product_name",itemList.get(position).getProdName());
                    editor.putString("product_type",itemList.get(position).getProductType());
                    editor.putString("product_brand",itemList.get(position).getBrand());
                    editor.putString("product_unit",itemList.get(position).getUnit());
                    editor.putString("product_mrp",itemList.get(position).getRetailPrice());
                    editor.putString("product_desc",""+itemList.get(position).getProdDesc());
                    editor.commit();
                    editor.apply();

                    Intent in = new Intent(context,Product_detail.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
            });

            holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (new SessionManager(getApplicationContext()).isLoggedIn()) {


                        SpotsDialog spotsDialog = new SpotsDialog(context);
                        spotsDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "add_cart",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            spotsDialog.dismiss();
                                            Log.e("response", "response");
                                            JSONObject object = new JSONObject(response);
                                            String status = object.getString("status");

                                            if (status.equalsIgnoreCase("0")) {

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
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

                                            } else {

                                                tv_item_counter.setText(String.valueOf(Integer.parseInt(tv_item_counter.getText().toString()) + 1));

                                                SharedPreferences sp = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putString("cartitemcount", tv_item_counter.getText().toString());
                                                editor.apply();

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                                sweetAlertDialog.setTitleText("Add to cart")
                                                        .setContentText("Item added to cart successfully")
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
                                            Log.e("JSONException", e.toString());
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
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("Prod_id", String.valueOf(itemList.get(position).getProdID()));
                                map.put("Qty", holder.et_qty.getText().toString().replace("Qty:", ""));
                                map.put("Loginid", context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id", ""));
                                map.put("Rate",itemList.get(position).getRetailPrice());
                                map.put("Total",String.valueOf(Double.parseDouble(holder.et_qty.getText().toString().replace("Qty:", ""))*Double.parseDouble(itemList.get(position).getRetailPrice())));
                                Log.e("Params_add_to_cart", "" + map);
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


                    } else {

                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Login")
                                .setContentText("To Add product in cart,you must Login")
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
                    String quantity = holder.et_qty.getText().toString().replace("Qty:","");

                }
            });
        }

        @Override
        public int getItemCount() {

            return itemList.size();

        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_offer_value, tv_product_name, tv_product_short_desc,tv_price,tv_discounted_price,tv_add_to_cart;
            LinearLayout ll_product;
            ImageView iv_product;
            EditText et_qty;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_offer_value = itemView.findViewById(R.id.tv_offer_value);
                et_qty = itemView.findViewById(R.id.et_qty);

                tv_product_name = itemView.findViewById(R.id.tv_product_name);
                tv_product_short_desc = itemView.findViewById(R.id.tv_product_short_desc);
                tv_price = itemView.findViewById(R.id.tv_price);

                tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);


                tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
                ll_product = itemView.findViewById(R.id.ll_product);
                iv_product = itemView.findViewById(R.id.iv_product);

            }
        }
    }



    public class recently_added_prod_adapter extends RecyclerView.Adapter<recently_added_prod_adapter.MyViewHolder> {

        List<recently_added_item_list> itemList = new ArrayList<>();
        Context context;
        Dialogs dialogs;

        public recently_added_prod_adapter(List<recently_added_item_list> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }

        @NonNull
        @Override
        public recently_added_prod_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new recently_added_prod_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull recently_added_prod_adapter.MyViewHolder holder, int position) {

            holder.tv_offer_value.setVisibility(View.GONE);

//            holder.tv_product_name.setText(itemList.get(position).getProdDetail().getProdName());
//            holder.tv_product_short_desc.setText(itemList.get(position).getProdDetail().getProdDesc()+"\n"+itemList.get(position).getProdDetail().getUnit());
//            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getProdDetail().getRetailPrice());
            holder.tv_discounted_price.setVisibility(View.GONE);

            holder.tv_product_name.setText(itemList.get(position).getBrand());
            holder.tv_product_short_desc.setText(itemList.get(position).getProductType()+" - "+itemList.get(position).getProdName()+"\n"+itemList.get(position).getUnit());
            holder.tv_price.setText("MRP : \u20B9" + itemList.get(position).getRetailPrice());

//            holder.tv_discounted_price.setText("Our Price : \u20B9" + itemList.get(position).getSavePrice());
//            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(itemList.get(position).getImages().size()>0) {


                Log.e("image_path",""+"http://43.230.198.115/paneka/"+itemList.get(position).getImages().get(0).getImagePath());

                Glide.with(context)
                        .load("http://43.230.198.115/paneka"+itemList.get(position).getImages().get(0).getImagePath().replace("~",""))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.pepsi_can)
                        .into(holder.iv_product);
            }


            holder.iv_product.setVisibility(View.VISIBLE);

            holder.ll_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sp = getSharedPreferences("prod_detail",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("product_id",""+itemList.get(position).getProdID());
                    editor.putString("product_name",itemList.get(position).getProdName());
                    editor.putString("product_type",itemList.get(position).getProductType());
                    editor.putString("product_brand",itemList.get(position).getBrand());
                    editor.putString("product_unit",itemList.get(position).getUnit());
                    editor.putString("product_mrp",itemList.get(position).getRetailPrice());
                    editor.putString("product_desc",""+itemList.get(position).getProdDesc());
                    editor.commit();
                    editor.apply();

                    Intent in = new Intent(context,Product_detail.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
            });

            holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (new SessionManager(getApplicationContext()).isLoggedIn()) {


                        SpotsDialog spotsDialog = new SpotsDialog(context);
                        spotsDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "add_cart",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            spotsDialog.dismiss();
                                            Log.e("response", "response");
                                            JSONObject object = new JSONObject(response);
                                            String status = object.getString("status");

                                            if (status.equalsIgnoreCase("0")) {

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
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

                                            } else {

                                                tv_item_counter.setText(String.valueOf(Integer.parseInt(tv_item_counter.getText().toString()) + 1));

                                                SharedPreferences sp = getSharedPreferences("cart_items", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putString("cartitemcount", tv_item_counter.getText().toString());
                                                editor.apply();

                                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                                sweetAlertDialog.setTitleText("Add to cart")
                                                        .setContentText("Item added to cart successfully")
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
                                            Log.e("JSONException", e.toString());
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
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> map = new HashMap<String, String>();
                                map.put("Prod_id", String.valueOf(itemList.get(position).getProdID()));
                                map.put("Qty", holder.et_qty.getText().toString().replace("Qty:", ""));
                                map.put("Loginid", context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id", ""));
                                map.put("Rate",itemList.get(position).getRetailPrice());
                                map.put("Total",String.valueOf(Double.parseDouble(holder.et_qty.getText().toString().replace("Qty:", ""))*Double.parseDouble(itemList.get(position).getRetailPrice())));
                                Log.e("Params_add_to_cart", "" + map);
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


                    } else {

                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Login")
                                .setContentText("To Add product in cart,you must Login")
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
                    String quantity = holder.et_qty.getText().toString().replace("Qty:","");

                }
            });
        }

        @Override
        public int getItemCount() {

            return itemList.size();

        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_offer_value, tv_product_name, tv_product_short_desc,tv_price,tv_discounted_price,tv_add_to_cart;
            LinearLayout ll_product;
            ImageView iv_product;
            EditText et_qty;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_offer_value = itemView.findViewById(R.id.tv_offer_value);
                et_qty = itemView.findViewById(R.id.et_qty);

                tv_product_name = itemView.findViewById(R.id.tv_product_name);
                tv_product_short_desc = itemView.findViewById(R.id.tv_product_short_desc);
                tv_price = itemView.findViewById(R.id.tv_price);

                tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);


                tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
                ll_product = itemView.findViewById(R.id.ll_product);
                iv_product = itemView.findViewById(R.id.iv_product);

            }
        }
    }

    public class topics_adapter extends RecyclerView.Adapter<topics_adapter.MyViewHolder> {

        List<category_list_model> candidateList = new ArrayList<>();
        Context context;

        public topics_adapter(List<category_list_model> candidateList, Context context) {
            this.candidateList = candidateList;
            this.context = context;
        }
        @NonNull
        @Override
        public topics_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new topics_adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull topics_adapter.MyViewHolder holder, int position) {

            Random rnd = new Random();
            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));


            holder.tv_cat_name.setText(candidateList.get(position).getCategoryName());
//            holder.iv_cat.setColorFilter(currentColor);

//            Glide.with(context)
//                    .load("http://43.230.198.115/paneka"+candidateList.get(position).getImages().replace("~",""))
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .error(R.drawable.beauty_hygine)
//                    .into(holder.iv_cat);


            holder.ll_cat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sp = getSharedPreferences("Cat_detail", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("cat_id", String.valueOf(candidateList.get(position).getID()));
                    editor.putString("cat_name", candidateList.get(position).getCategoryName());
                    editor.commit();
                    editor.apply();

                    Intent in = new Intent(context, Subcategories.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);

                }
            });
        }
        @Override
        public int getItemCount() {

            return candidateList.size();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_cat_name;
            LinearLayout ll_cat;
            ImageView iv_cat;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_cat_name = itemView.findViewById(R.id.tv_cat_name);
                ll_cat = itemView.findViewById(R.id.ll_cat);
                iv_cat = itemView.findViewById(R.id.iv_cat);
            }
        }
    }


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}