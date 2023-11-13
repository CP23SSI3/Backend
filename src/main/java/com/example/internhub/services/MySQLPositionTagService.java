package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.repositories.PositionTagRepository;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLPositionTagService implements PositionTagService {

    @Autowired
    private PositionTagRepository positionTagRepository;


    @Override
    public ResponseObjectList getAllPositionTag() {
        return new ResponseObjectList(200,
                "Position's tag list is successfully sended.",
                positionTagRepository.findAll());
//        return positionTagRepository.findAll();
    }
}
