package com.example.policeverification.DashBoard;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.policeverification.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpDeskFragment extends Fragment {

    ImageView call_one,call_two,call_three,call_four
            ,call_five,call_six,call_seven,call_eight,call_nine,call_ten;
    TextView number_one,number_two,number_three,number_four
            ,number_five,number_six,number_seven,number_eight,number_nine,number_ten;


    public HelpDeskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_help_desk, container, false);

        call_one = view.findViewById(R.id.call_one);
        call_two = view.findViewById(R.id.call_two);
        call_three = view.findViewById(R.id.call_three);
        call_four = view.findViewById(R.id.call_four);
        call_five = view.findViewById(R.id.call_five);
        call_six = view.findViewById(R.id.call_six);
        call_seven = view.findViewById(R.id.call_seven);
        call_eight = view.findViewById(R.id.call_eight);
        call_nine = view.findViewById(R.id.call_nine);
        call_ten = view.findViewById(R.id.call_ten);

        number_one = view.findViewById(R.id.number_one);
        number_two = view.findViewById(R.id.number_two);
        number_three = view.findViewById(R.id.number_three);
        number_four = view.findViewById(R.id.number_four);
        number_five = view.findViewById(R.id.number_five);
        number_six = view.findViewById(R.id.number_six);
        number_seven = view.findViewById(R.id.number_seven);
        number_eight = view.findViewById(R.id.number_eight);
        number_nine = view.findViewById(R.id.number_nine);
        number_ten = view.findViewById(R.id.number_ten);

        call_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_one.getText().toString());
            }
        });
        call_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_two.getText().toString());
            }
        });
        call_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_three.getText().toString());
            }
        });
        call_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_four.getText().toString());
            }
        });
        call_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_five.getText().toString());
            }
        });
        call_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_six.getText().toString());
            }
        });
        call_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_seven.getText().toString());
            }
        });
        call_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_eight.getText().toString());
            }
        });
        call_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_nine.getText().toString());
            }
        });
        call_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(number_nine.getText().toString());
            }
        });


        return view;
    }
    void callPhone(String number){
        Intent callIntent =new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+number));
        startActivity(callIntent);
    }

}
