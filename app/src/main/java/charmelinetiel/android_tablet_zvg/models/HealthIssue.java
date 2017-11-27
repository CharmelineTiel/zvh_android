package charmelinetiel.android_tablet_zvg.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthIssue implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<HealthIssue> CREATOR = new Creator<HealthIssue>() {


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
        this._id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public HealthIssue() {
    }

    public String getHealthIssueId() {
        return _id;
    }

    public void setHealthIssueId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_id);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}