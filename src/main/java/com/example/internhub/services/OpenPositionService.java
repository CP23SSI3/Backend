package com.example.internhub.services;

import com.example.internhub.entities.OpenPosition;
import com.example.internhub.responses.ResponseObjectList;

import java.util.List;

public interface OpenPositionService {
    public ResponseObjectList getAllOpenPositions();
}
