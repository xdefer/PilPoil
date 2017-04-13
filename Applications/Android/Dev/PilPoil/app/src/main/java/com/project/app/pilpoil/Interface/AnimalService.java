package com.project.app.pilpoil.Interface;

import com.project.app.pilpoil.Metier.AnimalPojo;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AnimalService {

    @GET("api/notLostAnimals/user/{userId}")
    Call<List<AnimalPojo>> getNotLostAnimalByUserId(@Path("userId") int userId, @Header("x-auth-token") String token);

    @GET("api/animals/user/{userId}")
    Call<List<AnimalPojo>> getAnimalByUserId(@Path("userId") int userId, @Header("x-auth-token") String token);

    @Headers({ "Accept: application/json","Content-Type: application/json; charset=UTF-8" })
    @POST("api/animals")
    Call<AnimalPojo> createAnimal(@Body AnimalPojo animalJson);

    @DELETE("api/animals/{idAnimal}")
    Call<ResponseBody> deleteAnimal(@Path("idAnimal") int idAnimal, @Header("x-auth-token") String token);

    @Headers({ "Accept: application/json", "Content-Type: application/json; charset=UTF-8" })
    @PUT("api/animals")
    Call<AnimalPojo> updateAnimal(@Body AnimalPojo animalJson, @Header("x-auth-token") String token);

}
