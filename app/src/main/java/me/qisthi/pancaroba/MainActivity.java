package me.qisthi.pancaroba;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.activeandroid.ActiveAndroid;

import java.util.List;

import me.qisthi.pancaroba.adapter.LocationAdapter;
import me.qisthi.pancaroba.helper.LocationBackgroundEvent;
import me.qisthi.pancaroba.helper.SwipeableRecyclerViewTouchListener;
import me.qisthi.pancaroba.model.LocationModel;

public class MainActivity extends Activity{
    private SearchView searchView;
    private Toolbar toolbar;
    public static List<LocationModel> locationCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize ActiveAndroid ORM
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main2);

        //bind variables
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        searchView = (SearchView) findViewById(R.id.searchLocation);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        //attach our location data to recycler view
        locationCache = LocationModel.list();
        final LocationAdapter locationAdapter = new LocationAdapter(locationCache);
        recList.setAdapter(locationAdapter);

        //bind events
        setActionBar(toolbar);
        getActionBar().setTitle("Places");

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals(""))
                {
                    LocationBackgroundEvent locationBackgroundEvent = new LocationBackgroundEvent();
                    locationBackgroundEvent.context = getApplicationContext();
                    locationBackgroundEvent.query = query;
                    locationBackgroundEvent.maxResults = "10";
                    locationBackgroundEvent.locationAdapter = locationAdapter;
                    locationBackgroundEvent.execute();
                }else{
                    Toast.makeText(getApplicationContext(), "Please provide search query first", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recList,new SwipeableRecyclerViewTouchListener.SwipeListener() {
                    @Override
                    public boolean canSwipe(int position) {
                        return true;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        LocationModel location = null;
                        for (int position : reverseSortedPositions) {
                            location = locationCache.get(position);
                            LocationModel.remove(location);
                            locationCache.remove(position);
                            locationAdapter.notifyItemRemoved(position);
                        }
                        locationAdapter.notifyDataSetChanged();
                        if(location!=null)
                        {
                            Toast.makeText(getApplicationContext(), location.getLocationName()+" removed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        LocationModel location = null;
                        for (int position : reverseSortedPositions) {
                            location = locationCache.get(position);
                            LocationModel.remove(location);
                            locationCache.remove(position);
                            locationAdapter.notifyItemRemoved(position);
                        }
                        locationAdapter.notifyDataSetChanged();
                        if(location!=null)
                        {
                            Toast.makeText(getApplicationContext(), location.getLocationName()+" removed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recList.addOnItemTouchListener(swipeTouchListener);
    }



}
