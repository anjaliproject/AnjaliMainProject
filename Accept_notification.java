package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Accept_notification extends Activity {
	ImageView img;
	Button b1,b2;
	JSONParser jsonParser = new JSONParser();
	SharedPreferences sh;
	public static String url="",url1="",url2="";
	String ip="";
	String postid="",lid="",id="",ss="",image="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accept_notification);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ip=sh.getString("ip", "");
		url="http://"+ip+":5000/notification";
		
		image=getIntent().getStringExtra("image");
		postid=getIntent().getStringExtra("postid");
		id=getIntent().getStringExtra("id");
		lid=sh.getString("lid", "");
		
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		img=(ImageView)findViewById(R.id.imageView1);
		
		url1="http://"+ip+":5000/acceptnotification";
		url2="http://"+ip+":5000/rejectnotification";
		
//		String photo=getIntent().getStringExtra("image");
//		String postid=getIntent().getStringExtra("postid");
		
		
		
		
		java.net.URL thumb_u;
		try {
			thumb_u = new java.net.URL("http://"+sh.getString("ip", "")+":5000/static/faces/"+postid+".jpg");
			Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
			img.setImageDrawable(thumb_d);
			

			
			
			
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();

		}
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				new Insert().execute();
				
				
			}
		});
	
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				new Insert1().execute();
				
				
			}
				
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accept_notification, menu);
		return true;
	}
	
	class Insert extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
       }
        protected String doInBackground(String... args)
        {
        	
 
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("lid",sh.getString("lid", "")));
            params.add(new BasicNameValuePair("postid",postid));
            params.add(new BasicNameValuePair("id",id));
            Log.d("aaaaaaaaaaa",lid);
            
            
            Log.d("ins===",url);
//            ur+="/"+a;
            JSONObject jobj=null;
			try {
				jobj = (JSONObject)jsonParser.makeHttpRequest(url1,"GET", params);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//            Log.d("ins===",json.toString());
			 String s=null;
	         
	            try {
	            	Log.d("==============", "Emmmntered");
	              ss=jobj.getString("tasks");
	                Log.d("Msg+++++++++++++++++++++++++++++++++++++++++++++++", ss);
	                if(ss.equals("success"))
	                {
	                	Log.d("**********", "Success");
	                	//Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
	                    publishProgress(ss);
	                }
	                else
	                {
	                	Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
	                }
	            }
	            catch(Exception e)
	            {
	            	
	            	Log.d("==============", "Catched");
	                
	            Log.d( "Error"+e,"================");
	            }
				return s;


	        	

	        }
	        @Override
			protected void onProgressUpdate(String... values) {
//	        	e.setText(values[0]);
				// TODO Auto-generated method stub, text, duration)
			}
			protected void onPostExecute(String file_url) {
	            // dismiss the dialog once done
	           // pDialog.dismiss();
				 if(ss.equals("success"))
				 {
					 Toast.makeText(getApplicationContext(), "Accept", Toast.LENGTH_LONG).show();
					 Intent i=new Intent(getApplicationContext(),User_home.class);
					  startActivity(i);
				 }
				 else
				 {
					 Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
				 }
				
	        }
	}
	class Insert1 extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
       }
        protected String doInBackground(String... args)
        {
        	
 
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("lid",sh.getString("lid", "")));
            params.add(new BasicNameValuePair("postid",postid));
            params.add(new BasicNameValuePair("id",id));
             Log.d("aaaaaaaaaaa",lid);
            
            
            Log.d("ins===",url);
//            url+="/"+a;
            JSONObject jobj=null;
			try {
				jobj = (JSONObject)jsonParser.makeHttpRequest(url2,"GET", params);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//            Log.d("ins===",json.toString());
			 String s=null;
	         
	            try {
	            	Log.d("==============", "Emmmntered");
	              ss=jobj.getString("tasks");
	                Log.d("Msg+++++++++++++++++++++++++++++++++++++++++++++++", ss);
	                if(ss.equals("success"))
	                {
	                	Log.d("**********", "Success");
	                	Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
	                    publishProgress(ss);
	                }
	                else
	                {
	                	Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
	                }
	            }
	            catch(Exception e)
	            {
	            	
	            	Log.d("==============", "Catched");
	                
	            Log.d( "Error"+e,"================");
	            }
				return s;


	        	

	        }
	        @Override
			protected void onProgressUpdate(String... values) {
//	        	e.setText(values[0]);
				// TODO Auto-generated method stub, text, duration)
			}
			protected void onPostExecute(String file_url) {
	            // dismiss the dialog once done
	           // pDialog.dismiss();
				 if(ss.equals("success"))
				 {
					 Toast.makeText(getApplicationContext(), "Rejected", Toast.LENGTH_LONG).show();
					 Intent i=new Intent(getApplicationContext(),Timeline.class);
					  startActivity(i);
				 }
				 else
				 {
					 Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
				 }
				
	        }
	}


}



