package com.example.internhub.services;

import com.example.internhub.dtos.CreateLanguageDTO;
import com.example.internhub.dtos.EditLanguageDTO;
import com.example.internhub.entities.Language;
import com.example.internhub.entities.Role;
import com.example.internhub.entities.User;
import com.example.internhub.exception.*;
import com.example.internhub.repositories.LanguageRepository;
import com.example.internhub.responses.BadRequestResponseEntity;
import com.example.internhub.responses.ForbiddenResponseEntity;
import com.example.internhub.responses.NotFoundResponseEntity;
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
    private LanguageRepository languageRepositories;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity addLanguage(CreateLanguageDTO createLanguageDTO, HttpServletRequest req) {
        try {
            checkAuthForLanguage(createLanguageDTO.getUser().getUserId(), req);
            Language language = modelMapper.map(createLanguageDTO, Language.class);
            User user = userService.getUserById(createLanguageDTO.getUser().getUserId());
            if (languageRepositories.findExistedLanguageName(language.getLanguageName(), user.getUserId()) != null)
                throw new LanguageExistedException();
            language.setUser(user);
            languageRepositories.save(language);
            return new ResponseEntity(new ResponseObject(200, "Add language successfully.", language),
                    null, HttpStatus.OK);
        } catch (LanguageExistedException e) {
            return new BadRequestResponseEntity(e);
        } catch (UserModifyLanguageException e) {
            return new ForbiddenResponseEntity(e);
        } catch (UserNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }

    private void checkAuthForLanguage(String userId, HttpServletRequest req) throws UserNotFoundException, UserModifyLanguageException {
        User loginUser = authService.getUserFromServletRequest(req);
        if (!loginUser.getRole().equals(Role.ADMIN) &&
                !loginUser.getUserId().equals(userId)) throw new UserModifyLanguageException();
    }

    @Override
    public ResponseEntity deleteLanguage(String languageId, HttpServletRequest req) {
        try {
            Language language = getLanguageById(languageId);
            checkAuthForLanguage(language.getUser().getUserId(), req);
            languageRepositories.delete(language);
            return new ResponseEntity(new ResponseObject(200, "Delete language id " + languageId + " successfully.", null),
                    null, HttpStatus.OK);
        } catch (UserModifyLanguageException e) {
            return new ForbiddenResponseEntity(e);
        } catch (UserNotFoundException | LanguageNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }

    @Override
    public ResponseEntity editLanguage(EditLanguageDTO editLanguageDTO, String languageId, HttpServletRequest req) {
        try {
            Language oldLanguage = getLanguageById(languageId);
            checkAuthForLanguage(oldLanguage.getUser().getUserId(), req);
            if (oldLanguage.getLanguageName().equals(editLanguageDTO.getLanguageName())) throw new NoEditedDataException();
            if (languageRepositories.findExistedLanguageName(editLanguageDTO.getLanguageName(), oldLanguage.getUser().getUserId()) != null)
                throw new LanguageExistedException();
            oldLanguage.setLanguageName(editLanguageDTO.getLanguageName());
            languageRepositories.save(oldLanguage);
            return new ResponseEntity(new ResponseObject(200, "Language is successfully updated.", null),
                    null, HttpStatus.OK);
        } catch (LanguageExistedException | NoEditedDataException e) {
            return new BadRequestResponseEntity(e);
        } catch (UserModifyLanguageException e) {
            return new ForbiddenResponseEntity(e);
        } catch (UserNotFoundException | LanguageNotFoundException e) {
            return new NotFoundResponseEntity(e);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
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
