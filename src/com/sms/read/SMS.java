package com.sms.read;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMS extends Activity {
	static int i = 100;
	Button btnSendSMS;
	static EditText txtPhoneNo;
	static EditText txtMessage;
	EditText webid;
	String s = "";
	String[] msg;

	/** Called when the activity is first created. */
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		if (Build.VERSION.SDK_INT >= 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		try {
			btnSendSMS = (Button) findViewById(R.id.send);
			txtPhoneNo = (EditText) findViewById(R.id.mobno);
			txtMessage = (EditText) findViewById(R.id.msg);
			FileInputStream fin = new FileInputStream(
					Environment.getExternalStorageDirectory()
							+ "/opass/regdetails.txt");
			byte[] b = new byte[fin.available()];

			fin.read(b);
			s = new String(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg = SMS.splitter(s);
		System.out.println("moblinooooooooooooooo" + msg[0]);
		System.out.println("rannnnnnnn" + msg[1]);

		txtMessage.setText(msg[0]);
		txtPhoneNo.setText(msg[1]);
		btnSendSMS.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String message = "";
			  	String mac = getMac();

				String phoneNo = txtPhoneNo.getText().toString();
				System.out.println("macccccc" + mac);
				if (Home.sel == 1) {
					message = txtMessage.getText().toString() + "/" + mac + "/"
							+ msg[2] + ",register";
				}
				if (Home.sel == 2) {
					message = txtMessage.getText().toString() + "/" + mac + "/"
							+ msg[2] + ",recovery";
				}

				if (phoneNo.length() > 0 && message.length() > 0) {
					sendSMS(phoneNo, message);

					String s = System.currentTimeMillis() + "";
					// int k=i*i;
					// / i--;
					// String op=s+k+"";
					// Toast.makeText(getApplicationContext(),"yout one time password is "+op,
					// Toast.LENGTH_LONG).show();
				} else
					Toast.makeText(getBaseContext(),
							"Please enter both phone number and message.",
							Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static String[] splitter(String sr) {
		// TODO Auto-generated method stub
		String s1[] = new String[3];
		String s = "";
		System.out.println("inside spliitr");
		int j = 0;
		for (int i = 0; i < sr.length(); i++) {
			if ((sr.charAt(i)) == '/') {
				System.out.println(s);
				s1[j] = s;
				j++;

				s = "";
			} else {
				s += sr.charAt(i);
			}

		}
		return s1;

	}

	private void sendSMS(String phoneNumber, String message) {
		String msg = new AESEncryptor().encrypt(message, "AbCdEfGhIjKlMnOp");
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				SMS.class), 0);
		SmsManager sms = SmsManager.getDefault();
		Toast.makeText(getApplicationContext(), "message send",
				Toast.LENGTH_SHORT).show();
		sms.sendTextMessage(phoneNumber, null, msg, pi, null);
		finish();
		Intent in = new Intent(SMS.this, Home.class);
		startActivity(in);
		finish();

	}

	public String getMac() {
		WifiManager wifiMan = (WifiManager) this
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		String macAddr = wifiInf.getMacAddress();
		return macAddr;
	}
}