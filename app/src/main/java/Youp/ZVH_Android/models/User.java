package Youp.ZVH_Android.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("consultantId")
    @Expose
    private String consultantId;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("isActivated")
    @Expose
    private Boolean isActivated;
    @SerializedName("activationToken")
    @Expose
    private String activationToken;
    @SerializedName("authToken")
    @Expose
    private String authToken;
    @SerializedName("resetPasswordToken")
    @Expose
    private String resetPasswordToken;
    public final static Parcelable.Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    };


    protected User(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.emailAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.lastname = ((String) in.readValue((String.class.getClassLoader())));
        this.firstname = ((String) in.readValue((String.class.getClassLoader())));
        this.consultantId = ((String) in.readValue((String.class.getClassLoader())));
        this.dateOfBirth = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.length = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.weight = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.isActivated = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.activationToken = ((String) in.readValue((String.class.getClassLoader())));
        this.authToken = ((String) in.readValue((String.class.getClassLoader())));
        this.resetPasswordToken = ((String) in.readValue((String.class.getClassLoader())));
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(emailAddress);
        dest.writeValue(lastname);
        dest.writeValue(firstname);
        dest.writeValue(consultantId);
        dest.writeValue(dateOfBirth);
        dest.writeValue(gender);
        dest.writeValue(length);
        dest.writeValue(weight);
        dest.writeValue(password);
        dest.writeValue(isActivated);
        dest.writeValue(activationToken);
        dest.writeValue(authToken);
        dest.writeValue(resetPasswordToken);
    }

    public int describeContents() {
        return 0;
    }

}