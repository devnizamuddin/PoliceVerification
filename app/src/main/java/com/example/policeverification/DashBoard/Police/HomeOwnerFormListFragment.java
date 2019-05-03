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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.policeverification.Adapter.HomeOwnerFormAdapter;
import com.example.policeverification.PoJo.HomeOwnerFormPoJo;
import com.example.policeverification.R;
import com.example.policeverification.DashBoard.ViewHomeOwnerFormFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeOwnerFormListFragment extends Fragment implements HomeOwnerFormAdapter.HomeOwnerFormItemClickListener {


    private Context context;
    private DatabaseReference databaseReference;
    private Spinner verification_status_sp;
    private RecyclerView home_owner_from_rv;
    private ArrayList<HomeOwnerFormPoJo> homeOwnerFormPoJos=new ArrayList<>();
    private HomeOwnerFormAdapter homeOwnerFormAdapter;
    private EditText search_et;
    RelativeLayout.LayoutParams searchParamsPrevious,searchParams,recyclerParamsPrevious,recyclerParams;


    public HomeOwnerFormListFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_home_owner_form_list, container, false);

        verification_status_sp = view.findViewById(R.id.verification_status_sp);
        home_owner_from_rv = view.findViewById(R.id.home_owner_from_rv);
        search_et = view.findViewById(R.id.search_et);


        searchParamsPrevious = (RelativeLayout.LayoutParams)search_et.getLayoutParams();
        searchParams = (RelativeLayout.LayoutParams)search_et.getLayoutParams();

        recyclerParamsPrevious = (RelativeLayout.LayoutParams)home_owner_from_rv.getLayoutParams();
        recyclerParams = (RelativeLayout.LayoutParams)home_owner_from_rv.getLayoutParams();

        databaseReference = FirebaseDatabase.getInstance().getReference("Form");

        ArrayAdapter<CharSequence> verificationStatusAdapter = ArrayAdapter.
                createFromResource(context, R.array.verification_status_array,
                        R.layout.spinner_center_item);
        verificationStatusAdapter.setDropDownViewResource(R.layout.spinner_center_item);
        verification_status_sp.setAdapter(verificationStatusAdapter);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        home_owner_from_rv.setLayoutManager(layoutManager);
        homeOwnerFormAdapter = new HomeOwnerFormAdapter(context,homeOwnerFormPoJos,this);
        home_owner_from_rv.setAdapter(homeOwnerFormAdapter);



        verification_status_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                getFormDatabaseAsSelected(verification_status_sp.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                getFormDatabaseAsSelected("All");
            }
        });

        search_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                search_et.getKeepScreenOn();

                setLayoutAstSearchFocus(b);
            }
        });
        search_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                search_et.setFocusable(true);
                return false;
            }
        });

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

        return view;
    }

    private void setLayoutAstSearchFocus(boolean b) {
        if (b){
            /*search_et.setLayoutParams(searchParams);
            home_owner_from_rv.setLayoutParams(recyclerParams);*/

            searchParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            recyclerParams.addRule(RelativeLayout.BELOW, R.id.search_et);
            search_et.setLayoutParams(searchParams);
            home_owner_from_rv.setLayoutParams(recyclerParams);
            Toast.makeText(context, "focus", Toast.LENGTH_SHORT).show();
        }
        else {

            search_et.setLayoutParams(searchParamsPrevious);
            home_owner_from_rv.setLayoutParams(recyclerParamsPrevious);
            Toast.makeText(context, "no focus", Toast.LENGTH_SHORT).show();
        }
    }

    private void filter(String searchText) {

        ArrayList<HomeOwnerFormPoJo>homeOwnerPoJos = new ArrayList<>();
        for (HomeOwnerFormPoJo formPoJo : homeOwnerFormPoJos){
            if (formPoJo.getPhoneNo().toLowerCase().contains(searchText.toLowerCase())){
                homeOwnerPoJos.add(formPoJo);
            }
        }
        homeOwnerFormAdapter.updateHomeOwnerFormList(homeOwnerPoJos);
    }

    private void getFormDatabaseAsSelected(String verificationStatus) {

        if (verificationStatus.equals("All")){
            databaseReference.child("HomeOwner").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){
                        homeOwnerFormPoJos.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            HomeOwnerFormPoJo homeOwnerFormPoJo = data.getValue(HomeOwnerFormPoJo.class);
                            homeOwnerFormPoJos.add(homeOwnerFormPoJo);
                        }
                        Toast.makeText(context, ""+ homeOwnerFormPoJos.size(), Toast.LENGTH_SHORT).show();

                        homeOwnerFormAdapter.updateHomeOwnerFormList(homeOwnerFormPoJos);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(context, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            databaseReference.child("HomeOwner").orderByChild("verificationStatus").equalTo(verificationStatus).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){
                        homeOwnerFormPoJos.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            HomeOwnerFormPoJo homeOwnerFormPoJo = data.getValue(HomeOwnerFormPoJo.class);
                            homeOwnerFormPoJos.add(homeOwnerFormPoJo);
                        }
                        homeOwnerFormAdapter.updateHomeOwnerFormList(homeOwnerFormPoJos);
                    }
                    else {
                        homeOwnerFormPoJos.clear();
                        homeOwnerFormAdapter.updateHomeOwnerFormList(homeOwnerFormPoJos);
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
    public void onClickHomeOwnerForm(HomeOwnerFormPoJo homeOwnerFormPoJo) {

        changeFragment(ViewHomeOwnerFormFragment.getInstance(homeOwnerFormPoJo.getUserId(),"Police"));
    }
    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
