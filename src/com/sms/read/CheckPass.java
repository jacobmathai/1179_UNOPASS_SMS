package com.sms.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.gsm.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckPass extends Activity {
	float X, x, y, x1, y1, x2, y2;
	float Y, X1, Y1, X2, Y2;
	int cnt = 0, i = 0;
	private static final int SELECT_PHOTO = 100;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkpass);
		final TextView tv = (TextView) findViewById(R.id.tv65);
		ImageView iv = (ImageView) findViewById(R.id.checkp);
		Bitmap bim = BitmapFactory.decodeFile("/mnt/sdcard/opass/pass.jpg");
		iv.setImageBitmap(bim);
		try {
			FileInputStream fin = new FileInputStream(
					"/mnt/sdcard/opass/lpass.txt");
			byte b[] = new byte[fin.available()];
			fin.read(b);
			String s = new String(b);

			String s1[] = s.split(",");
			X = Float.parseFloat(s1[0]);
			Y = Float.parseFloat(s1[1]);
			X1 = Float.parseFloat(s1[2]);
			Y1 = Float.parseFloat(s1[3]);
			X2 = Float.parseFloat(s1[4]);
			Y2 = Float.parseFloat(s1[5]);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT)
					.show();
			System.out.println(">>>>>>>>>>>>" + X + Y + X1 + Y1 + X2 + Y2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iv.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				i++;
				if (i == 1) {
					x = event.getX();
					System.out.println("??????????? " + x);
					y = event.getY();
					System.out.println("??????????? " + y);
				}
				if (i == 2) {
					x1 = event.getX();
					System.out.println(",,,,,, " + x1);
					y1 = event.getY();
					System.out.println(",,,,,," + y1);
				}
				if (i == 3) {
					x2 = event.getX();
					System.out.println("........ " + x2);
					y2 = event.getY();
					System.out.println("......... " + y2);

					System.out.println("/////////////// " + i);

					if ((x < (X + 25) && x > (X - 25))
							&& (y < (Y + 25) && y > (Y - 25))
							&& (x1 < (X1 + 25) && x1 > (X1 - 25))
							&& (y1 < (Y1 + 25) && y1 > (Y1 - 25))
							&& (x2 < (X2 + 25) && x2 > (X2 - 25))
							&& (y2 < (Y2 + 25) && y2 > (Y2 - 25))) {
						Toast.makeText(getApplicationContext(), "success",
								Toast.LENGTH_SHORT).show();
						if (StartApp.checkmode == 3) {
							Intent photoPickerIntent = new Intent(
									Intent.ACTION_PICK);
							photoPickerIntent.setType("image/*");
							startActivityForResult(photoPickerIntent,
									SELECT_PHOTO);
						}
						if (StartApp.checkmode == 2) {
							Intent in = new Intent(CheckPass.this, SMS.class);
							startActivity(in);
						}
						if (StartApp.checkmode == 1) {
							try {
								if (!AndClient.uname.equals("")) {
									String s2 = "";
									String s1 = new OneTimepass()
											.Random(AndClient.i);
									for (int i = 0; i < 1000 - (AndClient.i); i++) {
										s2 = new SHA2hashing().doHash(s1);
										System.out.println("dddddd" + s2);
										s1 = s2;
									}
									String mac = getMac();

									FileInputStream fin = new FileInputStream(
											Environment
													.getExternalStorageDirectory()
													+ "/opass/regdetails.txt");
									byte[] b = new byte[fin.available()];
									fin.read(b);
									String s = new String(b);
									String[] s3 = SMS.splitter(s);
									Toast.makeText(
											getApplicationContext(),
											s2 + "///" + AndClient.uname + "???"
													+ AndClient.i,
											Toast.LENGTH_LONG).show();
									sendSMS(s3[1].trim(), s2 + "/"
											+ AndClient.uname + "/" + mac
											+ ",login");


								} else {
								Toast.makeText(getApplicationContext(),
										"Server down", Toast.LENGTH_SHORT)
										.show();
							}   
							}

							catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				} else {
					cnt++;
					tv.setVisibility(1);
					tv.setText(cnt + "");
					if (cnt > 3) {

						// for(int i=0;i<30;i++)
						// {
						// try {
						// Thread.sleep(1000);
						// tv.setVisibility(1);
						tv.setText("Your have failed,try after sometime");

						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						finish();
						//
						// } catch (InterruptedException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						//
						//
						// Toast.makeText(getApplicationContext(), "lock...."+i,
						// Toast.LENGTH_SHORT).show();
						//
						// }
						//
					}
				}
				// Toast.makeText(getApplicationContext(),
				// "x-----"+x+"XXX"+X+"y--"+y+"YYY"+Y,
				// Toast.LENGTH_SHORT).show();

				return false;
			}
		});
	}

	private void sendSMS(String phoneNumber, String message) {
		String msg = new AESEncryptor().encrypt(message, "AbCdEfGhIjKlMnOp");
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				Home.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, msg, pi, null);

	}

	public String getMac() {
		WifiManager wifiMan = (WifiManager) this
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		String macAddr = wifiInf.getMacAddress();
		return macAddr;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();
				InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(
							selectedImage);
					Toast.makeText(getApplicationContext(), selectedImage + "",
							Toast.LENGTH_LONG).show();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap yourSelectedImage = BitmapFactory
						.decodeStream(imageStream);
				File _sdCard = Environment.getExternalStorageDirectory();
				File _picDir = new File(_sdCard, "opass");
				_picDir.mkdirs();

				File _picFile = new File(_picDir, "pass.jpg");
				FileOutputStream _fos = null;
				try {
					_fos = new FileOutputStream(_picFile);

					yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100,
							_fos);
					_fos.flush();
					Toast.makeText(getApplicationContext(), "Image downloaded",
							Toast.LENGTH_SHORT).show();
					_fos.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent in = new Intent(CheckPass.this, Image.class);
				startActivity(in);
				finish();
			}
		}
	}

}
