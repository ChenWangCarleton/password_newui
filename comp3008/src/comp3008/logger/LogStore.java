package comp3008.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/*
 * Author: Brittny Lapierre
 * Description: This class hold a collection of logs.
 * It also features export functions for exporting the
 * log collection to a CSV file.
 * 
 * The following functions can be found within:
 * 
 * LogStore() => Creates a new log store and initializes the log collection
 * 
 * getLogs() => Allows access to the internal collection as a concurrent hash map
 * 
 * createLog(String userID, String message, String applicationName) => Creates a new log and adds it to the collection
 * 
 * getModel() => Allows access to the internal collection as a map
 * 
 * size() => Returns the number of logs stores in the log collection
 * 
 * getInstance() => returns the instance of logstore
 * 
 * writeLogs() => Writes all of the logs to a csv file and saves them to the logs directory
 * 
 * getCurrentTimeStamp() => Gets the current time
 * */

public class LogStore {
	private ConcurrentHashMap<Integer, Log> logs;
	private static LogStore instance;
	private static String LOG_DIR = "logs/";
	
	//Creates a new log store and initializes the log collection
	public LogStore() {
		logs = new ConcurrentHashMap<Integer, Log>();
	}
	
	//Allows access to the internal collection as a concurrent hash map
	public ConcurrentHashMap<Integer, Log> getLogs() {
		return logs;
	}
	
	//Creates a new log and adds it to the collection
	public Log createLog(String userID, String message, String applicationName) {
		Log log = new Log(userID, message, applicationName);
		logs.put(logs.size(), log); //add a new log at the next index
		return log;
	}
	
	//Allows access to the internal collection as a map
	public Map<Integer, Log> getModel() {
		return logs;
	}
	
	//Returns the number of logs stores in the log collection
	public int size() {
		return logs.size();
	}
	
	public static LogStore getInstance() { //returns the instance of logstore
		if (instance == null)
			instance = new LogStore();
		return instance;
	}
	
	//Writes all of the logs to a csv file and saves them to the logs directory
	public static void writeLogs(){
		try {
			PrintWriter pw = new PrintWriter(new File(LOG_DIR + "log_" + getCurrentTimeStamp() + ".csv"));
			StringBuilder sb = new StringBuilder();
			sb.append("USER_ID,"); // log
			sb.append("TIMESTAMP,");
			sb.append("APP_NAME,");
			sb.append("ACTION");
			sb.append("\n");
			for(Enumeration<Log> logStore = LogStore.getInstance().getLogs().elements(); logStore.hasMoreElements();){
				Log log  = logStore.nextElement();
				sb.append(log.getUserID() + ","); // log
				sb.append(log.getTimestamp() + ",");
				sb.append(log.getApplicationName() + ",");
				sb.append(log.getMessage());
				sb.append("\n");
			}
			System.out.println(sb.toString());
	        pw.write(sb.toString());
	        pw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//"C:/Users/IBM_ADMIN/workspace/COMP4601A2/user_profiles.csv"));
	}
	
	//Gets the current time
	private static String getCurrentTimeStamp() {
	    return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
	}

}
