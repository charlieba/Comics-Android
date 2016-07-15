package com.comics.gio.comics;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

/**
 * Created by giova on 7/07/2016.
 */
public class adapterCardViewCharacter extends RecyclerView.Adapter<adapterCardViewCharacter.ViewHolder> {
    public static ArrayList<itemCharacter> items;
    private LayoutInflater l_Inflater;
    public static Context context;

    public adapterCardViewCharacter(Context context, ArrayList<itemCharacter> results) {
        items = results;
        l_Inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvName;
        ImageView ivCharacter;

        ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.placeCard);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            ivCharacter = (ImageView)itemView.findViewById(R.id.ivthumb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("presiono "+items.get(getAdapterPosition()).getName());
                }
            });
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public adapterCardViewCharacter(ArrayList<itemCharacter> myDataset) {
        items = myDataset;
    }

    @Override
    public adapterCardViewCharacter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_character, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(adapterCardViewCharacter.ViewHolder holder, int position) {
        holder.tvName.setText(items.get(position).getName());
        Picasso.with(this.context).load(items.get(position).getThumbnail()).into(holder.ivCharacter);

        //Here it is simply write onItemClick listener here
      /*  holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}