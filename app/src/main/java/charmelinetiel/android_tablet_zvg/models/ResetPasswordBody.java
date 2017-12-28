package charmelinetiel.android_tablet_zvg.models; ;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordBody implements Parcelable
{

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confirmedPassword")
    @Expose
    private String confirmedPassword;
    @SerializedName("token")
    @Expose
    private String token;
    public final static Parcelable.Creator<ResetPasswordBody> CREATOR = new Creator<ResetPasswordBody>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ResetPasswordBody createFromParcel(Parcel in) {
            return new ResetPasswordBody(in);
        }

        public ResetPasswordBody[] newArray(int size) {
            return (new ResetPasswordBody[size]);
        }

    }
            ;

    protected ResetPasswordBody(Parcel in) {
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.confirmedPassword = ((String) in.readValue((String.class.getClassLoader())));
        this.token = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ResetPasswordBody() {
    }

    /**
     *
     * @param resetPassword
     * @param token
     * @param password
     */
    public ResetPasswordBody(String password, String resetPassword, String token) {
        super();
        this.password = password;
        this.confirmedPassword = resetPassword;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String resetPassword) {
        this.confirmedPassword = resetPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(password);
        dest.writeValue(confirmedPassword);
        dest.writeValue(token);
    }

    public int describeContents() {
        return 0;
    }

}