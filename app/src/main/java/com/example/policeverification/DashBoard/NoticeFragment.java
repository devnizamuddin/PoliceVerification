package com.example.policeverification.DashBoard;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.policeverification.LoginFragment;
import com.example.policeverification.Adapter.NoticeAdapter;
import com.example.policeverification.PoJo.NoticePoJo;
import com.example.policeverification.R;
import com.example.policeverification.WriteNoticeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {


    private Context context;
    private FloatingActionButton fab;
    private ImageView popUpMenu;
    private FirebaseAuth firebaseAuth;
    private RecyclerView notice_rv;
    private ArrayList<NoticePoJo>noticePoJos = new ArrayList<>();
    private NoticeAdapter noticeAdapter;
    private DatabaseReference databaseReference;

    public NoticeFragment() {
        // Required empty public constructor
    }
    public static NoticeFragment getInstance(String role){

        Bundle bundle = new Bundle();
        bundle.putString("role",role);
        NoticeFragment noticeFragment = new NoticeFragment();
        noticeFragment.setArguments(bundle);
        return noticeFragment;
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
        View view =  inflater.inflate(R.layout.fragment_notice, container, false);

        fab = view.findViewById(R.id.fab);
        String role = getArguments().getString("role");
        popUpMenu = view.findViewById(R.id.popUpMenu);
        notice_rv = view.findViewById(R.id.notice_rv);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        noticeAdapter = new NoticeAdapter(context,noticePoJos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        notice_rv.setLayoutManager(layoutManager);
        notice_rv.setAdapter(noticeAdapter);

        if (role.equals("Police")){
            fab.setVisibility(FloatingActionButton.VISIBLE);
            Toast.makeText(context, "Police", Toast.LENGTH_SHORT).show();
        }
        else {
            fab.setVisibility(FloatingActionButton.INVISIBLE);
            Toast.makeText(context, ""+role, Toast.LENGTH_SHORT).show();
        }

        databaseReference.child("Notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    noticePoJos.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){

                        NoticePoJo noticePoJo = data.getValue(NoticePoJo.class);
                        noticePoJos.add(noticePoJo);

                    }
                    noticeAdapter.updateNotice(noticePoJos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFragment(new WriteNoticeFragment());
            }
        });

        popUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        return view;
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
