package me.qisthi.pancaroba.api.manager;

import android.content.Context;
import android.content.res.Resources;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import me.qisthi.pancaroba.R;
import me.qisthi.pancaroba.model.LocationModel;
import me.qisthi.pancaroba.model.LocationResponse;

/**
 * Created by elan on 3/2/15.
 */
public class PlaceManager {
    private Context context;
    private RestTemplate restTemplate;
    private Resources resources;
    private ObjectMapper mapper;
    private String hostAddress;

    public PlaceManager(Context context)
    {
        this.context = context;
        restTemplate = new RestTemplate(true);
        resources = context.getResources();
        mapper = new ObjectMapper();
        hostAddress = resources.getString(R.string.location_host);
    }

    public LocationResponse getData(String query, String maxResult) throws HttpServerErrorException
    {
        String key = resources.getString(R.string.microsoft_key);
        hostAddress = hostAddress + "?query="+query+"&maxResults="+maxResult+"&key="+key;
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate.getForObject(hostAddress, LocationResponse.class);
    }

}
