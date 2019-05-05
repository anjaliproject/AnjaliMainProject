package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {
	TextView t2,t3,t4,t5,t6,t7;
	ImageView img1;
	EditText e1,e2,e3,e4,e5,e6;
	Button btn1;
	JSONParser jsonParser = new JSONParser();
	SharedPreferences sh;
	public static String url="",url1="";
	String ip="";
    String name,gender,dob,city,email,mobile,photo,id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url="http://"+sh.getString("ip", "")+":5000/profile";
		
		t2=(TextView)findViewById(R.id.textView1);
		t3=(TextView)findViewById(R.id.textView2);
		t4=(TextView)findViewById(R.id.textView3);
		t5=(TextView)findViewById(R.id.textView4);
		t6=(TextView)findViewById(R.id.textView5);
		t7=(TextView)findViewById(R.id.textView6);
		
		img1=(ImageView)findViewById(R.id.imageView1);
		
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		e6=(EditText)findViewById(R.id.editText6);
		url1="http://"+sh.getString("ip", "")+":5000/pro_update";
		
		
        btn1=(Button)findViewById(R.id.button1);
        
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
     
        
        JSONArray json=null;
       
        try {
			
			
			json = (JSONArray)jsonParser.makeHttpRequest(url,"GET", params);
        	
          
                     JSONObject c=json.getJSONObject(0);
                   
                     id= c.getString("U_id");
                    name= c.getString("Name");
                    gender= c.getString("Gender");
                    dob= c.getString("DOB"); 
                    city= c.getString("City");
                    email= c.getString("Email");
                    mobile= c.getString("Mobile");
                    photo= c.getString("photo");
                    
                    
                    e1.setText(name);
                    e2.setText(dob);
                    e3.setText(city);
                    e4.setText(email);
                    e5.setText(mobile);
                    e6.setText(gender);
                    
                	java.net.URL thumb_u;
            		try {
            			thumb_u = new java.net.URL("http://"+sh.getString("ip", "")+":5000/static/image/"+id+"/"+photo);
            			Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            			img1.setImageDrawable(thumb_d);
            			Toast.makeText(getApplicationContext(), photo, Toast.LENGTH_LONG).show();

            			
            			
            			
            		}
            		catch(Exception e){
            			Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();

            		}
            		
                    
                    
                    
                  
                  
        }
        catch(Exception e)
        {
        	
        
        }

        
        
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String namee= e1.getText().toString();
				String dobb=e2.getText().toString();
				String cityy=e3.getText().toString();
				String emaill=e4.getText().toString();
				String mob=e5.getText().toString();
				String gen=e6.getText().toString();
				
				
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
				params.add(new BasicNameValuePair("name", namee));
				params.add(new BasicNameValuePair("gender", gen));
				params.add(new BasicNameValuePair("dob", dobb));
				params.add(new BasicNameValuePair("city", cityy));
				params.add(new BasicNameValuePair("email", emaill));
				params.add(new BasicNameValuePair("mobile", mob));
				
		           
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
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
