package com.vikash.freesms;

public class ChatMessage {
	
	String name;
	String number;
	String message;
	String toFromTag;
	String date;
	String time;
	private String SEND="1";
	private String RECEIVE="0";
	
	ChatMessage(String name,String number,String message,String toFromTag,String date,String time){
		this.name=name;
		this.message=message;
		this.toFromTag=toFromTag;
		this.date=date;
		this.time=time;
	}
	
	ChatMessage(String message,Boolean isMine){
		this.message=message;
		if(isMine){
			toFromTag="SEND";
		}else
			toFromTag="RECEIVE";
	}
	
	ChatMessage(String name,String message,String toFromTag,String date,String time){
		this.name=name;
		this.message=message;
		this.toFromTag=toFromTag;
		this.date=date;
		this.time=time;
	}
	public String  getMessage(){
		return message;
	}
	
	public boolean isMine(){
		if(toFromTag.contains(SEND)){
			return true;
		}else
			return false;
	}
	
	
}
