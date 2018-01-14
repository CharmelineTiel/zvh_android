package charmelinetiel.zorg_voor_het_hart.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("consultantId")
    @Expose
    private String consultantId;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    @SerializedName("subject")
    @Expose
    private String subject;
    public final static Creator<Message> CREATOR = new Creator<Message>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return (new Message[size]);
        }

    };

    protected Message(Parcel in) {
        this._id = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.consultantId = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.subject = ((String) in.readValue((String.class.getClassLoader())));
        this.dateTime = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Message() {
    }

    public String getDateTime(){

        return dateTime;
    }

    public String getMessageId() {
        return _id;
    }

    public void setMessageId(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_id);
        dest.writeValue(userId);
        dest.writeValue(consultantId);
        dest.writeValue(message);
        dest.writeValue(subject);
        dest.writeValue(dateTime);
    }

    public int describeContents() {
        return 0;
    }

}