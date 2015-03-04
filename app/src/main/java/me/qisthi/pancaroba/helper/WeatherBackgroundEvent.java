package me.qisthi.pancaroba.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import me.qisthi.pancaroba.api.manager.WeatherManager;
import me.qisthi.pancaroba.model.WeatherResponse;

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
public class WeatherBackgroundEvent extends AsyncTask<Void, Void, WeatherResponse> {
    public TextView textTemperature;
    public Context context;
    public double latitude;
    public double longitude;

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Loading....", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(WeatherResponse weatherResponse) {

        if(textTemperature!=null)
        {
            textTemperature.setText(weatherResponse.getCurrently().getTemperature()+" \u00b0C");
        }
        Toast.makeText(context,"Finished",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected WeatherResponse doInBackground(Void... params) {

        WeatherManager weatherManager = new WeatherManager(context);
//        double longitude = 107.60936;
//        double latitude = -6.9179;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentTime = dateFormat.format(System.currentTimeMillis());
        return weatherManager.getData(latitude, longitude, currentTime);
    }
}
