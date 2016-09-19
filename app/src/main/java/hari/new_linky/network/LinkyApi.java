package hari.new_linky.network;

import java.util.List;

import hari.new_linky.model.CreateLinkInput;
import hari.new_linky.model.Link;
import hari.new_linky.model.SignUpInput;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Hari Prasad on 9/16/16.
 */
public interface LinkyApi {
    @FormUrlEncoded
    @POST("/signin.json")
    Call<String> signin(@Field("email") String email, @Field("password") String password);

    @POST("/users.json")
    Call<String> signup(@Body SignUpInput signUpInput);

    @GET("/links.json")
    Call<List<Link>> links();

    @POST("/links.json")
    Call<String> createLink(@Body CreateLinkInput createLinkInput);

}
