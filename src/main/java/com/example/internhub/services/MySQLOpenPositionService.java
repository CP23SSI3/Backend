package com.example.internhub.services;

import com.example.internhub.dtos.CreateOpenPositionDTO;
import com.example.internhub.dtos.CreatePositionTagDTO;
import com.example.internhub.entities.OpenPosition;
import com.example.internhub.repositories.OpenPositionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLOpenPositionService implements OpenPositionService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PositionTagService positionTagService;
    @Autowired
    private OpenPositionRepository openPositionRepository;

    @Override
    public List<OpenPosition> getAllOpenPositions() {
        return openPositionRepository.findAll();
    }

    @Override
    public void createOpenPosition(CreateOpenPositionDTO createOpenPositionDTO) {
        OpenPosition openPosition = modelMapper.map(createOpenPositionDTO, OpenPosition.class);
        System.out.println(openPosition);
//        System.out.println(positionTagService.getPositionTagById(openPosition.getPositionTag().getPositionTagId()));
//        openPosition.setPositionTag(positionTagService.getPositionTagById(openPosition.getPositionTag().getPositionTagId()));
//        System.out.println(openPosition);
        openPositionRepository.save(openPosition);
//        System.out.println(openPosition);
//        System.out.println(createOpenPositionDTO);
    }
}
