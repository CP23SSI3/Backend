package com.example.internhub.services;

import com.example.internhub.entities.Post;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.repositories.PostPositionTagRepository;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLPostPositionTagService implements PostPositionTagService{

    @Autowired
    public PostPositionTagRepository postPositionTagRepository;

    @Override
    public ResponseObjectList getAllPostPositionTag() {
        List<PostPositionTag> postPositionTagList= postPositionTagRepository.findAll();
        return new ResponseObjectList(200, "PostPositionTag's list is successfully sended.", postPositionTagList);
    }

    @Override
    public void updatePostPositionTag(Post post, List<PostPositionTag> postPositionTagList) {
        post.getPostTagListObject().clear();
        for (PostPositionTag tag : postPositionTagList) {
            post.addPostTag(tag);
        }
    }

}
