package template.solainteractive.com.androidsolatemplate.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.model.Terminal;

/**
 * Created by Sola on 07/03/2018.
 */

public class TerminalAdapter extends RecyclerView.Adapter<TerminalAdapter.ViewHolder>{
    private List<Terminal> listItems;
    private Context context;

    public TerminalAdapter(List<Terminal> listItems, Context context)
    {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terminal_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewName.setText(listItems.get(position).getTerminalName());

        if(listItems.get(position).getTerminalTypeId()==1) {
            holder.textViewTypeId.setText("Big");
        } else if(listItems.get(position).getTerminalTypeId()==2) {
            holder.textViewTypeId.setText("Small");
        } else if(listItems.get(position).getTerminalTypeId()==3) {
            holder.textViewTypeId.setText("Medium");
        }

        holder.textViewOpen.setText(listItems.get(position).getTerminalOpenTime()+" - ");
        holder.textViewClose.setText(listItems.get(position).getTerminalClosedTime());
        holder.textViewLat.setText(String.valueOf(listItems.get(position).getTerminalLatitude()));
        holder.textViewLong.setText(String.valueOf(listItems.get(position).getTerminalLongitude()));

            Glide.with(context)
                    .load(listItems.get(position).getAvatarPicture())
                    .fitCenter()
                    .dontAnimate()
                    .placeholder(R.drawable.recharge_logo)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(holder.ivTerminal);

        if(listItems.get(position).getTerminalActiveStatus().equals("0")){
            holder.linearInactive.setVisibility(View.VISIBLE);
        }else{
            holder.linearInactive.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (listItems == null){
            return 0;
        } else {
            return listItems.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewTypeId;
        public TextView textViewName;
        public TextView textViewLat;
        public TextView textViewLong;
        public TextView textViewOpen;
        public TextView textViewClose;
        public ImageView ivTerminal;
        public CardView cardView;
        public LinearLayout linearInactive;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvTerminal);
            textViewTypeId = itemView.findViewById(R.id.tv_terminal_type_id);
            textViewName = itemView.findViewById(R.id.tv_terminal_name);
            textViewLat = itemView.findViewById(R.id.tv_terminal_latitude);
            textViewLong = itemView.findViewById(R.id.tv_terminal_longitude);
            textViewOpen = itemView.findViewById(R.id.tv_terminal_open);
            textViewClose = itemView.findViewById(R.id.tv_terminal_closed);
            ivTerminal = itemView.findViewById(R.id.ivTerminal);
            linearInactive = itemView.findViewById(R.id.linearInactive);
        }
    }

}
