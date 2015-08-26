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

<<<<<<< HEAD
    @SerializedName("weight")
    private Float weight;

=======
>>>>>>> User model with json builder and mock data model for testing.
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

<<<<<<< HEAD
    public void setWeight(Float weight) {
        this.weight = weight;
    }

=======
>>>>>>> User model with json builder and mock data model for testing.
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
<<<<<<< HEAD

    public Float getWeight() {
        return weight;
    }
=======
>>>>>>> User model with json builder and mock data model for testing.
}
