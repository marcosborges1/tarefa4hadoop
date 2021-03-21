package utils;

import java.text.Normalizer;

public class Text {

		
	public static String clearContent(String content) {

		String originalContent = content.toLowerCase();
		String[] words = originalContent.split("\\s+");
		// Remove retweets
//		if (words[0].equals("RT"))
//			return null;
		String tempResult = "";
		
		for (String word : words) {
			
			// Remove words <=2
			if (word.length() <=2) {
				continue;
			}
			// Remove links
			if (word.length() >= 4 && (word.substring(0, 3).equals("www")
					|| word.substring(0, 4).equals("http"))) {
				continue;
			}
			// Remove usernames
			if (word.charAt(0) == '@') {
				continue;
			}
			
			//Next phase
			
			// Remove punctuation and ASCII
			String token = removePunctuationWord(word); // obviously need to check for URLS first!!!
			if (token.equals("")) {
				continue;
			}
//			if (token.equals("rt")) {
//				continue;
//			}

			// Remove links after
			if (token.length() >= 4 && (token.substring(0, 3).equals("www") || token.substring(0, 4).equals("http"))) {
				continue;
			}
			tempResult += token + " ";
		}

		return tempResult;
	}

	public static String removePunctuationWord(String original) {

		String result = Normalizer.normalize(original, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
				.replaceAll("\\p{Punct}", "").replaceAll("\\b\\w{1,3}\\b", "");
		return result;
	}
}