package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;

import java.util.List;

public interface PositionTagService {
    public List<PositionTag> getAllPositionTag();
    public PositionTag getPositionTagById(String positionTagId);
//    public void createPositionTag();
    public PositionTag getPositionTag(PositionTag positionTag);
}
