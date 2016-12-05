package com.example.adiad.profileanalyser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class HealthFragment extends Fragment {
    Spinner smokespinner, ftspinner, inchspinner, activespinner,
            dietspinner, diabeticspinner;
    ArrayAdapter<CharSequence> smokeadapter;
    ArrayAdapter<CharSequence> ftadapter, inchadapter,
            activeadapter, dietadapter, diabeticadapter;
    EditText mass, highbp, lowbp,hdlch,totalch,age;
    Button hicalculate;
    health h=new health();
    heartage ha=new heartage();
    PrefManager prefManager;
    int a;
    int q=0, w=0, y=0,p=0,m=0,n=0;
    float w1 = 0, y1 = 0, z=0;
    private static final String TAG=MainActivity.class.getSimpleName();

    public HealthFragment()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_health, container, false);
        prefManager=new PrefManager(getContext());
        mass = (EditText) rootView.findViewById(R.id.mass);
        highbp = (EditText) rootView.findViewById(R.id.highbp);
        lowbp = (EditText) rootView.findViewById(R.id.lowbp);
        hicalculate = (Button) rootView.findViewById(R.id.hicalculate);
        totalch=(EditText)rootView.findViewById(R.id.totalch);
        hdlch=(EditText)rootView.findViewById(R.id.hdlch);

        age=(EditText)rootView.findViewById(R.id.age);

        smokespinner = (Spinner) rootView.findViewById(R.id.smokespinner);
        ftspinner = (Spinner) rootView.findViewById(R.id.ftspinner);
        inchspinner = (Spinner)rootView.findViewById(R.id.inchspinner);
        activespinner = (Spinner) rootView.findViewById(R.id.activespinner);
        dietspinner = (Spinner) rootView.findViewById(R.id.dietspinner);
        diabeticspinner = (Spinner) rootView.findViewById(R.id.diabeticspinner);


        smokeadapter = ArrayAdapter.createFromResource(getContext(), R.array.smoking_category, R.layout.support_simple_spinner_dropdown_item);
        smokeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smokespinner.setAdapter(smokeadapter);


        ftadapter = ArrayAdapter.createFromResource(getContext(), R.array.ft_category, R.layout.support_simple_spinner_dropdown_item);
        ftadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ftspinner.setAdapter(ftadapter);


        inchadapter = ArrayAdapter.createFromResource(getContext(), R.array.inch_category, R.layout.support_simple_spinner_dropdown_item);
        inchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inchspinner.setAdapter(inchadapter);


        activeadapter = ArrayAdapter.createFromResource(getContext(), R.array.active_category, R.layout.support_simple_spinner_dropdown_item);
        activeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activespinner.setAdapter(activeadapter);


        dietadapter = ArrayAdapter.createFromResource(getContext(), R.array.diet_category, R.layout.support_simple_spinner_dropdown_item);
        dietadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietspinner.setAdapter(dietadapter);


        diabeticadapter = ArrayAdapter.createFromResource(getContext(), R.array.diabetic_category, R.layout.support_simple_spinner_dropdown_item);
        diabeticadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diabeticspinner.setAdapter(diabeticadapter);


        smokespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                // Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i) + " selected", Toast.LENGTH_LONG).show();
                a = i;
                float b = 0;
                if (a == 0)
                    b = 1;
                else if (a == 1)
                    b = (float) 0.22;
                else if (a == 2)
                    b = (float) 0.33;
                else if (a == 3)
                    b = (float) 0.66;
                else if (a == 4)
                    b = 0;

                h.smoke = b;
                //Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition((int) h.smoke) + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ftspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //    Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i) + " selected", Toast.LENGTH_LONG).show();

                h.heightft = i + 1;
                //Toast.makeText(MainActivity.this, "jjb " + h.heightft, Toast.LENGTH_LONG).show();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i) + " selected", Toast.LENGTH_LONG).show();
                h.heightinch = i + 1;
                //Toast.makeText(MainActivity.this, "hbuhv " + h.heightinch, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        activespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i) + " selected", Toast.LENGTH_LONG).show();

                float c = 0;

                if (i == 0 || i == 1)
                    c = 1;
                else if (i == 2 || i == 3)
                    c = (float) 0.5;

                h.active = c;
                //Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition((int) h.active) + " selected", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dietspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i) + " selected", Toast.LENGTH_LONG).show();

                float d = 0;
                if (i == 0)
                    d = 1;
                else if (i == 1)
                    d = (float) 0.6;
                else if (i == 2)
                    d = (float) 0.2;
                else if (i == 3)
                    d = 0;

                h.diet = d;

                //Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition((int) h.diet) + " selected", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        diabeticspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i) + " selected", Toast.LENGTH_LONG).show();

                if (i == 0)
                    h.diabetic = 1;
                else
                    h.diabetic = 0;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







        hicalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                try {
                    q = Integer.parseInt(mass.getText().toString());
                    //         q=Integer.getInteger(mass.getText().toString());
                    Log.e("value of q", String.valueOf(q));
                }catch (NumberFormatException e){



                }
                try {
                    w = Integer.parseInt(highbp.getText().toString());
                    Log.e("value of w", String.valueOf(w));
                }catch (NumberFormatException e){}

                try {
                    y = Integer.parseInt(lowbp.getText().toString());
                    Log.e("value of y", String.valueOf(y));
                }catch (NumberFormatException e){}

                try {
                    p= Integer.parseInt(age.getText().toString());
                    //         q=Integer.getInteger(mass.getText().toString());
                    Log.e("value of p", String.valueOf(p));
                }catch (NumberFormatException e){

                }



                try {
                    m = Integer.parseInt(totalch.getText().toString());
                    Log.e("value of m", String.valueOf(m));
                }catch (NumberFormatException e){}


                try {
                    n = Integer.parseInt(hdlch.getText().toString());
                    Log.e("value of n", String.valueOf(n));
                }catch (NumberFormatException e){}





                if(w==0)
                    w1=1;

                else if (w <= 100)
                    w1 = (float) 0.7;
                else if (w > 100 && w <= 120)
                    w1 = 1;
                else if (w > 120 && w <= 140)
                    w1 = (float) 0.7;
                else if (w > 140 && w <= 160)
                    w1 = (float) 0.4;
                else if (w > 160)
                    w1 = 0;

                if(y==0)
                    y1=1;

                else if (y <= 70)
                    y1 = (float) 0.7;
                else if (y > 70 && y <= 80)
                    y1 = 1;
                else if (y > 80 && y <= 90)
                    y1 = (float) 0.7;
                else if (y > 90 && y <= 100)
                    y1 = (float) 0.4;
                else if (y > 100)
                    y1 = 0;

                z = (w1 + y1) / 2;

                h.bp =z;





                h.bmi = h.bmical(q, h.heightft, h.heightinch);


                float hi = h.smoke + h.active + h.diet + h.bmi + h.diabetic + h.bp;


                String hi1=Float.toString(hi);
                String bmi1=Float.toString(h.bmi);
                String smoke1=Float.toString(h.smoke);
                String active1=Float.toString(h.active);
                String diet1=Float.toString(h.diet);
                String diabetic1=Float.toString(h.diabetic);
                String bp1=Float.toString(h.bp);


                ha.bm = ha.bmi(q, h.heightft, h.heightinch);

                ha.smk=ha.smoking(p,h.smoke);
                ha.habp=ha.bp(w);
                ha.ch=ha.totalch(m,n);

                ha.heage=ha.heart(p,ha.bm,ha.smk,ha.habp,ha.ch);

                String ha1=Float.toString(ha.heage);
                String ch1=Float.toString(ha.ch);



                Toast.makeText(getContext(), "All empty fields are taken into consideration that you are fit in them. ", Toast.LENGTH_LONG).show();


                String health_module=hi1+" "+ha1;
                prefManager.set_health_module(health_module);

                Intent intent=new Intent(getContext(),Main2Activity.class);
                intent.putExtra("hi",hi1);
                intent.putExtra("ha",ha1);
                intent.putExtra("bmi",bmi1);
                intent.putExtra("smoke",smoke1);
                intent.putExtra("active",active1);
                intent.putExtra("diet",diet1);
                intent.putExtra("diabetic",diabetic1);
                intent.putExtra("bp",bp1);
                intent.putExtra("ch",ch1);
                startActivity(intent);



            }
        });



        return rootView;
            }

    }



