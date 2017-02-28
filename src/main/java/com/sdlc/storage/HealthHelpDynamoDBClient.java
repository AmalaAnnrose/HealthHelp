/**
 * 
 */
package com.sdlc.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * @author Amala
 *
 */
public class HealthHelpDynamoDBClient {
	private static Logger log = LoggerFactory.getLogger(HealthHelpDynamoDBClient.class);
	private final AmazonDynamoDBClient dynamoDBClient;
	
	public HealthHelpDynamoDBClient(final AmazonDynamoDBClient dynamoDBClient){
		  this.dynamoDBClient = dynamoDBClient;
	}
	
	
	/**
     * Creates a {@link DynamoDBMapper} using the default configurations.
     * 
     * @return
     */
    private DynamoDBMapper createDynamoDBMapper() {
        return new DynamoDBMapper(dynamoDBClient);
    }
    
    public void deleteDoctorDataItem(final DoctorDataItem doctorDataItem) {
        DynamoDBMapper mapper = createDynamoDBMapper();
        mapper.delete(doctorDataItem);
    }
    
    /**
     * Loads an item from DynamoDB by primary Hash Key. Callers of this method should pass in an
     * object which represents an item in the DynamoDB table item with the primary key populated.
     * 
     * @param tableItem
     * @return
     */
    public DoctorDataItem loadDoctorInfo(final DoctorDataItem doctorDataItem) {
        DynamoDBMapper mapper = createDynamoDBMapper();
        DoctorDataItem item = mapper.load(doctorDataItem);
        return item;
    }

    /**
     * Stores an item to DynamoDB.
     * 
     * @param tableItem
     */
    public void saveDoctorInfo(final DoctorDataItem doctorDataItem) {
        DynamoDBMapper mapper = createDynamoDBMapper();
        mapper.save(doctorDataItem);
        
    }
}
