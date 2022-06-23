package com.ces.intern.sunsama.service;

import com.ces.intern.sunsama.http.request.HashtagRequest;

import java.util.List;

public interface HashtagService {
    List getAllHashtag();
    String save(HashtagRequest hashtagRequest);
}
