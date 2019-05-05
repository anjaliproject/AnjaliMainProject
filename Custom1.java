package com.example.myprivacy;



import java.util.ArrayList;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Custom1 extends BaseAdapter{

	private Context Context;
	String url="";
	SharedPreferences sh;
	ArrayList<String> p;
	ArrayList<String> f;
	ArrayList<String> d;
	ArrayList<String> fl;
	ArrayList<String> t;
	
	
	public Custom1(Context applicationContext, ArrayList<String> pid, ArrayList<String> frmid, ArrayList<String> date, ArrayList<String> filename, ArrayList<String> text) {
		this.Context=applicationContext;
		this.p=pid;
		this.f=frmid;
		this.d=date;
		this.fl=filename;
		this.t=text;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fl.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		if(convertView==null)
		{
			gridView=new View(Context);
			gridView=inflator.inflate(R.layout.activity_custom1, null);
			
		}
		else
		{
			gridView=(View)convertView;
			
		}
		
		TextView tv1=(TextView)gridView.findViewById(R.id.textView1);
		TextView tv2=(TextView)gridView.findViewById(R.id.textView2);
		ImageView img=(ImageView)gridView.findViewById(R.id.imageView1);
		TextView tv3=(TextView)gridView.findViewById(R.id.textView3);
		sh=PreferenceManager.getDefaultSharedPreferences(Context);		
		url="http://"+sh.getString("ip", "")+":5000/static/faces/"+p.get(position)+".jpg";
		
		
		tv1.setTextColor(Color.BLACK);
		tv2.setTextColor(Color.GRAY);
		tv3.setTextColor(Color.GRAY);
		
		
		
		tv1.setText(f.get(position));
		tv2.setText(d.get(position));
		
	//String urll="http://192.168.43.79:5000/static/faces/"+fl.get(position);
	//Toast.makeText(Context, urll, Toast.LENGTH_LONG).show();
	tv3.setText(t.get(position));
//		java.net.URL thumb_u;
//		try {
//			thumb_u = new java.net.URL(urll);
//			Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
//			img.setImageDrawable(thumb_d);
//			

//			
//			Picasso.with(Context)
//		   .load(urll)
//		  //  .transform(new Circulartransform())
//		    .error(R.drawable.ic_launcher)
//		    .into(img);
	
	
//	java.net.URL thumb_u;
//	try {
//		thumb_u = new java.net.URL("http://"+sh.getString("ip", "")+":5000/static/faces/"+img);
//		Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
//		img.setImageDrawable(thumb_d);
//	
//	}
//	catch(Exception e){
//		
//
//	}
	
	java.net.URL thumb_u;
	try {
		thumb_u = new java.net.URL("http://"+sh.getString("ip", "")+":5000/static/faces/"+p.get(position)+".jpg");
		Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
		img.setImageDrawable(thumb_d);
//		Toast.makeText(Context, photo, Toast.LENGTH_LONG).show();

	
	}
	catch(Exception e){
//		Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();

	}
	
	
	
			img.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 url="http://"+sh.getString("ip", "")+":5000/static/faces/"+p.get(position)+".jpg";
						
						
					Toast.makeText(Context, p.get(position), Toast.LENGTH_LONG).show();
					Intent i=new Intent(Context,Imageview.class);
					i.putExtra("filename", url);
					i.putExtra("file", p.get(position)+".jpg");
					Context.startActivity(i);
					
				}
			});
			
		
		return gridView;
		
	}
	
	
	
}
