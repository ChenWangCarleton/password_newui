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
 * */

public class LogStore {
	private ConcurrentHashMap<Integer, Log> logs;
	private static LogStore instance;
	private static String LOG_DIR = "../../logs";
	
	public LogStore() {
		logs = new ConcurrentHashMap<Integer, Log>();
	}
	
	
	public ConcurrentHashMap<Integer, Log> getLogs() {
		return logs;
	}
	
	public Log createLog(String userID, String message) {
		Log log = new Log(userID, message);
		logs.put(logs.size(), log); //add a new log at the next index
		return log;
	}
	
	public Map<Integer, Log> getModel() {
		return logs;
	}
	
	public int size() {
		return logs.size();
	}
	
	public static LogStore getInstance() { //returns the hashmap
		if (instance == null)
			instance = new LogStore();
		return instance;
	}
	
	
	public static void writeLogs(){
		try {
			PrintWriter pw = new PrintWriter(new File(LOG_DIR + "log_" + getCurrentTimeStamp() + ".csv"));
			StringBuilder sb = new StringBuilder();
			for(Enumeration<Log> logStore = LogStore.getInstance().getLogs().elements(); logStore.hasMoreElements();){
				Log log  = logStore.nextElement();
				sb.append(log.getUserID() + ","); // log
				sb.append(log.getTimestamp() + ","); // log
				sb.append(log.getMessage()); // log
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
	
	private static String getCurrentTimeStamp() {
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}

}
