package com.example.internhub.services;

import com.example.internhub.dtos.CreateEducationDTO;
import com.example.internhub.dtos.EditEducationDTO;
import com.example.internhub.entities.Education;
import com.example.internhub.entities.User;
import com.example.internhub.exception.EducationNotFoundException;
import com.example.internhub.exception.UserModifyEducationException;
import com.example.internhub.exception.UserNotFoundException;
import com.example.internhub.repositories.EducationRepository;
import com.example.internhub.responses.BadRequestResponseEntity;
import com.example.internhub.responses.ForbiddenResponseEntity;
import com.example.internhub.responses.NotFoundResponseEntity;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Primary
public class MySQLEducationService implements EducationService{

    @Autowired
    private AuthService authService;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity addEducation(CreateEducationDTO createEducationDTO, HttpServletRequest req) {
        try {
            Education education = modelMapper.map(createEducationDTO, Education.class);
            User user = userService.getUserById(createEducationDTO.getUser().getUserId());
            checkAuthForEducation(education.getUser().getUserId(), req);
            education.setUser(user);
            educationRepository.save(education);
            return new ResponseEntity(new ResponseObject(200, "Add education successfully.", education),
                    null, HttpStatus.OK);
        } catch (UserModifyEducationException e) {
            return new ForbiddenResponseEntity(e);
        } catch (UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }

    private void checkAuthForEducation(String userId, HttpServletRequest req) throws UserNotFoundException, UserModifyEducationException {
        User loginUser = authService.getUserFromServletRequest(req);
        if (!loginUser.getRole().equals("ADMIN") &&
                !loginUser.getUserId().equals(userId)) throw new UserModifyEducationException();
    }

    @Override
    public ResponseEntity deleteEducation(String educationId, HttpServletRequest req) {
        try {
            Education education = getEducationById(educationId);
            checkAuthForEducation(education.getUser().getUserId(), req);
            System.out.println(educationId);
//            educationRepository.deleteById(educationId);
            educationRepository.delete(education);
            return new ResponseEntity(new ResponseObject(200, "Delete education id " + educationId + " successfully.", null),
                    null, HttpStatus.OK);
        } catch (EducationNotFoundException | UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (UserModifyEducationException e) {
            return new ForbiddenResponseEntity(e);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public ResponseEntity editEducation(EditEducationDTO editEducationDTO, String educationId, HttpServletRequest req) {
        try {
            Education education = getEducationById(educationId);
            checkAuthForEducation(education.getUser().getUserId(), req);
            education.setDegree(editEducationDTO.getDegree());
            education.setEducationDesc(editEducationDTO.getEducationDesc());
            education.setField(editEducationDTO.getField());
            education.setGrade(editEducationDTO.getGrade());
            education.setGraduatedYear(education.getGraduatedYear());
            education.setSchoolName(education.getSchoolName());
            education.setStartedYear(education.getStartedYear());
            educationRepository.save(education);
            return new ResponseEntity(new ResponseObject(200, "Education is successfully updated.", education),
                    null, HttpStatus.OK);
        } catch (EducationNotFoundException | UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (UserModifyEducationException e) {
            return new ForbiddenResponseEntity(e);
        } catch(Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public ResponseEntity getAllEducations() {
        return new ResponseEntity(new ResponseObject(200, "Education's list is successfully sent.", educationRepository.findAll()),
                null, HttpStatus.OK);
    }

    private Education getEducationById(String educationId) throws EducationNotFoundException {
        try {
            return educationRepository.findById(educationId).orElseThrow();
        } catch (Exception ex) {
            throw new EducationNotFoundException(educationId);
        }
    }
}
