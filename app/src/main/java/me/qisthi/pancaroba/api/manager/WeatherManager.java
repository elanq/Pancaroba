package me.qisthi.pancaroba.api.manager;

import android.content.Context;
import android.content.res.Resources;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import me.qisthi.pancaroba.R;
import me.qisthi.pancaroba.model.LocationModel;
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
public class WeatherManager {
    private Context context;
    private RestTemplate restTemplate;
    private Resources resources;
    private ObjectMapper mapper;
    private String hostAddress;

    public WeatherManager(Context context)
    {
        this.context = context;
        restTemplate = new RestTemplate(true);
        resources = context.getResources();
        mapper = new ObjectMapper();
        hostAddress = resources.getString(R.string.weather_host);
    }

    public WeatherResponse getData(double latitude, double longitude)
    {
        final String urlData = hostAddress+latitude+","+longitude+"?units=auto";
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        WeatherResponse weatherResponse= restTemplate.getForObject(urlData, WeatherResponse.class);
        return weatherResponse;
    }

    public List<LocationModel> getWeatherData()
    {
        List<LocationModel> locations = new ArrayList<>();
        for(LocationModel location : LocationModel.list())
        {
            WeatherResponse weatherResponse = getData(location.getLocationLat(), location.getLocationLng());
            location.setWeatherResponse(weatherResponse);
            locations.add(location);
        }
        return locations;
    }

    public LocationModel getWeatherData(LocationModel location)
    {
        WeatherResponse weatherResponse = getData(location.getLocationLat(), location.getLocationLng());
        location.setWeatherResponse(weatherResponse);
        return location;
    }
}
