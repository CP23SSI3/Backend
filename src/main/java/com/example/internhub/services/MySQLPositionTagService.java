package com.example.internhub.services;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.repositories.PositionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLPositionTagService implements PositionTagService {

    @Autowired
    private PositionTagRepository positionTagRepository;


    @Override
    public List<PositionTag> getAllPositionTag() {
        return positionTagRepository.findAll();
    }

    @Override
    public PositionTag getPositionTagById(String positionTagId) {
        return positionTagRepository.getById(positionTagId);
    }

    @Override
    public PositionTag getPositionTag(PositionTag positionTag) {
        return new PositionTag(positionTag.getPositionTagId(), positionTag.getPositionName());
    }
}
