package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;

import java.util.List;

public interface PositionTagService {
    public PositionTag getPositionTagById(String positionTagId);
    public PositionTag getPositionTag(PositionTag positionTag);
    public ResponseObjectList getAllPositionTag();
}
