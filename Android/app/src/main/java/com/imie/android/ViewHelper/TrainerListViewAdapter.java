package com.imie.android.ViewHelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imie.android.R;
import com.imie.android.model.Trainer;

import java.util.List;

/**
 * Created by charly on 31/07/2016.
 */
public class TrainerListViewAdapter extends ArrayAdapter<Trainer> {

    public TrainerListViewAdapter(Context context, List<Trainer> trainers) {
        super(context, 0, trainers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trainer_row_list,parent, false);
        }

        TrainerViewHolder viewHolder = (TrainerViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TrainerViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.ivTrainerListAvatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvTrainerListName);
            viewHolder.badges = (TextView) convertView.findViewById(R.id.tvTrainerListBadges);

            convertView.setTag(viewHolder);
        }

        // find item position in list of pokemon
        Trainer trainer = getItem(position);

        // fill the view
        viewHolder.avatar.setImageDrawable(new ColorDrawable(Color.BLACK));
        viewHolder.name.setText("Nom: " + trainer.getName());
        if (trainer.getBadges() != null) {
            for (int i = 0; i < trainer.getBadges().size(); i++) {
                viewHolder.badges.setText("Badges: " + trainer.getBadges().get(i).getName());
            }
        } else {
            viewHolder.badges.setText("Aucun badge");
        }

        return convertView;
    }

    private class TrainerViewHolder{
        public ImageView avatar;
        public TextView name;
        public TextView badges;
    }
}
