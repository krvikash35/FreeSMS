package com.vikash.freesms;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class HelpFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
            View rootView = inflater .inflate(R.layout.help, container, false);  
            return rootView;
    }
	
}
