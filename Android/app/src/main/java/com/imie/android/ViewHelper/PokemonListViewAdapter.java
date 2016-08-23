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

        PokemonViewHolder viewHolder = (PokemonViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PokemonViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.ivPokemonListAvatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvPokemonListName);
            viewHolder.type = (TextView) convertView.findViewById(R.id.tvPokemonListType);
            viewHolder.level = (TextView) convertView.findViewById(R.id.tvPokemonListLevel);
            viewHolder.experience = (TextView) convertView.findViewById(R.id.tvPokemonListExperience);
            viewHolder.hp = (TextView) convertView.findViewById(R.id.tvPokemonListHp);
            //viewHolder.id = (TextView) convertView.findViewById(R.id.tvPokemonListId);

            convertView.setTag(viewHolder);
        }

        // find item position in list of pokemon
        Pokemon pokemon = getItem(position);

        // fill the view
        viewHolder.avatar.setImageDrawable(new ColorDrawable(Color.BLACK));
        viewHolder.name.setText("Nom: " + pokemon.getName());
        viewHolder.type.setText("Type: " + pokemon.getPokemonType().getName());
        viewHolder.level.setText("Niveau: " + pokemon.getLevel().toString());
        viewHolder.experience.setText("Experience: " + pokemon.getExperience().toString());
        viewHolder.hp.setText("PV: " + pokemon.getHp().toString());
        //viewHolder.id.setText(pokemon.getId().toString());

        return convertView;
    }

    private class PokemonViewHolder {
        public ImageView avatar;
        public TextView name;
        public TextView type;
        public TextView level;
        public TextView experience;
        public TextView hp;
        //public TextView id;
    }
}
