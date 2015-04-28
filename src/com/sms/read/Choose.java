package com.sms.read;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Choose extends Activity{
	private static final int SELECT_PHOTO = 100;
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseimage);  
        Button b1=(Button)findViewById(R.id.choose);
        final File f=new File("/mnt/sdcard/opass/pass.jpg");
        final File f1=new File("/mnt/sdcard/opass/lpass.txt");
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!f.exists())
				{
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
				}
				else{
					Toast.makeText(getApplicationContext(), "Already choosed one Image",Toast.LENGTH_SHORT).show();
				}
				
			}
		});
       
        Button b3=(Button)findViewById(R.id.changepass);
        b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				StartApp.checkmode=3;
				Intent in=new Intent(Choose.this,CheckPass.class);
				startActivity(in);
				finish();
				
			}
		});
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(selectedImage);
					  Toast.makeText(getApplicationContext(), selectedImage+"", Toast.LENGTH_LONG).show();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	            File _sdCard = Environment.getExternalStorageDirectory();
	            File _picDir  = new File(_sdCard, "opass");
	            _picDir.mkdirs();

	            File _picFile = new File(_picDir,  "pass.jpg");
	            FileOutputStream _fos = null;
				try {
					_fos = new FileOutputStream(_picFile);
				
	          yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100, _fos);
	            _fos.flush();
	        Toast.makeText(getApplicationContext(), "Image downloaded", Toast.LENGTH_SHORT).show();
					_fos.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent in=new Intent(Choose.this,Image.class);
				startActivity(in);
	        }
	    }
	}
}
