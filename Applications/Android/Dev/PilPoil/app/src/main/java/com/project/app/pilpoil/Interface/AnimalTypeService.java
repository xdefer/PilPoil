package com.project.app.pilpoil.Interface;

import com.project.app.pilpoil.Metier.AnimalTypePojo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AnimalTypeService {

    @GET("api/animalTypesWithSpecificOrderBy")
    Call<List<AnimalTypePojo>> getAllAnimalType(@Header("x-auth-token") String token);

}
