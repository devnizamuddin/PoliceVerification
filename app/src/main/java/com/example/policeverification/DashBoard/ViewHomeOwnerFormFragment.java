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
import com.example.policeverification.PoJo.HomeOwnerFormPoJo;
import com.example.policeverification.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewHomeOwnerFormFragment extends Fragment {

    private Context context;
    private TextView name_tv, father_name_tv, mother_name_tv, nid_number_tv, occupation_tv,
            permanent_address_tv, present_address_tv, emergency_no_tv, phone_number_tv, reg_house_tv,
            verification_status_tv;
    private Button verify_btn;
    private DatabaseReference databaseReference;
    private String userId;
    private String fragment = null;

    public ViewHomeOwnerFormFragment() {
        // Required empty public constructor
    }

    public static ViewHomeOwnerFormFragment getInstance(String userId, String fragment) {

        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("fragment", fragment);
        ViewHomeOwnerFormFragment viewHomeOwnerFormFragment = new ViewHomeOwnerFormFragment();
        viewHomeOwnerFormFragment.setArguments(bundle);
        return viewHomeOwnerFormFragment;

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
        View view = inflater.inflate(R.layout.fragment_view_home_owner_form, container, false);

        name_tv = view.findViewById(R.id.name_tv);
        father_name_tv = view.findViewById(R.id.father_name_tv);
        mother_name_tv = view.findViewById(R.id.mother_name_tv);
        nid_number_tv = view.findViewById(R.id.nid_number_tv);
        occupation_tv = view.findViewById(R.id.occupation_tv);
        permanent_address_tv = view.findViewById(R.id.permanent_address_tv);
        present_address_tv = view.findViewById(R.id.present_address_tv);
        emergency_no_tv = view.findViewById(R.id.emergency_no_tv);
        phone_number_tv = view.findViewById(R.id.phone_number_tv);
        reg_house_tv = view.findViewById(R.id.reg_house_tv);
        verification_status_tv = view.findViewById(R.id.verification_status_tv);

        try {
            userId = getArguments().getString("userId");
            fragment = getArguments().getString("fragment");
        } catch (Exception e) {
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Form");
        verify_btn = view.findViewById(R.id.verify_btn);

        if (!fragment.equals("Police")) {
            verify_btn.setText("Edit");
        }


        getDataFromDatabaseToViewUser();
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fragment.equals(null)) {
                    changeFragment(HomeOwnerFormFragment.getInstance(userId));
                } else {
                    verifyHomeOwnerForm();

                }
            }
        });

        return view;
    }

    private void verifyHomeOwnerForm() {

        databaseReference.child("HomeOwner").child(userId).child("verificationStatus")
                .setValue("Verified").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(context, "Home Owner Form Verified", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getDataFromDatabaseToViewUser() {

        databaseReference.child("HomeOwner").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    HomeOwnerFormPoJo homeOwnerFormPoJo = dataSnapshot.getValue(HomeOwnerFormPoJo.class);
                    updateView(homeOwnerFormPoJo);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateView(HomeOwnerFormPoJo homeOwnerFormPoJo) {

        name_tv.setText(homeOwnerFormPoJo.getName());
        father_name_tv.setText(homeOwnerFormPoJo.getFatherName());
        mother_name_tv.setText(homeOwnerFormPoJo.getMotherName());
        nid_number_tv.setText(homeOwnerFormPoJo.getNidNo());
        occupation_tv.setText(homeOwnerFormPoJo.getOccupation());
        permanent_address_tv.setText(homeOwnerFormPoJo.getPermanentAddress());
        present_address_tv.setText(homeOwnerFormPoJo.getPresentAddress());
        emergency_no_tv.setText(homeOwnerFormPoJo.getEmergencyNo());
        phone_number_tv.setText(homeOwnerFormPoJo.getPhoneNo());
        reg_house_tv.setText(homeOwnerFormPoJo.getRegHouseAdd());
        verification_status_tv.setText("Verification Status : "+homeOwnerFormPoJo.getVerificationStatus());


    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

}
