package com.example.hulpdienstenapp;

import com.example.hulpdienstenapp.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

public class InstructionsActivity extends Activity {
	
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }
}