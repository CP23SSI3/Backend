package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.repositories.PositionTagRepository;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

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
    }

    @Override
    public ResponseObject getPositionTagById(String positionTagId, HttpServletResponse res) {
        try {
            return new ResponseObject(200,
                    ("Position tag id " + positionTagId + " is successfully sended."),
                    getPositionTagByPositionTagId(positionTagId));
        } catch (Exception e) {
            res.setStatus(404);
            return new ResponseObject(404,
                    "Position tag id " + positionTagId + " not found.",
            null);
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
