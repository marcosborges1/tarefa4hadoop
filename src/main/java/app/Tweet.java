package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Tweet {

	private String id;
	private String content;
	private String createdAt;

	public Tweet(String id, String content, String createdAt) {

		this.id = id;
		this.content = content.trim();
		this.createdAt = createdAt;

	}
	public Tweet(String id, String content) {

		this.id = id;
		this.content = content.trim();

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
			if(token.startsWith("#")) {
				//Concate token + period. I.e: #massa|morning
				tokens.add(token+this.getPeriod());
			}
		}
		return tokens;
	}
	
	public String getPeriod() {
		
		
		try {
			String period = "";
 			Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(this.getCreatedAt());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
		    int hour = cal.get(Calendar.HOUR_OF_DAY);
		    
		    if(hour>=0 && hour<12) {
		    	period = "|morning";
		    }
		    if(hour>=12 && hour<18) {
		    	period = "|afternoon";
		    }
		    if(hour>=18 && hour<=23) {
		    	period = "|night";
		    }
			return period;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null; 
		
	}

	
}
