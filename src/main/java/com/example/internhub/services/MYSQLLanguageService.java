package com.example.internhub.services;

import com.example.internhub.dtos.CreateLanguageDTO;
import com.example.internhub.entities.Language;
import com.example.internhub.entities.User;
import com.example.internhub.exception.UserModifyUserException;
import com.example.internhub.exception.UserNotFoundException;
import com.example.internhub.repositories.LanguageRepositories;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MYSQLLanguageService implements LanguageService {
    @Autowired
    private AuthService authService;
    @Autowired
    private LanguageRepositories languageRepositories;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity addLanguage(CreateLanguageDTO createLanguageDTO, HttpServletRequest req) {
        try {
            User loginUser = authService.getUserFromServletRequest(req);
            System.out.println(loginUser.toString());
            if (!loginUser.getRole().equals("ADMIN") &&
                    !loginUser.getUserId().equals(createLanguageDTO.getUserId())) throw new UserModifyUserException();
            Language language = modelMapper.map(createLanguageDTO, Language.class);
            User user = userService.getUserById(createLanguageDTO.getUserId());
            language.setUser(user);
            languageRepositories.save(language);
            return new ResponseEntity(new ResponseObject(200, "Add language successful.", languageRepositories.getLanguagesByUser(user)),
                    null, HttpStatus.OK);
        } catch (UserModifyUserException e) {
            return new ResponseEntity(new ResponseObject(400 ,e.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(new ResponseObject(404 ,e.getMessage(), null),
                    null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity getAllLanguages() {
        return new ResponseEntity(new ResponseObject(200, "Language list i8s successfully sended.",languageRepositories.findAll()),
                null, HttpStatus.OK);
    }
}
