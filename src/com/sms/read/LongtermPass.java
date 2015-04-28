package com.sms.read;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LongtermPass extends Activity {
	static String s="";
	 public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.password);  
	        final EditText ed=(EditText)findViewById(R.id.LTpassword);
	Button btn=(Button)findViewById(R.id.cpass);
	btn.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 s=ed.getText().toString();
		String url="http://"+Server_Config.tsp_ip+":8084/OPASS/PasswordcheckMobile.jsp?ltp="+s.trim()+"&un="+Request.uname;
			String s1=new WebClient().getData(url);
			Toast.makeText(getApplicationContext(), s1.trim(),Toast.LENGTH_SHORT).show();
			
			if(s1.trim().equals("valid"))
			{
			Intent in=new Intent(LongtermPass.this,SMS.class);
			startActivity(in);
			}
			else
			{
				Intent in=new Intent(LongtermPass.this,Home.class);
				startActivity(in);
			}
			
			
		}
	});
	    }
	
}
