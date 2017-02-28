/**
 * 
 */
package com.sdlc.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.sdlc.healthhelp.HealthHelpSpeechlet;

/**
 * @author Amala
 *
 */
public class AlexaSpeechletTest {
	 @Rule
	    public ExpectedException exception = ExpectedException.none();

	    private HealthHelpSpeechlet speechlet;
	    private Session session;

	    @Before
	    public void init() {
//	        speechlet = new HealthHelpSpeechlet("en-US", new ResourceUtteranceReader());
	    	speechlet = new HealthHelpSpeechlet();
	        session = ModelFactory.givenSession();
	    }

	    @Test
	    public void onSessionStarted() throws Exception {
	        speechlet.onSessionStarted(ModelFactory.givenSessionStartedRequest(), session);
	    }

	    @Test
	    public void onLaunch() throws Exception {
	        final SpeechletResponse response = speechlet.onLaunch(ModelFactory.givenLaunchRequest(), session);
	        Assert.assertNotNull(response);
	        Assert.assertTrue(response instanceof SpeechletResponse);

	        final SpeechletResponse alexaResponse = (SpeechletResponse)response;
	        Assert.assertNotNull(alexaResponse.getOutputSpeech());
	        Assert.assertTrue(alexaResponse.getOutputSpeech() instanceof SsmlOutputSpeech);

	        final SsmlOutputSpeech outputSpeech = (SsmlOutputSpeech)alexaResponse.getOutputSpeech();
	        Assert.assertEquals(outputSpeech.getSsml(), "<speak>Hello there</speak>");

	        Assert.assertNotNull(alexaResponse.getReprompt());
	        Assert.assertNotNull(alexaResponse.getReprompt().getOutputSpeech());
	        Assert.assertTrue(alexaResponse.getReprompt().getOutputSpeech() instanceof SsmlOutputSpeech);

	        final SsmlOutputSpeech repromptSpeech = (SsmlOutputSpeech)alexaResponse.getReprompt().getOutputSpeech();
	        Assert.assertEquals(repromptSpeech.getSsml(), "<speak>Hello again</speak>");
	    }

	    @Test
	    public void onIntentWithNoHandler() throws Exception {
	        exception.expect(SpeechletException.class);
	        speechlet.onIntent(ModelFactory.givenIntentRequest("IntentThatHasNoHandler"), session);
	    }

	    @Test
	    public void onIntentWithError() throws Exception {
	        // provoke an error with an non-numeric credit-value
	        Map<String, Slot> slots = new HashMap<>();
	        slots.put("name", Slot.builder().withName("name").withValue("Joe").build());
	        slots.put("credits", Slot.builder().withName("credits").withValue("notANumber").build());

	        final SpeechletResponse response = speechlet.onIntent(ModelFactory.givenIntentRequest("IntentWithOneUtteranceAndOneReprompt", slots), session);
	        Assert.assertNotNull(response);
	        Assert.assertTrue(response instanceof SpeechletResponse);

	        final SpeechletResponse alexaResponse = (SpeechletResponse)response;

	        Assert.assertNotNull(alexaResponse.getOutputSpeech());
	        Assert.assertTrue(alexaResponse.getOutputSpeech() instanceof SsmlOutputSpeech);

	        final SsmlOutputSpeech outputSpeech = (SsmlOutputSpeech)alexaResponse.getOutputSpeech();
	        Assert.assertEquals(outputSpeech.getSsml(), "<speak>Sorry, there was an error</speak>");
	    }

	    @Test
	    public void onIntent() throws Exception {
	        Map<String, Slot> slots = new HashMap<>();
	        slots.put("name", Slot.builder().withName("name").withValue("Joe").build());
	        slots.put("credits", Slot.builder().withName("credits").withValue("123").build());
	        final SpeechletResponse response = speechlet.onIntent(ModelFactory.givenIntentRequest("IntentWithOneUtteranceAndOneReprompt", slots), session);
	        Assert.assertNotNull(response);
	        Assert.assertTrue(response instanceof SpeechletResponse);

	        final SpeechletResponse alexaResponse = (SpeechletResponse)response;
	        Assert.assertNotNull(alexaResponse.getOutputSpeech());
	        Assert.assertTrue(alexaResponse.getOutputSpeech() instanceof SsmlOutputSpeech);

	        final SsmlOutputSpeech outputSpeech = (SsmlOutputSpeech)alexaResponse.getOutputSpeech();
	        Assert.assertEquals(outputSpeech.getSsml(), "<speak>Hello <say-as interpret-as=\"spell-out\">Joe</say-as>. Your current score is <say-as interpret-as=\"number\">123</say-as></speak>");

	        Assert.assertNotNull(alexaResponse.getReprompt());
	        Assert.assertNotNull(alexaResponse.getReprompt().getOutputSpeech());
	        Assert.assertTrue(alexaResponse.getReprompt().getOutputSpeech() instanceof SsmlOutputSpeech);

	        final SsmlOutputSpeech repromptSpeech = (SsmlOutputSpeech)alexaResponse.getReprompt().getOutputSpeech();
	        Assert.assertEquals(repromptSpeech.getSsml(), "<speak>This is a reprompt <say-as interpret-as=\"spell-out\">Joe</say-as> with your score of <say-as interpret-as=\"number\">123</say-as></speak>");
	    }

	    @Test
	    public void onSessionEnded() throws Exception {
	        speechlet.onSessionEnded(ModelFactory.givenSessionEndedRequest(), session);
	    }
}
