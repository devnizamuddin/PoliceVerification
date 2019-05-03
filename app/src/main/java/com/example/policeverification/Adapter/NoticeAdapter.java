package com.example.policeverification.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.policeverification.PoJo.NoticePoJo;
import com.example.policeverification.R;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private Context context;
    private ArrayList<NoticePoJo>noticePoJos;

    public NoticeAdapter(Context context, ArrayList<NoticePoJo> noticePoJos) {
        this.context = context;
        this.noticePoJos = noticePoJos;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.notice_single_layout,viewGroup,false);

        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder noticeViewHolder, int i) {

        NoticePoJo noticePoJo = noticePoJos.get(i);
        noticeViewHolder.tittle_tv.setText(noticePoJo.getTittle());
        noticeViewHolder.notice_tv.setText(noticePoJo.getNotice());
    }

    @Override
    public int getItemCount() {
        return noticePoJos.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        private TextView tittle_tv,notice_tv;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            tittle_tv = itemView.findViewById(R.id.tittle_tv);
            notice_tv = itemView.findViewById(R.id.notice_tv);

        }
    }
    public void updateNotice(ArrayList<NoticePoJo>noticePoJos){

        this.noticePoJos = noticePoJos;
        notifyDataSetChanged();

    }
}
