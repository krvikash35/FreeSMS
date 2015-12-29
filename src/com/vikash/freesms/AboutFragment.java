package com.vikash.freesms;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class AboutFragment extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
            View rootView = inflater .inflate(R.layout.about, container, false);  
            return rootView;
    }
	
}
