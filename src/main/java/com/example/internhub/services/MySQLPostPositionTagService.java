package com.example.internhub.services;

import com.example.internhub.entities.Post;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.repositories.PostPositionTagRepository;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLPostPositionTagService implements PostPositionTagService{

    @Autowired
    public PostPositionTagRepository postPositionTagRepository;

    @Override
    public ResponseEntity getAllPostPositionTag() {
        List<PostPositionTag> postPositionTagList= postPositionTagRepository.findAll();
        return new ResponseEntity(new ResponseObjectList(200, "PostPositionTag's list is successfully sent.", postPositionTagList),
                null, HttpStatus.OK);
    }

    @Override
    public void updatePostPositionTag(Post post, List<PostPositionTag> postPositionTagList) {
        post.getPostTagListObject().clear();
        for (PostPositionTag tag : postPositionTagList) {
            post.addPostTag(tag);
        }
    }

}
