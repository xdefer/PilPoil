package com.project.app.pilpoil.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.project.app.pilpoil.Fragment.MapFragment;
import com.project.app.pilpoil.Metier.Adress;
import com.project.app.pilpoil.R;

public class MapsActivity extends BaseActivity implements View.OnClickListener {

    AppCompatImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        search = (AppCompatImageView) findViewById(R.id.search);
        search.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.onSearch();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate: {
                MapFragment frag= (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
                Adress adressToSend = frag.getAdress();
                if(null != adressToSend.getCity()){
                    Intent intent=new Intent();
                    intent.putExtra("adress", adressToSend);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
