package com.example.internhub.services;

import com.example.internhub.dtos.CompanyPagination;
import com.example.internhub.dtos.EditCompanyDTO;
import com.example.internhub.entities.Company;
import com.example.internhub.entities.User;
import com.example.internhub.exception.CompNotFoundException;
import com.example.internhub.exception.UserModifyCompanyException;
import com.example.internhub.exception.UserModifyLanguageException;
import com.example.internhub.exception.UserNotFoundException;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.responses.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Primary
public class MySQLCompanyService implements CompanyService{

    @Autowired
    private AddressService addressService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;

    private void checkAuthForCompany(String compId, HttpServletRequest req) throws UserNotFoundException, UserModifyCompanyException {
        User loginUser = authService.getUserFromServletRequest(req);
        if (!loginUser.getRole().equals("ADMIN") &&
                !loginUser.getCompany().getCompId().equals(compId)) throw new UserModifyCompanyException();
    }

    @Override
    public ResponseEntity editCompany(EditCompanyDTO editCompanyDTO, String compId, HttpServletRequest req) {
        try {
            Company company = getCompanyByCompanyId(compId);
            checkAuthForCompany(compId, req);
            company.setCompDesc(editCompanyDTO.getCompDesc());
            company.setCompLogoKey(editCompanyDTO.getCompLogoKey());
            company.setCompName(editCompanyDTO.getCompName());
            company.setCompUrl(editCompanyDTO.getCompUrl());
            company.setLastActive(editCompanyDTO.getLastActive());
            company.setLastUpdate(editCompanyDTO.getLastUpdate());
            companyRepository.save(company);
            return new ResponseEntity(new ResponseObject(200, "Edit company id "+ compId + " succussfully.", null),
                    null, HttpStatus.OK);
        } catch (CompNotFoundException | UserNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (UserModifyCompanyException ex) {
            return new ForbiddenResponseEntity(ex);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public ResponseEntity getAllCompanies(int pageNumber, int pageSize) {
        Page<Company> companyPage = companyRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("lastActive").descending()));
        return new ResponseEntity(new ResponseObject(200,
                "Company's list is successfully sent.",
                modelMapper.map(companyPage, CompanyPagination.class)),
                null,
                HttpStatus.OK);
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

    @Override
    public Company getCompanyByCompanyId(String companyId) throws CompNotFoundException {
        try {
            return companyRepository.findById(companyId).orElseThrow();
        } catch (Exception e) {
            throw new CompNotFoundException(companyId);
        }
    }

    @Override
    public ResponseEntity getCompanyById(String companyId, HttpServletResponse res) {
        try {
            return new ResponseEntity(new ResponseObject(200,
                    ("Company id " + companyId + " is successfully sent."),
                    getCompanyByCompanyId(companyId)),
                    null, HttpStatus.OK);
        } catch (CompNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }


}
