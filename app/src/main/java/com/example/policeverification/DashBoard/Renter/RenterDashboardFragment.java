package com.example.policeverification.DashBoard.Renter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.policeverification.DashBoard.HelpDeskFragment;
import com.example.policeverification.DashBoard.MapFragment;
import com.example.policeverification.DashBoard.NoticeFragment;
import com.example.policeverification.DashBoard.ViewRenterFormFragment;
import com.example.policeverification.LoginFragment;
import com.example.policeverification.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class RenterDashboardFragment extends Fragment {

    private Context context;
    private FirebaseAuth firebaseAuth;
    private CardView notice_card,form_card,help_desk_card,map_card,logout_card;
    private String userId;
    private DatabaseReference databaseReference;

    public RenterDashboardFragment() {
        // Required empty public constructor
    }
    public static RenterDashboardFragment getInstance(String userId){

        Bundle bundle = new Bundle();
        bundle.putString("userId",userId);
        RenterDashboardFragment renterDashboardFragment = new RenterDashboardFragment();
        renterDashboardFragment.setArguments(bundle);
        return renterDashboardFragment;

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
        View view =  inflater.inflate(R.layout.fragment_renter_dashboard, container, false);


        notice_card = view.findViewById(R.id.notice_card);
        form_card = view.findViewById(R.id.form_layout);
        map_card = view.findViewById(R.id.map_card);
        help_desk_card = view.findViewById(R.id.help_desk_card);
        logout_card = view.findViewById(R.id.logout_card);




        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Form");

        userId = getArguments().getString("userId");


        notice_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(NoticeFragment.getInstance("Renter"));
            }
        });
        form_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("Renter").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            changeFragment(ViewRenterFormFragment.getInstance(userId,"Renter"));
                        }
                        else {
                            changeFragment(RenterFormFragment.getInstance(userId));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        logout_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                changeFragment(new LoginFragment());
            }
        });
        map_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new MapFragment());
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

}
