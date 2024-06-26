package com.example.internhub.services;

import com.example.internhub.dtos.CreateUserDTO;
import com.example.internhub.dtos.EditUserDTO;
import com.example.internhub.entities.User;
import com.example.internhub.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface UserService {
    public ResponseEntity checkIfUsernameExisted(String username);
    public ResponseEntity checkIfUsernameAndEmailExisted(String username, String email);
//    public ResponseEntity checkIfEmailExisted(String email);
    public ResponseEntity createUser(CreateUserDTO createUserDTO);
    public ResponseEntity deleteUser(HttpServletRequest req, String userId);
    public void deleteUserByUserId(String userId) throws UserNotFoundException;
    ResponseEntity editUserGeneralInformation(HttpServletRequest req,
                                              String userId, EditUserDTO editUserDTO);
    public String encryptedPassword(String rawPassword);
    public User findUserByUserName(String username);
    public User findUserByEmail(String email);
    public User findUserByUsernameOrEmail(String usernameOrEmail);
    public ResponseEntity getAllUserPagination(int pageNumber, int pageSize, String searchText, HttpServletResponse res);
    public ResponseEntity getResponseUserById(String userId, HttpServletRequest req, HttpServletResponse res);
    public User getUserById(String userId) throws UserNotFoundException;
    public boolean isPasswordMatch(String rawPassword, String encryptedPassword);
    public ResponseEntity updateUserProfilePicture(String userId, MultipartFile file, HttpServletRequest req);
    public void userActive(User user);
}
