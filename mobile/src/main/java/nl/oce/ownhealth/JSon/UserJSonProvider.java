package nl.oce.ownhealth.JSon;

import com.google.gson.Gson;

public class UserJSonProvider {

    private final static Gson gson = new Gson();

    public static String serializeModelToJson(UserModel user){
        return gson.toJson(user);
    }

    public static UserModel serializeModelToJson(String jSon){
        return gson.fromJson(jSon, UserModel.class);
    }
}