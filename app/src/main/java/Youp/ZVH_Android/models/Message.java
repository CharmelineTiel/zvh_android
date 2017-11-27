package Youp.ZVH_Android.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Parcelable
{

    @SerializedName("messageId")
    @Expose
    private String messageId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("consultantId")
    @Expose
    private String consultantId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subject")
    @Expose
    private String subject;
    public final static Parcelable.Creator<Message> CREATOR = new Creator<Message>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return (new Message[size]);
        }

    }
            ;

    protected Message(Parcel in) {
        this.messageId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.consultantId = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.subject = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Message() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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
        dest.writeValue(messageId);
        dest.writeValue(userId);
        dest.writeValue(consultantId);
        dest.writeValue(message);
        dest.writeValue(subject);
    }

    public int describeContents() {
        return 0;
    }

}