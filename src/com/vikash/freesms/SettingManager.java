package com.vikash.freesms;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SettingManager{
	private String user_id="";
	private String password="";
	private String default_gateway;	
	private String schedule_to_contact_number;
	private String schedule_to_contact_name;
	private String schedule_message;
	private int schedule_year;
	private int schedule_month;
	private int schedule_day;
	private int schedule_hour;
	private int schedule_minute;
	private boolean is_internet_mode;
	private boolean is_group_message;
	private boolean is_alarm_set;
	
	private SharedPreferences free_sms_pref;
	private SharedPreferences free_sms_default_pref;
	private SharedPreferences.Editor free_sms_pref_editor;
	private Editor free_sms_default_pref_editor;
	private int theme;
	
	SettingManager(Context c){
		free_sms_pref=c.getSharedPreferences("free_sms_preferences", Context.MODE_PRIVATE);		
		free_sms_pref_editor=free_sms_pref.edit();
		free_sms_default_pref=PreferenceManager.getDefaultSharedPreferences(c);
		free_sms_default_pref_editor=free_sms_default_pref.edit();
	}
	
	
	public void set_schedule_to_contact_number(String id){
		free_sms_pref_editor.putString("schedule_to_contact_number", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_schedule_to_contact_name(String id){
		free_sms_pref_editor.putString("schedule_to_contact_name", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_schedule_message(String id){
		free_sms_pref_editor.putString("schedule_message", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_schedule_year(int id){
		free_sms_pref_editor.putInt("schedule_year", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_schedule_month(int id){
		free_sms_pref_editor.putInt("schedule_month", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_schedule_day(int id){
		free_sms_pref_editor.putInt("schedule_day", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_theme(int id){
		free_sms_pref_editor.putInt("theme", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_schedule_hour(int id){
		free_sms_pref_editor.putInt("schedule_hour", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_schedule_minute(int id){
		free_sms_pref_editor.putInt("schedule_minute", id);
		free_sms_pref_editor.commit();
	}
			
	public void set_is_alarm_set(boolean id){
		free_sms_pref_editor.putBoolean("is_alarm_set", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_is_group_message(boolean id){
		free_sms_pref_editor.putBoolean("is_group_message", id);
		free_sms_pref_editor.commit();
	}
	
	public void set_default_gateway(String id){
		free_sms_default_pref_editor.putString("default_gateway_key", id);
		free_sms_default_pref_editor.commit();
	}
	
	public String get_user_id(){
		default_gateway=free_sms_default_pref.getString("default_gateway_key", "youmint");
		if(default_gateway.contentEquals("youmint")){
			user_id=free_sms_default_pref.getString("youmint_user_id_key", "");
		}else if(default_gateway.contentEquals("normal")){
			
		}
		
		return user_id;
	}
	
	public String get_password(){
		default_gateway=free_sms_default_pref.getString("default_gateway_key", "youmint");
		if(default_gateway.contentEquals("youmint")){
			password=free_sms_default_pref.getString("youmint_password_key", "");
		}else if(default_gateway.contentEquals("normal")){
			
		}
		return password;
	}
	
	public String get_default_gateway(){
		default_gateway=free_sms_default_pref.getString("default_gateway_key", "youmint");
		return default_gateway;
	}
	
	public String get_schedule_to_contact_name(){
		schedule_to_contact_name=free_sms_pref.getString("schedule_to_contact_name", "Unknown");
		return schedule_to_contact_name;
	}
	
	public String get_schedule_to_contact_number(){
		schedule_to_contact_number=free_sms_pref.getString("schedule_to_contact_number", "Unknown");
		return schedule_to_contact_number;
	}
	
	public String get_schedule_message(){
		schedule_message=free_sms_pref.getString("schedule_message", null);
		return schedule_message;
	}
	
	public int get_schedule_year(){
		schedule_year=free_sms_pref.getInt("schedule_year", 0);
		return schedule_year;
	}
	
	public int get_schedule_month(){
		schedule_month=free_sms_pref.getInt("schedule_month", 0);
		return schedule_month;
	}
	
	public int get_schedule_day(){
		schedule_day=free_sms_pref.getInt("schedule_day", 0);
		return schedule_day;
	}
	
	
	public boolean get_is_internet_mode(){
		is_internet_mode=free_sms_pref.getBoolean("is_internet_mode", true);
		return is_internet_mode;
	}
	
	public boolean get_is_group_message(){
		is_group_message=free_sms_pref.getBoolean("is_group_message", true);
		return is_group_message;
	}
	
	public boolean get_is_alarm_set(){
		is_alarm_set=free_sms_pref.getBoolean("is_alarm_set", true);
		return is_alarm_set;
	}

	public int get_schedule_hour(){
		schedule_hour=free_sms_pref.getInt("schedule_hour", 0);
		return schedule_hour;
	}

	public int get_schedule_minute(){
		schedule_minute=free_sms_pref.getInt("schedule_minute", 0);
		return schedule_minute;
	}
	
	public int get_theme(){
		theme=free_sms_pref.getInt("theme", R.style.AppTheme);
		return theme;
	}
}
