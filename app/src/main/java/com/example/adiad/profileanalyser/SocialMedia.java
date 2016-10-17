package com.example.adiad.profileanalyser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;


public class SocialMedia extends Fragment {

    private GoogleApiClient client;
    CallbackManager callbackManager;
    TextView status ;
    Button likes;
    Button posts;
    LoginButton loginButton;
    private ProfilePictureView profilePictureView;
    AccessTokenTracker access_tracker;
    ProfileTracker profile_tracker;
    Profile currentProfile;
    AccessToken currentAccessToken;

    FacebookCallback<LoginResult> callback= new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            currentAccessToken = loginResult.getAccessToken();
            currentProfile= Profile.getCurrentProfile();
            displayinfo(currentProfile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };
    protected  void displayinfo(Profile profile ){
        if(profile!=null)
        {
            status.setText("welcome: "+profile.getName());
            profilePictureView.setProfileId(profile.getId());
            likes.setEnabled(true);
            posts.setEnabled(true);
        }else
        {
            profilePictureView.setProfileId(null);
            status.setText("Logged Out!, Please Log In");
            likes.setEnabled(false);
            posts.setEnabled(false);
        }
    }
    private static TextView tvfb;
    private static TextView tvtw;

    public SocialMedia()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_social_media, container, false);
        
//        tvfb=(TextView)rootView.findViewById(R.id.tvfb);
//        tvtw=(TextView)rootView.findViewById(R.id.tvtw);
//
//        BottomBar bottomBar = (BottomBar) rootView.findViewById(R.id.bottomBar);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                if (tabId == R.id.facebook) {
//                    tvfb.setVisibility(View.VISIBLE);
//                    tvtw.setVisibility(View.GONE);
//                }
//                if(tabId == R.id.twitter)
//                {
//                    tvfb.setVisibility(View.GONE);
//                    tvtw.setVisibility(View.VISIBLE);
//                }
//            }
//        });




        FacebookSdk.sdkInitialize(getContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        List<String> permissions = new ArrayList<String>();
        permissions.add("user_likes");
        permissions.add("user_posts");
        permissions.add("user_birthday");
        loginButton.setReadPermissions(permissions);
        loginButton.registerCallback(callbackManager,callback);
        status= (TextView)rootView.findViewById(R.id.status);
        likes = (Button)rootView.findViewById(R.id.likes);
        posts = (Button)rootView.findViewById(R.id.posts);
        profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.user_pic);
        displayinfo(Profile.getCurrentProfile());


        profilePictureView.setCropped(true);

        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getContext(),ShowPosts.class);
                i2.putExtra("currentAccessToken", AccessToken.getCurrentAccessToken());
                startActivity(i2);
            }
        });
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getContext(),ShowLikes.class);
                i1.putExtra("currentAccessToken",AccessToken.getCurrentAccessToken());
                startActivity(i1);
            }
        });
        access_tracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newtAccessToken) {
                currentAccessToken=newtAccessToken;
            }
        };
        profile_tracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                currentProfile=newProfile;
                displayinfo(currentProfile);
            }
        };
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();





        return rootView;

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.pramit.fblogin/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        access_tracker.startTracking();
        profile_tracker.startTracking();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.pramit.fblogin/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

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

}
