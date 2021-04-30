package com.example.FeedGenerator.Controller;

import com.example.FeedGenerator.Model.FeedItemDao;
import com.example.FeedGenerator.Repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/app/v1/feed")
public class FeedController {

    @Autowired
    private FeedRepository feedRepository;

    @Value("${feed.size}")
    private Integer feedSize;



    @GetMapping
    public ResponseEntity<Object> getFeedData(@RequestParam(defaultValue = "0") Integer offset){
        List<FeedItemDao> list = feedRepository.findAll();
        Integer lastItemIndex = offset + feedSize < list.size() ? offset + feedSize : list.size();
        List<FeedItemDao> listToBeSent = offset < lastItemIndex ? list.subList(offset, lastItemIndex ) : new ArrayList<>();
        HashMap<String,Object> response = new HashMap<>();
        Boolean nextPage = list.size() > offset + feedSize;

        response.put("size", listToBeSent.size());
        response.put("nextPage",nextPage);
        response.put("items", listToBeSent);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchQueryString(@RequestParam String query){

        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(query);
        List<FeedItemDao> searchResult = feedRepository.findAllBy(textCriteria);

        HashMap<String,Object> response = new HashMap<>();
        response.put("size", searchResult.size());
        response.put("searchQuery", query);
        response.put("items", searchResult);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
