package charmelinetiel.android_tablet_zvg.webservices;

import java.util.List;

import charmelinetiel.android_tablet_zvg.models.HealthIssue;
import charmelinetiel.android_tablet_zvg.models.Measurement;
import charmelinetiel.android_tablet_zvg.models.UserLengthWeight;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import charmelinetiel.android_tablet_zvg.models.Consultant;
import charmelinetiel.android_tablet_zvg.models.User;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface APIService {

    @GET("Consultants")
    Call<List<Consultant>> getAllConsultants();

    @POST("/Users/register")
    Call<User> register(@Body User user);

    @POST("/Users/login")
    Call<ResponseBody> login(@Body User user);

    @PUT("/Users")
    Call<User> updateUserLenghtWeight(@Body UserLengthWeight lengthWeight, @Header("x-authtoken") String token);

    @GET("HealthIssues")
    Call<List<HealthIssue>> getAllHealthIssues(@Header("x-authtoken") String token);

    @POST("Measurements")
    Call<Measurement> postMeasurement(@Body Measurement measurement, @Header("x-authtoken") String token);

    @POST("/Users/forgotPassword")
    Call<ResponseBody> requestResetPasswordEmail(@Query("emailAddress") String emailAddress);

//    @GET("Articles/{nextId}")
//    Call<RootObject> getMoreArticles(@Header("x-authtoken") String api_token, @Path("nextId") int nextId, @Query("count") int count);
//
//    @GET("Articles/?feeds={feedId}")
//    Call<RootObject> getArticlesByCategory(@Header("x-authtoken") String api_token, @Query("feedId") int count);
//
//    @GET("Articles/Liked")
//    Call<RootObject> getLikedArticles(@Header("x-authtoken") String api_token);
//
//    @DELETE("Articles/{Id}/like")
//    Call<Void> UnlikeArticle(@Header("x-authtoken") String api_token, @Path("Id") String id);
//
//    @PUT("Articles/{Id}/like")
//    Call<Void> likeArticle(@Header("x-authtoken") String api_token, @Path("Id") String id);
//
//    @POST("Users/register")
//    Call <ServerResponse> register (@Body User user);
//
//    @POST("Users/login")
//    Call<AuthTokenResponse> getAuthToken(@Body User user);
}
