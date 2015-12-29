package com.vikash.freesms;

import java.util.Date;
import java.io.IOException;
import java.text.DateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {	
	private  boolean isItGrouMsg=false;
	private String[] menutitles;
	private String cDate;
	private String cTime;
	private String user_id;
	private String password;
	private String default_gateway;	;
	private static String contact_name;
	private static String contact_number="";
	private static String message;	
	private EditText etm;
	private EditText etc;
	private AlertDialog ad;		
	private TypedArray menuIcons;	 
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ListView quickMessageViewList;
	private ActionBarDrawerToggle mDrawerToggle;
	private List<RowItem> rowItems;
	private DrawerItemAdapter di_adapter;
	private QuickMessagesViewAdapter qm_adapter;
	private ContactManager contact_manager;
	private SettingManager free_sms_pref;
	private static boolean isMainActivityVisible;
	private DatabaseHandler db;
	private String SEND="1";
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);	
		free_sms_pref=new SettingManager(this);
		setTheme(free_sms_pref.get_theme());
		setContentView(R.layout.activity_main);		
		initialize_variable();
		contact_manager=new ContactManager();		
		etm = (EditText) findViewById(R.id.etmessage);
		etc = (EditText) findViewById(R.id.etcontact);			
		ad=new AlertDialog.Builder(this).create();
		ad.setTitle("Message");
		
		quickMessageViewList=(ListView)findViewById(R.id.main_message_list);
		db=new DatabaseHandler(this);		
		qm_adapter=new QuickMessagesViewAdapter(this, db.get_quick_messages());
		quickMessageViewList.setAdapter(qm_adapter);
		quickMessageViewList.setOnItemClickListener(new QuickMessageViewClickListener());
			 
		mTitle = mDrawerTitle = getTitle();
		menutitles = getResources().getStringArray(R.array.nav_drawer_items);
		menuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icon);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.slider_list);
		rowItems = new ArrayList<RowItem>();
		for (int i = 0; i < menutitles.length; i++) {
		   RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(i, -1));
		   rowItems.add(items);
		}
		menuIcons.recycle();
		di_adapter = new DrawerItemAdapter(getApplicationContext(), rowItems);
		mDrawerList.setAdapter(di_adapter);		  
		mDrawerList.setOnItemClickListener(new SlideitemListener());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);		  
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.app_name,R.string.app_name) {
				       public void onDrawerClosed(View view) {
				         getActionBar().setTitle(mTitle);
				         invalidateOptionsMenu();
				       }
				        public void onDrawerOpened(View drawerView) {
				              getActionBar().setTitle(mDrawerTitle);
				              invalidateOptionsMenu();
				         }
				  };
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	 }
		
	class QuickMessageViewClickListener implements ListView.OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			TextView tv=(TextView)view.findViewById(R.id.phonetv);
			Intent chatIntent=new Intent(view.getContext(),ChatActivity.class);
			chatIntent.putExtra("forNumber", tv.getText());
			startActivity(chatIntent);			
		}		
	}
	
	class SlideitemListener implements ListView.OnItemClickListener {
	       @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {
	          updateDisplay(position);
	    }
	}

	 @SuppressLint("NewApi")
	private void updateDisplay(int position) {
	      switch (position) {
	               
	               case 0:
	                           Intent gatewaySettingIntent=new Intent(this,Pref.class);  
	                           startActivity(gatewaySettingIntent);
	                           break;
	               case 1:
				               showScheduleScreen();
	                           break;
	               case 2:
	            	   		   Intent load_sent_msg_intent=new Intent(this,LoadSentMsgActivity.class);
	           		           startActivity(load_sent_msg_intent);
	            	   		   break;
	            	   		  
	               case 3:
	            	   		   startActivity(new Intent(this,ChangeTheme.class));
		                       break;
                        
	               case 4:
                       		   createShareIntent();
                       		   break;
                        
	               case 5:
	            	   		    Uri uri=Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
		            	   		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		            	   		try {
		            	   		  startActivity(goToMarket);
		            	   		} catch (ActivityNotFoundException e) {
		            	   		  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
		            	   		}
	            	   		    break;
                        
	               case 6:
                               Intent helpIntent=new Intent(getApplicationContext(), Help.class);
                               startActivity(helpIntent);
                               break;
                        
	               case 7:
                               Intent aboutIntent=new Intent(getApplicationContext(),About.class);
                               startActivity(aboutIntent);
                               break;
                   	                                 
	              default:
	                         break;
	    }

	  

	  }

	 @SuppressLint("NewApi")
     @Override
    public void setTitle(CharSequence title) {
	         mTitle = title;
	        getActionBar().setTitle(mTitle);
	        
	 }

   
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	         if (mDrawerToggle.onOptionsItemSelected(item))
	         {
	             return true;
	          }
             switch (item.getItemId())
             {
                case  R.id.action_settings:
                      return true;
                default :
                      return super.onOptionsItemSelected(item);
             }
	 }
	 
	
	 
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
	    	System.out.println("on post create");
	         super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	         mDrawerToggle.syncState();
	}

	public void selectGroupContact(View view) {
		Intent groupContactIntent=new Intent(this,GroupActivity.class);
		startActivityForResult(groupContactIntent,2);		
	}

	public void selectContact(View view) {
		Intent contactIntent = new Intent(Intent.ACTION_PICK,Phone.CONTENT_URI);
		startActivityForResult(contactIntent, 1);		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);	
		//check for contact picker request code, get phone number and name, and set it to display 
		if (resultCode == RESULT_OK && requestCode == 1) {
			Cursor cursor = null;
			try {
				Uri result = data.getData();				
				String id = result.getLastPathSegment();				
				cursor = getContentResolver().query(Phone.CONTENT_URI, new String[] { Phone.DISPLAY_NAME, Phone.NUMBER }, Phone._ID + "=?", new String[] { id }, null);				
				if (cursor.moveToFirst()) {
					contact_number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
					contact_number=PhoneNumberUtils.stripSeparators(contact_number);
					contact_name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
					etc.setText(contact_name.concat("<>").concat(contact_number));			
				}
			} catch (Exception e) {
				Log.e("FREESMSDEBUGTAG", "Failed to get phone number data", e);
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}	
		//check for group picker request code, get all phone number belonging to group, and set it to display
		else{
			if(requestCode == 2 && data!=null){
				String groupPhone=data.getStringExtra("GROUPPHONE");
				etc.setText(groupPhone);				
			}
		}		
	}
	
	public void saveMsgInDatabase(String phone,String name,String msg,String date,String time){
		DatabaseHandler db=new DatabaseHandler(this);
		db.saveMsgInDatabase(phone,name,msg, SEND ,date,time);
	}
	
	public void setDefaultGateway(View v){
		String[] listOfGateway={"Youmint","Normal"};
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("Pick your choice").setItems(listOfGateway, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
				switch(arg1){
					case 0:
						free_sms_pref.set_default_gateway("youmint");					
						break;
					case 1:
						free_sms_pref.set_default_gateway("normal");						
						break;
				}
				
			}
			
		});
		builder.show();
	}
	
	public void onBackPressed(){		
		super.onBackPressed();		
	}
	
	public void sendSMS(View view)
	{
		if(is_all_set_before_send())
		{			
			if (default_gateway.contentEquals("normal")){
				AlertDialog.Builder ald=new AlertDialog.Builder(this);
				ald.setTitle("Confirm to send...");
				ald.setMessage("You are in normal mode! It may cost you money! are you sure you want to send this message");
				ald.setPositiveButton("Yes", new DialogInterface.OnClickListener() {				
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						try{
							NormalMsgSender.send(contact_number, message);						
							if(!isItGrouMsg){
								cDate=DateFormat.getDateInstance().format(new Date());
								cTime=DateFormat.getTimeInstance().format(new Date());
								saveMsgInDatabase(contact_number, contact_name, message, cDate, cTime);
							}
							Toast.makeText(getApplicationContext(), "Sent through normal mode", Toast.LENGTH_SHORT).show();
						} catch (Exception e){
							e.printStackTrace();
						}
					}
				});		
				ald.setNegativeButton("No", new DialogInterface.OnClickListener() {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				ald.show();			
		    }else
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
				new Thread(new Runnable(){public void run()
				{					
						if (default_gateway.contentEquals("youmint")){
							
								try {
									YouMint.send(user_id, password, contact_number, message);	
									if(!isItGrouMsg)
									{
										cDate=DateFormat.getDateInstance().format(new Date());
										cTime=DateFormat.getTimeInstance().format(new Date());
										saveMsgInDatabase(contact_number, contact_name, message, cDate, cTime);
									}
									runOnUiThread(new Runnable() {public void run() {
											Toast.makeText(getApplicationContext(), "sent through internet mode", Toast.LENGTH_SHORT).show();
									}});
								} catch (Exception e) {
									runOnUiThread(new Runnable() {public void run() {
										Toast.makeText(getApplicationContext(), "couldn't send, check login details", Toast.LENGTH_LONG).show();
								}});
									e.printStackTrace();
									return;
								}							
							
						}else if(default_gateway.contentEquals("way2sms")){
							//To Do Future Development
							//Way2sms.send(user_id,password,contact_number,message);
						}						
					
				}}).start();
				

				Toast.makeText(getApplicationContext(), "Sent through internet mode", Toast.LENGTH_SHORT).show();
			 }
		}
			
	}
	
	public void showScheduleScreen(){		
		if(is_all_set_before_send()==false){
			return;
		}
		Intent messageSchedulerIntent=new Intent(this, ScheduleActivity.class);
		messageSchedulerIntent.putExtra("name", contact_name);
		messageSchedulerIntent.putExtra("number", contact_number);
		messageSchedulerIntent.putExtra("isItGroupMessage", isItGrouMsg);
		messageSchedulerIntent.putExtra("message", message);
		startActivity(messageSchedulerIntent);
	}
	
	public boolean is_all_set_before_send() {
		initialize_variable();
		String temp="";
		temp=etc.getText().toString();
		message=etm.getText().toString();
		if(temp.isEmpty() || message.isEmpty()){
			ad.setMessage("Enter valid Message and phone No");
			ad.show();
			return false;
		}
		
		String temp1[];
		if(temp.contains("<>")){			
			temp1=temp.split("<>");
			contact_number=temp1[1];
		}else{			
			contact_number=temp;			
		}
		
		if(!contact_number.contains(";")){
			isItGrouMsg=false;
			contact_number=PhoneNumberUtils.stripSeparators(contact_number);			
			contact_number=contact_number.replace("+91", "");
			if(contact_number.startsWith("0")){
				contact_number=contact_number.replaceFirst("0", "");
			}			
			if(contact_number.length() != 10  || contact_number.contains("*") || contact_number.contains("#") ){		
				ad.setMessage("Enter valid phone number");
				ad.show();
				return false;
			}						
		 }else{
			 isItGrouMsg=true;
		 }
		contact_name=contact_manager.getContactDisplayNameByNumber(contact_number, getApplicationContext());
		return true;				
	}		
	
	public void initialize_variable(){
		
		contact_name="";
		contact_number="";		
		message="";
		isItGrouMsg=false;		
		user_id=free_sms_pref.get_user_id();
		password=free_sms_pref.get_password();
		default_gateway=free_sms_pref.get_default_gateway();
	}
		
	public void createShareIntent(){		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.play.google.com");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	    
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		isMainActivityVisible=false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		isMainActivityVisible=true;
	}

	public static boolean isMainActivityVisible(){
		return isMainActivityVisible;
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		qm_adapter=new QuickMessagesViewAdapter(this, db.get_quick_messages());
		quickMessageViewList.setAdapter(qm_adapter);
	}

}

