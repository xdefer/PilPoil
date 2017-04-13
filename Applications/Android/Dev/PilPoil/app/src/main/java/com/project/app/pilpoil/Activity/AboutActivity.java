package com.project.app.pilpoil.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.project.app.pilpoil.Metier.AnimalPojo;
import com.project.app.pilpoil.R;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private CircularImageView circularImgViewLogo,btnMail, btnFacebook, btnStore;
    private FloatingActionButton fabBtnMail, fabBtnFacebook, fabBtnStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView imgToolBar = (ImageView) findViewById(R.id.imgToolbar);
        //imgToolBar.setImageResource(R.drawable.chienabout);
        imgToolBar.setBackgroundResource(R.drawable.chienabout);

        //circularImgViewLogo = (CircularImageView) findViewById(R.id.imgViewLogo);

        /*btnMail = (CircularImageView) findViewById(R.id.btnMail);
        btnFacebook = (CircularImageView) findViewById(R.id.btnFacebook);
        btnStore = (CircularImageView) findViewById(R.id.btnStore);*/

        fabBtnMail = (FloatingActionButton) findViewById(R.id.fabBtnMail);
        fabBtnFacebook = (FloatingActionButton) findViewById(R.id.fabBtnFacebook);
        fabBtnStore = (FloatingActionButton) findViewById(R.id.fabBtnStore);

        fabBtnMail.setOnClickListener(this);
        fabBtnFacebook.setOnClickListener(this);
        fabBtnStore.setOnClickListener(this);

        /*btnMail.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnStore.setOnClickListener(this);*/

        //circularImgViewLogo.setImageDrawable(getResources().getDrawable(R.drawable.logo));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabBtnMail:
                //Snackbar.make(v, "Email", Snackbar.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL  , "contact@pilpoil-app.fr");
                try {
                    startActivity(Intent.createChooser(i, getString(R.string.emailSend)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Snackbar.make(v, getString(R.string.emailError), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.fabBtnFacebook:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pilpoilapp/")));
                //Snackbar.make(v, "Facebook", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.fabBtnStore:
                Snackbar.make(v, "Store", Snackbar.LENGTH_SHORT).show();
                //MaterialDialog dialog = popupWait(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i;
        i = new Intent(AboutActivity.this, MainActivity.class);
        startActivity(i);
        this.finish();
    }
}
