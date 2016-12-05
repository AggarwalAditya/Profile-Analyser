package com.example.adiad.profileanalyser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowPhotos extends AppCompatActivity {

    List<String> imageids;
    List<String> imageurls;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos);
        imageids  = new ArrayList<String>();
        imageurls = new ArrayList<String>();
        queue = Volley.newRequestQueue(this);
        if(!AccessToken.getCurrentAccessToken().isExpired()) {
            //  Log.d("Json", "start fn ");
            //pd.show();
            getpics(AccessToken.getCurrentAccessToken());

        }


    }

    protected void getpics(AccessToken cat)
    {

        GraphRequest request = GraphRequest.newGraphPathRequest(
                cat,
                "/me/photos",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject tempo= null;
                        try {
                            tempo = new JSONObject(response.getRawResponse());
                            getids(tempo.getJSONArray("data"));
                            String next = getnext(tempo);
                            // Log.d("Json","next 1: "+ next);

                            if(!next.isEmpty())
                                makeRequests(next);
                            // Log.d("Json","fin fn");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    protected void makeRequests(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    getids(response.getJSONArray("data"));
                    //Log.i("Json",response.getJSONArray("data").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String  next= getnext(response);
                // Log.i("Json","next : "+ next);
                if (!next.isEmpty())
                {
                    makeRequests(next);
                }
                else
                {
                    Log.d("Json"," ids :" +imageids.toString()+"\n");
                    // getlinks(AccessToken.getCurrentAccessToken());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jsObjRequest);
    }

    protected String getnext(JSONObject obj)
    {
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

    protected void getids(JSONArray data) throws JSONException {
        JSONObject temp;
        for(int i=0;i<data.length();i++) {
            temp = data.getJSONObject(i);
            imageids.add(temp.getString("id"));
            GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/"+imageids.get(i)+"/picture",
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            JSONObject obj= null;
                            try {
                                obj = new JSONObject(response.getRawResponse());
                                // Log.d("Json","URLS : "+response.toString()+"\n");
                                imageurls.add(obj.getJSONObject("data").getString("url"));
                                //Log.d("Json","URLS runtime : "+obj.getJSONObject("data").getString("url")+"\n");
                                if(imageids.size() == imageurls.size())
                                {
                                    Log.d("Json","URLS after : "+imageurls.toString()+"\n");

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("type", "normal");
            parameters.putBoolean("redirect", false);
            request.setParameters(parameters);
            request.executeAsync();
        }


    }

    protected void getlinks(AccessToken cat)
    {
        for(int i=0;i<imageids.size();i++)
        {
            GraphRequest request = GraphRequest.newGraphPathRequest(
                    cat,
                    "/"+imageids.get(i)+"/picture",
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            JSONObject obj= null;
                            try {
                                obj = new JSONObject(response.getRawResponse());
                                // Log.d("Json","URLS : "+response.toString()+"\n");
                                imageurls.add(obj.getJSONObject("data").getString("url"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("type", "normal");
            parameters.putBoolean("redirect", false);
            request.setParameters(parameters);
            request.executeAsync();
        }
        Log.d("Json", "URLS after : " + imageurls.toString() + "\n");
    }

}

