package charmelinetiel.zorg_voor_het_hart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Consultant implements Parcelable
{

    private static List<Consultant> consultants;

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
    public final static Creator<Consultant> CREATOR = new Creator<Consultant>() {


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


    public String toString() {
        return getFirstname() + " " + getLastname();
    }

    public static void setConsultants(List<Consultant> newConsultants){
        consultants = newConsultants;
    }

    public static List<Consultant> getConsultants(){
        if(consultants == null){
            return new ArrayList<Consultant>();
        }
        return consultants;
    }
}