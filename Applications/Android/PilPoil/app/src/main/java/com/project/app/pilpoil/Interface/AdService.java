package com.project.app.pilpoil.Interface;

import com.project.app.pilpoil.Metier.AdPojo;
import com.project.app.pilpoil.Metier.UserPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdService {

    @Headers({ "Accept: application/json","Content-Type: application/json; charset=UTF-8" })
    @POST("api/ads")
    Call<AdPojo> createAd(@Body AdPojo adJson);

    @GET("api/ads/animalType/{animalTypeId}")
    Call<List<AdPojo>> getAdsByAnimalTypeId(@Path("animalTypeId") int animalTypeId, @Header("x-auth-token") String token);

    @GET("api/ads/animal/{animalId}")
    Call<List<AdPojo>> getAdsByAnimalId(@Path("animalId") int animalId, @Header("x-auth-token") String token);

    @Headers({ "Accept: application/json", "Content-Type: application/json; charset=UTF-8" })
    @PUT("api/ads/archive/{adId}")
    Call<ResponseBody> adToArchive(@Path("adId") int adId, @Header("x-auth-token") String token);

    @POST("api/ads/report/{id}/{reason}")
    Call<ResponseBody> reportAd(@Path("id") int idAd, @Path("reason") String reason, @Header("x-auth-token") String token);
}
