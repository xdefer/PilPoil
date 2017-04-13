package com.project.app.pilpoil.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.app.pilpoil.Activity.FindAndLostActivity;
import com.project.app.pilpoil.Metier.AnimalPojo;
import com.project.app.pilpoil.Metier.AuthTokenPojo;
import com.project.app.pilpoil.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PetListMainDrawerAdapter extends BaseAdapter {

    Context mContext;
    AuthTokenPojo token;
    ArrayList<AnimalPojo> petList;

    public PetListMainDrawerAdapter(Context context, ArrayList<AnimalPojo> petListItem, AuthTokenPojo token) {
        this.mContext = context;
        this.petList = petListItem;
        this.token = token;
    }

    @Override
    public int getCount() {
        return petList.size();
    }

    @Override
    public Object getItem(int position) {
        return petList.get(position);
    }

    public AnimalPojo getNavItem(int position)
    {
        return petList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_my_pets_main, null);
        }
        else { view = convertView; }

        final AnimalPojo currentPet = this.petList.get(position);

        ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView shortDesc  = (TextView) view.findViewById(R.id.shortDesc);
        ImageView imgViewCreateAlert = (ImageView) view.findViewById(R.id.imgViewCreateAlert);

        avatar.setColorFilter(Color.parseColor("#660000"));

        imgViewCreateAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declarePetLost(currentPet);
            }
        });

        if (null != currentPet.getName()) {
            name.setText(currentPet.getName());
        }

        if (currentPet.getAnimalType() != null) {
            if (currentPet.getPhoto() == null){
                if (currentPet.getAnimalType().getLabel().equals("Chat")) {
                    avatar.setImageResource(R.drawable.cat_icon);
                } else if (currentPet.getAnimalType().getLabel().equals("Chien")){
                    avatar.setImageResource(R.drawable.dog_icon);
                } else {
                    avatar.setImageResource(R.drawable.other_icon);
                }
                //avatar.setBackgroundResource(R.color.black);
            }else{
                Picasso.with(this.mContext).load(currentPet.getPhoto()).into(avatar);
            }
        }

        ArrayList<String> shortDescText = new ArrayList<String>();
        if (currentPet.getAnimalType() != null)
            shortDescText.add(currentPet.getAnimalType().getLabel());

        if (currentPet.getAge() != null && !currentPet.getAge().equals(""))
            shortDescText.add(currentPet.getAge());

        if (null != currentPet.getChip() && !currentPet.getChip().equals(""))
            shortDescText.add(currentPet.getChip());

        shortDesc.setText(android.text.TextUtils.join(" - ", shortDescText));

        return view;
    }

    private void declarePetLost(AnimalPojo animal) {
        Intent i = new Intent(this.mContext, FindAndLostActivity.class);
        i.putExtra("adTypeId", 1);
        i.putExtra("animal",  animal);
        this.mContext.startActivity(i);
        ((Activity)this.mContext).finish();
    }

}