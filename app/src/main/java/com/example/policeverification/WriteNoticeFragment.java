package com.example.policeverification;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.policeverification.PoJo.NoticePoJo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class WriteNoticeFragment extends Fragment {


    private Context context;
    private Button submit_btn;
    private EditText tittle_et,notice_et;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ImageView popUpMenu;

    public WriteNoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_write_notice, container, false);

        submit_btn = view.findViewById(R.id.submit_btn);
        tittle_et = view.findViewById(R.id.tittle_et);
        notice_et = view.findViewById(R.id.notice_et);
        popUpMenu = view.findViewById(R.id.popUpMenu);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        popUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotice();
            }
        });


        return view;
    }

    private void sendNotice() {

        String tittle = tittle_et.getText().toString();
        String notice = notice_et.getText().toString();

        if (!TextUtils.isEmpty(tittle) && !TextUtils.isEmpty(notice)){

            String id = databaseReference.push().getKey();
            NoticePoJo noticePoJo = new NoticePoJo(id,tittle,notice);
            databaseReference.child("Notice").child(id).setValue(noticePoJo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(context, "Notice Added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            Toast.makeText(context, "Tittle & NoticePoJo Required", Toast.LENGTH_SHORT).show();
        }

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.log_out_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.logout) {
                    firebaseAuth.signOut();
                    if (firebaseAuth.getCurrentUser()==null){
                        changeFragment(new LoginFragment());
                    }
                }
                return false;
            }
        });
        popup.show();
    }
    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
