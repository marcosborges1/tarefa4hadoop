package aux;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import utils.Text;

public class Tweet {

	private String content;
	private String createdAt;
	private String createdAtStr;
	
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
	
	public ArrayList<String> getHashTagsFromContentByPeriod() {
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer itr = new StringTokenizer(this.getContent().toLowerCase());
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
		StringTokenizer itr = new StringTokenizer(this.getContent().toLowerCase());
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
			String dateHour = "";
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
		StringTokenizer itr = new StringTokenizer(this.getContent().toLowerCase());
		while (itr.hasMoreTokens()) {
			String token = itr.nextToken();
			if(token.startsWith("#") && token.length() >= 2 && !token.subSequence(1, 2).equals("#")) {
				//Concate token + period. I.e: #massa|morning
				tokens.add(token+"\t"+this.getCreatedAtStr());
			}
		}
		return tokens;
	}
	
	public ArrayList<String> getSentencesByWord(String word) {
		
		String wordNoAccent = Normalizer.normalize(word, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		ArrayList<String> sentences = new ArrayList<String>();
		//Acentuação | Sem acentuação
		if(this.getContent().toLowerCase().contains(word.toLowerCase()) || this.getContent().toLowerCase().contains(wordNoAccent.toLowerCase())) {
			String content = Text.clearContent(this.getContent().toLowerCase());
			String[] words = content.split("\\s+");
			sentences = this.conbinatedSentences2(sentences,words);
			sentences = this.conbinatedSentences3(sentences,words);
			sentences = this.conbinatedSentences4(sentences,words);
		}
		return sentences;
	}
	
	public ArrayList<String> conbinatedSentences2(ArrayList<String> tokens, String[] words) {
		for(int i = 0; i < words.length - 1; i++) {
			tokens.add(words[i] + " " + words[i+1] + "\t2");
		}
		return tokens;
	}
	public ArrayList<String> conbinatedSentences3(ArrayList<String> tokens, String[] words) {
		for(int i = 0; i < words.length - 2; i++) {
			tokens.add(words[i] + " " + words[i+1] + " " + words[i+2] + "\t3");
		}
		return tokens;
	}
	public ArrayList<String> conbinatedSentences4(ArrayList<String> tokens, String[] words) {
		for(int i = 0; i < words.length - 3; i++) {
			tokens.add(words[i] + " " + words[i+1] + " " + words[i+2] + " " + words[i+3] + "\t4");
		}
		return tokens;
	}
	
	
	
}
