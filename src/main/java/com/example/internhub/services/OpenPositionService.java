package com.example.internhub.services;

import com.example.internhub.dtos.CreateOpenPositionDTO;
import com.example.internhub.dtos.CreatePositionTagDTO;
import com.example.internhub.entities.OpenPosition;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpenPositionService {
    public ResponseObjectList getAllOpenPositions();
    public void createOpenPosition(CreateOpenPositionDTO createOpenPositionDTO);
}
