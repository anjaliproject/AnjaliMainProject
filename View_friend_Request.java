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
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class View_friend_Request extends Activity implements OnItemClickListener {
	
	ListView L1;
	SharedPreferences sh;
	public static String url="";
	String ip="";
	ArrayList<String> lid;
	ArrayList<String> name;
	ArrayList<String> photo;
	ArrayList<String> city;
	
	JSONParser jsonParser = new JSONParser();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_friend__request);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		
		L1=(ListView)findViewById(R.id.listView1);
		L1.setOnItemClickListener(this);
		
		url="http://"+sh.getString("ip", "")+":5000/view_frequest";
		
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
	     
	        
	        JSONArray json=null;
		       
	        try {
				
				
				json = (JSONArray)jsonParser.makeHttpRequest(url,"GET", params);
	        	name=new ArrayList<String>();
	        	 lid=new ArrayList<String>();
	            photo=new ArrayList<String>();
	            city=new ArrayList<String>();
	          

	            for (int i = 0; i < json.length(); i++) {
	            	
	                     JSONObject c=json.getJSONObject(i);
	                     lid.add(c.getString("U_id"));
	                   
	                    name.add(c.getString("Name"));
	                    photo.add(c.getString("photo"));
	                    city.add(c.getString("City"));

	                }

	        }
	        catch(Exception e)
	        {
	        	
	        
	        }
	       
			L1.setAdapter(new Custom(View_friend_Request.this,lid, name));
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_friend__requsets, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		Intent i=new Intent(getApplicationContext(),Accept_Frequest.class);
		i.putExtra("name", name.get(arg2));
		i.putExtra("photo", photo.get(arg2));
		i.putExtra("city", city.get(arg2));
		i.putExtra("lid", lid.get(arg2));
		startActivity(i);
	}

}
