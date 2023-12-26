package com.example.internhub.dtos;

import com.example.internhub.entities.Post;
import com.example.internhub.responses.Object;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Any;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPagination extends Object {
    int number;
    int size;
    int totalPages;
    int totalElements;
    List<Post> content;
}
