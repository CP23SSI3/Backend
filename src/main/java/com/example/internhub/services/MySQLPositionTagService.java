package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.repositories.PositionTagRepository;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@Primary
public class MySQLPositionTagService implements PositionTagService {

    @Autowired
    private PositionTagRepository positionTagRepository;


    @Override
    public ResponseEntity getAllPositionTag() {
        return new ResponseEntity(new ResponseObjectList(200, "Position's tag list is successfully sended.", positionTagRepository.findAll()),
                null,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity getPositionTagById(String positionTagId, HttpServletResponse res) {
        try {
            return new ResponseEntity(new ResponseObject(200,
                    ("Position tag id " + positionTagId + " is successfully sent."),
                    getPositionTagByPositionTagId(positionTagId)),
                    null,
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseObject(404,
                    "Position tag id " + positionTagId + " not found.",
                    null),
                    null,
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public PositionTag getPositionTagByPositionTagId(String positionTagId) {
        return positionTagRepository.getById(positionTagId);
    }

    @Override
    public PositionTag getPositionTagByPositionTagName(String positionName) {
        return null;
    }

}
