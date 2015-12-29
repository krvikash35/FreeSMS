package com.vikash.freesms;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ChatActivity extends ListActivity {
	
	private static boolean isChatActivityVisible;
	public static String stopchat="";
	ArrayList<ChatMessage> messages;
	ChatMessageViewAdaptor adaptor;
	static String forNumber;
	SettingManager sm;
	DatabaseHandler db;
	String user_id;
	String password;
	String default_gateway;
	String cDate;
	String cTime;
	String name;
	String newInMsg;
	String toFromTag;
	AlertDialog ad;
	ContactManager cm;
	private boolean showNormalModeAlert=true;
	private TextView text;
	private String SEND="1";
	private String RECEIVE="0";
	
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		forNumber=getIntent().getStringExtra("forNumber");
		sm=new SettingManager(this);
		db=new DatabaseHandler(this);
		cm=new ContactManager();		
		ad=new AlertDialog.Builder(this).create();
		ad.setTitle("Message");		
		text=(TextView)findViewById(R.id.chattext);
		default_gateway=sm.get_default_gateway();
		user_id=sm.get_user_id();
		password=sm.get_password();
		name=cm.getContactDisplayNameByNumber(forNumber, this);
		messages=db.getChatMessages(forNumber);
		adaptor=new ChatMessageViewAdaptor(this, messages);
		setListAdapter(adaptor);			
	    setTitle(name);		
	}
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		newInMsg=intent.getStringExtra("newmsg");
		addNewMessage(new ChatMessage(newInMsg, false));
	}

	@Override
	protected void onPause() {
		super.onPause();
		isChatActivityVisible=false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		isChatActivityVisible=true;
	}

	public static boolean isChatActivityVisible(){
		return isChatActivityVisible;
	}
	
	public static String getCurrentChatNumber(){
		return forNumber;
	}

	public void sendMessage(View v)
	{	
		
		final String newOutMessage = text.getText().toString().trim(); 
		if(newOutMessage.length() > 0)
		{
			text.setText("");			
		}else return;
		
		if (default_gateway.contentEquals("normal"))			
		{			
			if(showNormalModeAlert)
			{
				AlertDialog.Builder ald=new AlertDialog.Builder(this);
				ald.setTitle("Confirm to send...");
				ald.setMessage("You are in normal mode! It may cost you money! are you sure you want to send this message");
				ald.setPositiveButton("Yes", new DialogInterface.OnClickListener()
					{				
						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							SmsManager.getDefault().sendTextMessage(forNumber, null, newOutMessage, null, null);
							cDate=DateFormat.getDateInstance().format(new Date());
							cTime=DateFormat.getTimeInstance().format(new Date());
							addNewMessage(new ChatMessage(newOutMessage, true));
							db.saveMsgInDatabase(forNumber, name, newOutMessage, SEND,cDate, cTime);
							showNormalModeAlert=false;					
						}				
					}	
				);			
				ald.setNegativeButton("No", new DialogInterface.OnClickListener() {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				ald.show();	
			}else
			{			  
				SmsManager.getDefault().sendTextMessage(forNumber, null, newOutMessage, null, null);
				cDate=DateFormat.getDateInstance().format(new Date());
				cTime=DateFormat.getTimeInstance().format(new Date());
				addNewMessage(new ChatMessage(newOutMessage, true));
				db.saveMsgInDatabase(forNumber, name, newOutMessage, SEND,cDate, cTime);
			}
		}
		else
		{
			ConnectivityManager cm=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
			if(!(activeNetwork!=null && activeNetwork.isConnectedOrConnecting())){
				ad.setMessage("No Internet Connection");
				ad.show();
				return;	
			}						
			if(user_id.isEmpty() || password.isEmpty()){
				ad.setMessage("Enter valid userid or password");
				ad.show();
				return;
			}
			new Thread(new Runnable(){public void run(){
			try
			{
				if (default_gateway.contentEquals("youmint"))
				{	
					YouMint.send(user_id,password,forNumber,newOutMessage);	
					cDate=DateFormat.getDateInstance().format(new Date());
					cTime=DateFormat.getTimeInstance().format(new Date());
					runOnUiThread(new Runnable() {public void run() {
						addNewMessage(new ChatMessage(newOutMessage, true));
					}});					
					db.saveMsgInDatabase(forNumber, name, newOutMessage,SEND, cDate, cTime);
				}
				else if(default_gateway.contentEquals("way2sms"))
				{
								//To Do Future Development
								//Way2sms.send(user_id,password,contact_number,message);
				}
							
			}catch (Exception e) {	
				runOnUiThread(new Runnable() {public void run() {
				Toast.makeText(getApplicationContext(), "couldn't send,check login details",Toast.LENGTH_LONG).show();
				}});
				e.printStackTrace();
				return;
			}
			}}).start();
			
		}		
	}
	
	private void addNewMessage(ChatMessage m) {
		messages.add(m);
		adaptor.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
	}
}
