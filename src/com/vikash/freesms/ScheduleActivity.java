package com.vikash.freesms;

import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ScheduleActivity extends Activity {
	
	SettingManager free_sms_pref;
	private String contact_name;
	private String contact_number;
	private String message;
	private boolean is_group_message=false;
	
	AlarmManager alarmManager;
	PendingIntent pendingIntent;
	Button dateSetButton;
	Button timeSetButton;
	AlertDialog ad;
	TextView totv;
	TextView messagetv;	
	SharedPreferences shpref;
	Editor editor;
	int cyear;
	int cmonth;
	int cday;
	int chour;
	int cminute;
	int syear;
	int smonth;
	int sday;
	int shour;
	int sminute;
	static final int DATE_DIALOG_ID=0;
	static final int TIME_DIALOG_ID=1;
	boolean isAlarmSet=false;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		SettingManager sm=new SettingManager(this);
		setTheme(sm.get_theme());
		setContentView(R.layout.activity_schedule);
		initialize();
		
	}

	
	@SuppressWarnings("deprecation")
	public void showDateSetterDialog(View v){			
		showDialog(DATE_DIALOG_ID);			
	}
	
	private DatePickerDialog.OnDateSetListener cDateSetListener=new DatePickerDialog.OnDateSetListener() {			
		@Override
		public void onDateSet(DatePicker view, int selyear, int selmonth, int selday) {
			syear=selyear;
			smonth=selmonth+1;
			sday=selday;				
			dateSetButton.setText(String.valueOf(sday).concat("-").concat(String.valueOf(smonth)).concat("-").concat(String.valueOf(syear)));
		}
	};
	
	@SuppressWarnings("deprecation")
	public void showTimeSetterDialog(View v){
		showDialog(TIME_DIALOG_ID);		
	}
	
	private TimePickerDialog.OnTimeSetListener cTimeSetListener=new TimePickerDialog.OnTimeSetListener() {		
		@Override
		public void onTimeSet(TimePicker view, int selhour, int selminute) {
			shour=selhour;
			sminute=selminute;
			timeSetButton.setText(String.valueOf(shour).concat(":").concat(String.valueOf(sminute)));
		}
	};
	
	protected Dialog onCreateDialog(int id){
		switch(id){
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, cDateSetListener, cyear, cmonth, cday);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, cTimeSetListener, chour, cminute, true);
		}
		return null;		
	}

	public void schedule(View v){
		
		free_sms_pref.set_schedule_message(message);
		free_sms_pref.set_schedule_to_contact_name(contact_name);
		free_sms_pref.set_schedule_to_contact_number(contact_number);
		free_sms_pref.set_schedule_year(syear);
		free_sms_pref.set_schedule_month(smonth);
		free_sms_pref.set_schedule_day(sday);
		free_sms_pref.set_schedule_hour(shour);
		free_sms_pref.set_schedule_minute(sminute);
		free_sms_pref.set_is_group_message(is_group_message);
		
		
		
		/*put all the data in preference database to use it later on scheduling
		String to=totv.getText().toString();
		String m=messagetv.getText().toString();			
		editor.putString("uidKey", MainActivity.user_id);
		editor.putString("pwdKey", MainActivity.password);
		editor.putString("toKey",to);
		editor.putString("nameKey", MainActivity.contact_name);
		editor.putString("messageKey", m);		
		editor.putLong("modeKey", MainActivity.sms_mode);				
		editor.putInt("yearKey", syear);
		editor.putInt("monthKey", smonth);
		editor.putInt("dayKey", sday);
		editor.putInt("hourKey", shour);
		editor.putInt("minuteKey", sminute);
		editor.putBoolean("isGroupMsgKey", false);
		editor.commit();	*/
				
		//Long time=new GregorianCalendar().getTimeInMillis()+5*1000;
		
		//alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
		//Toast.makeText(getApplicationContext(), "Message schedule", Toast.LENGTH_SHORT).show();
						
		Calendar currentCalendar=Calendar.getInstance();
		Calendar calendar=Calendar.getInstance();		
		calendar.set(Calendar.YEAR, syear);
		calendar.set(Calendar.MONTH, (smonth-1));
		calendar.set(Calendar.DAY_OF_MONTH, sday);
		calendar.set(Calendar.HOUR_OF_DAY, shour);
		calendar.set(Calendar.MINUTE, sminute);
		calendar.set(Calendar.SECOND, 0);	
				
		if(calendar.before(currentCalendar)){			
			Toast.makeText(getApplicationContext(), "past time! set future time", Toast.LENGTH_LONG).show();			
		}else{
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
			free_sms_pref.set_is_alarm_set(true);
			Toast.makeText(getApplicationContext(), "Message schedule", Toast.LENGTH_SHORT).show();
		}
	}


	public void cancelCurrentSchedule(View v){		
		
		if(free_sms_pref.get_is_alarm_set()){
			alarmManager.cancel(pendingIntent);
			Toast.makeText(getApplicationContext(), "Schedule cancelled", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), "No set schedule to cancel", Toast.LENGTH_SHORT).show();
		}
		free_sms_pref.set_is_alarm_set(false);
	}

	public void showCurrentSchedule(View v){	
		
		if(free_sms_pref.get_is_alarm_set()){
			String to;
			String msg;
			int syear;
			int smonth;
			int sday;
			int shour;
			int sminute;
					
			contact_name=free_sms_pref.get_schedule_to_contact_name();
			contact_number=free_sms_pref.get_schedule_to_contact_number();
			to=contact_name+"<>"+contact_number;
			msg=free_sms_pref.get_schedule_message();
			syear=free_sms_pref.get_schedule_year();
			smonth=free_sms_pref.get_schedule_month();
			sday=free_sms_pref.get_schedule_day();
			shour=free_sms_pref.get_schedule_hour();
			sminute=free_sms_pref.get_schedule_minute();
			
			totv.setText(to);
			messagetv.setText(msg);
			dateSetButton.setText(String.valueOf(sday).concat("-").concat(String.valueOf(smonth)).concat("-").concat(String.valueOf(syear)));
			timeSetButton.setText(String.valueOf(shour).concat(":").concat(String.valueOf(sminute)));
		}else{
			Toast.makeText(getApplicationContext(), "No set schedule to show", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void initialize(){
		Intent intentAlarm=new Intent(this,AlarmReceiver.class);
		pendingIntent=PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		free_sms_pref=new SettingManager(this);
		
		Calendar cCal=Calendar.getInstance();
		cyear=cCal.get(Calendar.YEAR);
		cmonth=cCal.get(Calendar.MONTH);
		cday=cCal.get(Calendar.DAY_OF_MONTH);
		chour=cCal.get(Calendar.HOUR_OF_DAY);
		cminute=cCal.get(Calendar.MINUTE);
		
		dateSetButton=(Button) findViewById(R.id.buttonDateSetter);
		timeSetButton=(Button) findViewById(R.id.buttonTimeSetter);
		totv=(TextView) findViewById(R.id.toet);
	    messagetv=(TextView)findViewById(R.id.msget);	
	    
	    contact_name=getIntent().getStringExtra("name");
	    contact_number=getIntent().getStringExtra("number");
	    is_group_message=getIntent().getBooleanExtra("isItGroupMessage", false);
		totv.setText(contact_name+"<>"+contact_number);
		message=getIntent().getStringExtra("message");
		messagetv.setText(message);
	}

}
