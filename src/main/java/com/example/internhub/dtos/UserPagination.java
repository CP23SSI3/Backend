package com.example.internhub.dtos;

import com.example.internhub.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserPagination {
    int number;
    int size;
    int totalPages;
    int totalElements;
    List<User> content;
}
