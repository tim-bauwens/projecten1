
package com.example.hulpdienstenapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        createButtonListeners();
    }

    //creates all the event listeners for the buttons on the main activity
	private void createButtonListeners() {
		
		Button buttonHowto = (Button) findViewById(R.id.buttonHowto);
		Button buttonSettings = (Button) findViewById(R.id.buttonSettings);
		Button buttonReport = (Button) findViewById(R.id.buttonReport);
		Button ButtonCall = (Button) findViewById(R.id.ButtonCall);
		Button ButtonHelp = (Button) findViewById(R.id.ButtonHelp);

		buttonHowto.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {

	      }
	    });
		
		buttonSettings.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {

	      }
	    });
		
		buttonReport.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {

	      }
	    });
		
		ButtonCall.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {

	      }
	    });
		
		ButtonHelp.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {

	      }
	    });
		
	}
}
