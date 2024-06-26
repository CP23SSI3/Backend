package com.example.internhub.services;

import com.example.internhub.dtos.EditCompanyDTO;
import com.example.internhub.entities.Company;
import com.example.internhub.exception.CompNotFoundException;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public interface CompanyService {
    public ResponseEntity editCompany(EditCompanyDTO editCompanyDTO, String compId, HttpServletRequest req);
    public ResponseEntity getAllCompanies(int pageNumber, int pageSize);
    public Company getCompany(Company company);
    public Company getCompanyByCompanyId(String companyId) throws CompNotFoundException;
    public ResponseEntity getCompanyById(String companyId, HttpServletResponse res);
    public ResponseEntity updateCompanyLogo(String compId, MultipartFile file, HttpServletRequest req);
    public ResponseEntity uploadCompanyLogo(MultipartFile file);
}
