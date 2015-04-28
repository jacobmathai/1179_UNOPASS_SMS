package com.sms.read;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Image  extends Activity {
	FileOutputStream fout;
int i=0;
String s="";
	AlertDialog alertDialog ;
	 public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.image);  
	        ImageView iv=(ImageView)findViewById(R.id.imageView1);
	        Bitmap bim=BitmapFactory.decodeFile("/mnt/sdcard/opass/pass.jpg");
	        iv.setImageBitmap(bim);
	        alertDialog=new AlertDialog.Builder(this).create();
	       
	        iv.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					i++;
					final float x=event.getX();
					final float y=event.getY();
					Toast.makeText(getApplicationContext(), "x-----"+x+"y-----"+y, Toast.LENGTH_SHORT).show();
					if(i==1)
					{
						s=x+","+y+",";
					}if(i==2)
					{
						s +=x+","+y+",";
					} if(i==3)
					{
						s+=x+","+y+",";
						Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
					    // Setting Dialog Title
				        alertDialog.setTitle("Set Password");
				 
				        // Setting Dialog Message
				        alertDialog.setMessage("Do u want to save this password?");
				 
				        
				        // Setting OK Button
				        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int which) {
				                // Write your code here to execute after dialog closed
				              //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
				                	try {
				                		fout=new FileOutputStream("/mnt/sdcard/opass/lpass.txt");

										fout.write((s).getBytes());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				                	Intent in=new Intent(Image.this,StartApp.class);
				                	startActivity(in);
				                	finish();
				                }
				        });
				        alertDialog.setButton2 ("Cancel", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int which) {
			                // Write your code here to execute after dialog closed
			              //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
			                	
			                }
			        });
			 	        // Showing Alert Message
				        alertDialog.show();
						
					}
					else
					{
						Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_SHORT).show();
						
					}
			
	    
					
					
					return false;
				}
			});
	    }

}
