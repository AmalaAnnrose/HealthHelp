/**
 * 
 */
package com.sdlc.healthhelp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;

/**
 * @author Amala
 *
 */
public class SpeechletResponseHelper {
private final static Logger LOGGER = LoggerFactory.getLogger("OutputHelper");
public static final String INVOCATION_NAME = "Health Help";
private static String SPEECH_WELCOME =  "Welcome to Health Help";
public static final String AUDIO_WELCOME =  "<audio src=\"https://s3.amazonaws.com/healthhelp-audio/healthhelp_welcome.mp3\" />";
private static final String AUDIO_SUCCESS = "<audio src=\"https://s3.amazonaws.com/success-audio/healthhelp_success.mp3\" />";
private static final String INITIAL_SPEECH = " How Can I help you ?";

//INTENTS
	public static final String HEALTHHELP_WELCOME_INTENT_NAME = "HealthHelpWelcomeIntent";
	public static final String APPOINTMENT_SCHEDULE_INTENT_NAME = "AppointmentIntent";
	public static final String DOCTOR_INFO_INTENT_NAME = "DoctorInfoIntent";
	public static final String AMAZON_HELP_INTENT_NAME = "AMAZON.HelpIntent";

public static SpeechletResponse getWelcome(){
	String output=AUDIO_WELCOME+" "+SPEECH_WELCOME + CommonUtil.HEALTHELP_PROMPT;
	
	return newAskResponse(output, CommonUtil.HEALTHELP_PROMPT);
}

/**
 * Wrapper for creating the Ask response from the input strings.

 * @param stringOutput
 *            the output to be spoken
 * @param repromptText
 *            the reprompt for if the user doesn't reply or is
 *            misunderstood.
 * @return SpeechletResponse the speechlet response
 */
public static SpeechletResponse newAskResponse(String stringOutput, String repromptText) {

	SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
	outputSpeech.setSsml("<speak> " + stringOutput + " </speak>");

	PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
	repromptOutputSpeech.setText(repromptText);

	Reprompt reprompt = new Reprompt();
	reprompt.setOutputSpeech(repromptOutputSpeech);
	return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
}

public static SpeechletResponse getResponse() {	
	SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
	String textOutput = INITIAL_SPEECH;
	String speechOutput = INITIAL_SPEECH;
	outputSpeech.setSsml("<speak> " + AUDIO_SUCCESS + speechOutput + "</speak>");
	Card card;
	
	try {
		card = buildCard(textOutput);
	} catch (Exception e) {
		LOGGER.error(e.getMessage());
		card= buildCard(textOutput);
	}
	
	return SpeechletResponse.newTellResponse(outputSpeech, card);
}

//card for error output
	private static SimpleCard buildCard(String s){
		SimpleCard card=new SimpleCard();
		card.setTitle(INVOCATION_NAME);
		card.setContent(s);
		return card;
	}
   

}
