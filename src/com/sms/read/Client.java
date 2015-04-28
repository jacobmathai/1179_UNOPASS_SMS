package com.sms.read;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket soc = null;
	InputStream in = null;
	OutputStream out = null;
	DataInputStream din = null;
	PrintStream ps = null;

	public Client(String ip, int port) {
		try {
			soc = new Socket(ip, port);
			in = soc.getInputStream();
			out = soc.getOutputStream();
			ps = new PrintStream(out);
			din = new DataInputStream(in);
			ps.println("android");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String sendMessage(String uname, String web, String mob1)
			throws IOException {
		System.out.println("inside msg");
		String reply = "";
		ps.println("register");
		ps.println(uname);
		ps.println(web);
		ps.println(mob1);
		String random = din.readLine();
		if (!random.equals("invalid webid")) {
			System.out.println(random);
			String mob = din.readLine();
			System.out.println(mob);
			reply = random + "/" + mob + "/";
		} else {
			reply = random;
		}
		ps.println("exit");
		return reply;
	}

	public String sendRecovery(String uname, String web, String mob1)
			throws IOException {
		// TODO Auto-generated method stub
		System.out.println("inside msg");
		String reply = "";
		ps.println("recovery");
		ps.println(uname);
		ps.println(web);
		ps.println(mob1);
		String random = din.readLine();
		if (!random.equals("invalid webid")) {
			System.out.println(random);
			String mob = din.readLine();
			System.out.println(mob);
			reply = random + "/" + mob + "/";
		} else {
			reply = random;
		}
		ps.println("exit");
		return reply;
	}

}
