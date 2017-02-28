/**
 * 
 */
package com.sdlc.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author Amala
 *
 */
@DynamoDBTable(tableName = "DOCTOR")
public class DoctorDataItem {

	private String doctorId;
	private String doctName;
	private String doctAddress;
	private String doctPhone;
	private String doctSpecialt;

	 @DynamoDBHashKey(attributeName = "DOCTOR_ID")
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@DynamoDBAttribute(attributeName = "DOCT_NAME")
	public String getDoctName() {
		return doctName;
	}

	public void setDoctName(String doctName) {
		this.doctName = doctName;
	}

	@DynamoDBAttribute(attributeName = "DOCT_ADDRESS")
	public String getDoctAddress() {
		return doctAddress;
	}

	public void setDoctAddress(String doctAddress) {
		this.doctAddress = doctAddress;
	}

	@DynamoDBAttribute(attributeName = "DOCT_PHONE")
	public String getDoctPhone() {
		return doctPhone;
	}

	public void setDoctPhone(String doctPhone) {
		this.doctPhone = doctPhone;
	}

	@DynamoDBAttribute(attributeName = "SPECIALT")
	public String getDoctSpecialt() {
		return doctSpecialt;
	}

	public void setDoctSpecialt(String doctSpecialt) {
		this.doctSpecialt = doctSpecialt;
	}

}
