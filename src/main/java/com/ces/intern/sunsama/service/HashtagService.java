package com.ces.intern.sunsama.service;

import com.ces.intern.sunsama.dto.HashtagDTO;

import java.util.List;

public interface HashtagService{
    List getAllHashtag();
    String save(HashtagDTO hashtagRequest);

    HashtagDTO update(HashtagDTO hashtagDTO);
}
