package com.example.internhub.services;

import com.example.internhub.dtos.CreateOpenPositionDTO;
import com.example.internhub.dtos.CreatePositionTagDTO;
import com.example.internhub.entities.OpenPosition;
import com.example.internhub.entities.Post;
import com.example.internhub.repositories.OpenPositionRepository;
import org.modelmapper.ModelMapper;
import com.example.internhub.responses.ResponseObjectList;
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
    public ResponseObjectList getAllOpenPositions() {
        return new ResponseObjectList(200,
                "Open position's list is successfully sended.",
                openPositionRepository.findAll());
    }

    @Override
    public void createOpenPosition(CreateOpenPositionDTO createOpenPositionDTO) {
        OpenPosition openPosition = modelMapper.map(createOpenPositionDTO, OpenPosition.class);
        openPositionRepository.save(openPosition);
    }


    @Override
    public void updateOpenPosition(Post post, List<OpenPosition> updateOpenPositionList) {
        List<OpenPosition> openPositionList = post.getOpenPositionList();
        openPositionList.clear();
        for (OpenPosition openPosition : updateOpenPositionList) {
            post.addOpenPosition(openPosition);
        }
    }
}
