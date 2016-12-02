package com.example.adiad.profileanalyser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class HomePage extends AppCompatActivity {

    private RelativeLayout self,event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        self=(RelativeLayout)findViewById(R.id.self);
        event=(RelativeLayout)findViewById(R.id.event);


        
        try {
            Glide.with(this).load(R.drawable.profile_cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void self_clicked(View view)
    {
        Intent i = new Intent(HomePage.this, MainActivity.class);
        i.putExtra("eventORnot","0");
        startActivity(i);
    }

    public void event_clicked(View view)
    {
        Intent j = new Intent(HomePage.this, Event_id_page.class);
        startActivity(j);
    }


}
