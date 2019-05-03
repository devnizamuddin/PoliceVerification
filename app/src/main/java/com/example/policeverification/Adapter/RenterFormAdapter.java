package com.example.policeverification.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.policeverification.PoJo.RenterFormPoJo;
import com.example.policeverification.R;

import java.util.ArrayList;

public class RenterFormAdapter extends RecyclerView.Adapter<RenterFormAdapter.FormViewHolder> {

    private Context context;
    private ArrayList<RenterFormPoJo>renterFormPoJos;
    private RenterFormItemClickListener renterFormItemClickListener;

    public RenterFormAdapter(Context context, ArrayList<RenterFormPoJo> renterFormPoJos, RenterFormItemClickListener renterFormItemClickListener) {
        this.context = context;
        this.renterFormPoJos = renterFormPoJos;
        this.renterFormItemClickListener = renterFormItemClickListener;
    }

    @NonNull
    @Override
    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.form_single_layout,viewGroup,false);

        return new FormViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormViewHolder formViewHolder, int i) {

        RenterFormPoJo renterFormPoJo = renterFormPoJos.get(i);
        formViewHolder.nameTv.setText(renterFormPoJo.getName());
        formViewHolder.number_tv.setText(renterFormPoJo.getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return renterFormPoJos.size() ;
    }

    public class FormViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv,number_tv;

        public FormViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_tv);
            number_tv = itemView.findViewById(R.id.phone_no_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    renterFormItemClickListener.onClickRenterFormItemClickListener(renterFormPoJos.get(getAdapterPosition()));
                }
            });
        }
    }
    public void updateRenterFormList(ArrayList<RenterFormPoJo>renterFormPoJos){
        this.renterFormPoJos = renterFormPoJos;
        notifyDataSetChanged();
    }
    public interface RenterFormItemClickListener{
        void onClickRenterFormItemClickListener(RenterFormPoJo renterFormPoJo);
    }
}
