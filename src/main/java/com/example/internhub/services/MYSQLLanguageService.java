package com.example.internhub.services;

import com.example.internhub.dtos.CreateLanguageDTO;
import com.example.internhub.dtos.EditLanguageDTO;
import com.example.internhub.entities.Language;
import com.example.internhub.entities.User;
import com.example.internhub.exception.*;
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
            checkAuthForLanguage(createLanguageDTO.getUserId(), req);
            Language language = modelMapper.map(createLanguageDTO, Language.class);
            User user = userService.getUserById(createLanguageDTO.getUserId());
            language.setUser(user);
            if (languageRepositories.getLanguagesByUserAndLanguageName(user, language.getLanguageName()) != null)
                throw new LanguageExistedException();
            languageRepositories.save(language);
            return new ResponseEntity(new ResponseObject(200, "Add language successful.", language),
                    null, HttpStatus.OK);
        } catch (UserModifyUserException | LanguageExistedException e) {
            return new ResponseEntity(new ResponseObject(400 ,e.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(new ResponseObject(404 ,e.getMessage(), null),
                    null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseObject(400 ,e.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        }
    }

    private void checkAuthForLanguage(String userId, HttpServletRequest req) throws UserNotFoundException, UserModifyUserException {
        User loginUser = authService.getUserFromServletRequest(req);
        if (!loginUser.getRole().equals("ADMIN") &&
                !loginUser.getUserId().equals(userId)) throw new UserModifyUserException();
    }

    @Override
    public ResponseEntity deleteLanguage(String languageId, HttpServletRequest req) {
        try {
            Language language = getLanguageById(languageId);
            checkAuthForLanguage(language.getUser().getUserId(), req);
            languageRepositories.delete(language);
            return new ResponseEntity(new ResponseObject(200, "Delete language id " + languageId + " successfully.", null),
                    null, HttpStatus.OK);
        } catch (UserModifyUserException e) {
            return new ResponseEntity(new ResponseObject(400 ,e.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException | LanguageNotFoundException e) {
            return new ResponseEntity(new ResponseObject(404 ,e.getMessage(), null),
                    null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity(new ResponseObject(400 ,ex.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity editLanguage(EditLanguageDTO editLanguageDTO, String languageId, HttpServletRequest req) {
        try {
            Language oldLanguage = getLanguageById(languageId);
            checkAuthForLanguage(oldLanguage.getUser().getUserId(), req);
            if (oldLanguage.getLanguageName().equals(editLanguageDTO.getLanguageName())) throw new NoEditedDataException();
            if (languageRepositories.getLanguagesByUserAndLanguageName(oldLanguage.getUser(), editLanguageDTO.getLanguageName()) != null)
                throw new LanguageExistedException();
            oldLanguage.setLanguageName(editLanguageDTO.getLanguageName());
            languageRepositories.save(oldLanguage);
            return new ResponseEntity(new ResponseObject(200, "Language is successfully updated.", null),
                    null, HttpStatus.OK);
        } catch (UserModifyUserException |LanguageExistedException | NoEditedDataException e) {
            return new ResponseEntity(new ResponseObject(400 ,e.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        }  catch (UserNotFoundException | LanguageNotFoundException e) {
            return new ResponseEntity(new ResponseObject(404 ,e.getMessage(), null),
                    null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseObject(400 ,e.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity getAllLanguages() {
        return new ResponseEntity(new ResponseObject(200, "Language list i8s successfully sended.",languageRepositories.findAll()),
                null, HttpStatus.OK);
    }

    private Language getLanguageById(String languageId) throws LanguageNotFoundException {
        try {
            return languageRepositories.findById(languageId).orElseThrow();
        } catch (Exception ex) {
            throw new LanguageNotFoundException(languageId);
        }
    }
}
