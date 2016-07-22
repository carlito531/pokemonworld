package com.imie.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class FightActivity extends AppCompatActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        // set navigation on tabs
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Dresseurs");
        spec.setContent(R.id.Dresseurs);
        spec.setIndicator("Dresseurs");
        tabHost.addTab(spec);

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        spec = tabHost.newTabSpec("Map");
        spec.setContent(R.id.Map);
        spec.setIndicator("Map");
        tabHost.addTab(spec);

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        spec = tabHost.newTabSpec("Pokemons");
        spec.setContent(R.id.Pokemons);
        spec.setIndicator("Pokemons");
        tabHost.addTab(spec);
    }
}
