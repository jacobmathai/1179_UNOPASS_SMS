package com.sms.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends Activity {
	static int i = 0;
	static int sel;
	Activity context;
	static String yourNumber;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		Button b1 = (Button) findViewById(R.id.reg);
		Button b2 = (Button) findViewById(R.id.login);
		Button b3=(Button)findViewById(R.id.butrec);
		final Button b4 = (Button) findViewById(R.id.okhome);
		final EditText lp = (EditText) findViewById(R.id.lp1);
		String ip = getLocalIpAddress();
		Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_LONG).show();
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		yourNumber = mTelephonyMgr.getLine1Number();
		yourNumber = StartApp.mobno;

		Toast.makeText(getApplicationContext(), yourNumber, Toast.LENGTH_LONG)
				.show();
		File f = new File(Environment.getExternalStorageDirectory() + "/opass");
		f.mkdirs();

  		final File f1 = new File(f.getAbsolutePath() + "/regdetails.txt");

		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sel = 1;
				StartApp.checkmode = 2;
				if (!f1.exists()) {

					Intent in = new Intent(Home.this, Request.class);
					startActivity(in);
					finish();
				} else {
					Intent in = new Intent(Home.this, CheckPass.class);
					startActivity(in);
					finish();
				}

			}
		});
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				try {
					StartApp.checkmode = 1;
					Intent in = new Intent(Home.this, CheckPass.class);
					startActivity(in);
					finish();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		 b3.setOnClickListener(new View.OnClickListener() {
		
		 @Override
		 public void onClick(View arg0) {
		 // TODO Auto-generated method stub
		 sel=2;
		 Intent in=new Intent(Home.this,Request.class);
		 startActivity(in);
		 finish();
		 }
		 });
	}

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						String ip = Formatter.formatIpAddress(inetAddress
								.hashCode());
						// Log.i(TAG, "***** IP="+ ip);
						return ip;
					}
				}
			}
		} catch (SocketException ex) {
			// Log.e(TAG, ex.toString());
		}
		return null;
	}
}