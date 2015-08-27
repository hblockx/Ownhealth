package nl.oce.ownhealth.JSon;

import com.google.*;
import com.google.gson.annotations.SerializedName;

public class PersonalDataModel {

    @SerializedName("prename")
    private String firstName;

    @SerializedName("name")
    private String lastName;

    @SerializedName("sex")
    private String sex;

    @SerializedName("age")
    private Integer age;


    @SerializedName("weight")
    private Float weight;

    public void setWeight(Float weight) {
        this.weight = weight;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSex() {
        return sex;
    }

    public Integer getAge() {
        return age;
    }

    public Float getWeight() {
        return weight;
    }

}
