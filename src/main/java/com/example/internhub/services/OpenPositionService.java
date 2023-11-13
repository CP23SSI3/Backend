package com.example.internhub.services;

import com.example.internhub.dtos.CreateOpenPositionDTO;
import com.example.internhub.dtos.CreatePositionTagDTO;
import com.example.internhub.entities.OpenPosition;
import com.example.internhub.responses.ResponseObjectList;

import java.util.List;

public interface OpenPositionService {
    public void createOpenPosition(CreateOpenPositionDTO createOpenPositionDTO);
    public ResponseObjectList getAllOpenPositions();
}
