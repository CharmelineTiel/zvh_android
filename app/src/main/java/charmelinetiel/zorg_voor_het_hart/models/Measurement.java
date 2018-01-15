
package charmelinetiel.zorg_voor_het_hart.models; import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

;

public class Measurement implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("healthIssueIds")
    @Expose
    private List<String> healthIssueIds = null;
    @SerializedName("bloodPressureLower")
    @Expose
    private Integer bloodPressureLower;
    @SerializedName("bloodPressureUpper")
    @Expose
    private Integer bloodPressureUpper;
    @SerializedName("healthIssueOther")
    @Expose
    private String healthIssueOther;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("feedback")
    @Expose
    private int result;
    @SerializedName("result")
    @Expose
    private String feedback;
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
        in.readList(this.healthIssueIds, (java.lang.String.class.getClassLoader()));
        this.bloodPressureLower = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.bloodPressureUpper = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.healthIssueOther = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.feedback = ((String) in.readValue((String.class.getClassLoader())));
        this.result = ((Integer) in.readValue((Integer.class.getClassLoader())));
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
     * @param measurementDateFormatted
     * @param healthIssueIds
     * @param measurementDateTime
     * @param userId
     * @param bloodPressureLower
     * @param healthIssueOther
     * @param comment
     */
    public Measurement(String id, List<String> healthIssueIds, Integer bloodPressureLower, Integer bloodPressureUpper, String healthIssueOther, String comment, String userId, Integer result, String feedback, String measurementDateTime, String measurementDateFormatted) {
        super();
        this.id = id;
        this.healthIssueIds = healthIssueIds;
        this.bloodPressureLower = bloodPressureLower;
        this.bloodPressureUpper = bloodPressureUpper;
        this.healthIssueOther = healthIssueOther;
        this.comment = comment;
        this.userId = userId;
        this.feedback = feedback;
        this.result = result;
        this.measurementDateTime = measurementDateTime;
        this.measurementDateFormatted = measurementDateFormatted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHealthIssueIds() {
        return healthIssueIds;
    }

    public void setHealthIssueIds(List<String> healthIssueIds) {
        this.healthIssueIds = healthIssueIds;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getResult() { return result; }

    public void setResult(Integer result){ this.result = result; }

    public String getMeasurementDateFormatted() {
        return measurementDateFormatted;
    }

    public void setMeasurementDateFormatted(String measurementDateFormatted) {
        this.measurementDateFormatted = measurementDateFormatted;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(healthIssueIds);
        dest.writeValue(bloodPressureLower);
        dest.writeValue(bloodPressureUpper);
        dest.writeValue(healthIssueOther);
        dest.writeValue(comment);
        dest.writeValue(userId);
        dest.writeValue(feedback);
        dest.writeValue(result);
        dest.writeValue(measurementDateTime);
        dest.writeValue(measurementDateFormatted);
    }

    public int describeContents() {
        return 0;
    }

}