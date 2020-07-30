package com.trisoft.ecommerce_new.barcodescanner.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.trisoft.ecommerce_new.R;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        /** Initialize Fabrice **/
//        Fabric.with(SplashActivity.this ,  new Crashlytics());
//        /** Initialize Firebase Ads **/
//        MobileAds.initialize(this, getResources().getString(R.string.app_ad_id));
        startMainActivity();
        finish();
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);

    }

}
