package com.example.services;

import com.example.config.TimeAPIConfig;
import com.example.model.TimeAPIModel;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TimeAPIServiceImpl implements TimeAPIService {

    private TimeAPIConfig timeAPIConfig;


    public TimeAPIServiceImpl(TimeAPIConfig timeAPIConfig) {
        this.timeAPIConfig = timeAPIConfig;
    }

    @Override
    public String getCurrentTime(String timeZone) {
        String url = timeAPIConfig.getEndpoint();
        // 60 seconds for connection timeout, 90 seconds for socket timeout
        try{
            HttpResponse<TimeAPIModel> response = Unirest.get(url).connectTimeout(60)
                    .queryString("timeZone", timeAPIConfig.getContinent()+"/"+timeZone)
                    .asObject(TimeAPIModel.class);

            return response.getBody().getDateTime();
        }catch (Exception e){
            System.err.println("Error with fetching data from time api");
            return "2025-08-03T16:39:41.default";
        }

    }


}
