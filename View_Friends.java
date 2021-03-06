package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class View_Friends extends Activity {
	
	ListView L11;
	
	SharedPreferences sh;
	public static String url="";
	String ip="";
	ArrayList<String> lid;
	ArrayList<String> name;
	ArrayList<String> photo;
	
	JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view__friends);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		L11=(ListView)findViewById(R.id.listView1);
//    	L11.setOnClickListener(this);
		
		url="http://"+sh.getString("ip", "")+":5000/view_friends";
		
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
	     
	        
	        JSONArray json=null;
	       
	        try {
				
				
				json = (JSONArray)jsonParser.makeHttpRequest(url,"GET", params);
	        	name=new ArrayList<String>();
	        	 lid=new ArrayList<String>();
	            photo=new ArrayList<String>();
	           
	          

	            for (int i = 0; i < json.length(); i++) {
	                     JSONObject c=json.getJSONObject(i);
	                     lid.add(c.getString("U_id"));
	                   
	                    name.add(c.getString("Name"));
	                    photo.add(c.getString("photo"));
	                  

	                }

	        }
	        catch(Exception e)
	        {
	        	
	        
	        }
	       
			L11.setAdapter(new Custom(View_Friends.this,lid, name));
		}
	   
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view__friends, menu);
		return true;
	}

//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		
//	}

}
