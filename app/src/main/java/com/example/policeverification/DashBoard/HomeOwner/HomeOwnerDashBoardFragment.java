package com.example.policeverification.DashBoard.HomeOwner;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.policeverification.DashBoard.HelpDeskFragment;
import com.example.policeverification.DashBoard.MapFragment;
import com.example.policeverification.DashBoard.NoticeFragment;
import com.example.policeverification.DashBoard.ViewHomeOwnerFormFragment;
import com.example.policeverification.LoginFragment;
import com.example.policeverification.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeOwnerDashBoardFragment extends Fragment {

    private CardView notice_card, form_card, help_desk_card, map_card, logout_card;
    private Context context;
    private String userId;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public HomeOwnerDashBoardFragment() {
        // Required empty public constructor
    }

    public static HomeOwnerDashBoardFragment getInstance(String userId) {

        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        HomeOwnerDashBoardFragment homeOwnerDashBoardFragment = new HomeOwnerDashBoardFragment();
        homeOwnerDashBoardFragment.setArguments(bundle);
        return homeOwnerDashBoardFragment;

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
        View view = inflater.inflate(R.layout.fragment_home_owner_dash_board, container, false);


        notice_card = view.findViewById(R.id.notice_card);
        form_card = view.findViewById(R.id.form_layout);
        map_card = view.findViewById(R.id.map_card);
        help_desk_card = view.findViewById(R.id.help_desk_card);
        logout_card = view.findViewById(R.id.logout_card);

        userId = getArguments().getString("userId");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Form");


        form_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("HomeOwner").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            changeFragment(ViewHomeOwnerFormFragment.getInstance(userId,"HomeOwner"));
                        }
                        else {
                            changeFragment(HomeOwnerFormFragment.getInstance(userId));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        notice_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(NoticeFragment.getInstance("Home Owner"));
            }
        });
        map_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new MapFragment());
            }
        });
        logout_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                changeFragmentWithOutBackstack(new LoginFragment());
            }
        });
        help_desk_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new HelpDeskFragment());
            }
        });


        return view;
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void changeFragmentWithOutBackstack(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
