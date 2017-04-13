package com.project.app.pilpoil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.AnimalService;
import com.project.app.pilpoil.List.PetListDrawerAdapter;
import com.project.app.pilpoil.Metier.AnimalPojo;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GlobalState;
import com.project.app.pilpoil.Service.OnSwipeTouchListener;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPetsActivity extends BaseActivity {

    private ListView myPets;
    private Button addAnimalButton;
    private Button addAnimalButtonFirst;
    private TextView noAnimalFoundText;
    private ArrayList<AnimalPojo> userPets = null;
    private PetListDrawerAdapter petListAdapter;
    private FloatingActionButton floatingButtonAddPet;
    private ProgressBar progressBar;
    private RelativeLayout layoutContent, layoutLoader;
    private SwipeLayout swipeLayout;

    GlobalState gs;
    private UserPojo currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        this.gs = (GlobalState) getApplication();
        this.currentUser = gs.getCurrentUser();

        this.myPets = (ListView) findViewById(R.id.petList);
        this.addAnimalButtonFirst = (Button) findViewById(R.id.addAnimalButtonFirst);
        this.noAnimalFoundText = (TextView) findViewById(R.id.noAnimalFoundText);

        floatingButtonAddPet = (FloatingActionButton) findViewById(R.id.floatingButtonAddPet);
        floatingButtonAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPetsActivity.this, AddPetActivity.class);
                MyPetsActivity.this.startActivity(i);
                finish();
            }
        });

        /*this.swipeLayout =  (SwipeLayout) findViewById(R.id.petItem);
        this.swipeLayout.setOnTouchListener(new OnSwipeTouchListener(MyPetsActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(MyPetsActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(MyPetsActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(MyPetsActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(MyPetsActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });*/

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);
        layoutLoader = (RelativeLayout) findViewById(R.id.layoutLoader);

        layoutContent.setVisibility(View.GONE);

        this.addAnimalButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyPetsActivity.this, AddPetActivity.class);
                MyPetsActivity.this.startActivity(i);
                finish();
            }
        });
        loadPetList();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void loadPetList(){
        this.myPets = (ListView) findViewById(R.id.petList);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnimalService animalService = retrofit.create(AnimalService.class);
        Call<List<AnimalPojo>> call = animalService.getAnimalByUserId(gs.getCurrentUser().getId(), gs.getAuthTokenPojo().getToken());
        call.enqueue(new Callback<List<AnimalPojo>>() {
            @Override
            public void onResponse(Call<List<AnimalPojo>> call, Response<List<AnimalPojo>> response) {
                userPets = (ArrayList<AnimalPojo>) response.body();
                if (userPets.isEmpty()) {
                    myPets.setVisibility(View.GONE);
                    floatingButtonAddPet.setVisibility(View.GONE);
                } else {
                    noAnimalFoundText.setVisibility(View.GONE);
                    addAnimalButtonFirst.setVisibility(View.GONE);
                    petListAdapter = new PetListDrawerAdapter(MyPetsActivity.this, userPets, gs.getAuthTokenPojo());
                    myPets.setAdapter(petListAdapter);
                }
                layoutLoader.setVisibility(View.GONE);
                layoutContent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<AnimalPojo>> call, Throwable t) {
                //Toast.makeText(MyPetsActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG).show();
                Snackbar.make(MyPetsActivity.this.findViewById(android.R.id.content).getRootView(), "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
                layoutLoader.setVisibility(View.GONE);
                layoutContent.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyPetsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
