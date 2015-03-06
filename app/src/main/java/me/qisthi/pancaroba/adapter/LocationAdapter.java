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

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.qisthi.pancaroba.MainActivity;
import me.qisthi.pancaroba.R;
import me.qisthi.pancaroba.WeatherDetailActivity;
import me.qisthi.pancaroba.model.LocationModel;
import me.qisthi.pancaroba.model.WeatherModel;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{
    private List<LocationModel> locations;
    MainActivity mainActivity;

    public LocationAdapter(List<LocationModel> locations, MainActivity mainActivity) {
        this.locations = locations;
        this.mainActivity = mainActivity;
    }

    //Bind location data into viewholder
    @Override
    public void onBindViewHolder(LocationViewHolder locationViewHolder, final int i) {
        final LocationModel locationModel = locations.get(i);
        String icon = locationModel.getWeatherResponse().getCurrently().getIcon();
        String temperature = Math.abs(locationModel.getWeatherResponse().getCurrently().getTemperature())+"";

        locationViewHolder.locationNameText.setText(locationModel.getLocationName());
        locationViewHolder.temperatureText.setText(temperature);
        locationViewHolder.imageWeather.setImageResource(getWeatherIcon(icon));
        locationViewHolder.imageDegree.setImageResource(R.drawable.celcius);

        locationViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change activity
                Intent intent = new Intent(mainActivity, WeatherDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("weatherDetail", locationModel);
                mainActivity.startActivity(intent);
            }
        });

    }

    //Inflate the item view in viewholder to view
    @Override
    public LocationViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public int getWeatherIcon(String icon)
    {
        switch (icon)
        {
            case WeatherModel.CLEAR_DAY : return R.drawable.clearday;
            case WeatherModel.CLEAR_NIGHT : return R.drawable.clearnight;
            case WeatherModel.CLOUDY : return R.drawable.cloudy;
            case WeatherModel.FOG : return R.drawable.fog;
            case WeatherModel.PARTLY_CLOUDY_DAY : return R.drawable.partlycloudyday;
            case WeatherModel.PARTLY_CLOUDY_NIGHT : return R.drawable.partlycloudynight;
            case WeatherModel.RAIN : return R.drawable.rain;
            case WeatherModel.SLEET : return R.drawable.sleet;
            case WeatherModel.SNOW: return R.drawable.snow;
            case WeatherModel.WIND: return R.drawable.wind;
            default: return R.drawable.fullmoon;
        }
    }

    //This class simply act as a view holder. Which means this class contains view information to be inflated
    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        protected TextView locationNameText;
        protected TextView temperatureText;
        protected ImageView imageWeather;
        protected ImageView imageDegree;



        public LocationViewHolder(View itemView) {
            super(itemView);
            locationNameText = (TextView) itemView.findViewById(R.id.txtName);
            temperatureText = (TextView) itemView.findViewById(R.id.txtTemperature);
            imageWeather = (ImageView) itemView.findViewById(R.id.imageWeather);
            imageDegree = (ImageView) itemView.findViewById(R.id.imageDegree);
        }
    }


}

