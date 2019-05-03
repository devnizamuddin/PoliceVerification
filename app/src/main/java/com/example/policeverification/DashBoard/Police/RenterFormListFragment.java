package com.example.policeverification.DashBoard.Police;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.policeverification.Adapter.RenterFormAdapter;
import com.example.policeverification.PoJo.RenterFormPoJo;
import com.example.policeverification.R;
import com.example.policeverification.DashBoard.ViewRenterFormFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RenterFormListFragment extends Fragment implements RenterFormAdapter.RenterFormItemClickListener {

    private Context context;
    private DatabaseReference databaseReference;
    private Spinner verification_status_sp;
    private RecyclerView renter_from_rv;
    private ArrayList<RenterFormPoJo>renterFormPoJos=new ArrayList<>();
    private RenterFormAdapter renterFormAdapter;
    private EditText search_et;

    public RenterFormListFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_renter_form_list, container, false);

        verification_status_sp = view.findViewById(R.id.verification_status_sp);
        renter_from_rv = view.findViewById(R.id.renter_from_rv);
        search_et = view.findViewById(R.id.search_et);

        databaseReference = FirebaseDatabase.getInstance().getReference("Form");

        ArrayAdapter<CharSequence> verificationStatusAdapter = ArrayAdapter.
                createFromResource(context, R.array.verification_status_array,
                        android.R.layout.simple_spinner_dropdown_item);
        verification_status_sp.setAdapter(verificationStatusAdapter);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        renter_from_rv.setLayoutManager(layoutManager);
        renterFormAdapter = new RenterFormAdapter(context,renterFormPoJos,this);
        renter_from_rv.setAdapter(renterFormAdapter);

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });

        verification_status_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(context, ""+verification_status_sp.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                getFormDatabaseAsSelected(verification_status_sp.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                getFormDatabaseAsSelected("All");
            }
        });


        return view;
    }

    private void filter(String string) {

        ArrayList<RenterFormPoJo>renterPoJos = new ArrayList<>();

        for (RenterFormPoJo renterForm : renterFormPoJos){
            if (renterForm.getPhoneNo().toLowerCase().contains(string.toLowerCase())){
                renterPoJos.add(renterForm);
            }
        }
        renterFormAdapter.updateRenterFormList(renterPoJos);


    }

    private void getFormDatabaseAsSelected(String verificationStatus) {

        if (verificationStatus.equals("All")){
            databaseReference.child("Renter").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){
                        renterFormPoJos.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            RenterFormPoJo renterFormPoJo = data.getValue(RenterFormPoJo.class);
                            renterFormPoJos.add(renterFormPoJo);
                        }
                        Toast.makeText(context, ""+ renterFormPoJos.size(), Toast.LENGTH_SHORT).show();

                        renterFormAdapter.updateRenterFormList(renterFormPoJos);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(context, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            databaseReference.child("Renter").orderByChild("verificationStatus").equalTo(verificationStatus).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){
                        renterFormPoJos.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            RenterFormPoJo renterFormPoJo = data.getValue(RenterFormPoJo.class);
                            renterFormPoJos.add(renterFormPoJo);
                        }
                        Toast.makeText(context, ""+renterFormPoJos.size(), Toast.LENGTH_SHORT).show();
                        renterFormAdapter.updateRenterFormList(renterFormPoJos);
                    }
                    else {
                        renterFormPoJos.clear();
                        renterFormAdapter.updateRenterFormList(renterFormPoJos);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(context, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        }



    }

    @Override
    public void onClickRenterFormItemClickListener(RenterFormPoJo renterFormPoJo) {

        changeFragment(ViewRenterFormFragment.getInstance(renterFormPoJo.getUserId(),"Police"));


    }
    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
