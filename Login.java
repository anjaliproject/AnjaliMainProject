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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class Login extends Activity implements OnClickListener {
EditText e1,e2;
Button b1,b2;
TextView t1;
JSONParser jsonParser = new JSONParser();
SharedPreferences sh;
String ip="192.168.43.235";
public static String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
  
        
        b1=(Button)findViewById(R.id.button1);
        url="http://"+ip+":5000/login";
        
        
        Editor ed=sh.edit();
		ed.putString("ip",ip);
		ed.commit();
		
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			
				String uname=e1.getText().toString();
				String pass=e2.getText().toString();
				
				try {
				   List<NameValuePair> params = new ArrayList<NameValuePair>();
		            params.add(new BasicNameValuePair("uname", uname));
		            params.add(new BasicNameValuePair("pass", pass));
		            

		            JSONObject jobj = (JSONObject)jsonParser.makeHttpRequest(url,"GET", params);
					 Log.d("ins===",jobj.toString());
		            	
		             String ss=jobj.getString("task");
		                if(ss.equals("invalid"))
		                {
		                	
		                	  Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_LONG).show();
				                 
		                }
		                else
		                {
		                	
		                	
		                	Editor ed=sh.edit();
		    				ed.putString("lid",ss);
		    				ed.commit();
		               
		                	
		                	Intent i= new Intent(getApplicationContext(),User_home.class);
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
        b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
				Intent i=new Intent(getApplicationContext(),Registration.class);
				startActivity(i);
				
			}
		});
        t1=(TextView)findViewById(R.id.textView1);
        
        t1.setOnClickListener(this);
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i=new Intent(getApplicationContext(),Forgot_Password.class);
		startActivity(i);
		
		
	}
    
}
