package com.example.internhub.services;

import com.example.internhub.entities.Post;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostPositionTagService {
    public ResponseObjectList getAllPostPositionTag();
    public void updatePostPositionTag(Post post, List<PostPositionTag> postPositionTagList);
}
