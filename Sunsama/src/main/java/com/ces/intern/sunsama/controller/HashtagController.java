package com.ces.intern.sunsama.controller;

import com.ces.intern.sunsama.dto.HashtagDTO;
import com.ces.intern.sunsama.http.response.HashtagResponse;
import com.ces.intern.sunsama.service.HashtagService;
import com.ces.intern.sunsama.util.ResponseMessage;
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
    public String createHashtag(@RequestBody HashtagDTO hashtagDTO)
    {
        return hashtagService.save(hashtagDTO);
    }
    @PutMapping("/{id}")
    public HashtagResponse updateHashtag(@RequestBody HashtagResponse hashtagRequest)
    {
        return hashtagService.update(hashtagRequest);
    }
    @DeleteMapping(value = "/{hashtagId}")
    public String deleteHashtag(@PathVariable Long hashtagId)
    {
        hashtagService.delete(hashtagId);
        return ResponseMessage.DELETE_SUCCESS;
    }
}
