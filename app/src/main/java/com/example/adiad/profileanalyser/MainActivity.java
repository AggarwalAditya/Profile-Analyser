package com.example.adiad.profileanalyser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    private Button post_event_details;
   // private Toolbar toolbar;
    private TabLayout tabLayout;
    private PrefManager prefManager;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.health,
            R.drawable.social,
            R.drawable.internal,
            R.drawable.tick
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        prefManager=new PrefManager(this);
//        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
////                if (tabId == R.id.tab_favorites) {
////                    // The tab with id R.id.tab_favorites was selected,
////                    // change your content accordingly.
////                }
//            }
//        });

        //Toast.makeText(getBaseContext(),prefManager.get_event_name() , Toast.LENGTH_LONG).show();
        post_event_details=(Button)findViewById(R.id.post_event_details);

        if(getIntent().getExtras().getString("eventORnot").contentEquals("1"))
        {
            Toast.makeText(getBaseContext(),prefManager.get_event_name() , Toast.LENGTH_LONG).show();
            post_event_details.setVisibility(View.VISIBLE);
        }
        else
        {
            post_event_details.setVisibility(View.GONE);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setOffscreenPageLimit(3);


    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        if(getIntent().getExtras().getString("eventORnot").contentEquals("1"))
        {
            tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HealthFragment(), "Health");
        adapter.addFragment(new SocialMedia(callbackManager), "Social Media");
        adapter.addFragment(new InternalMemory(), "Internal Memory");
        if(getIntent().getExtras().getString("eventORnot").contentEquals("1"))
        {
            adapter.addFragment(new Fragment_4(), "Submit");
        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
