package com.project.app.pilpoil.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.project.app.pilpoil.Metier.UserPojo;
import com.project.app.pilpoil.R;
import com.project.app.pilpoil.Service.GlobalState;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    GlobalState gs;
    private UserPojo currentUser = null;
    protected Menu m;
    protected MenuItem menuMyPets, menuSettings;
    private TextView username;
    private Button logButton;
    private ImageView logIcon;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * Helper method that can be used by child classes to
     * specify that they don't want a {@link Toolbar}
     * @return true
     */
    protected boolean useToolbar() {
        return true;
    }

    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     * @return
     */
    protected boolean useDrawerToggle() {
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getActionBarToolbar();

        setupNavDrawer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());

        gs = (GlobalState) BaseActivity.this.getApplication();
        currentUser = gs.getCurrentUser();
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                // Depending on which version of Android you are on the Toolbar or the ActionBar may be
                // active so the a11y description is set here.
                mActionBarToolbar.setNavigationContentDescription(getResources()
                        .getString(R.string.navdrawer_description_a11y));
                //setSupportActionBar(mActionBarToolbar);

                if (useToolbar()) { setSupportActionBar(mActionBarToolbar);
                } else { mActionBarToolbar.setVisibility(View.GONE); }
            }
        }

        return mActionBarToolbar;
    }

    private void setupNavDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (mDrawerLayout == null) {
            return;
        }

        // use the hamburger menu
        if( useDrawerToggle()) {
            mToggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, mActionBarToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.setDrawerListener(mToggle);
            mToggle.syncState();
        }
        else if(useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat
                    .getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        View header = mNavigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.userName);
        logButton = (Button) header.findViewById(R.id.logButton);
        logIcon = (ImageView) header.findViewById(R.id.logIcon);

        if (currentUser != null) {
            mNavigationView.getMenu().findItem(R.id.nav_my_pets).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);
            if (currentUser.getFirstName() != null){
                username.setText(getString(R.string.bonjour) + " " + currentUser.getFirstName().toString());
            }
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(BaseActivity.this, ProfilActivity.class);
                    startActivity(i);
                }
            });
            logIcon.setImageResource(R.drawable.logout_icon);
            logButton.setText(R.string.deconnexion);
            logButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gs.setCurrentUser(null);
                    gs.setCurrentAuthTokenPojo(null);
                    Intent i = new Intent(BaseActivity.this, MainActivity.class);
                    i.putExtra("userLogout", true);
                    startActivity(i);
                    //Snackbar.make(v, "Vous êtes déconnecté", Snackbar.LENGTH_SHORT).show();
                }
            });
        } else {
            mNavigationView.getMenu().findItem(R.id.nav_my_pets).setVisible(false);
            mNavigationView.getMenu().findItem(R.id.nav_settings).setVisible(false);
            username.setText(R.string.vous_netes_pas_connecte);
            logIcon.setImageResource(R.drawable.login_icon);
            logButton.setText(R.string.connexion);
            logButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(BaseActivity.this, ConnexionActivity.class);
                    startActivity(i);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_main:
                createBackStack(new Intent(this, MainActivity.class));
                break;
            case R.id.nav_my_pets:
                createBackStack(new Intent(this, MyPetsActivity.class));
                break;
            case R.id.nav_settings:
                createBackStack(new Intent(this, ProfilActivity.class));
                break;
            case R.id.nav_about:
                createBackStack(new Intent(this, AboutActivity.class));
                break;
        }

        closeNavDrawer();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);

        return true;
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     * @param intent
     */
    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

}
