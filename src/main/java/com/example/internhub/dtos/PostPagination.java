package com.example.internhub.dtos;

import com.example.internhub.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPagination {
    int number;
    int size;
    int totalPages;
    int totalElements;
    List<PostPaginationObject> content;
}
