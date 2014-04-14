
package com.example.hulpdienstenapp;

import com.example.hulpdienstenapp.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if not registered
        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);
        //else
        setContentView(R.layout.activity_main);
    }

    
    /** Called when the user clicks the report button */
    public void reportButton(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }
    
    /** Called when the user clicks the call button */
    public void callButton(View view) {
    	SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
    	int phonenumber = settings.getInt("phonenumber",0);
        try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phonenumber));
                startActivity(callIntent);
            }
        catch (ActivityNotFoundException activityException) {
                Log.e("Calling a Phone Number", "Call failed", activityException);
        }
    }
    
    /** Called when the user clicks the howTo button */
    public void howToButton(View view) {
        Intent intent = new Intent(this, InstructionsActivity.class);
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
