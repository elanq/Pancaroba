package me.qisthi.pancaroba.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import me.qisthi.pancaroba.MainActivity;
import me.qisthi.pancaroba.adapter.LocationAdapter;
import me.qisthi.pancaroba.api.manager.PlaceManager;
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
public class LocationBackgroundEvent extends AsyncTask<Void, Void, LocationResponse> {
    public TextView textResult;
    public Context context;
    public String query;
    public String maxResults;
    public LocationAdapter locationAdapter;

    private LocationModel locationModel;
    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Loading....", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPostExecute(LocationResponse locationResponse) {
        if(locationResponse!=null)
        {
            if(locationResponse.getStatusCode()==200)
            {
                if(locationResponse.getResourceSets().get(0).getEstimatedTotal()>0)
                {
                    //save location data
                    locationModel = new LocationModel();
                    locationModel.setLocationId(System.currentTimeMillis());
                    locationModel.setLocationLat(locationResponse.getResourceSets().get(0).getResources().get(0).getPoint().getCoordinates()[0]);
                    locationModel.setLocationLng(locationResponse.getResourceSets().get(0).getResources().get(0).getPoint().getCoordinates()[1]);
                    locationModel.setLocationName(locationResponse.getResourceSets().get(0).getResources().get(0).getName());
                    locationModel.setLocationLastUpdated(System.currentTimeMillis());
                    locationModel.save();

                    MainActivity.locationCache.add(locationModel);
                    locationAdapter.notifyItemInserted(MainActivity.locationCache.size()-1);
                    locationAdapter.notifyDataSetChanged();

                    Toast.makeText(context,locationModel.getLocationName()+ " added",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Query "+query+" not found",Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(context,"Error loading data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected LocationResponse doInBackground(Void... params) {
        PlaceManager placeManager = new PlaceManager(context);
        return placeManager.getData(query, maxResults);
    }
}
