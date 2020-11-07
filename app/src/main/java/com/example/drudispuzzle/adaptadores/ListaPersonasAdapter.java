package com.example.drudispuzzle.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.drudispuzzle.R;
import com.example.drudispuzzle.entidades.Usuario;

import java.util.ArrayList;


public class ListaPersonasAdapter extends RecyclerView.Adapter<ListaPersonasAdapter.PersonasViewHolder> {

    ArrayList<Usuario> playerList;

    public ListaPersonasAdapter(ArrayList<Usuario> playerList) {
        this.playerList = playerList;
    }

    @Override
    public PersonasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._list_item_players,null,false);
        return new PersonasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonasViewHolder holder, int position) {
        holder.name.setText(playerList.get(position).getP());
        holder.time.setText(playerList.get(position).getT());

    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class PersonasViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView name;

        public PersonasViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textDocumento);
            time = (TextView) itemView.findViewById(R.id.textTiempo);


        }
    }
}
