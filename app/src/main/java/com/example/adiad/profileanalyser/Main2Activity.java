package com.example.adiad.profileanalyser;



        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.LinearLayout;
        import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {


    TextView hi, ha, bmi, smk, diet, diab, chol, bp, active;
    LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        hi = (TextView) findViewById(R.id.textView13);
        ha = (TextView) findViewById(R.id.textView15);
        bmi = (TextView) findViewById(R.id.textView17);
        smk = (TextView) findViewById(R.id.textView19);
        active = (TextView) findViewById(R.id.textView21);
        diet = (TextView) findViewById(R.id.textView23);
        diab = (TextView) findViewById(R.id.textView25);
        bp = (TextView) findViewById(R.id.textView27);
        chol = (TextView) findViewById(R.id.textView29);

        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        ll8 = (LinearLayout) findViewById(R.id.ll8);
        ll9 = (LinearLayout) findViewById(R.id.ll9);



        String h=getIntent().getStringExtra("hi");

        String h1="/6";
        String h2=h+h1;
        String ha1=getIntent().getStringExtra("ha");
        String bmi1=getIntent().getStringExtra("bmi");
        String smoke1=getIntent().getStringExtra("smoke");
        String active1=getIntent().getStringExtra("active");
        String diet1=getIntent().getStringExtra("diet");
        String diabetic1=getIntent().getStringExtra("diabetic");
        String bp1=getIntent().getStringExtra("bp");
        String ch1=getIntent().getStringExtra("ch");

        float bmi2=Float.valueOf(bmi1);
        float smoke2=Float.valueOf(smoke1);
        float active2=Float.valueOf(active1);
        float diet2=Float.valueOf(diet1);
        float diabetic2=Float.valueOf(diabetic1);
        float bp2=Float.valueOf(bp1);
        float ch2=Float.valueOf(ch1);


        hi.setText(h2);

        ha.setText(ha1);

        if (bmi2==0){

            ll3.setBackgroundColor(0xFFFF0000);
            bmi.setText("Being overweight significantly increases your risk of heart disease and type 2 diabetes, and is associated with higher cholesterol and blood pressure levels.");


        }

        else   if(bmi2>0&&bmi2<1){

            ll3.setBackgroundColor(0xFFFFFF00);
            bmi.setText("Being overweight or underweight slightly also effects your body and also is a sign that the body is vulnerable to major diseases as well as less immunity.  ");

        }

        else if(bmi2==1)
        {
            ll3.setBackgroundColor(0xFF00FF00);
            bmi.setText("Being a healthy weight significantly reduces your risk of heart disease and type 2 diabetes and contributes in strong immune system as well.  ");

        }


        if(active2==0){
            ll5.setBackgroundColor(0xFFFF0000);
            active.setText("Not Being Ative at all leads to overweight, more cholestrol and  less immunity of the body.Due to this body becomes vulnerable to diseases.");

        }

        else if(active2==0.5){

            ll5.setBackgroundColor(0xFFFFFF00);
            active.setText("Being Partially Active helps you stay healthy and fit.But it is recommended that the body should be fully active to fight from diseases.");
        }
        else if(active2==1){
            ll5.setBackgroundColor(0xFF00FF00);
            active.setText("Being Fully Active helps you stay healthy and fit.It helps in losing cholestrol and makes body stronger to fight from diseases. ");
        }


        if(diabetic2==0){
            ll7.setBackgroundColor(0xFFFF0000);
            diab.setText("Being Diabetic significantly increases your risk to heart diseases.Proper care should be taken with proper meals and sugar levels must be maintained. ");
        }
        else if(diabetic2==1){
            ll7.setBackgroundColor(0xFF00FF00);
            diab.setText("Not Being Diabetic significantly decreases your risk to heart diseases.Still Proper meals and sugar levels must be maintained");

        }

        if(diet2==0){
            ll6.setBackgroundColor(0xFFFF0000);
            diet.setText("Without a proper diet body weight loses and leads to underweight and diseases and effects the immune system of the body.");

        }
        else if(diet2>0&&diet2<1){
            ll6.setBackgroundColor(0xFFFFFF00);
            diet.setText("A stable diet helps in avoiding diseases in day to day life But a proper diet is necessary to stay fit and healthy.");

        }
        else if(diet2==1){
            ll6.setBackgroundColor(0xFF00FF00);
            diet.setText("Taking a proper diet helps to build a healthy and fit body.Stronger immune system as well as no diseases.");
        }


        if(bp2<0.5){
            ll8.setBackgroundColor(0xFFFF0000);
            bp.setText("High Blood Pressure is not at all good for the body.It has a major impact on heart related problems as well as breathing problems.");
        }
        else if(bp2>0.5&&bp2<1){
            ll8.setBackgroundColor(0xFFFFFF00);
            bp.setText("Moderate high or low Blood Pressure is also not considered good for the body.Proper care must be taken to maintain it's level.");
        }
        else if(bp2==1){
            ll8.setBackgroundColor(0xFF00FF00);
            bp.setText("Ideal Blood Pressure is very good for the body. It reduces the risk of heart diseases as well as helps in breathing problems.");
        }

        if(ch2<=0){
            ll9.setBackgroundColor(0xFF00FF00);
            chol.setText("Low cholestrol is very good as it reduces the risk of heart diseases and also it decrases your heart age by a considerable factor.");

        }
        else if(ch2<=2){
            ll9.setBackgroundColor(0xFFFFFF00);
            chol.setText("Moderate cholestrol is not considered good for the body as the body becomes vulnerable to heart diseases and increases heart age.");


        }
        else if(ch2>2){
            ll9.setBackgroundColor(0xFFFF0000);
            chol.setText("High cholestrol is very harmful for the body as it has a major impact on heart diseases and leads to respiratory problems.");
        }


        if(smoke2<0.3){
            ll4.setBackgroundColor(0xFFFF0000);
            smk.setText("Smoking regularly or very frequently are not at all good for health.It have great impact on heart and a major factor of increased heart age.");

        }
        else if(smoke2>0.3&&smoke2<1){
            ll4.setBackgroundColor(0xFFFFFF00);
            smk.setText("Smoking very rarely or stopped from 1-2 months are slightly better than regular smoking.To stay healthy, this must lead to non-smoking.");
        }
        else if(smoke2==1){
            ll4.setBackgroundColor(0xFF00FF00);
            smk.setText("Not at all smoking is very good for the body. It reduces the risk of heart diseases and heart age is also not affected.");
        }

    }



}

