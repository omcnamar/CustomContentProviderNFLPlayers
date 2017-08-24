package com.olegsagenadatrytwo.customcontentprovidernflplayers;


import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AllPlayersActivity extends AppCompatActivity {

    private RecyclerView rvListPlayers;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemAnimator itemAnimator;
    private PlayersAdapter adapter;
    private ArrayList<NFLPlayer> nflPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_players);

        //List of Players for the recycler view
        nflPlayers = new ArrayList<>();

        rvListPlayers = (RecyclerView) findViewById(R.id.rvListPlayers);

        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        rvListPlayers.setLayoutManager(layoutManager);
        rvListPlayers.setItemAnimator(itemAnimator);

        getPlayers();
    }

    private void getPlayers() {
        // Retrieve student records
        String URL = "content://com.olegsagenadatrytwo.customcontentprovidernflplayers.NFLPlayerProvider";

        Uri players = Uri.parse(URL);
        Cursor c = managedQuery(players, null, null, null, "name");

        if (c.moveToFirst()) {
            do{
                NFLPlayer player = new NFLPlayer();
                player.setName(c.getString(c.getColumnIndex( NFLPlayerProvider.NAME)));
                player.setAge(c.getString(c.getColumnIndex( NFLPlayerProvider.AGE)));
                player.setPosition(c.getString(c.getColumnIndex( NFLPlayerProvider.POSITION)));
                player.setTeam(c.getString(c.getColumnIndex( NFLPlayerProvider.TEAM)));
                player.setRatings(c.getString(c.getColumnIndex( NFLPlayerProvider.RATING)));
                nflPlayers.add(player);

            } while (c.moveToNext());
        }
        adapter = new PlayersAdapter(nflPlayers);
        rvListPlayers.setAdapter(adapter);

    }
}
