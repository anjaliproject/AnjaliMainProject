package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class Feedback extends Activity {
	TextView t8;
	EditText e8;
	Button btn2;
	JSONParser jsonParser = new JSONParser();
	SharedPreferences sh;
	public static String url="";
	String ip="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		
		t8=(TextView)findViewById(R.id.textView1);
		
		e8=(EditText)findViewById(R.id.editText1);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		btn2=(Button)findViewById(R.id.button1);
		ip=sh.getString("ip", "");
		url="http://"+ip+":5000/feedback";
		
		 
			try
			{
			if(android.os.Build.VERSION.SDK_INT>9)
			{
				StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
				
			}
			catch(Exception e)
			{
				
			}
		
			btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String feed=e8.getText().toString();
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("feed", feed));
				params.add(new BasicNameValuePair("lid", sh.getString("lid", "0")));
		           
	            JSONObject jobj=null;
				try {
					jobj = (JSONObject)jsonParser.makeHttpRequest(url,"GET", params);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Toast.makeText(getApplicationContext(), e1+"", Toast.LENGTH_LONG).show();
				}
//	            Log.d("ins===",json.toString());
	         String s=null;
	         
	            try {
	            	
	             String ss=jobj.getString("task");
	                  if(ss.equals("invalid"))
	                {
	                	
	                	  Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_LONG).show();
			                 
	                }
	                else
	                {
	                	
	                	
	                	
	                	Intent i= new Intent(getApplicationContext(),Feedback.class);
	    				startActivity(i);
	    				
	                	Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
	                 
	                }
	            }
	            catch(Exception e)
	            {
	            	 Toast.makeText(getApplicationContext(), "err"+e, Toast.LENGTH_LONG).show();
		               
	 
	            }
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback, menu);
		return true;
	}

}
