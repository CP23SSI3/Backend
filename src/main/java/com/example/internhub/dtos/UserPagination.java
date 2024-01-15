package com.example.internhub.dtos;

import com.example.internhub.entities.User;
import com.example.internhub.responses.Object;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserPagination extends Object {
    int number;
    int size;
    int totalPages;
    int totalElements;
    List<User> content;
}
