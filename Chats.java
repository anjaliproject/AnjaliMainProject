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

public class Chats extends Activity implements OnItemClickListener {
	
	
	ListView L1;
	SharedPreferences sh;
	public static String url="";
	public static ArrayList<String>name;
	public static ArrayList<String>id;
	public static ArrayList<String>msg;
	String ip="";
	JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chats);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url="http://"+sh.getString("ip", "")+":5000/view_friends";
		L1=(ListView)findViewById(R.id.listView1);
		L1.setOnItemClickListener(this);
		new LoadAllProducts().execute();
	}
	 class LoadAllProducts extends AsyncTask<String,String,String>
     {

         protected void onPreExecute() {
             super.onPreExecute();

         }

         @Override
         protected String doInBackground(String... strings) {
      	   

        	 List<NameValuePair> params = new ArrayList<NameValuePair>();
        	 params.add(new BasicNameValuePair("lid", sh.getString("lid", "")));
             JSONArray json=null;
			try {
				json = (JSONArray)jsonParser.makeHttpRequest(url,"GET", params);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            // Log.d("Reultttttt=====---------",json+"");
             try
             {
                 //int success = json.getInt(TAG_SUCCESS);
                
                // Log.d("+++++++++++",ar+"");
                 name=new ArrayList<String>();
                 id=new ArrayList<String>();
                
              
                 for (int i = 0; i < json.length(); i++) {
                	 JSONObject c=json.getJSONObject(i);
                         name.add(c.getString("Name"));
                         id.add(c.getString("U_id"));
                        
                         
                          Log.d("+++++++++++",c+"");
                          Log.d("********************", name.get(i));
                        }

          	   

             }
             catch(JSONException e)
             {
                  Log.d("err====",e.getMessage());
             }

             return null;
         }
         protected void onPostExecute(String file_url) {

             runOnUiThread(new Runnable() {
                 public void run() {
                	 ArrayAdapter<String>ad=new ArrayAdapter<String>(Chats.this,android.R.layout.simple_list_item_1,name);
                     L1.setAdapter(ad);


                 }
             });

         }

}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	   
		Intent i=new Intent(getApplicationContext(),View_chats.class);
		
		i.putExtra("name", name.get(arg2));
		i.putExtra("Friend_id",id.get(arg2));
		
		startActivity(i);
		
	}

}
