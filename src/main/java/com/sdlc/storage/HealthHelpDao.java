/**
 * 
 */
package com.sdlc.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Amala
 *
 */
public class HealthHelpDao {
	private static Logger log = LoggerFactory.getLogger(HealthHelpDao.class);
	private final HealthHelpDynamoDBClient healthHelpDynamoDBClient;

	public HealthHelpDao(HealthHelpDynamoDBClient healthHelpDynamoDBClient) {
		this.healthHelpDynamoDBClient = healthHelpDynamoDBClient;
	}
	
	/**
     * Saves the {@link PaInput} into the database.
     * 
     * @param input
     */
    public void saveDoctorinfo(DoctorDataItem doctorDataItem) {
            healthHelpDynamoDBClient.saveDoctorInfo(doctorDataItem);
    }
    
    public DoctorDataItem findDoctorInfor(DoctorDataItem doctorDataItem){
    	DoctorDataItem dataItem = healthHelpDynamoDBClient.loadDoctorInfo(doctorDataItem);
    	return dataItem;
    }
}
