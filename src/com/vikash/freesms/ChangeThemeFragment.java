package com.vikash.freesms;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class ChangeThemeFragment extends Fragment{
	SettingManager sm;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {		
			sm=new SettingManager(getActivity());
            View rootView = inflater .inflate(R.layout.change_theme_fragment, container, false);  
            return rootView;
    }
	
	
}

