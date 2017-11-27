package Youp.ZVH_Android.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthIssue implements Parcelable
{

    @SerializedName("healthIssueId")
    @Expose
    private String healthIssueId;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Parcelable.Creator<HealthIssue> CREATOR = new Creator<HealthIssue>() {


        @SuppressWarnings({
                "unchecked"
        })
        public HealthIssue createFromParcel(Parcel in) {
            return new HealthIssue(in);
        }

        public HealthIssue[] newArray(int size) {
            return (new HealthIssue[size]);
        }

    }
            ;

    protected HealthIssue(Parcel in) {
        this.healthIssueId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public HealthIssue() {
    }

    public String getHealthIssueId() {
        return healthIssueId;
    }

    public void setHealthIssueId(String healthIssueId) {
        this.healthIssueId = healthIssueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(healthIssueId);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}