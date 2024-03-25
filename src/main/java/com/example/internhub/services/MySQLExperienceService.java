package com.example.internhub.services;

import com.example.internhub.dtos.CreateExperienceDTO;
import com.example.internhub.dtos.EditExperienceDTO;
import com.example.internhub.entities.Experience;
import com.example.internhub.entities.User;
import com.example.internhub.exception.ExperienceNotFoundException;
import com.example.internhub.exception.UserModifyExperienceException;
import com.example.internhub.exception.UserModifyLanguageException;
import com.example.internhub.exception.UserNotFoundException;
import com.example.internhub.repositories.ExperienceRepository;
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
public class MySQLExperienceService implements ExperienceService {

    @Autowired
    private AuthService authService;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity addExperience(CreateExperienceDTO createExperienceDTO, HttpServletRequest req) {
        try {
            checkAuthForExperience(createExperienceDTO.getUser().getUserId(), req);
            Experience experience = modelMapper.map(createExperienceDTO, Experience.class);
            User user = userService.getUserById(createExperienceDTO.getUser().getUserId());
            experience.setUser(user);
            experienceRepository.save(experience);
            return new ResponseEntity(new ResponseObject(200, "Add experience successfully.", experience),
                    null, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (UserModifyExperienceException e) {
            return new ForbiddenResponseEntity(e);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    private void checkAuthForExperience (String userId, HttpServletRequest req) throws UserNotFoundException, UserModifyExperienceException {
        User loginUser = authService.getUserFromServletRequest(req);
        if (!loginUser.getRole().equals("ADMIN") &&
                !loginUser.getUserId().equals(userId)) throw new UserModifyExperienceException();

    }

    @Override
    public ResponseEntity deleteExperience(String experienceId, HttpServletRequest req) {
        try {
            Experience experience = getExperienceById(experienceId);
            checkAuthForExperience(experience.getUser().getUserId(), req);
            experienceRepository.deleteById(experienceId);
            return new ResponseEntity(new ResponseObject(200, "Delete experience id " + experienceId + "  successfully.", null),
                    null, HttpStatus.OK);
        } catch (ExperienceNotFoundException | UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (UserModifyExperienceException e) {
            return new ForbiddenResponseEntity(e);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public ResponseEntity editExperience(EditExperienceDTO editExperienceDTO, String experienceId, HttpServletRequest req) {
        try {
            Experience experience = getExperienceById(experienceId);
            checkAuthForExperience(experience.getUser().getUserId(), req);
            experience.setCompName(editExperienceDTO.getCompName());
            experience.setEndedYear(editExperienceDTO.getEndedYear());
            experience.setExperienceDesc(editExperienceDTO.getExperienceDesc());
            experience.setExperienceName(editExperienceDTO.getExperienceName());
            experience.setPosition(editExperienceDTO.getPosition());
            experience.setStartedYear(editExperienceDTO.getStartedYear());
            experienceRepository.save(experience);
            return new ResponseEntity(new ResponseObject(200, "Experience is successfully updated.", experience),
                    null, HttpStatus.OK);
        } catch (ExperienceNotFoundException | UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (UserModifyExperienceException e) {
            return new ForbiddenResponseEntity(e);
        }
    }

    private Experience getExperienceById(String experienceId) throws ExperienceNotFoundException {
        try {
            Experience experience = experienceRepository.findById(experienceId).orElseThrow();
            return experience;
        } catch (Exception ex) {
            throw new ExperienceNotFoundException(experienceId);
        }
    }
}
