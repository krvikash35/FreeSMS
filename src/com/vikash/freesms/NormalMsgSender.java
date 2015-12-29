package com.vikash.freesms;

import java.util.Vector;

import android.telephony.SmsManager;

public class NormalMsgSender {
	
	private static Vector<Long> numbers;
	
	public static void send(String contact_number,String message){
		numbers = new Vector<Long>();
		String pharr[];
		if (contact_number.indexOf(';') >= 0)
		{
			pharr = contact_number.split(";");
			for (String t : pharr) 
			{				
					numbers.add(Long.valueOf(t));				
			}
		} 
		else 
		{
			numbers.add(Long.valueOf(contact_number));
		}		
		
		for(Long num:numbers)
		{
			SmsManager.getDefault().sendTextMessage(String.valueOf(num), null, message, null, null);
		}
	}
}
