package com.example.adiad.profileanalyser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment_4 extends Fragment {

    private Button submit_analysis;


    public Fragment_4() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_fragment_4, container, false);
        submit_analysis=(Button)rootView.findViewById(R.id.submit_analysis);
        return rootView;
    }

    public void submit_analysis(View view)
    {
        //AKSHAY
    }



}
