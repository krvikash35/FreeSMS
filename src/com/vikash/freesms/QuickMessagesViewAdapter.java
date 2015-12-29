package com.vikash.freesms;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuickMessagesViewAdapter extends BaseAdapter
{
	Context c;
	List<QuickMessages> qm;
	
	QuickMessagesViewAdapter(Context c,List<QuickMessages> qm)
	{
		this.c=c;
		this.qm=qm;
	}
	@Override
	public int getCount() 
	{
		return qm.size();
	}

	@Override
	public Object getItem(int position)
	{
		return qm.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return qm.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		QuickMessages QM=qm.get(position);
		LayoutInflater mInflator=(LayoutInflater) c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder=null;
		if(convertView==null)
		{
			
			convertView=mInflator.inflate(R.layout.sent_msg_list_view, null);
			holder=new ViewHolder();
			holder.name=(TextView)convertView.findViewById(R.id.nametv);
			holder.number=(TextView)convertView.findViewById(R.id.phonetv);
			holder.message=(TextView)convertView.findViewById(R.id.msgtv);
			holder.date=(TextView)convertView.findViewById(R.id.datetv);
			holder.time=(TextView)convertView.findViewById(R.id.timetv);
			holder.inouttv=(TextView)convertView.findViewById(R.id.inouttv);
			
			
			
			holder.name.setText(QM.get_name());
			holder.number.setText(QM.get_number());
			holder.message.setText(QM.get_message());
			holder.date.setText(QM.get_date());
			holder.time.setText(QM.get_time());
			
			convertView.setTag(holder);
		}else
			holder=(ViewHolder)convertView.getTag();
		
		if(QM.isItMine()){
			holder.inouttv.setBackgroundResource(R.drawable.send);
		}
		else
		{
			holder.inouttv.setBackgroundResource(R.drawable.receive);
		}
		return convertView;
	}
	
	private static class ViewHolder
	{
		TextView name;
		TextView number;
		TextView message;
		TextView date;
		TextView time;
		TextView inouttv;
	}
}
