package com.trisoft.ecommerce_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trisoft.ecommerce_new.Activities.MainActivity;
import com.trisoft.ecommerce_new.Cart.MyCart;
import com.trisoft.ecommerce_new.Intro.Login;
import com.trisoft.ecommerce_new.Orders.MyOrders;
import com.trisoft.ecommerce_new.Utils.SessionManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Navigation_drawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        getSupportActionBar().hide();


        TextView tv_build_version = findViewById(R.id.tv_app_version);
        TextView tv_name = findViewById(R.id.tv_user_name);
        TextView tv_email = findViewById(R.id.tv_email);

        tv_name.setText(getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("name",""));
        tv_email.setText(getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("email",""));

        tv_build_version.setText("Version : "+BuildConfig.VERSION_NAME);

        TextView tv_home = findViewById(R.id.tv_home);
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


        TextView tv_my_cart = findViewById(R.id.tv_my_cart);
        tv_my_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyCart.class));
                finish();
            }
        });


        TextView tv_share = findViewById(R.id.tv_share_app);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        TextView tv_rate = findViewById(R.id.tv_rate_app);
        tv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
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


        TextView tv_logout = findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(Navigation_drawer.this, SweetAlertDialog.WARNING_TYPE);
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
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                new SessionManager(getApplicationContext()).setLogin(false);
                                finish();
                            }
                        }).setCancelable(false);
                sweetAlertDialog.show();

            }
        });


        TextView tv_my_orders = findViewById(R.id.tv_my_orders);
        tv_my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MyOrders.class));
                finish();
            }
        });


        ImageView iv_close = findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}
