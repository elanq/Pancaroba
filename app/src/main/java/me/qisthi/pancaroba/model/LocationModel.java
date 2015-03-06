package me.qisthi.pancaroba.model;/*
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

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Location")
public class LocationModel extends Model implements Parcelable{
    public LocationModel instance;
    @Column(name = "locationId", unique = true)
    private Long locationId;
    @Column(name = "locationName")
    private String locationName;
    @Column(name = "locationLat")
    private double locationLat;
    @Column(name = "locationLng")
    private double locationLng;
    @Column(name = "locationLastUpdated")
    private Long locationLastUpdated;
    private WeatherResponse weatherResponse;

    public LocationModel() {
        super();
    }

    public LocationModel(Long locationId, String locationName, double locationLat, double locationLng, Long locationLastUpdated) {
        super();
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.locationLastUpdated = locationLastUpdated;
    }



    @Override
    public String toString() {
        return "LocationModel{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", locationLat=" + locationLat +
                ", locationLng=" + locationLng +
                ", locationLastUpdated=" + locationLastUpdated +
                '}';
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(double locationLng) {
        this.locationLng = locationLng;
    }

    public Long getLocationLastUpdated() {
        return locationLastUpdated;
    }

    public void setLocationLastUpdated(Long locationLastUpdated) {
        this.locationLastUpdated = locationLastUpdated;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public static List<LocationModel> list()
    {
        return new Select()
                .all().from(LocationModel.class).execute();
    }

    public static void remove(Long locationId)
    {
        new Delete().from(LocationModel.class).where("locationId = ?",locationId).execute();
    }

    public static void remove(LocationModel location)
    {
        if(location!=null)
        {
            Long locationId = location.getLocationId();
            new Delete().from(LocationModel.class).where("locationId = ?",locationId).execute();
        }
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel source) {
            return new LocationModel(source);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    private LocationModel(Parcel in)
    {
//        instance = in.readParcelable(getClass().getClassLoader());
        locationId = in.readLong();
        locationName = in.readString();
        weatherResponse = (WeatherResponse) in.readValue(WeatherResponse.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeParcelable(instance, flags);
        dest.writeLong(locationId);
        dest.writeString(locationName);
        dest.writeValue(weatherResponse);
    }
}
