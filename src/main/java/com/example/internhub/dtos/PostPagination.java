package com.example.internhub.dtos;

import com.example.internhub.entities.Post;
import com.example.internhub.responses.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
public class PostPagination extends ResponseData {
    int number;
    int size;
    int totalPages;
    int totalElements;
    List<Post> content;
}
