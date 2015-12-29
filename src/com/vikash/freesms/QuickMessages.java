package com.vikash.freesms;

public class QuickMessages {

	private String name;
	private String number;
	private String message;
	private String date;
	private String time;
	private String inoutchecker;
	private String SEND="1";
	
	public QuickMessages(){
		
	}
	
	public QuickMessages(String name,String number,String message,String date,String time,String inoutchecker) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.number=number;
		this.message=message;
		this.date=date;
		this.time=time;
		this.inoutchecker=inoutchecker;
	}
			
	public String get_name(){
		return name;
	}
	
	public String get_number(){
		return number;
	}
	
	public String get_message(){
		return message;
	}
	
	public String get_date(){
		return date;
	}
	
	public String get_time(){
		return time;
	}
	
	public boolean isItMine(){
		if(inoutchecker.contentEquals(SEND)){
			return true;
		}
		else
		{
			return false;
		}
	}
}
