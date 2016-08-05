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
import com.imie.android.model.Pokemon;

import java.util.List;

/**
 * Created by charly on 31/07/2016.
 */
public class PokemonListViewAdapter extends ArrayAdapter<Pokemon> {

    public PokemonListViewAdapter(Context context, List<Pokemon> pokemons) {
        super(context, 0, pokemons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokemon_row_list,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.ivPokemonListAvatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvPokemonListName);
            viewHolder.type = (TextView) convertView.findViewById(R.id.tvPokemonListType);
            viewHolder.level = (TextView) convertView.findViewById(R.id.tvPokemonListLevel);
            viewHolder.experience = (TextView) convertView.findViewById(R.id.tvPokemonListExperience);
            viewHolder.hp = (TextView) convertView.findViewById(R.id.tvPokemonListHp);
            viewHolder.attack1 = (TextView) convertView.findViewById(R.id.tvPokemonListAttack1);
            viewHolder.attack2 = (TextView) convertView.findViewById(R.id.tvPokemonListAttack2);
            viewHolder.attack3 = (TextView) convertView.findViewById(R.id.tvPokemonListAttack3);
            viewHolder.attack4 = (TextView) convertView.findViewById(R.id.tvPokemonListAttack4);

            convertView.setTag(viewHolder);
        }

        // find item position in list of pokemon
        Pokemon pokemon = getItem(position);

        // fill the view
        viewHolder.avatar.setImageDrawable(new ColorDrawable(Color.BLACK));
        viewHolder.name.setText(pokemon.getName());
        viewHolder.attack1.setText(pokemon.getAttack1().getName());
        viewHolder.attack2.setText(pokemon.getAttack2().getName());
        viewHolder.attack3.setText(pokemon.getAttack3().getName());
        viewHolder.attack4.setText(pokemon.getAttack4().getName());
        viewHolder.type.setText(pokemon.getPokemonType().getName());
        viewHolder.level.setText(pokemon.getLevel().toString());
        viewHolder.experience.setText(pokemon.getExperience().toString());
        viewHolder.hp.setText(pokemon.getHp().toString());

        return convertView;
    }

    private class TweetViewHolder{
        public ImageView avatar;
        public TextView name;
        public TextView type;
        public TextView level;
        public TextView experience;
        public TextView hp;
        public TextView attack1;
        public TextView attack2;
        public TextView attack3;
        public TextView attack4;
    }
}
