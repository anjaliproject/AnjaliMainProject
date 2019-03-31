package com.example.myprivacy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Profile extends Activity {
	TextView t2,t3,t4,t5,t6,t7;
	ImageView img1;
	EditText e3,e4,e5,e6,e7;
	RadioButton r3,r4;
	Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		t2=(TextView)findViewById(R.id.textView1);
		t3=(TextView)findViewById(R.id.textView2);
		t4=(TextView)findViewById(R.id.textView3);
		t5=(TextView)findViewById(R.id.textView4);
		t6=(TextView)findViewById(R.id.textView5);
		t7=(TextView)findViewById(R.id.textView6);
		
		img1=(ImageView)findViewById(R.id.imageView1);
		
		e3=(EditText)findViewById(R.id.editText1);
		e4=(EditText)findViewById(R.id.editText2);
		e5=(EditText)findViewById(R.id.editText3);
		e6=(EditText)findViewById(R.id.editText4);
		e7=(EditText)findViewById(R.id.editText5);
		
		r3=(RadioButton)findViewById(R.id.radioButton1);
		r4=(RadioButton)findViewById(R.id.radioButton2);
		
		btn1=(Button)findViewById(R.id.button1);
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
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
