package com.vikash.freesms;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Pref extends PreferenceActivity  {	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		SettingManager sm=new SettingManager(this);
		setTheme(sm.get_theme());
		super.onCreate(savedInstanceState);				
		addPreferencesFromResource(R.xml.pref);			
	}

	
}
