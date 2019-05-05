package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class View_chats extends Activity {
	
	SharedPreferences sh;
	public static String url="",url1="";
	String ip="";
	JSONParser jsonParser = new JSONParser();
	LinearLayout lt;
	EditText ed;
	Button b1;
	TextView t1;
	String lastid="",fid="",lid="";
	Handler hd;
	static String prv="";
	public static ArrayList<String> from_id,to_id,msg,date ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_chats);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		t1=(TextView)findViewById(R.id.textView1);
		t1.setText(getIntent().getStringExtra("Name"));
		
		try{
			if(android.os.Build.VERSION.SDK_INT>9){
				StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
			
		}catch(Exception e){
			
		}
		lastid="0";
		
		ed=(EditText)findViewById(R.id.editText1);
		lt=(LinearLayout)findViewById(R.id.linear1);
		b1=(Button)findViewById(R.id.button1);
		url="http://"+sh.getString("ip", "")+":5000/insertchat";
		url1="http://"+sh.getString("ip", "")+":5000/viewchat";
		
		
		fid=getIntent().getStringExtra("Friend_id");
	    lid=sh.getString("lid", "");
	    
	    hd=new Handler();
		hd.post(r);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String message=ed.getText().toString();
				try
				{
				 List<NameValuePair> params = new ArrayList<NameValuePair>();
	        	 params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
	        	 params.add(new BasicNameValuePair("To_id", fid));
		         params.add(new BasicNameValuePair("Message",message));
		            
		         JSONObject json = (JSONObject) jsonParser.makeHttpRequest(url, "GET", params);
		        	
		            Log.d("Reultttttt=====---------",json+"");
		            
		            String s=null;
		            try {
		            	s=json.getString("task");
		                Log.d("Msg+++++++++++++++++++++++++++++++++++++++++++++++", s);
		                if(s.equalsIgnoreCase("success"))
		                {
		                	
		                   ed.setText("");
		                  // lt.removeAllViews();
		                }
		                else
		                {
		                	
		
		                }
		            }
		            catch(Exception e)
		            {

		            	 Toast.makeText(getApplicationContext(), "err"+e.getMessage()+"", Toast.LENGTH_LONG).show();
			             
		            }

				
				}
				catch(Exception e)
				{
	            	Toast.makeText(getApplicationContext(), "err"+e.getMessage()+"", Toast.LENGTH_LONG).show();
		             
					
				}
				
				
			}
		});
		 
		
	}
	
	
    public Runnable r=new Runnable() {
    	

		@Override
		public void run() {
			
			 JSONArray json=null;
			try{
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
//	            params.add(new BasicNameValuePair("From_id", lid));
	            params.add(new BasicNameValuePair("To_id", fid));
	            params.add(new BasicNameValuePair("lastid", "0"));
	    		json = (JSONArray)jsonParser.makeHttpRequest(url1,"GET", params);
	    		
	            Log.d("Reultttttt=====---------",json+"");
//	               JSONArray ar=new JSONArray();
//	                ar=json.getJSONArray("product");
	               // Log.d("+++++++++++",ar+"");
	               
	                from_id=new ArrayList<String>();
	                to_id=new ArrayList<String>();
	                msg=new ArrayList<String>();
	                date=new ArrayList<String>();
	              // uid=new ArrayList<String>();
	               
	               lt.removeAllViews();

	               
	            
	                for (int i = 0; i < json.length(); i++) {
	                	
	                    JSONObject c=json.getJSONObject(i);
	                	   
	                       from_id.add(c.getString("From_id"));
	                        to_id.add(c.getString("To_id"));
	                        msg.add(c.getString("Message"));
	                        date.add(c.getString("Date"));
	                       
	                       
	                         Log.d("+++++++++++",c+"");
	                        
	               	
							TextView tv=new TextView(getApplicationContext());
							TextView tv1=new TextView(getApplicationContext());
							if(!c.getString("Date").equals(prv))
							{
								//Toast.makeText(getApplicationContext(), "result is"+prv, Toast.LENGTH_LONG).show();
								tv1.setText(c.getString("Date"));
								tv1.setGravity(Gravity.CENTER);
								prv=c.getString("Date");
							}
							
							if(from_id.get(i).equalsIgnoreCase(lid)){
								tv.setTextColor(Color.RED);
								tv.setText("Me"+": "+msg.get(i));
								tv.setGravity(Gravity.RIGHT);
								
								tv.setBackgroundColor(Color.WHITE);
								
								//tv1.setTextColor(Color.RED);
								//tv1.setText(date.get(i)+"");
								
								
								tv1.setBackgroundColor(Color.WHITE);
								
								
						
							}
							else{
								tv.setTextColor(Color.BLUE);
								tv.setText(msg.get(i));
								tv.setGravity(Gravity.LEFT);
								
								tv.setBackgroundColor(Color.YELLOW);
								
								//tv1.setTextColor(Color.BLACK);
								//tv1.setText(date.get(i));
								//tv1.setGravity(Gravity.CENTER);
								
								tv1.setBackgroundColor(Color.YELLOW);
							}							
						
							lt.addView(tv);
							lt.addView(tv1);
							
							
						}
		
			}catch(Exception e){

      //     	 Toast.makeText(getApplicationContext(), "err"+e.getMessage()+"", Toast.LENGTH_LONG).show();
	             
			}
			
			hd.postDelayed(r, 4000);
		}
		
	};

		
	
		
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_chats, menu);
		return true;
	}

}
