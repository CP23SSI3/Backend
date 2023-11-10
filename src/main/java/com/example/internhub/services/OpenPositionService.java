package com.example.internhub.services;

import com.example.internhub.dtos.CreateOpenPositionDTO;
import com.example.internhub.dtos.CreatePositionTagDTO;
import com.example.internhub.entities.OpenPosition;

import java.util.List;

public interface OpenPositionService {
    public List<OpenPosition> getAllOpenPositions();
    public void createOpenPosition(CreateOpenPositionDTO createOpenPositionDTO);
}
