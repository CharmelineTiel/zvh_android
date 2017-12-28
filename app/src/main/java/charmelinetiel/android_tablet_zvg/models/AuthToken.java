package charmelinetiel.android_tablet_zvg.models;

/**
 * Created by Tiel on 4-12-2017.
 */

public final class AuthToken {

    private static AuthToken instance = null;
    private String token;

    protected AuthToken() {
        // Exists only to defeat instantiation.
    }
    public static AuthToken getInstance() {

        if(instance == null) {
            instance = new AuthToken();
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
