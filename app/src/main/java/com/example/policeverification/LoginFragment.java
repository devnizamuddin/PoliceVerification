package com.example.policeverification;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.policeverification.Basic.GetBasicInfoFragment;
import com.example.policeverification.Basic.SetBasicInfoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    EditText number_et,v_code_et;
    Button send_verification_btn,confirm_btn;
    FirebaseAuth firebaseAuth;
    private Context context;
    private String codeSend;
    DatabaseReference databaseReference;

    public LoginFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        number_et = view.findViewById(R.id.number_et);
        send_verification_btn = view.findViewById(R.id.send_verification_btn);
        v_code_et = view.findViewById(R.id.v_code_et);
        confirm_btn = view.findViewById(R.id.confirm_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        send_verification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendVerificationCode();

            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = v_code_et.getText().toString();
                if (!TextUtils.isEmpty(code)){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSend, code);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        return view;
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                            changeFragmentAsUser();
                        } else {
                            Toast.makeText(context, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void changeFragmentAsUser() {

        final String userId = firebaseAuth.getCurrentUser().getUid();

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
                Toast.makeText(context, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sendVerificationCode() {
        String number = number_et.getText().toString();

        if (!TextUtils.isEmpty(number)){

            String phoneNumber = "+88"+number;
            PhoneAuthProvider.getInstance()
                    .verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS,getActivity(), mCallbacks);
        }
        else {
            Toast.makeText(context, "Please Enter Your Number", Toast.LENGTH_SHORT).show();
        }

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(context, "Verification Code Send", Toast.LENGTH_SHORT).show();
            codeSend = s;
        }
    };


    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }
}
