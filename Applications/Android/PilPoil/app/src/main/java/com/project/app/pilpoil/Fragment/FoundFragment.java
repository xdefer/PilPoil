package com.project.app.pilpoil.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.project.app.pilpoil.Activity.FindAndLostActivity;
import com.project.app.pilpoil.R;


public class FoundFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    private LinearLayout layoutFoundDog, layoutFoundCat, layoutFoundOther;

    private ShowcaseView showcaseView;
    private int counter = 0;

    public FoundFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_found, container, false);

        layoutFoundDog = (LinearLayout) rootView.findViewById(R.id.layoutFoundDog);
        layoutFoundCat = (LinearLayout) rootView.findViewById(R.id.layoutFoundCat);
        layoutFoundOther = (LinearLayout) rootView.findViewById(R.id.layoutFoundOther);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (!prefs.getBoolean("firstTimeFound", false)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTimeFound", true);
            editor.commit();
            showTuto();
        }

        layoutFoundDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FindAndLostActivity.class);
                i.putExtra("animalTypeId", 2);
                i.putExtra("adTypeId", 2);
                startActivity(i);
                getActivity().finish();
            }
        });

        layoutFoundCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FindAndLostActivity.class);
                i.putExtra("animalTypeId", 1);
                i.putExtra("adTypeId", 2);
                startActivity(i);
                getActivity().finish();
            }
        });

        layoutFoundOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FindAndLostActivity.class);
                i.putExtra("animalTypeId", 3);
                i.putExtra("adTypeId", 2);
                startActivity(i);
                getActivity().finish();
            }
        });

        return rootView;
    }

    private void showTuto() {
        showcaseView = new ShowcaseView.Builder(getActivity())
                .setTarget(new PointTarget(360, 410))
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme)
                .setContentTitle(getString(R.string.tuto_title_tab_find))
                .setContentText(getString(R.string.tuto_tab_find))
                .setOnClickListener(this)
                .build();
    }

    private void setAlpha(float alpha, View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            for (View view : views) {
                view.setAlpha(alpha);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (counter) {
            case 0:
                showcaseView.setShowcase(new PointTarget(1080, 410), true);
                showcaseView.setContentTitle(getString(R.string.tuto_title_tab_lost));
                showcaseView.setContentText(getString(R.string.tuto_tab_lost));
                break;

            /*case 1:
                showcaseView.setShowcase(new ViewTarget(layoutFoundOther), true);
                break;

            case 2:
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle("Check it out");
                showcaseView.setContentText("You don't always need a target to showcase");
                showcaseView.setButtonText(getString(R.string.close));
                setAlpha(0.4f, layoutFoundDog, layoutFoundCat, layoutFoundOther);
                break;*/

            case 1:
                showcaseView.hide();
                setAlpha(1.0f, rootView);
                break;
        }
        counter++;
    }

}
