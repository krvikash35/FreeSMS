package com.vikash.freesms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

@SuppressLint("NewApi")
public class ChangeTheme extends Activity{
	
	private SettingManager free_sms_pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		free_sms_pref=new SettingManager(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_theme_fragment);
	}	
	
	@SuppressLint("NewApi")
	public void darkred(View v){
		free_sms_pref.set_theme(R.style.darkredTheme);
		recreate();
		super.onBackPressed();		
	}
	
	@SuppressLint("NewApi")
	public void darkorange(View v){
		free_sms_pref.set_theme(R.style.darkOrangeTheme);
		recreate();
		super.onBackPressed();
	}
	
	@SuppressLint("NewApi")
	public void brown(View v){
		free_sms_pref.set_theme(R.style.brownTheme);
		recreate();
		super.onBackPressed();
	}
	
	@SuppressLint("NewApi")
	public void lightred(View v){
		free_sms_pref.set_theme(R.style.lightRedTheme);
		recreate();
		super.onBackPressed();
		
	}

	@SuppressLint("NewApi")
	public void lightwhite(View v){
		free_sms_pref.set_theme(R.style.lightWhiteTheme);
		recreate();
		super.onBackPressed();
		
	}
	
	@SuppressLint("NewApi")
	public void lightblue(View v){
		free_sms_pref.set_theme(R.style.lightBlueTheme);
		recreate();
		super.onBackPressed();
		
	}
	
	@SuppressLint("NewApi")
	public void lightrose(View v){
		free_sms_pref.set_theme(R.style.lightRoseTheme);
		recreate();
		super.onBackPressed();
		
	}
	
}

