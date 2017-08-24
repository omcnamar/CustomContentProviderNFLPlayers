package com.olegsagenadatrytwo.customcontentprovidernflplayers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by omcna on 8/24/2017.
 */

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {

    ArrayList<NFLPlayer> players = new ArrayList<>();

    public void setAnimals(ArrayList<NFLPlayer> players) {
        this.players = players;
    }

    public PlayersAdapter(ArrayList<NFLPlayer> players) {
        this.players = players;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nfl_player_layout_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.etName.setText(players.get(position).getName());
        holder.etAge.setText(players.get(position).getAge());
        holder.etTeam.setText(players.get(position).getTeam());
        holder.etPosition.setText(players.get(position).getPosition());
        holder.etRating.setText(players.get(position).getRatings());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private EditText etName;
        private EditText etAge;
        private EditText etTeam;
        private EditText etPosition;
        private EditText etRating;

        public ViewHolder(View itemView) {
            super(itemView);

            etName = (EditText) itemView.findViewById(R.id.etName);
            etAge = (EditText) itemView.findViewById(R.id.etAge);
            etTeam = (EditText) itemView.findViewById(R.id.etTeam);
            etPosition = (EditText) itemView.findViewById(R.id.etPosition);
            etRating = (EditText) itemView.findViewById(R.id.etRating);

        }
    }
}
