package com.example.myprivacy;

import java.util.ArrayList;

import java.util.List;
import org.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

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
import android.widget.Toast;

public class Send_Request extends Activity implements OnItemClickListener {
	
	ListView LV11;
	SharedPreferences sh;
	public static String url="";
	String ip="";
    private int pos;
	ArrayList<String> lid;
	ArrayList<String> name;
	ArrayList<String> photo;
	ArrayList<String> f_id;
	
	JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send__request);
		 sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		LV11=(ListView)findViewById(R.id.listView1);
		LV11.setOnItemClickListener(this);
		
		url="http://"+sh.getString("ip", "")+":5000/send_request";
		

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
     
        
        JSONArray json=null;
   
       
		try {
			
			
			json = (JSONArray)jsonParser.makeHttpRequest(url,"GET", params);
        	name=new ArrayList<String>();
        	 lid=new ArrayList<String>();
            photo=new ArrayList<String>();
            
            f_id=new ArrayList<String>();
          

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
       
		LV11.setAdapter(new Custom(Send_Request.this, name,photo));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send__request, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		
		 AlertDialog.Builder alert = new AlertDialog.Builder(Send_Request.this);

	        alert.setTitle("Send Request");


	        alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                try {

	                    List<NameValuePair> params = new ArrayList<NameValuePair>();
	                    params.add(new BasicNameValuePair("User_id", lid.get(pos)));
	                    params.add(new BasicNameValuePair("Friend_id", f_id.get(pos)));
	                    url = "http://"+ip+":5000/f_request";

	                    Log.d("ins===", url);
	                    JSONObject jobj = null;
	                    jobj = (JSONObject) jsonParser.makeHttpRequest(url, "GET", params);
	                    String ss = jobj.getString("task");
	                    Log.d("Msg++++++++++++++++", ss);
	                    if (ss.equals("done")) {


	                        Toast.makeText(getApplicationContext(), "location added", Toast.LENGTH_LONG).show();
	                        Intent i = new Intent(getApplicationContext(), Send_Request.class);
	                        startActivity(i);

	                    } else {


	                    }
	                } catch (Exception e) {
	                    Log.d("==============", "" + e);
	                    Toast.makeText(getApplicationContext(), "err"+e, Toast.LENGTH_LONG).show();
	                }


	                // Do something with value!
	            }
	        });

	        alert.setNegativeButton("Cancelt", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                // Canceled.
	                try {

	                    List<NameValuePair> params = new ArrayList<NameValuePair>();
	                    params.add(new BasicNameValuePair("User_id", lid.get(pos)));
	                    params.add(new BasicNameValuePair("Friend_id", lid.get(pos)));
	                    url = "http://" + ip + ":5000/f_request";

	                    Log.d("ins===", url);
	                    JSONObject jobj = null;
	                    jobj = (JSONObject) jsonParser.makeHttpRequest(url, "GET", params);
	                    String ss = jobj.getString("task");
	                    Log.d("Msg++++++++++++++++", ss);

	                } catch (Exception e) {
	                    Log.d("==============", "" + e);
	                }

	            }
	        });

	        alert.show();

		
	}

}
