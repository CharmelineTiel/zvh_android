package charmelinetiel.android_tablet_zvg.models;

/**
 * Created by Tiel on 4-12-2017.
 */

public final class authToken {

    private static authToken instance = null;
    private String token;

    protected authToken() {
        // Exists only to defeat instantiation.
    }
    public static authToken getInstance() {

        if(instance == null) {
            instance = new authToken();
        }
        return instance;
    }

    public void setAuthToken(String token)
    {
        this.token = token;
    }

    public String getAuthToken()
    {
        return token;
    }
}
