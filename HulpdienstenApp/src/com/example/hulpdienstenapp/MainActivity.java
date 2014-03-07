
package com.example.hulpdienstenapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    
    /** Called when the user clicks the report button */
    public void reportButton(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }
    
    /** Called when the user clicks the call button */
    public void callButton(View view) {
    }
    
    /** Called when the user clicks the howTo button */
    public void howToButton(View view) {
        Intent intent = new Intent(this, HowToActivity.class);
        startActivity(intent);
    }
    
    /** Called when the user clicks the settings button */
    public void settingsButton(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    
    /** Called when the user clicks the help button */
    public void helpButton(View view) {

    }
}
