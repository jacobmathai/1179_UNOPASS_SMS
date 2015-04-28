package com.sms.read;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartApp extends Activity {
	static String mobno="";
	static int checkmode;
	@SuppressLint("NewApi") public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);  
        if (Build.VERSION.SDK_INT >= 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
        final EditText ed=(EditText)findViewById(R.id.mobstart);
        Button b=(Button)findViewById(R.id.startapp);
        ed.setCursorVisible(false);
        b.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				mobno=ed.getText().toString();
				if(!mobno.equals(""))
				{
				new AndClient(getApplicationContext()).start();
				Intent in=new Intent(StartApp.this,Home.class);
				startActivity(in);
				finish();
				}
			}
		});
      ed.setOnTouchListener(new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			ed.setText("+91");
	        ed.setCursorVisible(true);

			return false;
		}
	});
  	Button b3=(Button)findViewById(R.id.graphical);
  	b3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent in=new Intent(StartApp.this,Choose.class);
			startActivity(in) ;
			finish();
		}
	});
    }


}
