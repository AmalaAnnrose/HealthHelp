package com.sdlc.healthhelp;

import java.util.Map;

/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.sdlc.healthhelp.util.AnalyticsManager;
import com.sdlc.healthhelp.util.SpeechletResponseHelper;
import com.sdlc.storage.DoctorDataItem;
import com.sdlc.storage.HealthHelpDao;
import com.sdlc.storage.HealthHelpDynamoDBClient;

/**
 * This sample shows how to create a simple speechlet for handling speechlet
 * requests.
 */
public class HealthHelpSpeechlet implements Speechlet {
	private static final Logger log = LoggerFactory.getLogger(HealthHelpSpeechlet.class);
	private AnalyticsManager analytics;
	private AmazonDynamoDBClient amazonDynamoDBClient;
	private HealthHelpDynamoDBClient healthHelpDynamoDBClient;
	private HealthHelpDao healthHelpDao;

	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		// any initialization logic goes here
		analytics = new AnalyticsManager();
		analytics.setUserId(session.getUser().getUserId());
		analytics.postSessionEvent(AnalyticsManager.ACTION_SESSION_START);
	}

	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		analytics.postEvent(AnalyticsManager.CATEGORY_LAUNCH, "Welcome");
		return SpeechletResponseHelper.getWelcome();
	}

	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
//		try {
			Intent intent = request.getIntent();
			analytics.postEvent(AnalyticsManager.CATEGORY_INTENT, intent.getName());
			String intentName = (intent != null) ? intent.getName() : null;
			switch (intent.getName()) {

			case SpeechletResponseHelper.HEALTHHELP_WELCOME_INTENT_NAME:
				return SpeechletResponseHelper.getResponse();

			case SpeechletResponseHelper.APPOINTMENT_SCHEDULE_INTENT_NAME:
				return getScheduleAppointmentResponse();
				
			case SpeechletResponseHelper.DOCTOR_INFO_INTENT_NAME:
				return getDoctorInfoResponse();

			case SpeechletResponseHelper.AMAZON_HELP_INTENT_NAME:
				return getHelpResponse();
			}
			
//			DoctorDataItem  doctordataItem = buildDoctorDataItem(session);
//			saveDoctorInformation(doctordataItem);

//		} catch (InvalidInputException ex) {
//			analytics.postException(ex.getMessage(), false);
//			return SpeechletResponseHelper.newAskResponse(ex.getSpeech(), ex.getSpeech());
//		}
		return SpeechletResponseHelper.getWelcome();
	}

	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		// any cleanup logic goes here
	}

	/**
	 * Creates and returns a {@code SpeechletResponse} with a welcome message.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHealthHelpWelcomeResponse() {
		String speechText = "Welcome to the Health help, How can I help you?";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HealthHelp");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	/**
	 * Creates a {@code SpeechletResponse} for the help intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelpResponse() {
		String speechText = "schedule an appointment";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HealthHelp");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	/**
	 * Creates and returns a {@code SpeechletResponse} with a welcome message.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getScheduleAppointmentResponse() {
		String speechText = "For Identification purpose please tell your last name and date of birth ?";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HealthHelp");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
	private AmazonDynamoDBClient getAmazonDynamoDBClient() {
		if (this.amazonDynamoDBClient == null) {
			this.amazonDynamoDBClient = new AmazonDynamoDBClient();
		}
		return this.amazonDynamoDBClient;
	}

	
	public HealthHelpDynamoDBClient getHealthHelpDynamoDBClient() {
		if(this.healthHelpDynamoDBClient == null){
			this.healthHelpDynamoDBClient = new HealthHelpDynamoDBClient(getAmazonDynamoDBClient());
		}
		return this.healthHelpDynamoDBClient;
	}


	public HealthHelpDao getHealthHelpDao() {
		if(this.healthHelpDao == null){
			this.healthHelpDao = new HealthHelpDao(getHealthHelpDynamoDBClient());
		}
		return healthHelpDao;
	}

	private void saveDoctorInformation(DoctorDataItem input) {
		getHealthHelpDao().saveDoctorinfo(input);

	}
	
	private DoctorDataItem buildDoctorDataItem(Session session) {
		// TODO: Make Session Data be a PaInput
		// Map<String,String> sessionData= getInputValuesFromSession();
		Map<String, Object> sessionData = session.getAttributes();
		DoctorDataItem doctorDataItem = new DoctorDataItem();
		doctorDataItem.setDoctAddress("Pittsburgh");
		doctorDataItem.setDoctName("Jasiel");
		doctorDataItem.setDoctorId("456");
		doctorDataItem.setDoctPhone("41298756890");
		doctorDataItem.setDoctSpecialt("Pediatrician");
		
		return doctorDataItem;
	}
	
	private SpeechletResponse getDoctorInfoResponse() {
		
		DoctorDataItem doctorDataItem = new DoctorDataItem();
		doctorDataItem.setDoctorId("1");
		DoctorDataItem info = getHealthHelpDao().findDoctorInfor(doctorDataItem);
		String speechText = info.getDoctName();

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HealthHelp");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
}