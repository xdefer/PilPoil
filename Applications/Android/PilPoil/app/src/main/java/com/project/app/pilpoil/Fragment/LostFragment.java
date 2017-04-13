package com.project.app.pilpoil.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.project.app.pilpoil.Activity.AddPetActivity;
import com.project.app.pilpoil.Activity.ConnexionActivity;
import com.project.app.pilpoil.Activity.FindAndLostActivity;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Interface.AnimalService;
import com.project.app.pilpoil.List.PetListDrawerAdapter;
import com.project.app.pilpoil.List.PetListMainDrawerAdapter;
import com.project.app.pilpoil.Metier.AnimalPojo;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GlobalState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LostFragment extends Fragment {

    private View rootView;

    private LinearLayout layoutConnexion, layoutCreateAnimal, layoutLost, layoutLostNoCo, layoutLostNoPet;
    private ImageView imgViewCreateAlert;
    private ListView myPets;
    private ArrayList<AnimalPojo> userPets = null;
    private PetListMainDrawerAdapter petListAdapter;

    private int[] z = new int[]{1, 0};

    GlobalState gs;
    private UserPojo currentUser = null;

    public LostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lost, container, false);

        gs = (GlobalState) getActivity().getApplication();
        currentUser = gs.getCurrentUser();

        /* Get Element */
        layoutConnexion = (LinearLayout) rootView.findViewById(R.id.layoutConnexionFragmentLost);
        layoutCreateAnimal = (LinearLayout) rootView.findViewById(R.id.layoutCreateAnimalFragmentLost);
        layoutLost = (LinearLayout) rootView.findViewById(R.id.layoutLostFragmentLost);
        layoutLostNoCo = (LinearLayout) rootView.findViewById(R.id.layoutLostNoCoFragmentLost);
        layoutLostNoPet = (LinearLayout) rootView.findViewById(R.id.layoutLostNoPetFragmentLost);

        /* Add Listener on Btn */
        layoutConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ConnexionActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        layoutCreateAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddPetActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        layoutLost.setVisibility(View.GONE);
        layoutLostNoCo.setVisibility(View.GONE);
        layoutLostNoPet.setVisibility(View.GONE);

        checkLayoutToDisplay();

        return rootView;
    }

    private void checkLayoutToDisplay() {
        if (currentUser != null) {
            loadPetList();
        } else {
            layoutLost.setVisibility(View.GONE);
            layoutLostNoPet.setVisibility(View.GONE);
            layoutLostNoCo.setVisibility(View.VISIBLE);
        }
    }

    public void loadPetList(){
        myPets = (ListView) rootView.findViewById(R.id.petList);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnimalService animalService = retrofit.create(AnimalService.class);
        Call<List<AnimalPojo>> call = animalService.getNotLostAnimalByUserId(gs.getCurrentUser().getId(), gs.getAuthTokenPojo().getToken());
        call.enqueue(new Callback<List<AnimalPojo>>() {
            @Override
            public void onResponse(Call<List<AnimalPojo>> call, Response<List<AnimalPojo>> response) {
                userPets = (ArrayList<AnimalPojo>) response.body();
                if (userPets.isEmpty()) {
                    layoutLost.setVisibility(View.GONE);
                    layoutLostNoPet.setVisibility(View.VISIBLE);
                    layoutLostNoCo.setVisibility(View.GONE);
                } else {
                    myPets = (ListView) rootView.findViewById(R.id.petsList);
                    petListAdapter = new PetListMainDrawerAdapter(getActivity(), userPets, gs.getAuthTokenPojo());
                    myPets.setAdapter(petListAdapter);
                    layoutLost.setVisibility(View.VISIBLE);
                    layoutLostNoPet.setVisibility(View.GONE);
                    layoutLostNoCo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<AnimalPojo>> call, Throwable t) {
                //Toast.makeText(getActivity(), "Une erreur est survenue", Toast.LENGTH_LONG).show();
                Snackbar.make(rootView, "Une erreur est survenue", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
