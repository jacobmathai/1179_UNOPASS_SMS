package com.sms.read;

import java.io.FileInputStream;
import java.io.IOException;

import android.os.Environment;

public class OneTimepass {

	public String Random(int i) throws IOException {
		FileInputStream fin = new FileInputStream(
				Environment.getExternalStorageDirectory()
						+ "/opass/regdetails.txt");
		byte[] b = new byte[fin.available()];
		fin.read(b);
		String s = new String(b);
		String[] s2 = SMS.splitter(s);
		long a = Long.parseLong(s2[0]);

		long k = (a) ^ (999 - i);
		int m;
		String s1 = "";
		for (int j = 0; j < 5; j++) {
			m = i + (65 + (i ^ j));
			System.out.println("mmmm" + m);
			s1 += (char) m;
		}

		System.out.println(s1);
		System.out.println(k + "" + s1);
		String p = k + "" + s1;
		return p;
	}
}
