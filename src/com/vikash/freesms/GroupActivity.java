package com.vikash.freesms;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GroupActivity extends Activity implements OnItemClickListener {	
	ListView listview;
	String phone;
	String name;
	String contact_name_and_number;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		//set list view layout
		setContentView(R.layout.grouplistview);
		listview=(ListView)findViewById(R.id.listview);		
		//get all available group and set it to group list view
		Cursor mCursor=this.getContentResolver().query(ContactsContract.Groups.CONTENT_URI, null, null, null, null);
		startManagingCursor(mCursor);
		ListAdapter adapter=new SimpleCursorAdapter(this,R.layout.grouplistview,mCursor,new String[]{ContactsContract.Groups.TITLE},new int[]{R.id.groupnameid});
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		
	}
	
	public void onItemClick(AdapterView<?> adapter,View view,int position,long id){	
		//get list of phone number of selected group and return this list to Main Activity
		String group_contact_name_and_number=getlistofcontacts(id);
		Intent intentGroupPhone=new Intent();
		intentGroupPhone.putExtra("GROUPPHONE", group_contact_name_and_number);
		setResult(2,intentGroupPhone);
		finish();		
	}
			
	public String getlistofcontacts( long groupId ) {
		phone="";
		name="";
	    String[] cProjection = { ContactsContract.Contacts.DISPLAY_NAME, android.provider.ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID };
	    Cursor groupCursor = getContentResolver().query(Data.CONTENT_URI,cProjection,CommonDataKinds.GroupMembership.GROUP_ROW_ID + "= ?" + " AND "+ ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE + "='"+ ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE + "'",new String[] { String.valueOf(groupId) }, null);
	    if (groupCursor != null && groupCursor.moveToFirst())
	    {
	        do
	        {	            
	            long contactId = groupCursor.getLong(groupCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID));
	            Cursor numberCursor = getContentResolver().query(Phone.CONTENT_URI,new String[] { Phone.NUMBER,Phone.DISPLAY_NAME }, Phone.CONTACT_ID + "=" + contactId, null, null);
	            if (numberCursor.moveToFirst())
	            {
	                int numberColumnIndex = numberCursor.getColumnIndex(Phone.NUMBER);
	                int nameColumnIndex=numberCursor.getColumnIndex(Phone.DISPLAY_NAME);
	                do
	                {
	                	String Name=numberCursor.getString(nameColumnIndex);
	                	name=name.concat(Name).concat(";");
	                	
	                    String phoneNumber = numberCursor.getString(numberColumnIndex);	                    
	                    phoneNumber=PhoneNumberUtils.stripSeparators(phoneNumber);	                    
	                    phoneNumber=phoneNumber.replace("+91", "");
	            		if(phoneNumber.startsWith("0")){
	            			phoneNumber=phoneNumber.replaceFirst("0", "");
	            		}
	            		if(phoneNumber.length()==10 && !(phoneNumber.contains("#")) && !(phoneNumber.contains("#"))){
	            			phone=phone.concat(phoneNumber).concat(";");
	            		}
	                } while (numberCursor.moveToNext());
	                numberCursor.close();	                
	            }
	            else
	            {
	                //Toast.makeText(this,"no contact are there",1000).show();
	            }
	        } while (groupCursor.moveToNext());
	        groupCursor.close();
	    }
	    else
	    {
	        Toast.makeText(this,"No contacts in this group",Toast.LENGTH_SHORT).show();
	    }
	    
	    contact_name_and_number=name.concat("<>").concat(phone);
	    return contact_name_and_number;
	}
	
	@Override	
	public void finish() {
		super.finish();
	}
	
	public void onBackPressed(){
		super.onBackPressed() ;
	}
}