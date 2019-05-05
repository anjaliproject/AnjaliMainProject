package com.example.myprivacy;




import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
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

public class Custom extends BaseAdapter{

	private Context Context;
	ArrayList<String> a,b;
	
	SharedPreferences sh;
	
	public Custom(Context applicationContext, ArrayList<String> x,ArrayList<String> y) {
		this.Context=applicationContext;
		this.a=x;
		this.b=y;

		sh=PreferenceManager.getDefaultSharedPreferences(applicationContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return a.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		if(convertView==null)
		{
			gridView=new View(Context);
			gridView=inflator.inflate(R.layout.activity_custom, null);
			
		}
		else
		{
			gridView=(View)convertView;
			
		}
		
		TextView tv1=(TextView)gridView.findViewById(R.id.textView1);	
		ImageView img=(ImageView)gridView.findViewById(R.id.imageView1);
		

       
		tv1.setTextColor(Color.BLACK);
		
		
		tv1.setText(b.get(position));
		
		java.net.URL thumb_u;
		try {
			thumb_u = new java.net.URL("http://"+sh.getString("ip", "")+":5000/static/image/"+a.get(position)+"/"+a.get(position)+".jpg");
			Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
			img.setImageDrawable(thumb_d);
			

			
			
			
		}
		catch(Exception e){
			
		}
		
		return gridView;
		
	}
	
	
	
}
