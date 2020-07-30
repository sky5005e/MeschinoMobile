package com.trisoft.ecommerce_new.barcodescanner.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.trisoft.ecommerce_new.Orders.MyOrders;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.barcodescanner.model.barcode_order_model;

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

public class Order_history_fragment extends Fragment {

    View view;


    RecyclerView rv_my_orders;


    public Order_history_fragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_order_history_fragment, container, false);

        rv_my_orders = view.findViewById(R.id.rv_my_orders);



        if(getContext()!=null) {
            myordersList();
        }




        return view;

      

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            if(getContext()!=null) {
                myordersList();
            }

        }
    }



    private void myordersList() {

        SpotsDialog dialog = new SpotsDialog(getContext());
        dialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi + "barcode_orders",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            Log.e("barcode_order_hist",response);

                            dialog.dismiss();



                            if (status.equalsIgnoreCase("0")) {

                               

                            } else {

                                Gson gson = new Gson();

                                JSONArray array = object.getJSONArray("data");
                                List<barcode_order_model> orderLists = new ArrayList<>();

                                for (int i = 0; i < array.length(); i++) {

                                    barcode_order_model orderList = gson.fromJson(array.getJSONObject(i).toString(), barcode_order_model.class);
                                    orderLists.add(orderList);
                                }



                                OrderList_Adapter completePackageAdapter = new OrderList_Adapter(getContext(),orderLists);

                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                                rv_my_orders.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
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
                map.put("user_id",getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id",""));
                Log.e("order_his_param",""+map);
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


    public class OrderList_Adapter extends RecyclerView.Adapter<OrderList_Adapter.MyViewHolder> {

        List <barcode_order_model> order_history_modelsList=new ArrayList<>();
        Context context;

        public OrderList_Adapter(Context context, List<barcode_order_model> order_history_modelsList) {

            context=context;
            this.order_history_modelsList=order_history_modelsList;

        }

        @NonNull
        @Override
        public OrderList_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.barcode_scan_his_layout, parent, false);

            OrderList_Adapter.MyViewHolder myViewHolder=new OrderList_Adapter.MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull OrderList_Adapter.MyViewHolder holder, final int position) {

//            holder.textView_orderid.setText(""+order_history_modelsList.get(position).getYorderID());
//            holder.textView_orderstatus.setText(order_history_modelsList.get(position).getDeliverAddress());
            holder.textView_orderdate.setText(order_history_modelsList.get(position).getTime());


            holder.textView_orderid.setVisibility(View.GONE);
            holder.textView_orderstatus.setVisibility(View.GONE);
            holder.tv_ordstatus.setVisibility(View.GONE);


            holder.textView_orderPrice.setText("\u20B9"+String.valueOf(order_history_modelsList.get(position).getTotalAmount()));

//            holder.tv_ordstatus.setText(""+order_history_modelsList.get(position).getStatus());

            holder.rv_order_items.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

            holder.Pname.setText(order_history_modelsList.get(position).getProductDetail().get(position).getProdName());
            holder.Pqty.setText(""+order_history_modelsList.get(position).getTotalQty());
            holder.Pprice.setText("\u20B9"+order_history_modelsList.get(position).getProductDetail().get(position).getMRP()+"x"+order_history_modelsList.get(position).getTotalQty()+"= \u20B9"+String.valueOf(Double.parseDouble(order_history_modelsList.get(position).getProductDetail().get(position).getMRP())*Double.parseDouble(String.valueOf(order_history_modelsList.get(position).getTotalQty()))));



            if(order_history_modelsList.get(position).getProductDetail().get(position).getImages().size()>0) {

                Glide.with(getActivity())
                        .load("http://43.230.198.115/paneka" + order_history_modelsList.get(position).getProductDetail().get(position).getImages().get(0).getImagePath().replace("~", ""))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(holder.img_Pimg);
            }



            holder.tv_add_review.setVisibility(View.GONE);



            holder.tv_cancel_order.setVisibility(View.GONE);


            holder.tv_cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   
                }
            });

        }

        @Override
        public int getItemCount() {
            return order_history_modelsList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout ll_order;
            TextView tv_ordstatus,textView_orderid,textView_orderstatus,textView_orderdate,textView_orderPrice,textView_paymentmethod,tv_ordertime_slot,tv_cancel_order;
            RecyclerView rv_order_items;
            ImageView Pimg,img_Pimg;
            TextView Pname,Pprice,Pqty,tv_add_review;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                rv_order_items= itemView.findViewById(R.id.rv_order_items);

                img_Pimg= itemView.findViewById(R.id.img_Pimg);


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

        List<barcode_order_model.ProductDetail> object=new ArrayList<>();
        Context context;

        public Products_Adapter(Context context, List<barcode_order_model.ProductDetail> object) {

            this.object=object;
            context=context;
        }

        @NonNull
        @Override
        public Products_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productdetail_layout, parent, false);
            Products_Adapter.MyViewHolder myViewHolder=new Products_Adapter.MyViewHolder(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull Products_Adapter.MyViewHolder holder, int position) {
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
