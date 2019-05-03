package com.example.policeverification.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.policeverification.PoJo.HomeOwnerFormPoJo;
import com.example.policeverification.R;

import java.util.ArrayList;

public class HomeOwnerFormAdapter extends RecyclerView.Adapter<HomeOwnerFormAdapter.HomeOwnerViewHolder> {

    private Context context;
    private ArrayList<HomeOwnerFormPoJo>homeOwnerFormPoJos;
    private HomeOwnerFormItemClickListener homeOwnerFormItemClickListener;

    public HomeOwnerFormAdapter(Context context, ArrayList<HomeOwnerFormPoJo> homeOwnerFormPoJos, HomeOwnerFormItemClickListener homeOwnerFormItemClickListener) {
        this.context = context;
        this.homeOwnerFormPoJos = homeOwnerFormPoJos;
        this.homeOwnerFormItemClickListener = homeOwnerFormItemClickListener;
    }

    @NonNull
    @Override
    public HomeOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.form_single_layout,viewGroup,false);

        return new HomeOwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeOwnerViewHolder homeOwnerViewHolder, int i) {

        HomeOwnerFormPoJo homeOwnerFormPoJo = homeOwnerFormPoJos.get(i);
        homeOwnerViewHolder.nameTv.setText(homeOwnerFormPoJo.getName());
        homeOwnerViewHolder.number_tv.setText(homeOwnerFormPoJo.getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return homeOwnerFormPoJos.size();
    }

    public class HomeOwnerViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv,number_tv;

        public HomeOwnerViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_tv);
            number_tv = itemView.findViewById(R.id.phone_no_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeOwnerFormItemClickListener.onClickHomeOwnerForm(homeOwnerFormPoJos.get(getAdapterPosition()));
                }
            });
        }
    }

    public void updateHomeOwnerFormList(ArrayList<HomeOwnerFormPoJo>homeOwnerFormPoJos){

        this.homeOwnerFormPoJos = homeOwnerFormPoJos;
        notifyDataSetChanged();

    }
    public interface HomeOwnerFormItemClickListener{

        void onClickHomeOwnerForm(HomeOwnerFormPoJo homeOwnerFormPoJo);

    }
}
