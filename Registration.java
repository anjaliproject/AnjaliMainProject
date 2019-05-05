package com.example.myprivacy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity {
	EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9;
	RadioButton r1,r2;
	TextView tv1;
	Button b1,b2;
	 String path,fileName,attach,type;
	 String jsonresult="";
	JSONParser jsonParser = new JSONParser();
	SharedPreferences sh;
	public static String url="";
	String ip="";
	String fname="",lname="",gender="",dob="",city="",email="",mobile="",photo="",password="",confirmpass="";
	
	
	  public static String imageurl="";
		File f=null;
		 private Uri mImageCaptureUri;
			public static Bitmap imag =null;
			 private File outPutFile = null;
			 Uri imageUri=null;
				private static final int CAMERA_PIC_REQUEST = 0;
				
				
				String fpth="";

				 private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
				private static final int SELECT_PICTURE = 2500;
				public static String encodedImage="";
	  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		ed1=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		
	    tv1=(TextView)findViewById(R.id.textView1);
		
	    r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);
		
		ed3=(EditText)findViewById(R.id.editText3);
		ed4=(EditText)findViewById(R.id.editText4);
		ed5=(EditText)findViewById(R.id.editText5);
		ed6=(EditText)findViewById(R.id.editText6);
		ed7=(EditText)findViewById(R.id.editText7);
		ed8=(EditText)findViewById(R.id.editText8);
		ed9=(EditText)findViewById(R.id.editText9);
		
		b1=(Button)findViewById(R.id.button1);
	
		b2=(Button)findViewById(R.id.button2);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		url="http://"+sh.getString("ip", "")+":5000/signup";
		
		try
		{
		if(android.os.Build.VERSION.SDK_INT>9)
		{
			StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
			
		}
		catch(Exception e)
		{
			
		}
		 

		
		try
		{
		if(android.os.Build.VERSION.SDK_INT>9)
		{
			StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
			
		}
		catch(Exception e)
		{
			
		}
		 ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, "NewPicture");
			values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
			imageUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		    	startActivityForResult(i, GALLERY_CODE);
			}
		});
		
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
							
				selectUploadOption() ;	
//				Intent i = new Intent(getApplicationContext(),Login.class);
//				startActivity(i);
				
			}
		});

		
			
//		b2.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				
//				String f_name=ed1.getText().toString();
//				String l_name=ed2.getText().toString();
//				String gender=tv1.getText().toString();
//				String dob=ed3.getText().toString();
//				String city=ed4.getText().toString();
//				String email=ed5.getText().toString();
//				String mobile=ed9.getText().toString();
//				String photo=ed6.getText().toString();
//				String password=ed7.getText().toString();
//				String confirm_pw=ed8.getText().toString();
//			
//				
//				
//			}
//		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}
	public String uploadFile(String sourceFileUri,String sourceFileUri1 ) {
        try
        {
            String fileName = sourceFileUri;
            String fileName1 = sourceFileUri1;
            
//            Toast.makeText(getApplicationContext(),"aaaaaaaa ",Toast.LENGTH_LONG).show();

            FileUploads fp = new FileUploads(fileName);
    		Map mp = new HashMap<String,String>();
    		Map mp1 = new HashMap<String,String>();
//            Toast.makeText(getApplicationContext(),"cccccc ",Toast.LENGTH_LONG).show();

            
            mp.put("fpath", fpth);
            mp.put("fname", fname);
            mp.put("lname", lname);
            mp.put("email", email);
            mp.put("city", city);
            mp.put("dob", dob);
            mp.put("password", password);
//            mp.put("confirmpass", confirmpass);
            mp.put("gender", gender);
            mp.put("mobile", mobile);
//            mp.put("type", type);
            mp1.put("files", fileName);
    		mp1.put("filesp", fileName1);
    		 Toast.makeText(getApplicationContext(),"Successfully Registered ",Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),"eeeeeeeeeeee ",Toast.LENGTH_LONG).show();
           String re= fp.multipartRequest(url, mp, mp1, "files", "application/octet-stream");
            //Toast.makeText(getApplicationContext(),"ffffffffffffffffff ",Toast.LENGTH_LONG).show();
//            jsonresult = fp.getResponse().toString();

//            Toast.makeText(getApplicationContext()," Success ",Toast.LENGTH_LONG).show();
            return "OK";
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"error "+e,Toast.LENGTH_LONG).show();
            return "na";
        }
    }

	 private void selectUploadOption() 
	 {
	  final CharSequence[] items = {"Agree", "Not Agree" };

	  AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
	  builder.setTitle("Do you want to allow anyone to upload your photo?");
	  builder.setItems(items, new DialogInterface.OnClickListener() {
	   @Override
	   public void onClick(DialogInterface dialog, int item) {
		   
		   
			   if (items[item].equals("Agree")) 
			    {
				   type="PUBLIC";
				   
			    }
			   else if(items[item].equals("Not Agree")) 
			    {
				   type="PRIVATE";
			    }
			     fname=ed1.getText().toString();
				 lname=ed2.getText().toString();
				 gender=tv1.getText().toString();
				 dob=ed3.getText().toString();
				 city=ed4.getText().toString();
			     email=ed5.getText().toString();
				 mobile=ed9.getText().toString();
				 photo=ed6.getText().toString();
				 password=ed7.getText().toString();
				 confirmpass=ed8.getText().toString();
				if(r1.isChecked())
				{
					gender=r1.getText().toString();
				}
				else
				{
					gender=r2.getText().toString();
				}
				if(fname.equals(""))
				{
					ed1.setError("Enter first name");
					ed1.requestFocus();
				}
				else if(lname.equals(""))
				{
					ed2.setError("Enter Last name");
					ed2.requestFocus();
				}
				else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
				{
					ed5.setError("Enter Valid Email");
					ed5.requestFocus();
				}
				else if(city.equals(""))
				{
					ed4.setError("Enter city");
					ed4.requestFocus();
				}
				else if(dob.equals(""))
				{
					ed3.setError("Enter date of birth");
					ed3.requestFocus();
				}
				else if(mobile.equals(""))
				{
					ed9.setError("Enter mobile");
					ed9.requestFocus();
				}
//				else if(photo.equals(""))
//				{
//					ed6.setError("select photo");
//					ed6.requestFocus();
//				}
				else if(password.equals(""))
				{
					ed7.setError("Enter password");
					ed7.requestFocus();
				}
				else if(password.length()<8)
				{
					ed7.setError("Minimum 8 characters required");
					ed7.requestFocus();
				}
				else if(!password.equals(confirmpass))
				{
					ed8.setError("Password and Confirm password are does not match");
					ed8.requestFocus();
					ed8.clearComposingText();
					ed7.clearComposingText();
				}
				else if(password.equals(confirmpass))
				{
						String ok=uploadFile(path,fpth);
					   if(ok.equals("OK"))
						{
							Intent i=new Intent(getApplicationContext(),Login.class);
							startActivity(i);
						}
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Does not match", Toast.LENGTH_LONG).show();
				}
	
			   
               
			   
			   
		   }	    
	   
	  });
	  builder.show();
	 }
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) 
		  {
			  try {
				  mImageCaptureUri = data.getData();
		       Uri selectedImage = data.getData();
		       path=getRealPathFromURI(selectedImage);
		       fpth=getRealPathFromURI(selectedImage);
		       Bitmap photo = decodeFile(new File(fpth));
		      // iv.setImageBitmap(photo);
		       ed6.setText(fpth);
			  }
			   catch (Exception e) {
				   e.printStackTrace();
			   }
		  }
		  else if  (requestCode == CAMERA_PIC_REQUEST)
		   {	     
			if (resultCode == RESULT_OK) 
			{   
//		   try
//		   {
//			   try
//			   {
//				   System.out.println("Camera Image URI : "+mImageCaptureUri);
//				  // CropingIMG();
	//
//			    fpth=outPutFile.getPath();//File imgFile=new File(data.);
//			    Toast.makeText(getApplicationContext(), fpth, Toast.LENGTH_LONG).show(); 
////			    Bitmap myBitmap=BitmapFactory.decodeFile(fpth);
////			    img.setImageBitmap(myBitmap);
	//
//			    
//			    Bitmap photo = decodeFile(new File(fpth));
//			       v1.setImageBitmap(photo);
//			    
//			    
////			    Bundle extras=data.getExtras();
////			    Bitmap photo = (Bitmap)extras.get("data");
////			    //decodeFile(outPutFile);
////			    imageView.setImageBitmap(photo);
//			//setContentView(img);
//			    fpth=outPutFile.getPath();
//			  }
//			  catch (Exception e) {
//				   e.printStackTrace();
//			  }
			   
//			   try
//			   {
//				   System.out.println("Camera Image URI : "+mImageCaptureUri);
//				  // CropingIMG();
	//
//			    fpth=outPutFile.getPath();//File imgFile=new File(data.);
//			    Bitmap myBitmap=BitmapFactory.decodeFile(fpth);
//			    v1.setImageBitmap(myBitmap);
	//
//			    fpth=outPutFile.getPath();
//			    Log.d("---------------", "fffff"+fpth);
//			    
//			  }
//			  catch (Exception e) {
//				  Log.d("rrrr**********", "image"+e);
//				   e.printStackTrace();
//			  }
//		  }
//		  catch (Exception e) {
//			   e.printStackTrace();
//			   Log.d("/*/*/*/*", "image"+e);
//		    }
//			   
			   
			   
		//  }
			   
			   
				  
		            try {
		            	
		                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
		               
		                		//v1.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, 120, 120, false));
		               Bitmap newbit=getResizedBitmap(thumbnail, 1886, 2386); 
//		                iv.setImageBitmap(newbit);
//		                iv.setVisibility(View.VISIBLE);
		                 imageurl = getRealPathFromURI(imageUri);  
		                fpth=imageurl;
		                String[] ff=fpth.split("/");
		                String imgname=ff[5];
		                Toast.makeText(getApplicationContext(), imageurl+"  pp  "+imgname, Toast.LENGTH_SHORT).show();
		               
		               File file = new File(imageurl);
		               int ln=(int) file.length();
		               byte[] byteArray = null;
		   	       try
		   	        {
		   		        InputStream inputStream = new FileInputStream(file);
		   		        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		   		        byte[] b = new byte[ln];
		   		        int bytesRead =0;
		   		    
		   		        while ((bytesRead = inputStream.read(b)) != -1)
		   		        {
		   		        	bos.write(b, 0, bytesRead);
		   		        }
		   		        inputStream.close();
		   		        byteArray = bos.toByteArray();
		   	        }
		   	        catch (IOException e)
		   	        {
		   	            Toast.makeText(this,"String :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
		   	        }
		   	        String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
		               
		   	        encodedImage=str;
		   	     Log.d("sems", "-------12-------");
		   	    // Toast.makeText(getApplicationContext(),encodedImage, Toast.LENGTH_LONG).show();
		               
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
	   
			   
			 
			   
			   
		  }	   
			   
			   
//		  catch (Exception e) {
//			   e.printStackTrace();
//		  }
		   else if (requestCode == CROPING_CODE) {
		   
		   try {
			   if(outPutFile.exists()){
				   Bitmap photo = decodeFile(outPutFile);              
//				   iv.setImageBitmap(photo);
		     
				   fpth=imageurl;
		    }
		    else {
		     Toast.makeText(getApplicationContext(), "Error while save image", Toast.LENGTH_SHORT).show();
		    }
		   }
		   catch (Exception e) {
			   e.printStackTrace();
		   }
		  }
		  if(fpth.equals(""))
			{
			 // b3.setEnabled(false);
			  Toast.makeText(getApplicationContext(), "fpth null", Toast.LENGTH_SHORT).show();
			   
			}
			else
			{
//				b3.setEnabled(true)=
				;
//				b4.setVisibility(View.VISIBLE);
				  Toast.makeText(getApplicationContext(), "ygygy", Toast.LENGTH_SHORT).show();
				   
			}
		 }
		 }


		 private Bitmap decodeFile(File f) {
		  try {
		   // decode image size
		   BitmapFactory.Options o = new BitmapFactory.Options();
		   o.inJustDecodeBounds = true;
		   BitmapFactory.decodeStream(new FileInputStream(f), null, o);

		   // Find the correct scale value. It should be the power of 2.
		   final int REQUIRED_SIZE = 512;
		   int width_tmp = o.outWidth, height_tmp = o.outHeight;
		   int scale = 1;
		   while (true) {
		    if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
		     break;
		    width_tmp /= 2;
		    height_tmp /= 2;
		    scale *= 2;
		   }

		   // decode with inSampleSize
		   BitmapFactory.Options o2 = new BitmapFactory.Options();
		   o2.inSampleSize = scale;
		   return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		  } catch (FileNotFoundException e) {
		  }
		  return null;
		 }

		 private String getRealPathFromURI(Uri contentURI) {
			  String path;
			  Cursor cursor = getContentResolver()
			          .query(contentURI, null, null, null, null);
			  if (cursor == null)
			   path=contentURI.getPath();

			  else {
			   cursor.moveToFirst();
			   int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			   path=cursor.getString(idx);

			  }
			  if(cursor!=null)
			   cursor.close();
			  return path;
			 }


		   public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		        int width = bm.getWidth();
		        int height = bm.getHeight();
		        float scaleWidth = ((float) newWidth) / width;
		        float scaleHeight = ((float) newHeight) / height;
		        // CREATE A MATRIX FOR THE MANIPULATION
		        Matrix matrix = new Matrix();
		        // RESIZE THE BIT MAP
		        matrix.postScale(scaleWidth, scaleHeight);

		        // "RECREATE" THE NEW BITMAP
		        Bitmap resizedBitmap = Bitmap.createBitmap(
		            bm, 0, 0, width, height, matrix, false);
		        bm.recycle();
		        return resizedBitmap;
		    }


}
