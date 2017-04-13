package com.project.app.pilpoil.Interface;

import com.project.app.pilpoil.Metier.AuthTokenPojo;
import com.project.app.pilpoil.Metier.UserPojo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @Headers({ "Content-type: application/x-www-form-urlencoded", "Accept: application/json" })
    @FormUrlEncoded
    @POST("api/authenticate")
    Call<AuthTokenPojo> auth(@Field("username") String username, @Field("password") String password);

    @Headers({ "Accept: application/json"})
    @GET("api/account")
    Call<UserPojo> getCurrentAccount(@Header("x-auth-token") String token);

    @Headers({ "Accept: text/plain", "Content-Type: application/json; charset=UTF-8" })
    @POST("api/register")
    Call<ResponseBody> userRegister(@Body UserPojo userJson);

    @GET("api/users/{login}")
    Call<UserPojo> getUserByLogin(@Path("login") String login, @Header("x-auth-token") String token);

    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    @POST("api/account/change_password")
    Call<ResponseBody> updatePassword(@Body String password, @Header("x-auth-token") String token);

    @Headers({ "Accept: application/json", "Content-Type: application/json; charset=UTF-8" })
    @PUT("api/users")
    Call<UserPojo> updateUser(@Body UserPojo user, @Header("x-auth-token") String token);

    //@Headers({ "Accept: text/plain", "Content-Type: application/json; charset=UTF-8" })
    //@POST("api/account/reset_password/init")
    //Call<ResponseBody> resetPassword(@Body String mail);

    //@Headers({ "Accept: text/plain", "Content-Type: application/json; charset=UTF-8" })
    @POST("api/account/reset_password_android/init/{email}")
    Call<ResponseBody> resetPassword(@Path("email") String email);
}
