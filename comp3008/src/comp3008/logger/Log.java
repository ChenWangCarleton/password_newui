package comp3008.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Author: Brittny Lapierre
 * Description: This class represents a log entity 
 * for our program. It is used as a model for the 
 * objects in the LogStore.
 * */

public class Log {
	private String timestamp;
	private String message;
	private String userID;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Log(String userID, String message){
		this.timestamp = this.getCurrentTimeStamp();
		this.message = message;
		this.userID = userID;
		this.printLog();
	}
	
	public void printLog(){
		System.out.println(this.timestamp + ": User action recorded " + this.userID + " - "+ this.message);
	}
	
	private String getCurrentTimeStamp() {
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}

}
