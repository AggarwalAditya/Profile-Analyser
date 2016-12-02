package com.example.adiad.profileanalyser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Event_id_page extends AppCompatActivity {

    private Button event_btn;
    private EditText event_name,person_name,phone_number;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_id_page);
        event_btn=(Button)findViewById(R.id.event_btn);
        event_name=(EditText)findViewById(R.id.event_name);
        person_name=(EditText)findViewById(R.id.person_name);
        phone_number=(EditText)findViewById(R.id.phone_number);
        prefManager = new PrefManager(this);

    }

    public void enter_event_clicked(View view)
    {
        String event_str=event_name.getText().toString()+" ";
        String person_str=person_name.getText().toString()+" ";
        String phone_str=phone_number.getText().toString()+" ";

        if(!event_str.contentEquals(" ") && !person_str.contentEquals(" ") && !phone_str.contentEquals(" "))
        {
            prefManager.set_event_name(event_str);
            prefManager.set_person_name(person_str);
            prefManager.set_phone_number(phone_str);
            Intent i = new Intent(Event_id_page.this, MainActivity.class);
            i.putExtra("eventORnot","1");
            startActivity(i);

        }

        else
        {
            Toast.makeText(getBaseContext(), "Please enter your details.", Toast.LENGTH_LONG).show();
        }
    }

}
