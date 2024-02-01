package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public interface PositionTagService {
    public ResponseEntity getAllPositionTag();
    public ResponseEntity getPositionTagById(String positionTagId, HttpServletResponse res);
    public PositionTag getPositionTagByPositionTagId(String positionTagId);
    public PositionTag getPositionTagByPositionTagName(String positionName);
}
