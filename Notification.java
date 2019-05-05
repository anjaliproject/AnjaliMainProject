package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Notification extends Activity implements OnItemClickListener {
	ListView L1;
	JSONParser jsonParser = new JSONParser();
	public static ArrayList<String> image,name,postid,id;
	SharedPreferences sh;
	public static String url="";
	String ip="";
	String lid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url="http://"+sh.getString("ip", "")+":5000/notification";
		
		L1=(ListView)findViewById(R.id.listView1);
		
		lid=sh.getString("lid", "");
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
		
		L1.setOnItemClickListener(this);
		
		
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
	     
	        
	        JSONArray json=null;
	       
	        try {
				
	        	Log.d("==============", "Emmmntered");
				json = (JSONArray)jsonParser.makeHttpRequest(url,"GET", params);
	        	image=new ArrayList<String>();
	        	 postid=new ArrayList<String>();
	            id=new ArrayList<String>();
	            name=new ArrayList<String>();
	           
	          
	            Log.d("**********", "Success");
	            for (int i = 0; i < json.length(); i++) {
	                     JSONObject c=json.getJSONObject(i);
	                     image.add(c.getString("Image"));
	                     name.add(c.getString("Name"));
	                     postid.add(c.getString("postid"));
	                     id.add(c.getString("id"));
	                     Log.d("aaaaaaaaa", image.get(i));
	                    
//	                    name.add(c.getString("Name")+" Posted a photo of you");	                  

	                }

	        }
	        catch(Exception e)
	        {
	        	
	        
	        }
	       
			ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, name);
			L1.setAdapter(ad);
		}

	
	
	
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		Intent i =new Intent(getApplicationContext(),Accept_notification.class);
		i.putExtra("image", image.get(arg2));
		i.putExtra("postid", postid.get(arg2));
		i.putExtra("id", id.get(arg2));
		startActivity(i);
		
	}

}
