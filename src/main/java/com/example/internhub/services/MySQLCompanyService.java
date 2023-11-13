package com.example.internhub.services;

import com.example.internhub.entities.Company;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLCompanyService implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public ResponseObjectList getAllCompanies() {
        return new ResponseObjectList(200,
                "Company's list is successfully sended.",
                companyRepository.findAll());
    }

    @Override
    public Company getCompanyById(String id) {
        return companyRepository.getById(id);
    }

}
