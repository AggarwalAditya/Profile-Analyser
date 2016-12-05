package com.example.adiad.profileanalyser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Fragment_4 extends Fragment {

    private Button submit_analysis;
    PrefManager prefManager;

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
        prefManager=new PrefManager(getContext());

        submit_analysis=(Button)rootView.findViewById(R.id.submit_analysis);
        submit_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_name=prefManager.get_event_name();
                String person_name=prefManager.get_person_name();
                String phone_number=prefManager.get_phone_number();
                String health_module_info=prefManager.get_health_module();
                String social_media_posts=prefManager.get_social_media_posts();
                String social_media_likes=prefManager.get_social_media_likes();



                //AKSHAY
                Toast.makeText(getContext(),"Submit Analysis Clicked",Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }





}
