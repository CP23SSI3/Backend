package com.example.internhub.services;

import com.example.internhub.entities.Company;
import com.example.internhub.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CompanyService {
    public List<Company> getAllCompanies();
}
