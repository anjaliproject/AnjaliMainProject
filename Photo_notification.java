package com.example.myprivacy;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ListView;

public class Photo_notification extends Activity {
	ListView LV1;
	
	JSONParser jsonParser = new JSONParser();
	SharedPreferences sh;
	public static String url="";
	String ip="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_notification);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url="http://"+sh.getString("ip", "")+":5000/notification";
		
		LV1=(ListView)findViewById(R.id.listView1);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_notification, menu);
		return true;
	}

}
