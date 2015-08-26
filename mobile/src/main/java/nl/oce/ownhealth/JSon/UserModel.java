package nl.oce.ownhealth.JSon;

import com.google.gson.annotations.SerializedName;

public class UserModel {

	@SerializedName("user")
	private  PersonalDataModel personalData;

	@SerializedName("trackdata")
	private  UserBiologicalData trackData;

	@SerializedName("timestamp")
	private  String timeStamp;

	public PersonalDataModel getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalDataModel personalData) {
		this.personalData = personalData;
	}

	public UserBiologicalData getTrackData() {
		return trackData;
	}

	public void setTrackData(UserBiologicalData trackData) {
		this.trackData = trackData;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
