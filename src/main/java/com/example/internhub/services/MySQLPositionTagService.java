package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.exception.PositionTagNotFoundException;
import com.example.internhub.repositories.PositionTagRepository;
import com.example.internhub.responses.BadRequestResponseEntity;
import com.example.internhub.responses.NotFoundResponseEntity;
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
        } catch (PositionTagNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }

    @Override
    public PositionTag getPositionTagByPositionTagId(String positionTagId) throws PositionTagNotFoundException {
        try {
            return positionTagRepository.findById(positionTagId).orElseThrow();
        } catch (Exception ex) {
            throw new PositionTagNotFoundException(positionTagId);
        }
    }

    @Override
    public PositionTag getPositionTagByPositionTagName(String positionName) {
        return null;
    }

}
