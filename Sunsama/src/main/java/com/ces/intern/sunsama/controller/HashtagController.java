package com.ces.intern.sunsama.controller;

import com.ces.intern.sunsama.http.request.HashtagRequest;
import com.ces.intern.sunsama.service.HashtagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin
@RestController
@RequestMapping("/hashtags")
public class HashtagController {
    private final ModelMapper modelMapper;
    private final HashtagService hashtagService;
    @Autowired
    public HashtagController(ModelMapper modelMapper, HashtagService hashtagService) {
        this.modelMapper = modelMapper;
        this.hashtagService = hashtagService;
    }

    @GetMapping("")
    public List getAllHashtag()
    {
        return hashtagService.getAllHashtag();
    }
    @PostMapping("/")
    public String createHashtag(@RequestBody HashtagRequest hashtagRequest)
    {
        return hashtagService.save(hashtagRequest);
    }
}
