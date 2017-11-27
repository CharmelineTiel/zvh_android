package Youp.ZVH_Android.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measurement implements Parcelable
{

    @SerializedName("measurementId")
    @Expose
    private String measurementId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("bloodPressureUpper")
    @Expose
    private Integer bloodPressureUpper;
    @SerializedName("bloodPressureLower")
    @Expose
    private Integer bloodPressureLower;
    @SerializedName("healthIssueIds")
    @Expose
    private List<String> healthIssueIds = null;
    @SerializedName("healthIssueOther")
    @Expose
    private String healthIssueOther;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("measurementDateTime")
    @Expose
    private String measurementDateTime;
    public final static Parcelable.Creator<Measurement> CREATOR = new Creator<Measurement>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Measurement createFromParcel(Parcel in) {
            return new Measurement(in);
        }

        public Measurement[] newArray(int size) {
            return (new Measurement[size]);
        }

    }
            ;

    protected Measurement(Parcel in) {
        this.measurementId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.bloodPressureUpper = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bloodPressureLower = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.healthIssueIds, (java.lang.String.class.getClassLoader()));
        this.healthIssueOther = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.measurementDateTime = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Measurement() {
    }

    public String getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(String measurementId) {
        this.measurementId = measurementId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBloodPressureUpper() {
        return bloodPressureUpper;
    }

    public void setBloodPressureUpper(Integer bloodPressureUpper) {
        this.bloodPressureUpper = bloodPressureUpper;
    }

    public Integer getBloodPressureLower() {
        return bloodPressureLower;
    }

    public void setBloodPressureLower(Integer bloodPressureLower) {
        this.bloodPressureLower = bloodPressureLower;
    }

    public List<String> getHealthIssueIds() {
        return healthIssueIds;
    }

    public void setHealthIssueIds(List<String> healthIssueIds) {
        this.healthIssueIds = healthIssueIds;
    }

    public String getHealthIssueOther() {
        return healthIssueOther;
    }

    public void setHealthIssueOther(String healthIssueOther) {
        this.healthIssueOther = healthIssueOther;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(String measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(measurementId);
        dest.writeValue(userId);
        dest.writeValue(bloodPressureUpper);
        dest.writeValue(bloodPressureLower);
        dest.writeList(healthIssueIds);
        dest.writeValue(healthIssueOther);
        dest.writeValue(comment);
        dest.writeValue(measurementDateTime);
    }

    public int describeContents() {
        return 0;
    }

}