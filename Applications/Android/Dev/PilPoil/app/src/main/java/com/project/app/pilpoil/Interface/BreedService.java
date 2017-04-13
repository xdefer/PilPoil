package com.project.app.pilpoil.Interface;

import com.project.app.pilpoil.Metier.BreedPojo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BreedService {

    @GET("api/breeds/animalType/{animalTypeId}")
    Call<List<BreedPojo>> getBreedsByAnimalTypeId(@Path("animalTypeId") int animalTypeId);

}
