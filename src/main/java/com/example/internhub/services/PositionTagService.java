package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PositionTagService {
    public ResponseObjectList getAllPositionTag();
    public ResponseObject getPositionTagById(String positionTagId);
    public PositionTag getPositionTagByPositionTagId(String positionTagId);
    public PositionTag getPositionTagByPositionTagName(String positionName);
//    public PositionTag getPositionTag(PositionTag positionTag);
}
