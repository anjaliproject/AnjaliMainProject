package com.example.myprivacy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Timeline extends Activity implements OnItemClickListener {
	ListView L1;
	public static String lid="",ss="";
	public static ArrayList<String> postid,frmid,date,Description;
	JSONParser jsonParser = new JSONParser();
	SharedPreferences sh;
	public static String url="";
	String ip="";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
	
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url="http://"+sh.getString("ip", "")+":5000/timeline";
		
		new Insert().execute();
		L1=(ListView)findViewById(R.id.listView1);
		L1.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
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
            
            Log.d("ins===",url);
            JSONArray jobj=null;
			try {
				jobj = (JSONArray)jsonParser.makeHttpRequest(url,"GET", params);
			    postid=new ArrayList<String>();
            	frmid=new ArrayList<String>();
            	date=new ArrayList<String>();
            	Description=new ArrayList<String>();
              	for (int i = 0; i < jobj.length(); i++) 
                    {
                        JSONObject c=jobj.getJSONObject(i);
                       postid.add(c.getString("Post_id"));
                       frmid.add(c.getString("Name"));
                       String day=c.getString("Date");
                       day=day.replace("00:00:00 GMT", "");
                       date.add(day);
                       Description.add(c.getString("Description"));
                    }
                	
            }
            catch(Exception e)
            {
            	
            	Log.d("==============", "Catched");
                
            Log.d( "Error"+e,"================");
            }
			return null;
        }
        
        @Override
		protected void onProgressUpdate(String... values) {
//        	e.setText(values[0]);
			// TODO Auto-generated method stub, text, duration)
		}
		protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
           // pDialog.dismiss();
			L1.setAdapter(new Custom1(Timeline.this,  postid,frmid,date,postid,Description));
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder ald=new AlertDialog.Builder(Timeline.this);
		ald.setTitle("Do you want to exit  ?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Intent.ACTION_MAIN);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.addCategory(Intent.CATEGORY_HOME);
				startActivity(i);
			}
		}).setNegativeButton("NO", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		AlertDialog al=ald.create();
		al.show();
	}
	

}


