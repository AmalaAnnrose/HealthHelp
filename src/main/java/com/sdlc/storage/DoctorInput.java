/**
 * 
 */
package com.sdlc.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;

/**
 * @author Amala
 *
 */
public class DoctorInput {

	private static Logger log = LoggerFactory.getLogger(DoctorInput.class);
    private Session session;
	private DoctorDataItem doctorDataItem;
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public DoctorDataItem getDoctorDataItem() {
		return doctorDataItem;
	}
	public void setDoctorDataItem(DoctorDataItem doctorDataItem) {
		this.doctorDataItem = doctorDataItem;
	}
	 public static DoctorInput newInstance(Session session, DoctorDataItem doctorDataItem) {
		 DoctorInput input = new DoctorInput();
	        input.setSession(session);
	        return input;
	    }
}
