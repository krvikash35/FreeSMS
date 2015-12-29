package com.vikash.freesms;

import android.app.Activity;

import android.content.Intent;


 public class ThemeChanger

{

	 private static int cTheme;
	 public final static int DARKRED = 0;
	 public final static int BROWN = 1;
	 public final static int APPDEFAULTTHEME=2;

	 public static void changeToTheme(Activity activity, int theme)

	 {
		 	cTheme = theme;
		 	activity.finish();
		 	activity.startActivity(new Intent(activity, activity.getClass()));
	 }

	 public static void onActivityCreateSetTheme(Activity activity)
	 {

		 switch (cTheme)
		 {
		 	
		 	case DARKRED:
		 		activity.setTheme(R.style.darkredTheme);
		 		break;

		 	case BROWN:
		 		activity.setTheme(R.style.brownTheme);
		 		break;
		 	case APPDEFAULTTHEME:
		 		activity.setTheme(R.style.AppTheme);
		 		break;
		 	default:
		 		activity.setTheme(R.style.AppTheme);
		 		break;
		 }
	 }

}