package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.repositories.PositionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MySQLPositionTagService implements PositionTagService {

    @Autowired
    private PositionTagRepository positionTagRepository;


    @Override
    public List<PositionTag> getAllPositionTag() {
        return positionTagRepository.findAll();
    }
}
