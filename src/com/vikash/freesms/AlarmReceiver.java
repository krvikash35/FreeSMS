package com.vikash.freesms;

import java.text.DateFormat;
import java.util.Date;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver{
	
	SettingManager free_sms_pref;
	private String contact_name;
	private String contact_number;
	private String default_gateway;	
	private String message;
	private String user_id;
	private String password;	
	private String cDate;
	private String cTime;
	private boolean isGroupMsg;
	private Context context;
	private String notificationMessage;
	private String SEND="1";
	
	
	
	@Override
	public void onReceive(final Context context, Intent arg1) {
		
		 this.context=context;
		 free_sms_pref=new SettingManager(context);
		 free_sms_pref.set_is_alarm_set(false);		 
		 contact_number=free_sms_pref.get_schedule_to_contact_number();
		 message=free_sms_pref.get_schedule_message();
		 user_id=free_sms_pref.get_user_id();
		 password=free_sms_pref.get_password();
		 default_gateway=free_sms_pref.get_default_gateway();
		 contact_name=free_sms_pref.get_schedule_to_contact_name();
		 isGroupMsg=free_sms_pref.get_is_group_message();
		 notificationMessage="To:"+contact_name+"\n"+"Message:"+message;
		 		 		 
		 if(default_gateway.contentEquals("normal"))
		 {
			NormalMsgSender.send(contact_number, message);				
			if(!isGroupMsg)
			{
				cDate=DateFormat.getDateInstance().format(new Date());
				cTime=DateFormat.getTimeInstance().format(new Date());
				DatabaseHandler db=new DatabaseHandler(context.getApplicationContext());
				db.saveMsgInDatabase(contact_number, contact_name, message, SEND, cDate, cTime);
			}
			displayNotification("Schedule Success");
				
		 }else
		 {	
			new Thread(new Runnable(){public void run(){
			try
			{
				if (default_gateway.contentEquals("youmint"))
				{	
					YouMint.send(user_id,password,contact_number,message);	
					if(!isGroupMsg)
					{
						cDate=DateFormat.getDateInstance().format(new Date());
						cTime=DateFormat.getTimeInstance().format(new Date());
						DatabaseHandler db=new DatabaseHandler(context.getApplicationContext());
						db.saveMsgInDatabase(contact_number, contact_name, message, SEND, cDate, cTime);
						
					}
					displayNotification("Schedule Success");
				}
				else if(default_gateway.contentEquals("way2sms"))
				{
								//To Do Future Development
								//Way2sms.send(user_id,password,contact_number,message);
				}
							
			}catch (Exception e) {
				displayNotification("Schedule Failed");
				e.printStackTrace();
				return;
			}
			}}).start();
						 
		}
	 }	
	
	public void displayNotification(String NotificationTitle){
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.freesms_icon)
		        .setContentTitle(NotificationTitle)
		        .setContentText(notificationMessage)
		        .setSound(soundUri); 
		notificationManager.notify(0, mBuilder.build());
		
	}
}


