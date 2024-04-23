package com.example.internhub.services;

import com.example.internhub.dtos.CompanyPagination;
import com.example.internhub.dtos.EditCompanyDTO;
import com.example.internhub.entities.Address;
import com.example.internhub.entities.Company;
import com.example.internhub.entities.Role;
import com.example.internhub.entities.User;
import com.example.internhub.exception.*;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.responses.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

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
    @Autowired
    private S3Service s3Service;
    @Value("${company.logo.bucket.name}")
    private String INTERNHUB_COMPANY_LOGO_BUCKET;
    @Value("${company.logo.link}")
    private String COMPANY_LOGO_LINK;

    private void checkAuthForCompany(String compId, HttpServletRequest req) throws UserNotFoundException, UserModifyCompanyException {
        User loginUser = authService.getUserFromServletRequest(req);
        if (!loginUser.getRole().equals(Role.ADMIN) &&
                !loginUser.getCompany().getCompId().equals(compId)) throw new UserModifyCompanyException();
    }

    @Override
    public ResponseEntity editCompany(EditCompanyDTO editCompanyDTO, String compId, HttpServletRequest req) {
        try {
            Company company = getCompanyByCompanyId(compId);
            checkAuthForCompany(compId, req);
            company.setCompDesc(editCompanyDTO.getCompDesc());
            company.setCompLogoKey(editCompanyDTO.getCompLogoKey());
            company.setDefaultWelfare(editCompanyDTO.getDefaultWelfare());
            company.setCompName(editCompanyDTO.getCompName());
            company.setCompUrl(editCompanyDTO.getCompUrl());
            company.setLastActive(editCompanyDTO.getLastActive());
            company.setLastUpdate(editCompanyDTO.getLastUpdate());
            if (editCompanyDTO.getAddress() != null) {
                Address address =  modelMapper.map(editCompanyDTO.getAddress(), Address.class);
                if (company.getAddress() == null) {
                    address.setAddressId(UUID.randomUUID().toString());
                    company.setAddress(address);
                } else {
                    addressService.updateAddress(company.getAddress(), address);
                }
            }
            companyRepository.save(company);
            return new ResponseEntity(new ResponseObject(200, "Edit company id "+ compId + " successfully.", company),
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

    @Override
    public ResponseEntity updateCompanyLogo(String compId, MultipartFile file, HttpServletRequest req) {
        try {
            Company comp = getCompanyByCompanyId(compId);
            checkAuthForCompany(compId, req);
            String key = s3Service.uploadMultiPartFilePictureWithFilenameToJPGToS3(INTERNHUB_COMPANY_LOGO_BUCKET, compId, file);
            comp.setCompLogoKey(COMPANY_LOGO_LINK + "/" + key);
            return new ResponseEntity(new ResponseObject(200, "Company logo is successfully updated.", null),
                    null, HttpStatus.OK);
        } catch (CompNotFoundException | UserNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (UserModifyCompanyException e) {
            return new ForbiddenResponseEntity(e);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }

    @Override
    public ResponseEntity uploadCompanyLogo(MultipartFile file) {
        try {
            String logo = s3Service.uploadMultiPartFilePictureWithFilenameToJPGToS3(INTERNHUB_COMPANY_LOGO_BUCKET, UUID.randomUUID().toString(), file);
            return new ResponseEntity(new ResponseObject(200,
                    "Company logo is successfully uploaded.",
                    logo), null, HttpStatus.OK);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }


}
