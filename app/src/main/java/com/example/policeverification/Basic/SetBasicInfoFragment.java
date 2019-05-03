package com.example.policeverification.Basic;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.policeverification.PoJo.UserBasicPoJo;
import com.example.policeverification.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetBasicInfoFragment extends Fragment {

    EditText name_et;
    Spinner role_sp;
    private Context context;
    Button submit_btn;
    DatabaseReference databaseReference;
    String userId;

    public SetBasicInfoFragment() {
        // Required empty public constructor
    }
    public static SetBasicInfoFragment getInstance(String userId){

        Bundle bundle = new Bundle();
        bundle.putString("userId",userId);
        SetBasicInfoFragment setBasicInfoFragment = new SetBasicInfoFragment();
        setBasicInfoFragment.setArguments(bundle);
        return setBasicInfoFragment;

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
        View view =  inflater.inflate(R.layout.fragment_set_basic_info, container, false);

        name_et = view.findViewById(R.id.name_et);
        role_sp = view.findViewById(R.id.role_sp);
        submit_btn = view.findViewById(R.id.submit_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = getArguments().getString("userId");

        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.
                createFromResource(context, R.array.role_array, android.R.layout.simple_spinner_dropdown_item);

        role_sp.setAdapter(departmentAdapter);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setUserBasicData();


            }
        });

        return view;
    }

    private void setUserBasicData() {

        String name = name_et.getText().toString();
        final String role = role_sp.getSelectedItem().toString();
        UserBasicPoJo userBasicPoJo = new UserBasicPoJo(userId,name,role);
        databaseReference.child("User_basic_info").child(userId).setValue(userBasicPoJo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Your Info Added", Toast.LENGTH_SHORT).show();
                    changeFragment(GetBasicInfoFragment.getInstance(userId));
                }
                else {
                    Toast.makeText(context, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
