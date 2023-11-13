package com.example.internhub.services;

import com.example.internhub.entities.Company;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CompanyService {
    public ResponseObjectList getAllCompanies();
    public Company getCompanyById(String id);
    public Company createCompany();
    public Company getCompany(Company company);
}
