package com.example.policeverification.DashBoard;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.policeverification.DashBoard.HomeOwner.HomeOwnerFormFragment;
import com.example.policeverification.DashBoard.Renter.RenterFormFragment;
import com.example.policeverification.PoJo.RenterFormPoJo;
import com.example.policeverification.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ViewRenterFormFragment extends Fragment {


    private Context context;
    private TextView name_tv, father_name_tv, mother_name_tv, nid_number_tv, occupation_tv,
            permanent_address_tv, emergency_no_tv, phone_number_tv, previous_no_tv, present_no_tv,
            verification_status_tv;
    private Button verify_btn;
    private String userId;
    private DatabaseReference databaseReference;
    private String fragment;

    public ViewRenterFormFragment() {
        // Required empty public constructor
    }

    public static ViewRenterFormFragment getInstance(String userId,String fragment) {

        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("fragment",fragment);
        ViewRenterFormFragment viewRenterFormFragment = new ViewRenterFormFragment();
        viewRenterFormFragment.setArguments(bundle);
        return viewRenterFormFragment;

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
        View view = inflater.inflate(R.layout.fragment_view_renter_form, container, false);

        try {
            userId = getArguments().getString("userId");
            fragment = getArguments().getString("fragment");
        } catch (Exception e) {
        }

        name_tv = view.findViewById(R.id.name_tv);
        father_name_tv = view.findViewById(R.id.father_name_tv);
        mother_name_tv = view.findViewById(R.id.mother_name_tv);
        nid_number_tv = view.findViewById(R.id.nid_number_tv);
        occupation_tv = view.findViewById(R.id.occupation_tv);
        permanent_address_tv = view.findViewById(R.id.permanent_address_tv);
        emergency_no_tv = view.findViewById(R.id.emergency_no_tv);
        phone_number_tv = view.findViewById(R.id.phone_number_tv);
        previous_no_tv = view.findViewById(R.id.previous_no_tv);
        present_no_tv = view.findViewById(R.id.present_no_tv);
        verification_status_tv = view.findViewById(R.id.verification_status_tv);

        databaseReference = FirebaseDatabase.getInstance().getReference("Form");

        verify_btn = view.findViewById(R.id.verify_btn);
        if (!fragment.equals(null)) {
            verify_btn.setText("Edit");
        }

        getDataFromDatabaseToViewUser();

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fragment.equals("Police")) {
                    changeFragment(RenterFormFragment.getInstance(userId));
                } else {
                    verifyRenterForm();

                }
            }
        });

        return view;
    }

    private void verifyRenterForm() {

        databaseReference.child("Renter").child(userId).child("verificationStatus").setValue("Verified")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Renter Form Verified", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void getDataFromDatabaseToViewUser() {

        databaseReference.child("Renter").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    RenterFormPoJo renterFormPoJo = dataSnapshot.getValue(RenterFormPoJo.class);
                    updateView(renterFormPoJo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateView(RenterFormPoJo renterFormPoJo) {

        name_tv.setText(renterFormPoJo.getName());
        father_name_tv.setText(renterFormPoJo.getFatherName());
        mother_name_tv.setText(renterFormPoJo.getMotherName());
        nid_number_tv.setText(renterFormPoJo.getNidNo());
        occupation_tv.setText(renterFormPoJo.getOccupation());
        permanent_address_tv.setText(renterFormPoJo.getPermanentAddress());
        emergency_no_tv.setText(renterFormPoJo.getEmergencyNo());
        phone_number_tv.setText(renterFormPoJo.getPhoneNo());
        previous_no_tv.setText(renterFormPoJo.getPreviousNo());
        present_no_tv.setText(renterFormPoJo.getPresentNo());
        verification_status_tv.setText("Verification Status : " + renterFormPoJo.getVerificationStatus());

    }
    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
