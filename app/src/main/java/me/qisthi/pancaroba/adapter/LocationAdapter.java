package me.qisthi.pancaroba.adapter;

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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.qisthi.pancaroba.R;
import me.qisthi.pancaroba.model.LocationModel;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{
    private List<LocationModel> locations;

    public LocationAdapter(List<LocationModel> locations) {
        this.locations = locations;
    }

    //Bind location data into viewholder
    @Override
    public void onBindViewHolder(LocationViewHolder locationViewHolder, int i) {
        LocationModel locationModel = locations.get(i);
        locationViewHolder.locationName.setText(locationModel.getLocationName());
    }

    //Inflate the item view in viewholder to view
    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    //This class simply act as a view holder. Which means this class contains view information to be inflated
    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        protected TextView locationName;

        public LocationViewHolder(View itemView) {
            super(itemView);
            locationName = (TextView) itemView.findViewById(R.id.txtName);
        }
    }


}

