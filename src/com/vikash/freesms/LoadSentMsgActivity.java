package com.vikash.freesms;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class LoadSentMsgActivity extends Activity {

	ListView sentMsglv;
	SettingManager sm;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		sm=new SettingManager(this);
		setTheme(sm.get_theme());
		setContentView(R.layout.sent_msg_list_view);
		sentMsglv=(ListView)findViewById(R.id.sentMsglist);
		DatabaseHandler db=new DatabaseHandler(this);
		//db.deleteTable();
		
		Cursor sentMsgCursor=db.getSentMessagesCursor();
		startManagingCursor(sentMsgCursor);
		if(sentMsgCursor.moveToFirst()){
			ListAdapter sentMsgAdapter= new SimpleCursorAdapter(this,R.layout.sent_msg_list_view,sentMsgCursor,new String[]{"name","phone_number","message","date","time"},new int[]{R.id.nametv,R.id.phonetv,R.id.msgtv,R.id.datetv,R.id.timetv});
			sentMsglv.setAdapter(sentMsgAdapter);
		}
	}
}
