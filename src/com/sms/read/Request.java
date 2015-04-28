package com.sms.read;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Request extends Activity {
	Button btnSendSMS;
	static EditText txtPhoneNo;
	static EditText userid;
	EditText webid;
	static String uname = "";
	static String reply;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		userid = (EditText) findViewById(R.id.uid);
		webid = (EditText) findViewById(R.id.wid);
		btnSendSMS = (Button) findViewById(R.id.send1);
		btnSendSMS.setOnClickListener(new View.OnClickListener() {  

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				uname = userid.getText().toString().trim();
				String web = webid.getText().toString().trim();
				String message = uname.trim() + "/" + web.trim() + "/"
						+ Home.yourNumber;
				try {
					if (Home.sel == 1) {
						reply = new Client(Server_Config.tsp_ip,
								Server_Config.tso_port).sendMessage(
								uname.trim(), web.trim(), Home.yourNumber);
					}
					if (Home.sel == 2) {
						reply = new Client(Server_Config.tsp_ip,
								Server_Config.tso_port).sendRecovery(
							  	uname.trim(), web.trim(), Home.yourNumber);
					}
					if (!reply.equals("invalid webid")) {

						if (reply != null || !reply.equals("")) {
							if (!reply.startsWith("exist")) {
								Toast.makeText(getApplicationContext(),
										"respones from server recieved",
			  							Toast.LENGTH_LONG).show();
								File f = new File(Environment
										.getExternalStorageDirectory()
										+ "/opass");
								if (!f.exists()) {
									f.mkdirs();
								}
								File f1 = new File(f.getAbsolutePath()
										+ "/regdetails.txt");
								if (!f1.exists()) {
									f1.createNewFile();
								}
								FileOutputStream fout = new FileOutputStream(f1);
								reply = reply + Request.uname + "/*";
								Toast.makeText(getApplicationContext(), reply,
										Toast.LENGTH_LONG).show();
								fout.write((reply + ",").getBytes());
								StartApp.checkmode = 2;
								Intent in = new Intent(Request.this,
										CheckPass.class);
								startActivity(in);
								finish();
							} else {
								Toast.makeText(getApplicationContext(),
										"existing username", Toast.LENGTH_SHORT)
										.show();
							}
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"invalid webid", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}
}
