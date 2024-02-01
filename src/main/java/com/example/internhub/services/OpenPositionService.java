package com.example.internhub.services;

import com.example.internhub.dtos.CreateOpenPositionDTO;
import com.example.internhub.dtos.CreatePositionTagDTO;
import com.example.internhub.entities.OpenPosition;
import com.example.internhub.entities.Post;
import com.example.internhub.exception.EmptyPositionListException;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpenPositionService {
    public ResponseEntity getAllOpenPositions();
    public void createOpenPosition(CreateOpenPositionDTO createOpenPositionDTO);
    public void updateOpenPosition(Post post, List<OpenPosition> updateOpenPositionList) throws EmptyPositionListException;
}
