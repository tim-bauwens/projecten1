package com.example.hulpdienstenapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

public class HowToActivity extends Activity {
	
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
    }
}