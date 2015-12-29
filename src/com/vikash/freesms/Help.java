package com.vikash.freesms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

@SuppressLint("NewApi")
public class Help extends Activity {

	private SettingManager free_sms_pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		free_sms_pref=new SettingManager(this);
		setTheme(free_sms_pref.get_theme());
		setContentView(R.layout.help);
	}	
}
