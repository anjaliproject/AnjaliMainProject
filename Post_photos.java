package com.example.myprivacy;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Post_photos extends Activity {
	
	ImageView img1;
	TextView t1,t2;
	EditText ed1,ed2;
	Button bt1,bt2;
	JSONParser jsonParser = new JSONParser();
	private static final int FILE_SELECT_CODE = 0;
	SharedPreferences sh;
	public static String url="";
	String ip="";
    String path,fileName,attach,type;
	String jsonresult="";
    String desc="",fname="",lid="",status="",ok="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_photos);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url="http://"+sh.getString("ip", "")+":5000/post";
		
		img1=(ImageView)findViewById(R.id.imageView1);
		
		t1=(TextView)findViewById(R.id.textView2);
		t2=(TextView)findViewById(R.id.textView3);
		
		ed1=(EditText)findViewById(R.id.editText2);
		ed2=(EditText)findViewById(R.id.editText3);
		
		bt1=(Button)findViewById(R.id.button1);
		bt2=(Button)findViewById(R.id.button2);
		bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //getting all types of files
		        intent.setType("*/*");
		        intent.addCategory(Intent.CATEGORY_OPENABLE);

		        try {
		            startActivityForResult(Intent.createChooser(intent, ""),FILE_SELECT_CODE);
		        } catch (android.content.ActivityNotFoundException ex) {

		            Toast.makeText(getApplicationContext(), "Please install a File Manager.",Toast.LENGTH_SHORT).show();
		        }
				
			}
		});
		
		bt2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				desc=ed2.getText().toString();
				
				uploadFile(path);
				Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(),User_home.class);
				startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_photos, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (resultCode == RESULT_OK)
        {
            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());

            //String path = null;
            try
            {
               path = FileUtils.getPath(this, uri);
                //Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
                String[] name=path.split("/");
                ed1.setText(path);

                fname = name[name.length-1];
                String[] tty=fname.split("\\.");
                type=tty[1];

            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error"+e, Toast.LENGTH_LONG).show();
            }
        }
        else{
            attach="";
            // Toast.makeText(getApplicationContext(), "df", Toast.LENGTH_LONG).show();
        }
}
	public String uploadFile(String sourceFileUri) {
        try
        {
            String fileName = sourceFileUri;
//            Toast.makeText(getApplicationContext(),"aaaaaaaa ",Toast.LENGTH_LONG).show();

            FileUpload fp = new FileUpload(fileName);
//            Toast.makeText(getApplicationContext(),"bbbbbbb ",Toast.LENGTH_LONG).show();

            Map mp = new HashMap<String, String>();
//            Toast.makeText(getApplicationContext(),"cccccc ",Toast.LENGTH_LONG).show();

            mp.put("name", fileName);
            mp.put("fname", fname);
            mp.put("desc", desc);
            mp.put("status", status);
            Log.d("dddddddddddd",desc);
            mp.put("lid", sh.getString("lid", ""));
            Log.d("lllllllllllll",url);
                       
//            Toast.makeText(getApplicationContext(),"ddddddddddddddd ",Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),"eeeeeeeeeeee ",Toast.LENGTH_LONG).show();
            jsonresult=fp.multipartRequest(url, mp, fileName, "files", "application/octet-stream");
            //Toast.makeText(getApplicationContext(),"ffffffffffffffffff ",Toast.LENGTH_LONG).show();
//            jsonresult = fp.getResponse().toString();

           // Toast.makeText(getApplicationContext()," "+jsonresult,Toast.LENGTH_LONG).show();
            return jsonresult;
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"error "+e,Toast.LENGTH_LONG).show();
            return "na";
        }
    }

}



