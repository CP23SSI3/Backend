package com.example.internhub.services;

import com.example.internhub.entities.Address;
import com.example.internhub.entities.Company;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Primary
public class MySQLCompanyService implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    @Override
    public ResponseObjectList getAllCompanies() {
        return new ResponseObjectList(200,
                "Company's list is successfully sended.",
                companyRepository.findAll());
    }

    @Override
    public ResponseObject getCompanyById(String companyId) {
        return new ResponseObject(200,
                ("Company id " + companyId + " is successfully sended."),
                getCompanyByCompanyId(companyId));
    }

    @Override
    public Company getCompanyByCompanyId(String companyId) {
        return companyRepository.getById(companyId);
    }

    @Override
    public Company createCompany() {
        Address address = addressService.getAddressByAddressId("5ee4a66e-9750-4340-a9c3-6aff93aa2ad3");
        Company company = new Company();
        companyRepository.save(company);
        return company;
    }

    @Override
    public Company getCompany(Company company){
        return new Company(addressService.getAddress(company.getAddress()),
                company.getCompDesc(),
                company.getCompId(), company.getCompLogoKey(),
                company.getCompName(), company.getCompUrl(),
                company.getCreatedDate(), company.getDefaultWelfare(),
                company.getLastActive(), company.getLastUpdate());
    }

}
