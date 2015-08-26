package nl.oce.ownhealth.JSon;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zoldi on 8/26/2015.
 */
public class UserBiologicalData {

    @SerializedName("heartrate")
    private Integer heartRate;

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }
}
