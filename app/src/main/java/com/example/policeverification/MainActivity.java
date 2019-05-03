package com.example.policeverification;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.policeverification.Basic.GetBasicInfoFragment;
import com.example.policeverification.Basic.SetBasicInfoFragment;
import com.example.policeverification.DashBoard.HomeOwner.HomeOwnerDashBoardFragment;
import com.example.policeverification.DashBoard.Police.PoliceDashBoardFragment;
import com.example.policeverification.DashBoard.Renter.RenterDashboardFragment;
import com.example.policeverification.PoJo.RenterFormPoJo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


       //setFragmentAsUser();
       changeFragment(new PoliceDashBoardFragment());

    }

    private void setFragmentAsUser() {

        if (firebaseAuth.getCurrentUser() !=null){
            userId = firebaseAuth.getCurrentUser().getUid();
            databaseReference.child("User_basic_info").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){
                        changeFragment(GetBasicInfoFragment.getInstance(userId));
                    }
                    else {
                        changeFragment(SetBasicInfoFragment.getInstance(userId));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            changeFragment(new LoginFragment());

        }
    }

    public void changeFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();

    }
}
