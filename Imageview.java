package com.example.myprivacy;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
//import com.squareup.picasso.Picasso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Imageview extends Activity {
	
	ImageView img;
	public static String file="",url="",ff="",dwn="";
	public static String f[];
	ProgressDialog mProgressDialog;
	private PowerManager.WakeLock mWakeLock;
	SharedPreferences sh;
	String postid;
	

	static final int DIALOG_DOWNLOAD_PROGRESS = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageview);
		
		img=(ImageView)findViewById(R.id.imageView1);
		url=getIntent().getStringExtra("filename");
		ff=getIntent().getStringExtra("file");
	    Toast.makeText(getApplicationContext(), url,Toast.LENGTH_LONG ).show();
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
//		postid=getIntent().getStringExtra("postid");
		
		
		//urll="http://192.168.43.79:5000/static/faces/"+file;
//		Toast.makeText(getApplicationContext(), "url"+urll, Toast.LENGTH_LONG).show();
//		Picasso.with(getApplicationContext())
//		.load(urll)
//		//.transform(new Circulartransform())
//		.error(R.drawable.ic_launcher)
//		.into(img);
//		
		java.net.URL thumb_u;
		try {
			//"http://"+sh.getString("ip", "")+":5000/static/faces/"+url+""
			thumb_u = new java.net.URL(url);
			Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
			img.setImageDrawable(thumb_d);
		
		}
		catch(Exception e){
			

		}
		
		
			img.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View arg0) {
					// TODO Auto-generated method stub
					AlertDialog.Builder ald=new AlertDialog.Builder(Imageview.this);
			   		ald.setTitle("Do you want to download??")
			   		.setPositiveButton(" YES ", new DialogInterface.OnClickListener() {
			   			
			   			@Override
			   			public void onClick(DialogInterface arg0, int arg1) {
			   				startDownload();
			   			}
			   		})
			   		.setNegativeButton(" NO ", new DialogInterface.OnClickListener() {
			   			
			   			@Override
			   			public void onClick(DialogInterface arg0, int arg1) {
			   			
			   			}
			   		});
			   		
			   		AlertDialog al=ald.create();
			   		al.show();
	
					return true;
				}
			});
		
		
	}
	
	
	private void startDownload() {
		Log.d("url------------------------", url);
		new DownloadFileAsync().execute(url);
		Log.d("compete------------",url);
}	

@Override
protected Dialog onCreateDialog(int id) {
switch (id) {
	case DIALOG_DOWNLOAD_PROGRESS:
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Downloading File...");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		return mProgressDialog;
}
return null;
}

class DownloadFileAsync extends AsyncTask<String, String, String> {

@Override
protected void onPreExecute() {
	super.onPreExecute();

   		PowerManager pm = (PowerManager) getSystemService(Imageview.POWER_SERVICE);
    		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,getClass().getName());
    		mWakeLock.acquire();
    		Log.d("333333333333333",url);
	showDialog(DIALOG_DOWNLOAD_PROGRESS);
}

@Override
protected String doInBackground(String... aurl) {
	int count;

try {

	URL url = new URL(aurl[0]);
	URLConnection conexion = url.openConnection();
	conexion.connect();
	
	Log.d("urllllllllllllllllllllllllllllll",aurl[0]);

	int lenghtOfFile = conexion.getContentLength();
	Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);

	File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Downloadssss");
		if (!imagesFolder.exists())
		    imagesFolder.mkdirs(); 
	InputStream input = new BufferedInputStream(url.openStream());
	Log.d("fileeeeeeeeee",ff);
	OutputStream output = new FileOutputStream(imagesFolder + "/" + ff);
    byte data[] = new byte[1024];

	long total = 0;

		while ((count = input.read(data)) != -1) {
			total += count;
			publishProgress(""+(int)((total*100)/lenghtOfFile));
			output.write(data, 0, count);
		}
		
		output.flush();
		output.close();
		input.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}		

protected void onProgressUpdate(String... progress) {
	 Log.d("ANDRO_ASYNC",progress[0]);
	 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
}

@Override
protected void onPostExecute(String unused) {
	dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
}
}
	
	

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.imageview, menu);
		return true;
	}

}
