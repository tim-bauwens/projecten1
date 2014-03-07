package com.example.hulpdienstenapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class ReportActivity extends Activity {
	
	protected ViewFlipper viewFlipper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipperReport);
    }
    
    /** Called when the user clicks the return button */
    public void returnButton(View view) {
    	viewFlipper.showPrevious();
    }
    /** Called when the user clicks the crime button */
    public void crimeButton(View view) {
    	viewFlipper.showNext();
    }
    /** Called when the user clicks the accident button */
    public void accidentButton(View view) {
    	viewFlipper.showNext();
    }
    /** Called when the user clicks the fire button */
    public void fireButton(View view) {
    	viewFlipper.showNext();
    }
    /** Called when the user clicks the medic button */
    public void medicButton(View view) {
    	viewFlipper.showNext();
    }
}
