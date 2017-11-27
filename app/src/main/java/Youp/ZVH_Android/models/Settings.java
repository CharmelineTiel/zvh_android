package Youp.ZVH_Android.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings implements Parcelable
{

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("notificationsEnabled")
    @Expose
    private Boolean notificationsEnabled;
    @SerializedName("largerText")
    @Expose
    private Boolean largerText;
    @SerializedName("autoLogin")
    @Expose
    private Boolean autoLogin;
    @SerializedName("dailyReminders")
    @Expose
    private Boolean dailyReminders;
    @SerializedName("sendMeasurementsToConsultant")
    @Expose
    private Boolean sendMeasurementsToConsultant;
    public final static Parcelable.Creator<Settings> CREATOR = new Creator<Settings>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        public Settings[] newArray(int size) {
            return (new Settings[size]);
        }

    }
            ;

    protected Settings(Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.notificationsEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.largerText = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.autoLogin = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.dailyReminders = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.sendMeasurementsToConsultant = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Settings() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public Boolean getLargerText() {
        return largerText;
    }

    public void setLargerText(Boolean largerText) {
        this.largerText = largerText;
    }

    public Boolean getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public Boolean getDailyReminders() {
        return dailyReminders;
    }

    public void setDailyReminders(Boolean dailyReminders) {
        this.dailyReminders = dailyReminders;
    }

    public Boolean getSendMeasurementsToConsultant() {
        return sendMeasurementsToConsultant;
    }

    public void setSendMeasurementsToConsultant(Boolean sendMeasurementsToConsultant) {
        this.sendMeasurementsToConsultant = sendMeasurementsToConsultant;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(notificationsEnabled);
        dest.writeValue(largerText);
        dest.writeValue(autoLogin);
        dest.writeValue(dailyReminders);
        dest.writeValue(sendMeasurementsToConsultant);
    }

    public int describeContents() {
        return 0;
    }

}