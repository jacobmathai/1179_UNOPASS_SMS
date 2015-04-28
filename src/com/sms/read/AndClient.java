package com.sms.read;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 * @author kites-javaa
 */
public class AndClient extends Thread {

	Socket soc = null;
	static String uname = "";
	static int i = 0;
	Handler handler;
	Context mv;

	public AndClient(Context con) {
		mv = con;
		try {
  
		  	soc = new Socket(Server_Config.tsp_ip, 9999);
			handler = new Handler() {
				public void handleMessage(Message msg) {
					try {
						Toast.makeText(mv,
								"Login Request from server recieved",
								Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			System.out.println("AndClient started");

			InputStream im = soc.getInputStream();
			OutputStream out = soc.getOutputStream();

			DataInputStream din = new DataInputStream(im);
			PrintStream ps = new PrintStream(out);
			System.out.println("working");
			ps.println("client");
			while (true) {

				String msg = din.readLine();
				System.out.println(msg);
				// if(msg==null)
				// {
				// break;

				// }

				if (msg.equals("login")) {
					i = Integer.parseInt(din.readLine());

					System.out.println("desss" + i);
					uname = din.readLine();
					System.out.println(uname);
					// Toast.makeText(new Home(),"Login Request Recieved",
					// Toast.LENGTH_LONG).show();
					handler.sendMessage(handler.obtainMessage());
					ps.println("exit");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// new AndClient().start();
	}

}
