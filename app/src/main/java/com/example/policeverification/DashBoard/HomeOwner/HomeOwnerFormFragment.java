package com.example.policeverification.DashBoard.HomeOwner;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.policeverification.PoJo.HomeOwnerFormPoJo;
import com.example.policeverification.PoJo.RenterFormPoJo;
import com.example.policeverification.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeOwnerFormFragment extends Fragment {

    private Context context;
    private EditText name_et, father_name_et, mother_name_et, nid_number_et, occupation_et,
            permanent_address_et, present_address_et, emergency_no_et, phone_number_et, reg_house_et;
    private Button confirm_btn;
    private DatabaseReference databaseReference;
    private String userId;

    public HomeOwnerFormFragment() {
        // Required empty public constructor
    }

    public static HomeOwnerFormFragment getInstance(String userId) {

        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        HomeOwnerFormFragment homeOwnerFormFragment = new HomeOwnerFormFragment();
        homeOwnerFormFragment.setArguments(bundle);
        return homeOwnerFormFragment;
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
        View view = inflater.inflate(R.layout.fragment_home_owner_form, container, false);

        name_et = view.findViewById(R.id.name_et);
        father_name_et = view.findViewById(R.id.father_name_et);
        mother_name_et = view.findViewById(R.id.mother_name_et);
        nid_number_et = view.findViewById(R.id.nid_number_et);
        occupation_et = view.findViewById(R.id.occupation_et);
        permanent_address_et = view.findViewById(R.id.permanent_address_et);
        present_address_et = view.findViewById(R.id.present_address_et);
        emergency_no_et = view.findViewById(R.id.emergency_no_et);
        phone_number_et = view.findViewById(R.id.phone_number_et);
        reg_house_et = view.findViewById(R.id.reg_house_et);

        userId = getArguments().getString("userId");

        databaseReference = FirebaseDatabase.getInstance().getReference("Form");

        confirm_btn = view.findViewById(R.id.confirm_btn);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFromToDatabase();
            }
        });

        return view;
    }

    private void sendFromToDatabase() {

        String name = name_et.getText().toString();
        String fName = father_name_et.getText().toString();
        String mName = mother_name_et.getText().toString();
        String nidNo = nid_number_et.getText().toString();
        String occupation = occupation_et.getText().toString();
        String permanentAddress = permanent_address_et.getText().toString();
        String emergencyNo = emergency_no_et.getText().toString();
        String phoneNo = phone_number_et.getText().toString();
        String presentAddress = present_address_et.getText().toString();
        String registerHouseAddress = reg_house_et.getText().toString();
        String verification_status = "Unverified";

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(fName) && !TextUtils.isEmpty(mName) &&
                !TextUtils.isEmpty(nidNo) && !TextUtils.isEmpty(occupation)
                && !TextUtils.isEmpty(permanentAddress) &&
                !TextUtils.isEmpty(emergencyNo) && !TextUtils.isEmpty(phoneNo)
                && !TextUtils.isEmpty(presentAddress) &&
                !TextUtils.isEmpty(registerHouseAddress)) {

            //All Field Filled Up
            HomeOwnerFormPoJo homeOwnerFormPoJo = new HomeOwnerFormPoJo
                    (userId, name, fName, mName, nidNo, occupation,
                            permanentAddress, presentAddress, emergencyNo, phoneNo,
                            registerHouseAddress, verification_status);

            databaseReference.child("HomeOwner").child(userId).setValue(homeOwnerFormPoJo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Form Submitted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "" + task.getException()
                                .getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            //All field not filled up
            Toast.makeText(context, "All field are required", Toast.LENGTH_SHORT).show();
        }

    }


}
