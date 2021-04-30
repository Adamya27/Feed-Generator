package com.example.FeedGenerator.Service;

import com.example.FeedGenerator.Model.Feed;
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


@Component
public class FeedScheduler {

    @Value("${feed.url}")
    private String feedURL;

    @Autowired
    private FeedRepository feedRepository;


    @Scheduled(fixedDelay = 600000)
    public void cronJobFetchFeed() throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
       String response = restTemplate.getForObject(feedURL, String.class);
       JSONObject jsonObject = XML.toJSONObject(response);

        ObjectMapper mapper = new ObjectMapper();

        Feed feed = mapper.readValue(jsonObject.getJSONObject("rss").getJSONObject("channel").toString(), Feed.class) ;

       feedRepository.saveAll(feed.getItem());
    }


}
