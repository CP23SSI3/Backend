package com.example.internhub.services;

import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.stereotype.Service;

@Service
public interface PostPositionTagService {
    public ResponseObjectList getAllPostPositionTag();
}
