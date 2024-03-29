package com.example.internhub.services;

import com.example.internhub.dtos.CreateSkillDTO;
import com.example.internhub.dtos.EditSkillDTO;
import com.example.internhub.entities.Role;
import com.example.internhub.entities.Skill;
import com.example.internhub.entities.User;
import com.example.internhub.exception.SkillNotFoundException;
import com.example.internhub.exception.UserModifySkillException;
import com.example.internhub.exception.UserNotFoundException;
import com.example.internhub.repositories.LanguageRepository;
import com.example.internhub.repositories.SkillRepository;
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
public class MySQLSkillService implements SkillService{

    @Autowired
    private AuthService authService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SkillRepository skillRepository;

    @Override
    public ResponseEntity addSkill(CreateSkillDTO createSkillDTO, HttpServletRequest req) {
        try {
            Skill skill = modelMapper.map(createSkillDTO, Skill.class);
            User user = userService.getUserById(createSkillDTO.getUser().getUserId());
            checkAuthForSkill(user.getUserId(), req);
            skill.setUser(user);
            skillRepository.save(skill);
            return new ResponseEntity(new ResponseObject(200, "Skill is successfully added.", skill),
                    null, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (UserModifySkillException e) {
            return new ResponseEntity(new ResponseObject(400 ,e.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    private void checkAuthForSkill(String userId, HttpServletRequest req) throws UserModifySkillException, UserNotFoundException {
        User loginUser = authService.getUserFromServletRequest(req);
        if (!loginUser.getRole().equals(Role.ADMIN) &&
                !loginUser.getUserId().equals(userId)) throw new UserModifySkillException();
    }

    @Override
    public ResponseEntity deleteSkill(String skillId, HttpServletRequest req) {
        try {
            Skill skill = getSkillById(skillId);
            checkAuthForSkill(skill.getUser().getUserId(), req);
            skillRepository.deleteById(skillId);
            return new ResponseEntity(new ResponseObject(200, "Delete skill id " + skillId + "successfully.", null),
                    null, HttpStatus.OK);
        } catch (SkillNotFoundException | UserNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (UserModifySkillException e) {
            return new ForbiddenResponseEntity(e);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public ResponseEntity editSkill(EditSkillDTO editSkillDTO, String skillId, HttpServletRequest req) {
        try {
            Skill skill = getSkillById(skillId);
            checkAuthForSkill(skill.getUser().getUserId(), req);
            skill.setSkillName(editSkillDTO.getSkillName());
            skill.setSkillDesc(editSkillDTO.getSkillDesc());
            skillRepository.save(skill);
            return new ResponseEntity(new ResponseObject(200, "Skill is successfully updated.", skill),
                    null, HttpStatus.OK);
        } catch (UserModifySkillException e) {
            return new ForbiddenResponseEntity(e);
        } catch (SkillNotFoundException | UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }


    private Skill getSkillById(String skillId) throws SkillNotFoundException {
        try {
            return skillRepository.findById(skillId).orElseThrow();
        } catch (Exception ex) {
            throw new SkillNotFoundException(skillId);
        }
    }

}
