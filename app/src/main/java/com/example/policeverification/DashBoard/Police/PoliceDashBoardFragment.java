package com.example.policeverification.DashBoard.Police;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.policeverification.DashBoard.NoticeFragment;
import com.example.policeverification.LoginFragment;
import com.example.policeverification.R;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoliceDashBoardFragment extends Fragment {


    private Context context;
    private CardView notice_card, renter_card,home_owner_card,logout_card;
    private FirebaseAuth firebaseAuth;

    public PoliceDashBoardFragment() {
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
        View view = inflater.inflate(R.layout.fragment_police_dash_board, container, false);

        notice_card = view.findViewById(R.id.notice_card);
        renter_card = view.findViewById(R.id.renter_card);
        home_owner_card = view.findViewById(R.id.home_owner_card);
        logout_card = view.findViewById(R.id.logout_card);

        firebaseAuth = FirebaseAuth.getInstance();

        notice_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFragment(NoticeFragment.getInstance("Police"));
            }
        });

        renter_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFragment(new RenterFormListFragment());
            }
        });
        home_owner_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFragment(new HomeOwnerFormListFragment());
            }
        });
        logout_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                changeFragment(new LoginFragment());
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
