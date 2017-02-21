/**
 * 
 */
package com.sdlc.healthhelp.util;

import java.util.ArrayList;

/**
 * @author Amala
 *
 */
public class CommonUtil {
	// Voice
	public static final String SPEECH_WELCOME = "Welcome to Health Help ";
	public static final String HEALTHELP_PROMPT = " How Can I Help You?";
	
	
	private static ArrayList<String> getValidIntents() {
		// if (validIntents!=null){
		// return validIntents;
		// } else {
		ArrayList<String> validIntents = new ArrayList<String>();
		validIntents.add(SPEECH_WELCOME);
		validIntents.add(HEALTHELP_PROMPT);
		return validIntents;
	}

	public static boolean isValidIntent(String intentName) {
		return (getValidIntents().contains(intentName));
	}

}
