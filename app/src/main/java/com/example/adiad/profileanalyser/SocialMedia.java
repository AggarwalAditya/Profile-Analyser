package com.example.adiad.profileanalyser;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class SocialMedia extends Fragment  implements ConnectivityReceiver.ConnectivityReceiverListener {

    private GifImageView gif;
    public GoogleApiClient client;
    CallbackManager callbackManager;
    TextView status;
    Button likes;
    Button posts;
    LoginButton loginButton;
    private ProfilePictureView profilePictureView;
    AccessTokenTracker access_tracker;
    ProfileTracker profile_tracker;
    Profile currentProfile;
    AccessToken currentAccessToken;

    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            currentAccessToken = loginResult.getAccessToken();
            currentProfile = Profile.getCurrentProfile();
            Log.i("messageerror", "chal gaya");
            displayinfo(currentProfile);
        }

        @Override
        public void onCancel() {
            Log.i("messageerror", "cancel ho gaya");
        }

        @Override
        public void onError(FacebookException error) {

            Log.i("messageerror", "error aa gaya");
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    protected void displayinfo(Profile profile) {
        if (profile != null) {
            //status.setText("welcome: " + profile.getName());
            profilePictureView.setProfileId(profile.getId());
            likes.setEnabled(true);
            posts.setEnabled(true);
            likes.setVisibility(View.VISIBLE);
            posts.setVisibility(View.VISIBLE);
            profilePictureView.setVisibility(View.VISIBLE);
            //  status.setVisibility(View.VISIBLE);
        } else {
            profilePictureView.setProfileId(null);
            // status.setText("Logged Out!, Please Log In");
            likes.setEnabled(false);
            posts.setEnabled(false);
            likes.setVisibility(View.GONE);
            posts.setVisibility(View.GONE);
            profilePictureView.setVisibility(View.GONE);
            // status.setVisibility(View.GONE);
        }

    }

    private static TextView tvfb;
    private static TextView tvtw;

    public SocialMedia() {

    }

    public SocialMedia(CallbackManager managern) {
        this.callbackManager = managern;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getContext());
        callbackManager = CallbackManager.Factory.create();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_social_media, container, false);
//        tvfb=(TextView)rootView.findViewById(R.id.tvfb);
//        tvtw=(TextView)rootView.findViewById(R.id.tvtw);
//c
        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        checkConnection(rootView);
        List<String> permissions = new ArrayList<String>();
        permissions.add("user_likes");
        permissions.add("user_posts");
        permissions.add("user_birthday");
        permissions.add("user_photos");

        loginButton.setReadPermissions(permissions);
        loginButton.registerCallback(callbackManager, callback);
        // status = (TextView) rootView.findViewById(R.id.status);
        likes = (Button) rootView.findViewById(R.id.likes);
        posts = (Button) rootView.findViewById(R.id.posts);
        profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.user_pic);
        displayinfo(Profile.getCurrentProfile());
        BottomBar bottomBar = (BottomBar) rootView.findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.facebook) {
                    checkConnection(rootView);
                    loginButton.setVisibility(View.VISIBLE);
                    likes.setVisibility(View.VISIBLE);
                    posts.setVisibility(View.VISIBLE);

                    profilePictureView.setVisibility(View.VISIBLE);
                    // status.setVisibility(View.VISIBLE);


                }
                if (tabId == R.id.twitter) {
                    checkConnection(rootView);
                    loginButton.setVisibility(View.GONE);
                    likes.setVisibility(View.GONE);
                    posts.setVisibility(View.GONE);
                    profilePictureView.setVisibility(View.GONE);
                    //status.setVisibility(View.GONE);
                }
            }
        });


        profilePictureView.setCropped(true);

        if (Profile.getCurrentProfile() == null) {

        }
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getContext(), ShowPosts.class);
                i2.putExtra("currentAccessToken", AccessToken.getCurrentAccessToken());
                startActivity(i2);

            }
        });
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i1 = new Intent(getContext(), ShowLikes.class);
//                i1.putExtra("currentAccessToken", AccessToken.getCurrentAccessToken());
//                startActivity(i1);
                get_picture();
            }
        });
        access_tracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newtAccessToken) {
                currentAccessToken = newtAccessToken;
            }
        };
        profile_tracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                currentProfile = newProfile;
                displayinfo(currentProfile);
            }
        };
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.

        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();

        return rootView;

    }


    //
    @Override
    public void onDestroy() {
        super.onDestroy();
        access_tracker.stopTracking();
        profile_tracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SocialMedia Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.adiad.profileanalyser/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SocialMedia Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.adiad.profileanalyser/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
    }

    private void checkConnection(View rootView) {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected, rootView);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected, View rootView) {

        String message;
        int color;
        likes = (Button) rootView.findViewById(R.id.likes);
        posts = (Button) rootView.findViewById(R.id.posts);
        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.user_pic);
        gif = (GifImageView) rootView.findViewById(R.id.gif);
        if (isConnected) {
            message = "Good! Connected to Internet";
            gif.setVisibility(View.GONE);
            likes.setVisibility(View.VISIBLE);
            posts.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            profilePictureView.setVisibility(View.VISIBLE);
            color = Color.WHITE;


        } else {
            message = "Sorry! Not connected to internet";

            color = Color.RED;


            gif.setVisibility(View.VISIBLE);
            likes.setVisibility(View.GONE);
            posts.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            profilePictureView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected, getView());
    }

    public void get_picture() {

        final ArrayList<String> ar = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                currentAccessToken,
                "me/photos/uploaded?fields=id,picture", new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        try {
                            Log.e("response", response.toString());
                            JSONObject jsonObject = response.getJSONObject();
                            JSONArray jsonArray_data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray_data.length(); i++) {
                                JSONObject js = jsonArray_data.getJSONObject(i);
                                ar.add(i, js.getString("id"));
                                Log.d("111", i + " " + js.getString("id") + " " + js.getString("picture"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        request.executeAndWait();
        Log.e("error", ar.toString());
        for (int i = 0; i < ar.size(); i++) {
            get_large_image(ar.get(i));
        }
    }
    }.start();

//        pg.dismiss();
//        for
        }

    public void get_large_image(String id) {
        final String temp = id;
//        ProgressDialog pg=new ProgressDialog(getContext());
//        pg.show();
//        final HashMap<String ,String> hmp=new HashMap<>(100);
       new Thread(){
            @Override
            public void run() {
        new GraphRequest(
                currentAccessToken,
                temp + "/picture?fields=url&redirect=0",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
        /* handle the result */
                        try {
                            Log.e("777", response.toString());
                            JSONObject jsonObject = response.getJSONObject();
//                                        JSONObject js=jsonObject.getJSONObject("data").getString("url");
//                                        hmp.put(js.getString("id"),js.getString("picture"));
                            Log.d("88888", " ::" + jsonObject.getJSONObject("data").getString("url"));
                            String answer=POST(jsonObject.getJSONObject("data").getString("url"));
                            Log.e("ans",answer);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

       }.start();
//        pg.dismiss();
//        Log.e("error",hmp.toString());
    }
    public static String POST(String image_url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost("https://api.projectoxford.ai/emotion/v1.0/recognize");

            String json = "";

            // 3. build jsonObject
            //String movie=movie_name.getText().toString();
            JSONObject jsonObject = new JSONObject();
            //jsonObject.accumulate("movie", movie);


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity("{'url':"+"\'"+image_url+"\'}");

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Ocp-Apim-Subscription-Key", "98c5f335e32f4d10b735f92b61b9c371");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }


    private static  String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }



}



