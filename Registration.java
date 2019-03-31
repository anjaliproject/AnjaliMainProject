package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Registration extends Activity {
	EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9;
	RadioButton r1,r2;
	TextView tv1;
	Button b1,b2;
	JSONParser jsonParser = new JSONParser();
	SharedPreferences sh;
	public static String url="";
	String ip="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		ed1=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		
	    tv1=(TextView)findViewById(R.id.textView1);
		
	    r1=(RadioButton)findViewById(R.id.radioButton1);
		r2=(RadioButton)findViewById(R.id.radioButton2);
		
		ed3=(EditText)findViewById(R.id.editText3);
		ed4=(EditText)findViewById(R.id.editText4);
		ed5=(EditText)findViewById(R.id.editText5);
		ed6=(EditText)findViewById(R.id.editText9);
		ed7=(EditText)findViewById(R.id.editText6);
		ed8=(EditText)findViewById(R.id.editText7);
		ed9=(EditText)findViewById(R.id.editText8);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		b1=(Button)findViewById(R.id.button1);
		
		
	
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		b2=(Button)findViewById(R.id.button2);
		
		ip=sh.getString("ip", "");
		url="http://"+ip+":5000/signup";
		
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
			
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
//				String f_name=ed1.getText().toString();
//				String l_name=ed2.getText().toString();
//				String gender=tv1.getText().toString();
//				String dob=ed3.getText().toString();
//				String city=ed4.getText().toString();
//				String email=ed5.getText().toString();
//				String mobile=ed9.getText().toString();
//				String photo=ed6.getText().toString();
//				String password=ed7.getText().toString();
//				String confirm_pw=ed8.getText().toString();
//				
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("name", f_name));
//				params.add(new BasicNameValuePair("name", l_name));
//				params.add(new BasicNameValuePair("gender", gender));
//				params.add(new BasicNameValuePair("dob", dob));
//				params.add(new BasicNameValuePair("city", city));
//				params.add(new BasicNameValuePair("email", email));
//				params.add(new BasicNameValuePair("mobile", mobile));
//				params.add(new BasicNameValuePair("fname", photo));
//				params.add(new BasicNameValuePair("pass", password));
//				
//				params.add(new BasicNameValuePair("lid", sh.getString("lid", "0")));
//		           
//				
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

}
