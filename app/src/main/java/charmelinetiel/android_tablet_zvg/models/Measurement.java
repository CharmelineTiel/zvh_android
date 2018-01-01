package charmelinetiel.android_tablet_zvg.models; ;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measurement implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("healthIssuesIds")
    @Expose
    private List<String> healthIssuesIds = null;
    @SerializedName("bloodPressureLower")
    @Expose
    private Integer bloodPressureLower;
    @SerializedName("bloodPressureUpper")
    @Expose
    private Integer bloodPressureUpper;
    @SerializedName("healthIssueOther")
    @Expose
    private String healthIssueOther;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("measurementDateTime")
    @Expose
    private String measurementDateTime;
    @SerializedName("measurementDateFormatted")
    @Expose
    private String measurementDateFormatted;
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
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.healthIssuesIds, (java.lang.String.class.getClassLoader()));
        this.bloodPressureLower = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bloodPressureUpper = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.healthIssueOther = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.measurementDateTime = ((String) in.readValue((String.class.getClassLoader())));
        this.measurementDateFormatted = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Measurement() {
    }

    /**
     *
     * @param bloodPressureUpper
     * @param id
     * @param healthIssuesIds
     * @param measurementDateFormatted
     * @param measurementDateTime
     * @param userId
     * @param bloodPressureLower
     * @param healthIssueOther
     */
    public Measurement(String id, List<String> healthIssuesIds, Integer bloodPressureLower, Integer bloodPressureUpper, String healthIssueOther, String userId, String measurementDateTime, String measurementDateFormatted) {
        super();
        this.id = id;
        this.healthIssuesIds = healthIssuesIds;
        this.bloodPressureLower = bloodPressureLower;
        this.bloodPressureUpper = bloodPressureUpper;
        this.healthIssueOther = healthIssueOther;
        this.userId = userId;
        this.measurementDateTime = measurementDateTime;
        this.measurementDateFormatted = measurementDateFormatted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHealthIssuesIds() {
        return healthIssuesIds;
    }

    public void setHealthIssuesIds(List<String> healthIssuesIds) {
        this.healthIssuesIds = healthIssuesIds;
    }

    public Integer getBloodPressureLower() {
        return bloodPressureLower;
    }

    public void setBloodPressureLower(Integer bloodPressureLower) {
        this.bloodPressureLower = bloodPressureLower;
    }

    public Integer getBloodPressureUpper() {
        return bloodPressureUpper;
    }

    public void setBloodPressureUpper(Integer bloodPressureUpper) {
        this.bloodPressureUpper = bloodPressureUpper;
    }

    public String getHealthIssueOther() {
        return healthIssueOther;
    }

    public void setHealthIssueOther(String healthIssueOther) {
        this.healthIssueOther = healthIssueOther;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(String measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }

    public String getMeasurementDateFormatted() {
        return measurementDateFormatted;
    }

    public void setMeasurementDateFormatted(String measurementDateFormatted) {
        this.measurementDateFormatted = measurementDateFormatted;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(healthIssuesIds);
        dest.writeValue(bloodPressureLower);
        dest.writeValue(bloodPressureUpper);
        dest.writeValue(healthIssueOther);
        dest.writeValue(userId);
        dest.writeValue(measurementDateTime);
        dest.writeValue(measurementDateFormatted);
    }

    public int describeContents() {
        return 0;
    }

}