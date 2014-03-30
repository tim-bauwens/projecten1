package com.example.hulpdienstenapp;

import com.example.hulpdienstenapp.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity {
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    	SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);

        
        //fill country spinner with data
        final Spinner spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(countryAdapter);
        
        //spinner country selection update      
        spinnerCountry.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String country = spinnerCountry.getSelectedItem().toString();
				SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
				SharedPreferences.Editor editor = settings.edit();

				int[] numbers = getResources().getIntArray(R.array.phonenumbers);
				int number = numbers[(int) spinnerCountry.getSelectedItemId()];
				TextView textViewNumber = (TextView)findViewById(R.id.textViewNumber);
				textViewNumber.setText(number+"");

				editor.putString("country", country);
				editor.putInt("phonenumber", number);
				editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
               
        //country checkbox
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBoxCountries);
        boolean autoCountry = settings.getBoolean("autoCountry",true);
        if(autoCountry){
        	checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
    	   @Override
    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    		   if (buttonView.isChecked())
    		   { 
    			   //checked
    			   SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
    			   SharedPreferences.Editor editor = settings.edit();
    			   editor.putBoolean("autoCountry", true);
    			   editor.commit();
    		   } 
    		   else 
    		   {
    			   //not checked
    			   SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
    			   SharedPreferences.Editor editor = settings.edit();
    			   editor.putBoolean("autoCountry", false);
    			   editor.commit();
    		   } 
    	   }
    	});
        
        //if automatic country selection is enabled set the country
        if (autoCountry) {
            String currentCountry = this.getResources().getConfiguration().locale.getCountry();

            for(int i = 0; i < spinnerCountry.getCount(); i++){
            	String country = spinnerCountry.getItemAtPosition(i).toString();
            	String iso = country.substring(0, 2);
            	if(currentCountry.equals(iso)){
					spinnerCountry.setSelection(countryAdapter.getPosition(country));
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("country", country);
					editor.commit();
            		break;
            	}
            }
        }
        else{
            String country = settings.getString("country","AF:Afghanistan");
        	spinnerCountry.setSelection(countryAdapter.getPosition(country));
        }
    }
}
