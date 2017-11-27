package Youp.ZVH_Android.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Consultant implements Parcelable
{
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    public final static Parcelable.Creator<Consultant> CREATOR = new Creator<Consultant>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Consultant createFromParcel(Parcel in) {
            return new Consultant(in);
        }

        public Consultant[] newArray(int size) {
            return (new Consultant[size]);
        }

    }
            ;

    protected Consultant(Parcel in) {
        this._id = ((String) in.readValue((String.class.getClassLoader())));
        this.firstname = ((String) in.readValue((String.class.getClassLoader())));
        this.lastname = ((String) in.readValue((String.class.getClassLoader())));
        this.emailAddress = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Consultant() {
    }

    public String getConsultantId() {
        return _id;
    }

    public void setConsultantId(String _id) {
        this._id = _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_id);
        dest.writeValue(firstname);
        dest.writeValue(lastname);
        dest.writeValue(emailAddress);
    }

    public int describeContents() {
        return 0;
    }

}