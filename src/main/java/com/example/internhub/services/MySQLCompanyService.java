package com.example.internhub.services;

import com.example.internhub.entities.Company;
import com.example.internhub.exception.CompNotFoundException;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletResponse;

@Service
@Primary
public class MySQLCompanyService implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    @Override
    public ResponseEntity getAllCompanies() {
        return new ResponseEntity(new ResponseObjectList(200,
                "Company's list is successfully sent.",
                companyRepository.findAll()),
                null,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity getCompanyById(String companyId, HttpServletResponse res) {
        try {
            return new ResponseEntity(new ResponseObject(200,
                    ("Company id " + companyId + " is successfully sent."),
                    getCompanyByCompanyId(companyId)),
                    null, HttpStatus.OK);
        } catch (CompNotFoundException ex) {
            return new ResponseEntity(new ResponseObject(404,
                    ex.getMessage(),
                    null),
                    null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity(new ResponseObject(400,
                    ex.getMessage(),
                    null),
                    null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Company getCompanyByCompanyId(String companyId) throws CompNotFoundException {
        try {
            return companyRepository.findById(companyId).orElseThrow();
        } catch (Exception e) {
            throw new CompNotFoundException(companyId);
        }
    }

    @Override
    public Company getCompany(Company company){
        try {
            return new Company(addressService.getAddress(company.getAddress()),
                    company.getCompDesc(),
                    company.getCompId(), company.getCompLogoKey(),
                    company.getCompName(), company.getCompUrl(),
                    company.getCreatedDate(), company.getDefaultWelfare(),
                    company.getLastActive(), company.getLastUpdate());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
