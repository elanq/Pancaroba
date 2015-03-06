package me.qisthi.pancaroba;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import me.qisthi.pancaroba.model.LocationModel;


public class WeatherDetailActivity extends Activity {
    private Toolbar toolbar;
    private LinearLayout rowContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        //load parcel
        LocationModel locationModel = getIntent().getParcelableExtra("weatherDetail");

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        toolbar.setTitle(locationModel.getLocationName());
        setActionBar(toolbar);
//        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //you are awesome. you finally get weather detail object
        //now bind the data to view

        rowContainer = (LinearLayout)findViewById(R.id.row_container);
        View view = findViewById(R.id.row_weather_summary);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(locationModel.getWeatherResponse().getHourly().getSummary());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_detail, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings : return true;
            case android.R.id.home :
                //maybe we should save the location data to cache first.
                //So we didn't even need to call forecast API over and over again
//                NavUtils.navigateUpFromSameTask(this);
                super.onBackPressed();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
