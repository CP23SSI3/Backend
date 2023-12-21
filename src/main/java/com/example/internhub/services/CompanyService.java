package com.example.internhub.services;

import com.example.internhub.entities.Company;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public interface CompanyService {
    public ResponseObjectList getAllCompanies();
    public ResponseObject getCompanyById(String companyId, HttpServletResponse res);
    public Company getCompanyByCompanyId(String companyId);
    public Company getCompany(Company company);
}
