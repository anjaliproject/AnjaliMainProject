package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot_Password extends Activity {
	
	EditText e1,e2;
	Button b1;
	
	JSONParser jsonParser = new JSONParser();
	SharedPreferences sh;
	public static String url="";
	String ip="";
	String name="",email="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot__password);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url="http://"+sh.getString("ip", "")+":5000/forgotpassword";
		  try {
	            if(Build.VERSION.SDK_INT>9)
	            {
	                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
	                StrictMode.setThreadPolicy(policy);
	            }
	        }
	        catch (Exception e)
	        {

	        }
		
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				name=e1.getText().toString();
				email=e2.getText().toString();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				 params.add(new BasicNameValuePair("User_name", name));
	             params.add(new BasicNameValuePair("Email", email));
	             
	             JSONObject jobj = null;
	                try {
	                    jobj = (JSONObject) jsonParser.makeHttpRequest(url, "GET", params);
	                    //Toast.makeText(getApplicationContext(),"rere"+ur,Toast.LENGTH_LONG).show();
	                } catch (JSONException e1) {
	                     //TODO Auto-generated catch block
	                    // Toast.makeText(getApplicationContext(), "e1" + e1, Toast.LENGTH_LONG).show();
	                }
	                String s = null;
	                
	                try {

	                    String ss = jobj.getString("task");

	                    if (ss.equals("failed")) {
	                        Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_LONG).show();

	                    } else {


	                        Intent i = new Intent(getApplicationContext(), Login.class);
	                        startActivity(i);

	                    }


	                } catch (Exception e) { // TODO Auto-generated catch block
	                    Log.d("err",e+"");
	                    //Toast.makeText(getApplicationContext(), "e" + e, Toast.LENGTH_LONG).show();
	                }


				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot__password, menu);
		return true;
	}

}
