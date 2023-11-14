package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;

import java.util.List;

public interface PositionTagService {
    public ResponseObjectList getAllPositionTag();
    public ResponseObject getPositionTagById(String positionTagId);
    public PositionTag getPositionTagByPositionTagId(String positionTagId);
    public PositionTag getPositionTag(PositionTag positionTag);
}
