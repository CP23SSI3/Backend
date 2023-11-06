package com.example.internhub.services;

import com.example.internhub.entities.OpenPosition;
import com.example.internhub.repositories.OpenPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLOpenPositionService implements OpenPositionService {

    @Autowired
    private OpenPositionRepository openPositionRepository;


    @Override
    public List<OpenPosition> getAllOpenPositions() {
        return openPositionRepository.findAll();
    }
}
