package com.example.internhub.dtos;

import com.example.internhub.entities.Post;
import com.example.internhub.responses.Object;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PostPagination extends Object {
    int number;
    int size;
    int totalPages;
    int totalElements;
    List<Post> content;
}
