package com.example.internhub.services;

import com.example.internhub.entities.Address;
import com.example.internhub.entities.Company;
import com.example.internhub.repositories.CompanyRepository;
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
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(String id) {
        return companyRepository.getById(id);
    }

    @Override
    public Company createCompany() {
        Address address = addressService.getAddressById("5ee4a66e-9750-4340-a9c3-6aff93aa2ad3");
        Company company = new Company(UUID.randomUUID().toString(),
                "a", "a", "a", "a"
                , LocalDateTime.now(), LocalDateTime.now(),
                LocalDateTime.now(), "a.com", address);
        companyRepository.save(company);
        return company;
    }

    @Override
    public Company getCompany(Company company){
        return new Company(company.getCompId(), company.getCompName(),
                company.getCompLogoKey(), company.getCompDesc(),
                company.getDefaultWelfare(), company.getCreatedDate(),
                company.getLastUpdate(), company.getLastActive(),
                company.getCompUrl(), addressService.getAddress(company.getAddress()));
    }

}
