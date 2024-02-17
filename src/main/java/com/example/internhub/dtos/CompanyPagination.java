package com.example.internhub.dtos;

import com.example.internhub.entities.Company;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CompanyPagination {
    int number;
    int size;
    int totalPages;
    int totalElements;
    List<Company> content;
}
