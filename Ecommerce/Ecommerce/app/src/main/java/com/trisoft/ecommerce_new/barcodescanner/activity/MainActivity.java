package com.trisoft.ecommerce_new.barcodescanner.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.getkeepsafe.relinker.MissingLibraryException;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.trisoft.ecommerce_new.Cart.MyCart;
import com.trisoft.ecommerce_new.Fragments.Account;
import com.trisoft.ecommerce_new.Fragments.Help_support;
import com.trisoft.ecommerce_new.Intro.Login;
import com.trisoft.ecommerce_new.Offers.Offer;
import com.trisoft.ecommerce_new.Products.Product_by_cat;
import com.trisoft.ecommerce_new.R;
import com.trisoft.ecommerce_new.Utils.Dialogs;
import com.trisoft.ecommerce_new.Utils.SessionManager;
import com.trisoft.ecommerce_new.barcodescanner.fragment.BarcodeFragment;
import com.trisoft.ecommerce_new.barcodescanner.fragment.LicenseFragment;
import com.trisoft.ecommerce_new.barcodescanner.fragment.Order_history_fragment;
import com.trisoft.ecommerce_new.barcodescanner.fragment.ProductListFragment;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static com.trisoft.ecommerce_new.Utils.ApiConstant.baseUrlApi;

public class MainActivity extends AppCompatActivity implements BarcodeFragment.ScanRequest {

    private Context context ;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String BARCODE_KEY = "BARCODE";
    private Barcode barcodeResult;
    private final String TAG = MainActivity.class.getSimpleName() ;
    private final int MY_PERMISSION_REQUEST_CAMERA = 1001;
    private ItemScanned itemScanned ;

    String popup_status="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bar);

        getSupportActionBar().setTitle("Barcode Shopping");
        context = this;
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



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

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        LinearLayout ll_offers = findViewById(R.id.ll_offers);
        ll_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Offer.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BarcodeFragment(), "Scanner");
        adapter.addFragment(new ProductListFragment(), "Scaned Item");
        adapter.addFragment(new Order_history_fragment(),"Order History");
        viewPager.setAdapter(adapter);
    }

    public String getScanTime() {
     DateFormat timeFormat = new SimpleDateFormat("hh:mm a" , Locale.getDefault());
        return  timeFormat.format(new Date());
    }

    public String getScanDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    @Override
    public void scanBarcode() {
        /** This method will listen the button clicked passed form the fragment **/
         checkPermission();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void showDialog(final String scanContent, final String currentTime, final String currentDate) {

        SpotsDialog spotsDialog = new SpotsDialog(MainActivity.this);
        spotsDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi+"product_barcode",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            spotsDialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            if (status.equalsIgnoreCase("0")) {

                                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitleText("Scan Barcode")
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

                                JSONObject UserData = object.getJSONObject("data");
                                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                View itemView = getLayoutInflater().inflate(R.layout.scanned_prod_popup,
                                        null);
                                PopupWindow mpopup = new PopupWindow(itemView, LinearLayout.LayoutParams.FILL_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT, true); // Creation of popup
                                mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
                                mpopup.showAtLocation(itemView, Gravity.CENTER, 0, 0);

                                EditText et_qty = itemView.findViewById(R.id.et_qty);
                                TextView tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);

                                TextView tv_offer_value = itemView.findViewById(R.id.tv_offer_value);
                                TextView tv_product_name = itemView.findViewById(R.id.tv_product_name);
                                TextView tv_product_short_desc = itemView.findViewById(R.id.tv_product_short_desc);
                                TextView tv_price = itemView.findViewById(R.id.tv_price);

                               tv_product_name.setText(UserData.getString("Brand"));
                               tv_product_short_desc.setText(UserData.getString("ProductType")+" - "+UserData.getString("Prod_Name")+"\n"+UserData.getString("Unit"));
                               tv_price.setText("MRP : \u20B9" + UserData.getString("Retail_Price"));


                               TextView tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
                               LinearLayout ll_product = itemView.findViewById(R.id.ll_product);
                               ImageView iv_product = itemView.findViewById(R.id.iv_product);

                               Glide.with(context)
                                        .load("http://43.230.198.115/paneka" + UserData.getString("images").replace("~",""))
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .error(R.drawable.logo_paneka)
                                        .into(iv_product);

                                ImageView iv_close = itemView.findViewById(R.id.iv_close);
                                iv_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popup_status="0";
                                        mpopup.dismiss();
                                    }
                                });

                                tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (new SessionManager(getApplicationContext()).isLoggedIn()) {


                                            SpotsDialog spotsDialog = new SpotsDialog(context);
                                            spotsDialog.show();

//                                            barcode_product_add[user_id,prod_ID]
                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrlApi +"barcode_product_add",
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


                                                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                                                    sweetAlertDialog.setTitleText("Added")
                                                                            .setContentText("Item added to Scanned Item List")
                                                                            .setConfirmText("Ok")
                                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                                @Override
                                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                                    sweetAlertDialog.dismiss();
                                                                                    mpopup.dismiss();
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
                                                    try {
                                                        map.put("prod_ID", UserData.getString("Prod_ID"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    map.put("qty", et_qty.getText().toString().replace("Qty:", ""));
                                                    map.put("user_id", context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("user_id", ""));

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
                                            sweetAlertDialog.setTitleText("Item Add")
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

                                et_qty.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Dialogs dialogs = new Dialogs(MainActivity.this);
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
                                        dialogs.singleSelectDialog(qty, et_qty, "Select Quantity", selectedposition);
                                        String quantity = et_qty.getText().toString().replace("Qty:","");
                                    }
                                });


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

                map.put("BarCode",scanContent);
                Log.e("param_scanned_barcode",""+map);

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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are You Sure want to Exit From Barcode Shopping? ")
                .setTitle(R.string.exit_title);
        builder.setPositiveButton(R.string.ok_title, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                  MainActivity.this.finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                      dialog.dismiss();
            }
        });

        builder.show();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG , getResources().getString(R.string.camera_permission_granted));
            startScanningBarcode();
        } else {
            requestCameraPermission();

        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                    ActivityCompat.requestPermissions(MainActivity.this,  new String[] {Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);

        } else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    private void startScanningBarcode() {
        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(MainActivity.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                .withText("Scanning...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        showDialog(barcode.rawValue , getScanTime(),getScanDate());
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_PERMISSION_REQUEST_CAMERA && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScanningBarcode();
        } else {
            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.sorry_for_not_permission), Snackbar.LENGTH_SHORT)
                    .show();
        }

    }


    public interface  ItemScanned{
        void itemUpdated();
    }

}
