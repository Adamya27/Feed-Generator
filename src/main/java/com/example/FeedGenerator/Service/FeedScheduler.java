package com.example.FeedGenerator.Service;

import com.example.FeedGenerator.Model.Feed;
import com.example.FeedGenerator.Model.FeedItemDao;
import com.example.FeedGenerator.Repository.FeedRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class FeedScheduler {

    @Value("${feed.url}")
    private String feedURL;

    @Value("${time.conversion.estimate}")
    private Integer timeConversionConstant;

    @Autowired
    private FeedRepository feedRepository;


    @Scheduled(fixedDelay = 600000000)
    public void cronJobFetchFeed() throws JsonProcessingException, ParseException {

        RestTemplate restTemplate = new RestTemplate();
       String response = restTemplate.getForObject(feedURL, String.class);
       JSONObject jsonObject = XML.toJSONObject(response);

        ObjectMapper mapper = new ObjectMapper();

        Feed feed = mapper.readValue(jsonObject.getJSONObject("rss").getJSONObject("channel").toString(), Feed.class) ;
        List<FeedItemDao> list= new ArrayList<FeedItemDao>(feed.getItem());
        for (FeedItemDao feedItemDao: list){
            feedItemDao.setDate(transformDate(feedItemDao.getPubDate()));
        }

       feedRepository.saveAll(feed.getItem());
    }

    public Timestamp transformDate(String date) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        if(date == null){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis() - timeConversionConstant);

            return timestamp;
        }

        String[] dateArray = date.split(" ");
        String modifiedDate = dateArray[3] + "-"+getMonth(dateArray[2])+"-"+dateArray[1]+" "+dateArray[4];
        Timestamp timestamp = Timestamp.valueOf(modifiedDate);;

        return timestamp;
    }

    public String getMonth(String month){

        switch (month){
            case "Jan": return "01";
            case "Feb": return "02";
            case "Mar": return "03";
            case "Apr": return "04";
            case "May": return "05";
            case "Jun": return "06";
            case "Jul": return "07";
            case "Aug": return "08";
            case "Sep": return "09";
            case "Oct": return "10";
            case "Nov": return "11";
            case "Dec": return "12";
            default: return "01";

        }
    }


}
