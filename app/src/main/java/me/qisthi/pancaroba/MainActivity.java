package me.qisthi.pancaroba;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.activeandroid.ActiveAndroid;
import com.nispok.snackbar.Snackbar;

import java.util.List;

import me.qisthi.pancaroba.helper.NetworkUtil;
import me.qisthi.pancaroba.helper.WeatherBackgroundEvent;
import me.qisthi.pancaroba.model.LocationModel;

public class MainActivity extends Activity{
    private SearchView searchView;
    private Toolbar toolbar;
    public static List<LocationModel> locationCache;
    private RecyclerView recList;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize ActiveAndroid ORM
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main2);

        //bind variables
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        searchView = (SearchView) findViewById(R.id.searchLocation);
        recList = (RecyclerView) findViewById(R.id.cardList);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.accentColor));

        //bind events
        setActionBar(toolbar);
        getActionBar().setTitle("Pancaroba");


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getActionBar().setDisplayShowTitleEnabled(true);
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActionBar().setDisplayShowTitleEnabled(false);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //startup background process
                //check whether is connected or not
                if(NetworkUtil.isConnected(getApplicationContext()))
                {
                    getWeatherData();
                }else{
//           Toast.makeText(getApplicationContext(), "Your device is not connected to network", Toast.LENGTH_LONG).show();
                    Snackbar.with(getApplicationContext()).dismiss();
                    Snackbar.with(getApplicationContext())
                            .color(R.color.accentColor)
                            .text("Your device is not connected to network")
                            .show(MainActivity.this);
                    refreshLayout.setRefreshing(false);
                }
            }
        });
        //data init
        getWeatherData();

    }



    private void getWeatherData()
    {
        WeatherBackgroundEvent backgroundEvent = new WeatherBackgroundEvent();
        backgroundEvent.context = getApplicationContext();
        backgroundEvent.recList = recList;
        backgroundEvent.searchView = searchView;
        backgroundEvent.mainActivity = MainActivity.this;
        backgroundEvent.refreshLayout = refreshLayout;
        backgroundEvent.execute();
    }



}
