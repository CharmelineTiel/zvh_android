package charmelinetiel.android_tablet_zvg.models;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Faq implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private String answer;
    public final static Parcelable.Creator<Faq> CREATOR = new Creator<Faq>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Faq createFromParcel(Parcel in) {
            return new Faq(in);
        }

        public Faq[] newArray(int size) {
            return (new Faq[size]);
        }

    }
            ;

    protected Faq(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.question = ((String) in.readValue((String.class.getClassLoader())));
        this.answer = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Faq() {
    }

    /**
     *
     * @param id
     * @param answer
     * @param question
     */
    public Faq(String id, String question, String answer) {
        super();
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(question);
        dest.writeValue(answer);
    }

    public int describeContents() {
        return 0;
    }

}