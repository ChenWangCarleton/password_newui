package comp3008.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Author: Brittny Lapierre
 * Description: This class represents a log entity 
 * for our program. It is used as a model for the 
 * objects in the LogStore.
 * 
 * The following functions can be found within:
 * 
 * getApplicationName() => Returns the current app the user is logged into
 * 
 * setApplicationName(String applicationName) => Sets the name for the current app the user is logged onto
 * 
 * getUserID() => Gets the current user id
 * 
 * setUserID(String userID) => Sets the current user id
 * 
 * getTimestamp() => Gets the log timestamp
 * 
 * setTimestamp(String timestamp) => Sets the timestamp for the log
 * 
 * getMessage() => Gets the message for the log
 * 
 * setMessage(String message) => Sets the message for the log
 * 
 * Log(String userID, String message, String applicationName) => Creates a log and initializes it with the values passed into the constructor
 * 
 * printLog() => Prints the log to the console
 * 
 * getCurrentTimeStamp() => Gets the current timestamp
 * */

public class Log {
	private String timestamp;
	private String message;
	private String userID;
	private String applicationName;
	
	//Returns the current app the user is logged into
	public String getApplicationName() {
		return applicationName;
	}

	//Sets the name for the current app the user is logged onto
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	//Gets the current user id
	public String getUserID() {
		return userID;
	}

	//Sets the current user id
	public void setUserID(String userID) {
		this.userID = userID;
	}

	//Gets the log timestamp
	public String getTimestamp() {
		return timestamp;
	}

	//Sets the timestamp for the log
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	//Gets the message for the log
	public String getMessage() {
		return message;
	}

	//Sets the message for the log
	public void setMessage(String message) {
		this.message = message;
	}

	//Creates a log and initializes it with the values passed into the constructor
	public Log(String userID, String message, String applicationName){
		this.timestamp = this.getCurrentTimeStamp();
		this.message = message;
		this.userID = userID;
		this.applicationName = applicationName;
		this.printLog();
	}
	
	//Prints the log to the console
	public void printLog(){
		System.out.println(this.timestamp + ": User " + this.userID + " action recorded in app " + this.applicationName + " - "+ this.message);
	}
	
	//Gets the current timestamp
	private String getCurrentTimeStamp() {
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}

}
