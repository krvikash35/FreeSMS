package com.vikash.freesms;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver{	
	SettingManager free_sms_pref;
	int syear;
	int smonth;
	int sday;
	int shour;
	int sminute;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		free_sms_pref=new SettingManager(arg0);
		Intent intentAlarm=new Intent(arg0.getApplicationContext(),AlarmReceiver.class);
		PendingIntent pendingIntent=PendingIntent.getBroadcast(arg0.getApplicationContext(), 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager=(AlarmManager)arg0.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		
		syear=free_sms_pref.get_schedule_year();
		smonth=free_sms_pref.get_schedule_month();
		sday=free_sms_pref.get_schedule_day();
		shour=free_sms_pref.get_schedule_hour();
		sminute=free_sms_pref.get_schedule_minute();
		
		Calendar currentCalendar=Calendar.getInstance();
		Calendar calendar=Calendar.getInstance();		
		calendar.set(Calendar.YEAR, syear);
		calendar.set(Calendar.MONTH, (smonth-1));
		calendar.set(Calendar.DAY_OF_MONTH, sday);
		calendar.set(Calendar.HOUR_OF_DAY, shour);
		calendar.set(Calendar.MINUTE, sminute);
		calendar.set(Calendar.SECOND, 0);					
		
		if(calendar.before(currentCalendar)){			
						
		}else{
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);		
		}	
	}
}
