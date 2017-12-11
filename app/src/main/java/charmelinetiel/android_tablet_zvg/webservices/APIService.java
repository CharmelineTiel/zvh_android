package charmelinetiel.android_tablet_zvg.webservices;

import java.util.List;

import charmelinetiel.android_tablet_zvg.models.HealthIssue;
import charmelinetiel.android_tablet_zvg.models.Measurement;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import charmelinetiel.android_tablet_zvg.models.Consultant;
import charmelinetiel.android_tablet_zvg.models.User;
import retrofit2.http.Query;


public interface APIService {

    @GET("Consultants")
    Call<List<Consultant>> getAllConsultants();

    @POST("/Users/register")
    Call<User> register(@Body User user);

    @GET("HealthIssues")
    Call<List<HealthIssue>> getAllHealthIssues();

    @POST("Measurements")
    Call<Measurement> postMeasurement(@Body Measurement measurement, @Query("userId") String userId);

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
