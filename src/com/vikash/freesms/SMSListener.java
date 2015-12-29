package com.vikash.freesms;

import java.text.DateFormat;
import java.util.Date;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;

public class SMSListener extends BroadcastReceiver{

	private String cDate="";
	private String cTime="";
	private String contact_number;
	private String contact_name="";
	private String message="";
	private String RECEIVE="0";
	

	@Override
	public void onReceive(Context context, Intent intent) {
		DatabaseHandler db=new DatabaseHandler(context);
		ContactManager cm=new ContactManager();		 
        Bundle extras = intent.getExtras();         
               
        if ( extras != null )
        {
            
            Object[] smsExtra = (Object[]) extras.get( "pdus" );
             
            for ( int i = 0; i < smsExtra.length; ++i )
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);                 
                message = sms.getMessageBody().toString();
                contact_number = sms.getOriginatingAddress();                
                cDate=DateFormat.getDateInstance().format(new Date());
				cTime=DateFormat.getTimeInstance().format(new Date());
				contact_name=cm.getContactDisplayNameByNumber(contact_number, context);
				
				contact_number=PhoneNumberUtils.stripSeparators(contact_number);
		        contact_number=contact_number.replace("+91", "");
		        if(contact_number.length()==10){
		        	
		        	if(ChatActivity.isChatActivityVisible()){
		        		if(contact_number.contentEquals(ChatActivity.getCurrentChatNumber())){
		        			Intent ci=new Intent();
		        			ci.setClass(context, ChatActivity.class);
		        			ci.putExtra("newmsg", message);
		        			ci.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);	  	
		        		    context.startActivity(ci);
		        		}
		        	}
		        	
		        	if(MainActivity.isMainActivityVisible()){
		        		Intent mi=new Intent();
		        		mi.setClass(context, MainActivity.class);
		        		mi.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		        		context.startActivity(mi);
		        	}
		        	
		        	
		        	db.saveMsgInDatabase(contact_number, contact_name, message,RECEIVE, cDate, cTime);
		        }								
            }           
            
        }
         
	}

}
