package nl.oce.ownhealth.JSon;

/**
 * Created by Zoldi on 8/26/2015.
 */
public class CreateMockUserAndJson {

    private final UserBiologicalData ubd = new UserBiologicalData();
    private final PersonalDataModel pdm = new PersonalDataModel();
    private final UserModel um = new UserModel();

    public CreateMockUserAndJson(){
        setUpBiologicalData();
        setUpPersonalData();
        setUpUserModel();
    }

    private void setUpBiologicalData() {
        ubd.setHeartRate(60);
    }

    private void setUpPersonalData() {
        pdm.setFirstName("Beata");
        pdm.setLastName("Zoldi");
        pdm.setSex("female");
        pdm.setAge(22);
    }

    private void setUpUserModel() {
        um.setPersonalData(pdm);
        um.setTrackData(ubd);
        um.setTimeStamp("2015-08-24T10:20:40");
    }

    public UserModel getUserModel(){
        return um;
    }
}
