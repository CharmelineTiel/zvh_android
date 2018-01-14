
package charmelinetiel.zorg_voor_het_hart.models; import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

;

public class UserLengthWeight implements Parcelable
{

    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    public final static Parcelable.Creator<UserLengthWeight> CREATOR = new Creator<UserLengthWeight>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserLengthWeight createFromParcel(Parcel in) {
            return new UserLengthWeight(in);
        }

        public UserLengthWeight[] newArray(int size) {
            return (new UserLengthWeight[size]);
        }

    }
            ;

    protected UserLengthWeight(Parcel in) {
        this.length = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.weight = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public UserLengthWeight() {
    }

    /**
     *
     * @param weight
     * @param length
     */
    public UserLengthWeight(Integer length, Integer weight) {
        super();
        this.length = length;
        this.weight = weight;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(length);
        dest.writeValue(weight);
    }

    public int describeContents() {
        return 0;
    }

}