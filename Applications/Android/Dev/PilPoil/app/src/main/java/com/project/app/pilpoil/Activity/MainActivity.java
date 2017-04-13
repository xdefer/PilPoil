package com.project.app.pilpoil.Activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionItemTarget;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.project.app.pilpoil.Constant.Constants;
import com.project.app.pilpoil.Fragment.FoundFragment;
import com.project.app.pilpoil.Fragment.LostFragment;
import com.project.app.pilpoil.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    public TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        try {
            boolean creatingAd = getIntent().getExtras().getBoolean("createAd");
            boolean creatingUser = getIntent().getExtras().getBoolean("createUser");
            boolean userLogon = getIntent().getExtras().getBoolean("userLogon");
            boolean userLogout = getIntent().getExtras().getBoolean("userLogout");
            if(creatingAd)
                //Toast.makeText(getBaseContext(), "Merci de votre signalement", Toast.LENGTH_LONG).show();
                Snackbar.make(MainActivity.this.findViewById(android.R.id.content).getRootView(), "Merci de votre signalement", Snackbar.LENGTH_SHORT).show();
            if(creatingUser)
                //Toast.makeText(getBaseContext(), "Utilisateur créé", Toast.LENGTH_LONG).show();
                Snackbar.make(MainActivity.this.findViewById(android.R.id.content).getRootView(), "Utilisateur crée", Snackbar.LENGTH_SHORT).show();
            if(userLogon) {
                //Toast.makeText(getBaseContext(), "Utilisateur créé", Toast.LENGTH_LONG).show();
                Snackbar.make(MainActivity.this.findViewById(android.R.id.content).getRootView(), "Vous êtes connecté", Snackbar.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1, false);
            }
            if(userLogout)
                //Toast.makeText(getBaseContext(), "Utilisateur créé", Toast.LENGTH_LONG).show();
                Snackbar.make(MainActivity.this.findViewById(android.R.id.content).getRootView(), "Vous êtes déconnecté", Snackbar.LENGTH_SHORT).show();
        } catch(Exception e){
            Log.w(Constants.LOGK_LATERALMENU, "Erreur Menu");
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FoundFragment(), "Trouvé");
        adapter.addFrag(new LostFragment(), "Perdu");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Pour quitter, cliquez une nouvelle fois sur Retour", Toast.LENGTH_SHORT).show();
        Snackbar.make(MainActivity.this.findViewById(android.R.id.content).getRootView(), "Pour quitter, cliquez une nouvelle fois sur Retour", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
