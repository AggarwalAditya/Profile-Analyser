package com.example.adiad.profileanalyser;

import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InternalMemory extends Fragment {

    private PackageManager packageManager=null;
    private List<ApplicationInfo> applist=null;
    private ApplicationAdapter listadaptor=null;
    private ListView listView;
    private PieChart mPieChart,cat_piechart;
    private ProgressDialog progress;
    private ArrayList<Float> mylist=new ArrayList<>();
    private ArrayList<String> package_names=new ArrayList<>();
    private static float social=0;
    private static float sports=0;
    private static float others=0;
    private static float maps_navigation=0;

    public InternalMemory()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_internal_memory, container, false);
        mPieChart=(PieChart)rootView.findViewById(R.id.piechart);
        cat_piechart=(PieChart)rootView.findViewById(R.id.piechart2);

        BottomBar bottomBar = (BottomBar) rootView.findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.data_usage) {
                    mPieChart.setVisibility(View.VISIBLE);
                    cat_piechart.setVisibility(View.GONE);
                }
                if(tabId == R.id.category)
                {
                    mPieChart.setVisibility(View.GONE);
                    cat_piechart.setVisibility(View.VISIBLE);
                    cat_piechart.clearChart();
                    making_category_piechart(cat_piechart, social, maps_navigation, sports);
                }
            }
        });

        packageManager = rootView.getContext().getPackageManager();
        listView=(ListView)rootView.findViewById(R.id.list);
        new LoadApplications().execute();
        new Crawling().execute();
        making_category_piechart(cat_piechart, social, maps_navigation, sports);

        mPieChart.startAnimation();
        cat_piechart.startAnimation();

        return rootView;
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        //JSONArray packages=new JSONArray();
        //ArrayList<Integer> uidlist=new ArrayList<>();
        int col =Color.parseColor("#FE6DA8");
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                    int id=info.uid;
                    long recived = TrafficStats.getUidRxBytes(id);
                    long send = TrafficStats.getUidTxBytes(id);
            //        packages.put(info.packageName);
                    package_names.add(info.packageName);
                    float val=(float)(recived+send);///(float)(100.0);
                   // mylist.add(val);
                    String mystring =" "+info.loadLabel(packageManager);
                   // System.out.println(mystring);
                    mPieChart.addPieSlice(new PieModel(mystring+" ",val, col));

                    col=col+1000;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }

    private void making_category_piechart(PieChart cat_piechart,float social,float maps_navigation,float sports)
    {
        int col =Color.parseColor("#FE6DA8");
        col=col+4000;
        cat_piechart.addPieSlice(new PieModel("Social" ,social, col));
        col=col+9000;
        cat_piechart.addPieSlice(new PieModel("Sports" ,sports, col));
        col=col+7000;
        cat_piechart.addPieSlice(new PieModel("Maps & navigation" ,maps_navigation, col));
//        col=col+1000;
//        cat_piechart.addPieSlice(new PieModel("Others" ,others, col));
    }

    private class Crawling extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getContext());
            progress.setTitle("Categorizing your apps");
            progress.setMessage("Loading...");
            progress.setIndeterminate(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for(int i=0;i<package_names.size();i++)
            {
                String new_package=package_names.get(i);
                if(new_package.contains("com.example")==false)
                {
                    String url="https://play.google.com/store/apps/details?id="+new_package;
                    Document doc=null;
                    String genre="";
                    try {
                        doc = Jsoup.connect(url).get();
                        try {
                            genre=doc.select("a.document-subtitle.category").text();
                            Log.d("category",genre);
                        }
                        catch (NullPointerException n)
                        {
                            n.printStackTrace();
                        }

                    } catch (IOException e) {

                        e.printStackTrace();
                    }


                    switch (genre)
                    {
                        case "Social":
                            ++social;
                            break;
                        case "Sports":
                            ++sports;
                            break;
                        case "Maps & Navigation":
                            ++maps_navigation;
                            break;
                        default:
                            ++others;

                    }

                }
            }


            Log.e("cat.", "social" + social);
            Log.e("cat.", "sports " + sports);
            Log.e("cat.", "maps " + maps_navigation);
            Log.e("cat.", "others " + others);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();
            super.onPostExecute(result);

        }
    }

    private class LoadApplications extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(getContext());
            progress.setTitle("Your Apps");
            progress.setMessage("Loading...");
            progress.setIndeterminate(false);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(getContext(),
                    R.layout.snippet_list_row, applist);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result)
        {
            listView.setAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }




    }


}
