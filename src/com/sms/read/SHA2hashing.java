
package com.sms.read;

import java.security.MessageDigest;

public class SHA2hashing {
public String doHash(String text){
    String hashedText="";
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Hex format : " + sb.toString());

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        hashedText=hexString.toString();
    	System.out.println("Hex format : " + hexString.toString());
    } catch (Exception e) {
        e.printStackTrace();
    }
    return hashedText;
}
    public static void main(String[] args) {
        System.out.println("Generated Hash>>>>>"+new SHA2hashing().doHash("ajith123"));
    }
}
