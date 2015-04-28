package com.sms.read;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;
 import android.app.Activity;
 public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";    
        String str1 ="";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                str +=  msgs[i].getOriginatingAddress();                     
                str += " :";
                str += msgs[i].getMessageBody().toString();
           str1 +=msgs[i].getMessageBody();
                str += "\n";        
            }
         // SMS.txtMessage.setText(str);
            //---display the new SMS message---
            Toast.makeText(context, "Reply from"+str, Toast.LENGTH_SHORT).show();
            
          
           // Toast.makeText(context,str1, Toast.LENGTH_LONG).show();
        }                         
    }
}
