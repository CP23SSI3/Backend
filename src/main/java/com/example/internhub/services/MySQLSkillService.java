package com.example.internhub.services;

import com.example.internhub.dtos.CreateSkillDTO;
import com.example.internhub.entities.Skill;
import com.example.internhub.entities.User;
import com.example.internhub.exception.UserModifySkillException;
import com.example.internhub.exception.UserNotFoundException;
import com.example.internhub.repositories.LanguageRepository;
import com.example.internhub.repositories.SkillRepository;
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
        }
    }

    private void checkAuthForSkill(String userId, HttpServletRequest req) throws UserModifySkillException, UserNotFoundException {
        User loginUser = authService.getUserFromServletRequest(req);
        if (!loginUser.getRole().equals("ADMIN") &&
                !loginUser.getUserId().equals(userId)) throw new UserModifySkillException();
    }
}
