package com.trisoft.ecommerce_new.barcodescanner.fragment;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.razorpay.PaymentResultListener;
import com.trisoft.ecommerce_new.Activities.Add_address;
import com.trisoft.ecommerce_new.Activities.Checkout;
import com.trisoft.ecommerce_new.Activities.MainActivity;
import com.trisoft.ecommerce_new.BuildConfig;
import com.trisoft.ecommerce_new.Cart.MyCart;
import com.trisoft.ecommerce_new.Fragments.Account;
import com.trisoft.ecommerce_new.Fragments.Help_support;
import com.trisoft.ecommerce_new.Intro.Login;
import com.trisoft.ecommerce_new.Offers.Offer;
import com.trisoft.ecommerce_new.Orders.MyOrders;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.Utils.Dialogs;
import com.trisoft.ecommerce_new.Utils.SessionManager;

import com.trisoft.ecommerce_new.barcodescanner.activity.Make_barcode_payment;
import com.trisoft.ecommerce_new.barcodescanner.model.barcode_cart_items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

/**
 * Created by PT on 2/9/2017.
 */

public class ProductListFragment extends Fragment implements PaymentResultListener {


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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_prduct_list,container,false);


        rv_my_cart =view.findViewById(R.id.rv_my_cart);
        tv_total_amt =view.findViewById(R.id.tv_total_amt);
        ll_bottom =view.findViewById(R.id.ll_bottom1);


        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        tv_item_counter = view.findViewById(R.id.tv_item_counter);
        rl_cart = view.findViewById(R.id.rl_cart);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id","");
        String name = sharedPreferences.getString("name","");
        String email = sharedPreferences.getString("email","");
        String mobile = sharedPreferences.getString("mobile","");
        String city = sharedPreferences.getString("city","");
        String pincode = sharedPreferences.getString("pincode","");
        String address = sharedPreferences.getString("address","");



        if(new SessionManager(getActivity()).isLoggedIn()){

            tv_title.setText("Location\n"+address+","+city);

        }else{

            tv_title.setText("Location\nYour shipping address ");

        }

        ImageView iv_edit_loc = view.findViewById(R.id.iv_edit_loc);
        iv_edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SessionManager(getActivity()).isLoggedIn()) {

                    startActivity(new Intent(getActivity(), Account.class));

                }else{

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE);
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
                                    startActivity(new Intent(getActivity(), Login.class));

                                }
                            }).setCancelable(false);
                    sweetAlertDialog.show();
                }
            }
        });


        btn_proceed_now = view.findViewById(R.id.btn_proceed_now);

        btn_proceed_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getActivity().getSharedPreferences("cart_item", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("total_amt",""+total_price);
                editor.putString("total_qty",""+total_qty);
                editor.commit();
                editor.apply();
                startActivity(new Intent(getActivity(), Make_barcode_payment.class));


            }
        });


        getCartItem();

        return  view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
getCartItem();
        }
    }

    private void getCartItem() {

        SpotsDialog dialog = new SpotsDialog(getActivity());
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "barcode_product_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("barcode_cart_resp",""+response);
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            dialog.dismiss();




                            if (status.equalsIgnoreCase("0")) {


//                                SharedPreferences sp = getActivity().getSharedPreferences("cart_items",Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sp.edit();
//                                editor.putString("cartitemcount",String.valueOf(0));
//                                editor.apply();
//                                editor.commit();
//
//                                tv_item_counter.setText(String.valueOf(0));


                                rv_my_cart.setVisibility(View.GONE);
                                ll_bottom.setVisibility(View.GONE);

//                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
//                                sweetAlertDialog.setTitleText("My Cart")
//                                        .setContentText("No item found")
//                                        .setConfirmText("Shop now")
//                                        .setConfirmClickListener(sweetAlertDialog1 -> {
//                                            sweetAlertDialog.dismiss();
//                                            startActivity(new Intent(getActivity(), MainActivity.class));
//                                        }).show();
//
//                                sweetAlertDialog.setCancelable(false);

                            } else {

                                rv_my_cart.setVisibility(View.VISIBLE);
                                ll_bottom.setVisibility(View.VISIBLE);

                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<barcode_cart_items> cartLists = new ArrayList<>();
                                cartLists.clear();
                                total_price =0.0;

                                for (int i = 0; i < array.length(); i++) {
                                    barcode_cart_items cartList = gson.fromJson(array.getJSONObject(i).toString(), barcode_cart_items.class);
                                    cartLists.add(cartList);
                                    total_price+= Double.parseDouble(cartList.getProductDetail().getMRP())*Double.parseDouble(""+cartList.getQty());

                                }


                                tv_total_amt.setText("Total Amount : \u20B9"+String.valueOf(total_price));

                                total_qty = String.valueOf(cartLists.size());
                                Cart_adapter completePackageAdapter = new Cart_adapter(cartLists,getActivity());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
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
                map.put("user_id",getActivity().getSharedPreferences("UserData", MODE_PRIVATE).getString("user_id",""));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    public class Cart_adapter extends RecyclerView.Adapter<Cart_adapter.MyViewHolder> {

        List<barcode_cart_items> itemList = new ArrayList<>();
        Context context;

        Dialogs dialogs;

        public Cart_adapter(List<barcode_cart_items> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }
        @NonNull
        @Override
        public Cart_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull Cart_adapter.MyViewHolder holder, int position) {

//            holder.tv_offer_value.setText(itemList.get(position).getDiscountValue()+"%");

            holder.tv_offer_value.setVisibility(View.GONE);
            holder.tv_product_name.setText(itemList.get(position).getProductDetail().getProdName());
            holder.tv_product_short_desc.setText(""+itemList.get(position).getProductDetail().getBrand());
            holder.tv_price.setText("MRP : \u20B9"+itemList.get(position).getProductDetail().getMRP()+" x "+itemList.get(position).getQty()+" = "+String.valueOf(Double.parseDouble(itemList.get(position).getQty())*Double.parseDouble(itemList.get(position).getProductDetail().getRetailPrice())));
//            holder.tv_discounted_price.setText("Our Price : \u20B9"+itemList.get(position).getDiscountPrice());
//            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.tv_discounted_price.setVisibility(View.GONE);

            holder.iv_add_to_fvrt.setVisibility(View.GONE);

            holder.et_qty.setText("Qty : "+itemList.get(position).getQty());

            Log.e("item_qty",""+itemList.get(position).getQty());


            if(itemList.get(position).getImages().size()>0) {

                Glide.with(context)
                        .load("http://43.230.198.115/paneka" + itemList.get(position).getImages().get(0).getImagePath().replace("~", ""))
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

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"barcode_product_add",
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
                            map.put("user_id",getActivity().getSharedPreferences("UserData", MODE_PRIVATE).getString("user_id",""));
                            map.put("prod_ID",""+itemList.get(position).getProductDetail().getProdID());
                            map.put("qty","0");
                            map.put("Rate","0");
                            map.put("Total","0");

                            Log.e("barcode_remove_param",""+map);

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
                            map.put("user_id",getActivity().getSharedPreferences("UserData", MODE_PRIVATE).getString("user_id",""));
                            map.put("Prod_ID",""+itemList.get(position).getProductDetail().getProdID());
                            map.put("Qty",holder.et_qty.getText().toString().replace("Qty : ",""));
                            map.put("Rate",itemList.get(position).getProductDetail().getRetailPrice());
                            map.put("Total",String.valueOf(Double.parseDouble(itemList.get(position).getQty())*Double.parseDouble(itemList.get(position).getProductDetail().getRetailPrice())));


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

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"barcode_product_add",
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
                            map.put("user_id",getActivity().getSharedPreferences("UserData", MODE_PRIVATE).getString("user_id",""));
                            map.put("prod_ID",""+itemList.get(position).getProductDetail().getProdID());
                            map.put("qty",holder.et_qty.getText().toString().replace("Qty:",""));
                            map.put("Rate",itemList.get(position).getProductDetail().getRetailPrice());
                            map.put("Total",String.valueOf(Double.parseDouble(itemList.get(position).getQty())*Double.parseDouble(itemList.get(position).getProductDetail().getRetailPrice())));
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
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            SpotsDialog spotsDialog = new SpotsDialog(getActivity());
            spotsDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"barcode_checkout",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                spotsDialog.dismiss();
                                Log.e("response","response");
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");

                                if (status.equalsIgnoreCase("0")) {
                                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                                    sweetAlertDialog.setTitleText("Place Order")
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

                                    SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                                    sweetAlertDialog.setTitleText("Place Order")
                                            .setContentText("Order Placed Sucessfully")
                                            .setConfirmText("Ok")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    startActivity(new Intent(getActivity(), MainActivity.class));
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

                    Map<String, String> placeOrder = new HashMap<>();
                    placeOrder.put("user_id", getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
                    placeOrder.put("amount",razorpayPaymentID);
                    placeOrder.put("method","Online");
                    Log.e("params_order",""+placeOrder);

                    return placeOrder;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception e) {

            Log.e("rzrpay", "Exception in onPaymentSuccess", e);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(getActivity(), "Payment failed: " + code + " " + response, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("rzrpay", "Exception in onPaymentError", e);
        }
    }


}
