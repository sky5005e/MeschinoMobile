package com.trisoft.ecommerce_new.Offers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trisoft.ecommerce_new.R;

public class Offer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        getSupportActionBar().setTitle("Offers");
    }
}
