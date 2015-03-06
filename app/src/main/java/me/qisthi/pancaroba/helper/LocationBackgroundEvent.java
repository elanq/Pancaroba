package me.qisthi.pancaroba.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;

import me.qisthi.pancaroba.MainActivity;
import me.qisthi.pancaroba.R;
import me.qisthi.pancaroba.adapter.LocationAdapter;
import me.qisthi.pancaroba.api.manager.PlaceManager;
import me.qisthi.pancaroba.api.manager.WeatherManager;
import me.qisthi.pancaroba.model.LocationModel;
import me.qisthi.pancaroba.model.LocationResponse;

/*
The MIT License (MIT)

Copyright (c) 2015 elan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
public class LocationBackgroundEvent extends AsyncTask<Void, Void, LocationModel> {
    public Context context;
    public String query;
    public String maxResults;
    public LocationAdapter locationAdapter;
    public MainActivity mainActivity;

    private LocationModel locationModel = null;
    @Override
    protected void onPreExecute() {
        Snackbar.with(context)
                .color(R.color.accentColor)
                .text("Loading...")
                .show(mainActivity);

    }

    @Override
    protected void onPostExecute(LocationModel locationModel) {
        if(locationModel!=null)
        {
            MainActivity.locationCache.add(locationModel);
            locationAdapter.notifyItemInserted(MainActivity.locationCache.size() - 1);
            locationAdapter.notifyDataSetChanged();

            Snackbar.with(context)
                    .color(R.color.accentColor)
                    .text(locationModel.getLocationName()+ " added")
                    .show(mainActivity);

        }else{
            Snackbar.with(context)
                    .color(R.color.accentColor)
                    .text("Error loading data")
                    .show(mainActivity);
        }

    }

    @Override
    protected LocationModel doInBackground(Void... params) {
        PlaceManager placeManager = new PlaceManager(context);
        WeatherManager weatherManager = new WeatherManager(context);

        LocationResponse locationResponse= placeManager.getData(query, maxResults);
        if(locationResponse!=null) {
            if (locationResponse.getStatusCode() == 200) {
                if (locationResponse.getResourceSets().get(0).getEstimatedTotal() > 0) {
                    //save location data
                    locationModel = new LocationModel();
                    locationModel.setLocationId(System.currentTimeMillis());
                    locationModel.setLocationLat(locationResponse.getResourceSets().get(0).getResources().get(0).getPoint().getCoordinates()[0]);
                    locationModel.setLocationLng(locationResponse.getResourceSets().get(0).getResources().get(0).getPoint().getCoordinates()[1]);
                    locationModel.setLocationName(locationResponse.getResourceSets().get(0).getResources().get(0).getName());
                    locationModel.setLocationLastUpdated(System.currentTimeMillis());
                    locationModel = weatherManager.getWeatherData(locationModel);
                    locationModel.save();

                    return locationModel;
                }
            }
        }
        return null;
    }
}
