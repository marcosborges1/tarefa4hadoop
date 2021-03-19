package app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Tweet {

	private String id;
	private String content;
	private String createdAt;
	private String createdAtStr;
	
	public Tweet() {
		//Constructor
	}
	
	public Tweet(String id, String content, String createdAt) {

		this.id = id;
		this.content = content.trim();
		this.createdAt = createdAt;

	}
	
	public Tweet(String content, String createdAtStr) {

		this.content = content.trim();
		this.createdAtStr = createdAtStr;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getCreatedAtStr() {
		return createdAtStr;
	}

	public void setCreatedAtStr(String createdAtStr) {
		this.createdAtStr = createdAtStr;
	}

	public ArrayList<String> getHashTagsFromContent(){
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer itr = new StringTokenizer(this.getContent());
		while (itr.hasMoreTokens()) {
			String token = itr.nextToken();
			if(token.startsWith("#")) {
				tokens.add(token);
			}
		}
		return tokens;
	}
	
	public ArrayList<String> getHashTagsFromContentByPeriod() {
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer itr = new StringTokenizer(this.getContent());
		while (itr.hasMoreTokens()) {
			String token = itr.nextToken();
			if(token.startsWith("#") && token.length() >= 2 && !token.subSequence(1, 2).equals("#")) {
				//Concate token + period. I.e: #massa\tmorning to better visualization in Excel App by tsv
				tokens.add(token+this.getPeriod());
			}
		}
		return tokens;
	}
	
	public ArrayList<String> getHashTagsFromContentByDateHour() {
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer itr = new StringTokenizer(this.getContent());
		while (itr.hasMoreTokens()) {
			String token = itr.nextToken();
			if(token.startsWith("#") && token.length() >= 2 && !token.subSequence(1, 2).equals("#")) {
				//Concate token + period. I.e: #massa\tmorning to better visualization in Excel App by tsv
				tokens.add(token+this.getDateHour());
			}
		}
		return tokens;
	}
	
	public String getDateHour() {
		
		try {
			String dateHour = "\t";
			Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(this.getCreatedAt());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			dateHour+=cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR);
			dateHour+="\t";
			dateHour+=cal.get(Calendar.HOUR_OF_DAY);
			return dateHour;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String getPeriod() {
		
		try {
			String period = "";
 			Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(this.getCreatedAt());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
		    int hour = cal.get(Calendar.HOUR_OF_DAY);
		    
		    if(hour>=0 && hour<12) {
		    	period = "\tmorning";
		    }
		    if(hour>=12 && hour<18) {
		    	period = "\tafternoon";
		    }
		    if(hour>=18 && hour<=23) {
		    	period = "\tnight";
		    }
			return period;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null; 
		
	}
	
	public ArrayList<String> getHashTagsFromContentByDay() {
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer itr = new StringTokenizer(this.getContent());
		while (itr.hasMoreTokens()) {
			String token = itr.nextToken();
			if(token.startsWith("#")) {
				//Concate token + period. I.e: #massa|morning
				tokens.add(token+"\t"+this.getCreatedAtStr());
			}
		}
		return tokens;
	}
	
	public ArrayList<String> getSentencesByWord(String word) {
		
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer itr = new StringTokenizer(this.getContent());
		while (itr.hasMoreTokens()) {
			String token = itr.nextToken();
			if(token.toLowerCase().equals(word.toLowerCase())) {
				//Concate token + period. I.e: #massa|morning
				tokens.add(token);
			}
		}
		
		return tokens;
		
	}

	
}
