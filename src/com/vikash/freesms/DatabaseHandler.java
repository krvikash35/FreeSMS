package com.vikash.freesms;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION=2;
	private static final String DATABASE_NAME="freeSMS";
	private static final String TABLE_MESSAGES="MessagesTable";
	private static final String KEY_ID="_id";
	private static final String KEY_PHONE_NO="phone_number";
	private static final String KEY_NAME="name";
	private static final String KEY_DATE="date";
	private static final String KEY_TIME="time";
	private static final String KEY_MSG="message";
	private static final String KEY_TAG="tag";
	private static int numberOfSavedMsg=0;
	private String RECEIVE="0";
	private String SEND="1";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SENTMESSAGE_TABLE="CREATE TABLE " + TABLE_MESSAGES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				  + KEY_PHONE_NO + " TEXT,"
				  + KEY_NAME + " TEXT," 
				  + KEY_MSG + " TEXT,"
				  + KEY_TAG + " TEXT,"
				  + KEY_DATE + " TEXT,"
				  + KEY_TIME + " TEXT" +
			  ")";
		db.execSQL(CREATE_SENTMESSAGE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
		onCreate(db);
	}

	public  void saveMsgInDatabase(String phone,String name,String msg,String mtag,String date,String time){
		SQLiteDatabase db=this.getWritableDatabase();		
		ContentValues values=new ContentValues();
		values.put(KEY_PHONE_NO, phone);
		values.put(KEY_NAME, name);
		values.put(KEY_MSG, msg);
		values.put(KEY_TAG,mtag);
		values.put(KEY_DATE, date);
		values.put(KEY_TIME, time);		
		db.insert(TABLE_MESSAGES, null, values);
		db.close();
	}

	public Cursor getSentMessagesCursor(){	
		String selectQuery="SELECT * FROM " + TABLE_MESSAGES + " WHERE " + KEY_TAG + "=" + SEND + " ORDER BY " + KEY_ID + " ASC";
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery(selectQuery, null);
		numberOfSavedMsg=cursor.getCount();		
		if(numberOfSavedMsg>20){
			deleteSomeSavedMsg();
		}		
		return cursor;		
	}

	private void deleteSomeSavedMsg() {
		String deleteQuery="DELETE FROM " + TABLE_MESSAGES + " WHERE " + KEY_ID + " IN " + "(SELECT " + KEY_ID + " FROM " + TABLE_MESSAGES + " ORDER BY " + KEY_ID + " ASC LIMIT 10)";
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL(deleteQuery);
		System.out.println("deleted");
		
	}
	
	public void deleteTable(){
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
	}
	
	public List<QuickMessages> get_quick_messages(){
		String name,number,message,date,time,inoutchecker;
		List<QuickMessages> qm= new ArrayList<QuickMessages>();
		QuickMessages qms=new QuickMessages();
		String selectQuery="SELECT * FROM " + TABLE_MESSAGES + " GROUP BY " + KEY_PHONE_NO + " ORDER BY " + KEY_ID + " DESC";
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst())
		{
			do{
				name=cursor.getString(cursor.getColumnIndex(KEY_NAME));
				number=cursor.getString(cursor.getColumnIndex(KEY_PHONE_NO));
				message=cursor.getString(cursor.getColumnIndex(KEY_MSG));
				date=cursor.getString(cursor.getColumnIndex(KEY_DATE));
				time=cursor.getString(cursor.getColumnIndex(KEY_TIME));	
				inoutchecker=cursor.getString(cursor.getColumnIndex(KEY_TAG));
				
				qms=new QuickMessages(name,number,message,date,time,inoutchecker);
				qm.add(qms);
				cursor.moveToNext();
			}while(!cursor.isAfterLast());
			
		}
		
		return qm;
	}
	
	public ArrayList<ChatMessage> getChatMessages(String forNumber){
		String name,message,toFromTag,date,time;
		ArrayList<ChatMessage> cms=new ArrayList<ChatMessage>();
		ChatMessage cm;
		
		String selectQuery="SELECT * FROM " + TABLE_MESSAGES + " WHERE " + KEY_PHONE_NO + "=" + forNumber + " ORDER BY " + KEY_ID + " ASC";
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst())
		{
			do{			
				name=cursor.getString(cursor.getColumnIndex(KEY_NAME));
				message=cursor.getString(cursor.getColumnIndex(KEY_MSG));	
				toFromTag=cursor.getString(cursor.getColumnIndex(KEY_TAG));
				date=cursor.getString(cursor.getColumnIndex(KEY_DATE));
				time=cursor.getString(cursor.getColumnIndex(KEY_TIME));
				cm=new ChatMessage(name, message, toFromTag, date, time);
				cms.add(cm);
				
			}while(cursor.moveToNext());
			
		}
		return cms;
	}
}
