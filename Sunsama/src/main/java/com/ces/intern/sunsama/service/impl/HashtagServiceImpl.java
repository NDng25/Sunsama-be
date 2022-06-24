package com.ces.intern.sunsama.service.impl;

import com.ces.intern.sunsama.dto.HashtagDTO;
import com.ces.intern.sunsama.entity.HashtagEntity;
import com.ces.intern.sunsama.http.exception.AlreadyExistException;
import com.ces.intern.sunsama.http.exception.NotFoundException;
import com.ces.intern.sunsama.reponsitory.HashtagRepository;
import com.ces.intern.sunsama.service.HashtagService;
import com.ces.intern.sunsama.util.ExceptionMessage;
import com.ces.intern.sunsama.util.ReponseMessage;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
@Service
public class HashtagServiceImpl implements HashtagService
{


    private final HashtagRepository hashtagRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public HashtagServiceImpl(HashtagRepository hashtagRepository, ModelMapper modelMapper) {
        this.hashtagRepository = hashtagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List getAllHashtag() {
        List listHastag = new ArrayList<>();

        hashtagRepository.findAll().forEach(listHastag::add);

        Type listType = new TypeToken<List<HashtagDTO>>() {}.getType();

        List<HashtagDTO> projectDTOS = modelMapper.map(listHastag,listType);
        return projectDTOS;
    }

    @Override
    @Transactional
    public String save(HashtagDTO hashtagDTO) {
        if(hashtagRepository.countByName(hashtagDTO.getName())>=1)
        {
            throw new AlreadyExistException(ExceptionMessage.Hashtag_ALREADY_EXIST.getMessage());
        }
        else
        {
            HashtagEntity hashtagEntity=modelMapper.map(hashtagDTO,HashtagEntity.class);
            hashtagRepository.save(hashtagEntity);
        }
        return ReponseMessage.CREATE_SUCCESS;
    }

    @Override
    public HashtagDTO update(HashtagDTO hashtagDTO) {
    HashtagEntity hashtagEntity=hashtagRepository.findById(hashtagDTO.getId())
            .orElseThrow(()->new NotFoundException(ExceptionMessage.NOT_FOUND_RECORD.getMessage()));
    hashtagEntity.setName(hashtagDTO.getName());
    hashtagRepository.save(hashtagEntity);

    return hashtagDTO ;
}
}
