package com.example.hulpdienstenapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;



public class RegisterActivity extends Activity {

	boolean validUser = false;
	Dialog dialog;
	TextView dialogText;
	String username;
	String encryptedPassword;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        dialog = new Dialog(this);
        dialogText = new TextView(this);
        dialogText.setPadding(15, 15, 15, 15);
        dialog.setContentView(dialogText);
	}	
	
	
    /** Called when the user clicks the login button */
    public void loginButton(View view) {
    	//get form data
    	username = ((TextView) findViewById(R.id.EditTextUsername)).getText().toString();
    	String password = ((TextView) findViewById(R.id.EditTextPassword)).getText().toString();
    	encryptedPassword = "";
    	try{
    		MessageDigest md = MessageDigest.getInstance("SHA-512","BC");
    		byte[] digesta = md.digest(password.getBytes());
			encryptedPassword = String.format("%0128x", new BigInteger(1, digesta));
    	}
    	catch(Exception x) {}
    	
    	sendData(username, encryptedPassword);
    }

	private void sendData(String username, String password)
	{
	     // 1) Connect via HTTP. 2) Encode data. 3) Send data.
	    try
	    {
	    	String remoteUrl = "http://timbauwens1.ikdoeict.be/projecten1/checkUser.php";
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet(remoteUrl+"?username="+username+"&password="+password);      
	        httpget.setHeader("Content-type", "application/json");
	        InputStream inputStream = null;
	        String result = null;
	        try {
	            HttpResponse response = httpclient.execute(httpget);           
	            HttpEntity entity = response.getEntity();
	            inputStream = entity.getContent();
	            // json is UTF-8 by default
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
	            StringBuilder sb = new StringBuilder();

	            String line = null;
	            while ((line = reader.readLine()) != null)
	            {
	                sb.append(line + "\n");
	            }
	            result = sb.toString();
	            checkLogin(result);
	        } catch (Exception e) { 
	        	dialog.setTitle("No connection with the server could be made!");
	        	dialogText.setText("Error: " + e.toString());
	        }
	        finally {
	            try{
	            	if(inputStream != null)
	            		inputStream.close();
	            	}
	            catch(Exception squish){
	            	dialog.setTitle("Unexpected error!");
		        	dialogText.setText("Error: " + squish.toString());
	            }
	        }

	    }
	    catch(Exception e)
	    {
            dialog.setTitle("No connection with the server could be made!");
        	dialogText.setText("Error: " + e.toString());
	    }  
	}


	private void checkLogin(String result) {
		JSONObject jObject;
		try {
			jObject = new JSONObject(result);
			validUser = jObject.getBoolean("loggedIn");
		} catch (JSONException e) {
			dialog.setTitle("Unexcpected error!");
        	dialogText.setText("Error: " + e.toString());
		}
		
	    if(validUser){
	    	dialog.setTitle("Login succesful!");
	    	//add user data to SharedPreferences
	    	SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
	    	SharedPreferences.Editor editor = settings.edit();
	    	editor.putString("username", username);
	    	editor.putString("password", encryptedPassword);
    		editor.putBoolean("loggedIn", validUser);
    		editor.commit();
    		//redirect to main menu
    		finish();
	    }
	    else{
	    	//else give incorrect user data message
	    	dialog.setTitle("Login failed!");
	    	dialogText.setText("Incorrect username or password.");
	    }
	    dialog.show();
	}
}
