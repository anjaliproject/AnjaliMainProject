package com.example.myprivacy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Post_photos extends Activity {
	
	ImageView img1;
	TextView t1,t2;
	EditText ed1,ed2;
	Button bt1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_photos);
		
		img1=(ImageView)findViewById(R.id.imageView1);
		
		t1=(TextView)findViewById(R.id.textView2);
		t2=(TextView)findViewById(R.id.textView3);
		
		ed1=(EditText)findViewById(R.id.editText2);
		ed2=(EditText)findViewById(R.id.editText3);
		
		bt1=(Button)findViewById(R.id.button2);
		bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_photos, menu);
		return true;
	}

}
