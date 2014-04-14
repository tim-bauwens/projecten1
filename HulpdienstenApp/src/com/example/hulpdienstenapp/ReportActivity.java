package com.example.hulpdienstenapp;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.hulpdienstenapp.MyLocation.LocationResult;
import com.example.hulpdienstenapp.R;

import android.app.Activity;
import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ReportActivity extends Activity {
	
	protected ViewFlipper viewFlipper;
	MyLocation myLocation = new MyLocation();
	double coordinates[] = new double[2];
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
   
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipperReport);
        findCurrentLocation();
    }
    
    private void findCurrentLocation() {
        myLocation.getLocation(this, locationResult);
    }
    
    public LocationResult locationResult = new LocationResult() {

        @Override
        public void gotLocation(Location location) {
            // TODO Auto-generated method stub
            if (location != null) {
            	coordinates[0] = location.getLatitude();
            	coordinates[1] = location.getLongitude();
            }
        }
    };
    
    /** Called when the user clicks the return button */
    public void returnButton(View view) {
    	viewFlipper.showPrevious();
    }
    /** Called when the user clicks the crime button */
    public void crimeButton(View view) {
    	fillTypeSpinner(1);
    	viewFlipper.showNext();
    }
    
    /** Called when the user clicks the fire button */
    public void fireButton(View view) {
    	fillTypeSpinner(2);
    	viewFlipper.showNext();
    }
    
    /** Called when the user clicks the medic button */
    public void medicButton(View view) {
    	fillTypeSpinner(3);
    	viewFlipper.showNext();
    }
    
    /** Called when the user clicks the accident button */
    public void accidentButton(View view) {
    	fillTypeSpinner(4);
    	viewFlipper.showNext();
    }

    
    private void fillTypeSpinner(int type){
        //fill type spinner with data
    	Spinner spinnerCountry = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> typeAdapter = new ArrayAdapter<CharSequence>(this, 0);
        switch(type){
        case 1:
        	typeAdapter = ArrayAdapter.createFromResource(this, R.array.typesCrime, android.R.layout.simple_spinner_item);
        	break;
        case 2:
        	typeAdapter = ArrayAdapter.createFromResource(this, R.array.typesFire, android.R.layout.simple_spinner_item);
        	break;
        case 3:
        	typeAdapter = ArrayAdapter.createFromResource(this, R.array.typesMedical, android.R.layout.simple_spinner_item);
        	break;
        case 4:
        	typeAdapter = ArrayAdapter.createFromResource(this, R.array.typesAccident, android.R.layout.simple_spinner_item);
        	break;
        }
              
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(typeAdapter);
    }
    
    /** Called when the user clicks the send button */
    public void sendButton(View view) {
    	//gather data from form
        String location = ((TextView) findViewById(R.id.editTextLocation)).getText().toString();
        String numVictims = ((TextView) findViewById(R.id.editTextNumVictims)).getText().toString();
        String numWounded = ((TextView) findViewById(R.id.editTextNumWounded)).getText().toString();
        String type = ((Spinner) findViewById(R.id.spinnerType)).getSelectedItem().toString();
        String description = ((TextView) findViewById(R.id.editTextDescription)).getText().toString();
        
        //try to send the data
        sendReportData(coordinates, location, numVictims, numWounded, type, description);
    }

	private void sendReportData(double[] coordinates, String location,String numVictims, String numWounded, String type,String description){
	    //Add data to be send.
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
	    nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(coordinates[0])));
	    nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(coordinates[1])));
	    nameValuePairs.add(new BasicNameValuePair("location", location));
	    nameValuePairs.add(new BasicNameValuePair("numVictims", numVictims));
	    nameValuePairs.add(new BasicNameValuePair("numWounded", numWounded));
	    nameValuePairs.add(new BasicNameValuePair("type", type));
	    nameValuePairs.add(new BasicNameValuePair("description", description));
	    //user id
	    //user code?
	    this.sendData(nameValuePairs);
	}
	
	private void sendData(ArrayList<NameValuePair> data)
	{
        //create feedback dialog to show
        Dialog dialog = new Dialog(this);
        TextView dialogText = new TextView(this);
        dialogText.setPadding(15, 15, 15, 15);
        dialog.setContentView(dialogText);
        
	     // 1) Connect via HTTP. 2) Encode data. 3) Send data.
	    try
	    {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://timbauwens1.ikdoeict.be/projecten1/AddReportData.php");
	        httppost.setEntity(new UrlEncodedFormEntity(data));
			@SuppressWarnings("unused")
			HttpResponse response = httpclient.execute(httppost);
	        dialog.setTitle("Data sent succesfully!");
            dialogText.setText("thank you for reporting this emergency! \nPlease wait until the emergency services arrive.");
	    }
	    catch(Exception e)
	    {
            dialog.setTitle("Data could not be sent!");
        	dialogText.setText("Error: " + e.toString());
	    }  
	    
	    //show success/failure message
        dialog.show();
	}
}

