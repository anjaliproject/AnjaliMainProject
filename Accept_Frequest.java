package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Accept_Frequest extends Activity {
	TextView t1,t2;
	EditText e1,e2;
	Button b1,b2;
	ImageView img;
	
	public static String url="", url1="",url2="";
	String ip="";
    JSONParser jsonParser = new JSONParser();
    SharedPreferences sh;

    String id="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accept__frequest);
//		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ip=sh.getString("ip", "");
		url="http://"+ip+":5000/view_frequest";
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		
		img=(ImageView)findViewById(R.id.imageView1);
		
		id=getIntent().getStringExtra("lid");
     	String name=getIntent().getStringExtra("name");
		String city=getIntent().getStringExtra("city");
		String photo=getIntent().getStringExtra("photo");
		e1.setText(name);
		e2.setText(city);
		
		java.net.URL thumb_u;
		try {
			thumb_u = new java.net.URL("http://"+sh.getString("ip", "")+":5000/static/image/"+id+"/"+photo);
			Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
			img.setImageDrawable(thumb_d);
			

			
			
			
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();

		}
		
		
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		url1="http://"+ip+":5000/accept_request";
		url2="http://"+ip+":5000/reject_request";
		
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("lid", sh.getString("lid", "0")));
		           
            JSONObject jobj=null;
				try {
					 jobj = (JSONObject)jsonParser.makeHttpRequest(url1,"GET", params);
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
	                	
	                	
	                	
	                	Intent i= new Intent(getApplicationContext(),View_friend_Request.class);
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


	
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
                List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("lid", sh.getString("lid", "0")));
		           
            JSONObject jobj=null;
				try {
					 jobj = (JSONObject)jsonParser.makeHttpRequest(url2,"GET", params);
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
	                	
	                	
	                	
	                	Intent i= new Intent(getApplicationContext(),View_friend_Request.class);
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
		getMenuInflater().inflate(R.menu.accept__frequest, menu);
		return true;
	}

}
