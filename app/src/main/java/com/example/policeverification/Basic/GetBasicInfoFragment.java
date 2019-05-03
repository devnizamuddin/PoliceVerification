package com.example.policeverification.Basic;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.policeverification.DashBoard.HomeOwner.HomeOwnerDashBoardFragment;
import com.example.policeverification.DashBoard.Police.PoliceDashBoardFragment;
import com.example.policeverification.DashBoard.Renter.RenterDashboardFragment;
import com.example.policeverification.LoginFragment;
import com.example.policeverification.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GetBasicInfoFragment extends Fragment {


    DatabaseReference databaseReference;
    String userId;
    private Context context;
    TextView name_tv, role_tv;
    String name, role;
    Button next_btn;
    ImageView popUpMenu;
    FirebaseAuth firebaseAuth;

    public GetBasicInfoFragment() {
        // Required empty public constructor
    }

    public static GetBasicInfoFragment getInstance(String userId) {

        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        GetBasicInfoFragment basicInfoFragment = new GetBasicInfoFragment();
        basicInfoFragment.setArguments(bundle);
        return basicInfoFragment;

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

        View view = inflater.inflate(R.layout.fragment_get_basic_info, container, false);

        name_tv = view.findViewById(R.id.name_Tv);
        role_tv = view.findViewById(R.id.role_tv);
        next_btn = view.findViewById(R.id.next_btn);
        popUpMenu = view.findViewById(R.id.popUpMenu);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = getArguments().getString("userId");

        databaseReference.child("User_basic_info").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        name = dataSnapshot.child("name").getValue(String.class);
                        role = dataSnapshot.child("role").getValue(String.class);
                    }
                    updateView();
                } else {
                    changeFragment(SetBasicInfoFragment.getInstance(userId));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role = role_tv.getText().toString();
                switch (role) {
                    case "Renter":
                        changeFragment(RenterDashboardFragment.getInstance(userId));
                        break;
                    case "Police Verifier":
                        changeFragment(new PoliceDashBoardFragment());
                        break;
                    case "Home Owner":
                        changeFragment(HomeOwnerDashBoardFragment.getInstance(userId));
                        break;

                }
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

    private void updateView() {

        name_tv.setText(name);
        role_tv.setText(role);

    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
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
                    if (firebaseAuth.getCurrentUser() == null) {
                        changeFragment(new LoginFragment());
                    }
                }
                return false;
            }
        });
        popup.show();
    }


}
