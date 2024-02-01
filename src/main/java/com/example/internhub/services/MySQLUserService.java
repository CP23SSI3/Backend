package com.example.internhub.services;

import com.example.internhub.dtos.CreateUserDTO;
import com.example.internhub.dtos.UserPagination;
import com.example.internhub.entities.User;
import com.example.internhub.entities.UserPrinciple;
import com.example.internhub.exception.EmailExistedException;
import com.example.internhub.exception.UserNotFoundException;
import com.example.internhub.exception.UsernameExistedException;
import com.example.internhub.repositories.UserRepository;
import com.example.internhub.responses.ResponseObject;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class MySQLUserService implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity getAllUserPagination(int pageNumber, int pageSize,HttpServletResponse res) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        UserPagination userPagination = modelMapper.map(userPage, UserPagination.class);
        return new ResponseEntity(new ResponseObject(200, "The user's list is successfully sended.",
                userPagination),
                null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getResponseUserById(String userId, HttpServletRequest req, HttpServletResponse res) {
        try {
            return new ResponseEntity(new ResponseObject(200, "The user's data is already sent.", getUserById(userId)),
                    null, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity(new ResponseObject(404, ex.getMessage(), null),
                    null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity(new ResponseObject(400, ex.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        try{
            return userRepository.findById(userId).orElseThrow();
        } catch (Exception ex) {
            throw new UserNotFoundException("User id " + userId + " not found.");
        }
    }

    @Override
    public String encryptedPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity createUser(CreateUserDTO createUserDTO) {
        try {
            User user = modelMapper.map(createUserDTO, User.class);
            if (findUserByEmail(user.getEmail()) != null) throw new EmailExistedException();
            if (findUserByUserName(user.getUsername()) != null) throw new UsernameExistedException();
            userRepository.save(user);
            return new ResponseEntity(new ResponseObject(200, "Create user successfully.", user),
                    null, HttpStatus.OK);
        } catch (EmailExistedException | UsernameExistedException ex) {
            return new ResponseEntity(new ResponseObject(400, ex.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(new ResponseObject(400, ex.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity deleteUser(String userId) {
        try {
            deleteUserByUserId(userId);
            return new ResponseEntity(new ResponseObject(200, "Delete user id " + userId + " successfully.", null),
                    null, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity(new ResponseObject(404, ex.getMessage(), null),
                    null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteUserByUserId(String userId) throws UserNotFoundException {
        getUserById(userId);
        userRepository.deleteById(userId);
    }

}
