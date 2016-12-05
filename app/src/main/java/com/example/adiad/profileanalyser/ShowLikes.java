package com.example.adiad.profileanalyser;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowLikes extends AppCompatActivity {

    public PrefManager prefManager;
    String enterainment[] = {"Enterainment","TV", "Series","Movie", "Show","Television","Play"};
    String news[] = {"News","Media", "Magazine"};
    String e_comm[] = {"Ecommerce","E-Commerce", "Shopping","Retail", "Brand","Telecom","Clothing","Product"};
    String tech[] = {"Tech","Computer", "App","Internet", "Website","Web","Company"};
    String people[] = {"Community","Social", "Club","Group", "Cultural"};
    String sports[] = {"Sports","Team", "Cricket","Football", "Basketball"};
    String health[] = {"Health","Beauty", "Fitness","Yoga", "Gym","Exercise","Nutrition"};
    String food[] = {"Food","Beverage", "Dish","Drink"};
    String music[] = {"Music","Band", "Lyrics","Artist","Album","Song"};
    String education[] = {"School","University","College","Study"};
    int  centerainment =0;
    int  cnews =0;
    int cecomm = 0;
    int ctech = 0;
    int cpeople = 0;
    int cfood =0;
    int chealth = 0;
    int ceducation = 0;
    int csports = 0;
    int cmusic = 0;
    int cothers= 0;
    ProgressDialog pd;
    AccessToken currentAccessToken;
    TextView disp;
    RequestQueue queue;
    List<String> likes = new ArrayList<String>();

    String getnext(JSONObject obj) {
        String next_url ="";
        try {
            if(obj.getJSONObject("paging").has("next"))
            {
                next_url= obj.getJSONObject("paging").getString("next");
            }
            //disp.setText(next_url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return next_url;
    }

    void makeRequest(String url) {
        //disp.setText(url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    show_likes(response.getJSONArray("data"));
                    //Log.i("Json",response.getJSONArray("data").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String  next=getnext(response);
                if (!next.isEmpty())
                {
                    makeRequest(next);
                }
                else
                {

                    float total = likes.size();
                    float   penterainment = (centerainment/total)*100;
                    float phealth = (centerainment/total)*100;
                    float pnews = (cnews/total)*100;
                    float pecomm = (cecomm/total)*100;
                    float psports= (csports/total)*100;
                    float ptech = (ctech/total)*100;
                    float pmusic = (cmusic/total)*100;
                    float pothers =(cothers/total)*100;
                    float ppeople = (cpeople/total)*100;
                    float peducation = (ceducation/total)*100;
                    disp.setText("(1)entertainment : "+ penterainment + " (2) tech : "+ ptech +" (3) sports :"+ psports +" (4)music : "+ pmusic+" (5)people :"+ppeople+ " (6)news :" + pnews+ " (7)E-Commerce :"+ pecomm+" (8) education "+peducation + " (9)health"+ phealth +" (10)others :" + pothers);
                    prefManager.set_likes_info(penterainment + " " + ptech + " " + psports + " " + pmusic + " "+ppeople+ " " + pnews+ " "+ pecomm+" "+peducation + " "+ phealth +" " + pothers);
                    make_UI_changes(penterainment,phealth,pnews,pecomm,psports,ptech,pmusic,pothers,ppeople,peducation);
                    pd.hide();
                }


                //  boolean done = categorise(likes);
               /* if(done)
                {

                }*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        queue.add(jsObjRequest);

    }
    void show_likes(JSONArray objects) throws JSONException {
        //disp.setText(objects.getJSONObject(0).toString());
        for (int i = 0; i < objects.length(); ++i) {
            JSONObject object = null;
            try {
                object = objects.getJSONObject(i);
                likes.add(object.getString("category"));
                categorise(object.getString("category"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    public PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_likes2);
        prefManager=new PrefManager(this);
        Bundle extras = getIntent().getExtras();
        currentAccessToken = (AccessToken) extras.get("currentAccessToken");
        disp = (TextView) findViewById(R.id.disp);
        pieChart=(PieChart)findViewById(R.id.piechart);
        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.show();
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            show_likes(object.getJSONObject("likes").getJSONArray("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String next = null;
                        try {
                            next = getnext(object.getJSONObject("likes"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //disp.setText(next);
                        if(!next.isEmpty()){
                            makeRequest(next);
                        }


                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "likes{category}");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void make_UI_changes(float penterainment,float phealth,float pnews,float pecomm,float psports,float ptech,float pmusic,float pothers,float ppeople,float peducation)
    {
        disp.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);
        int index=0;
        int col = Color.parseColor("#FE6DA8");
        col=col+4000;
        pieChart.addPieSlice(new PieModel("Entertainment" ,penterainment,col));

        col=col+4000;
        pieChart.addPieSlice(new PieModel("Health" ,phealth,col));

        col=col+4000;
        pieChart.addPieSlice(new PieModel("News" ,pnews,col));

        col=col+4000;
        pieChart.addPieSlice(new PieModel("E-commerce" ,pecomm,col));

        col=col+4000;
        pieChart.addPieSlice(new PieModel("Sports" ,psports,col));

        col=col+4000;
        pieChart.addPieSlice(new PieModel("Technology" ,ptech,col));
        col=col+4000;
        pieChart.addPieSlice(new PieModel("Music" ,pmusic,col));
        col=col+4000;
        pieChart.addPieSlice(new PieModel("Education" ,peducation,col));
        col=col+4000;
        pieChart.addPieSlice(new PieModel("Others" ,pothers,col));
        col=col+4000;
        pieChart.addPieSlice(new PieModel("People" ,ppeople,col));


    }

    protected boolean categorise(String like)
    {
        int flag=1;
       /* for(int i=0;i<likes.size();i++)
        {*/
        String temp = like;
        for(int j=0 ; j< enterainment.length;j++)
        {
            if(temp.contains(enterainment[j]))
            {
                centerainment++;
                flag=0;
                break;
            }
        }
        for(int j=0 ; j< news.length;j++)
        {
            if(temp.contains(news[j]))
            {
                cnews++;
                flag=0;
                break;
            }
        }
        for(int j=0 ; j< e_comm.length;j++)
        {
            if(temp.contains(e_comm[j]))
            {
                cecomm++;
                flag=0;
                break;
            }
        }
        for(int j=0 ; j< tech.length;j++)
        {
            if(temp.contains(tech[j]))
            {
                ctech++;
                flag=0;
                break;
            }
        }
        for(int j=0 ; j< people.length;j++)
        {
            if(temp.contains(people[j]))
            {
                cpeople++;
                flag=0;
                break;
            }
        }
        for(int j=0 ; j< food.length;j++)
        {
            if(temp.contains(food[j]))
            {
                cfood++;
                flag=0;
                break;
            }

        }
        for(int j=0 ; j< health.length;j++)
        {
            if(temp.contains(health[j]))
            {
                chealth++;
                flag=0;
                break;
            }
        }
        for(int j=0 ; j< sports.length;j++)
        {
            if(temp.contains(sports[j]))
            {
                csports++;
                flag=0;
                break;
            }
        }
        for(int j=0 ; j< education.length;j++)
        {
            if(temp.contains(education[j]))
            {
                ceducation++;
                flag=0;
                break;
            }
        }
        for(int j=0 ; j< music.length;j++)
        {
            if(temp.contains(music[j]))
            {
                cmusic++;
                flag=0;
                break;
            }
        }

        if(flag==1)
        {
            cothers++;
        }

        //  }
        return true;
    }
}