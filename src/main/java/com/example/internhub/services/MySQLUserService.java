package com.example.internhub.services;

import com.example.internhub.dtos.UserPagination;
import com.example.internhub.entities.User;
import com.example.internhub.repositories.UserRepository;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

//    @Value("${jwt.secret}")
//    private String secretKey;
//    private Algorithm algorithm = Algorithm.HMAC512(secretKey);
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseObject getAllUserPagination(int pageNumber, int pageSize,HttpServletResponse res) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        UserPagination userPagination = modelMapper.map(userPage, UserPagination.class);
        return new ResponseObject(200, "The user's list is successfully sended.",
                userPagination);
    }

    @Override
    public ResponseObject getResponseUserById(String userId, HttpServletRequest req, HttpServletResponse res) {
        try {
            return new ResponseObject(200, "The user's data is already sent.", getUserById(userId));
        } catch (Exception ex) {
            res.setStatus(404);
            return new ResponseObject(404, "User id " + userId + " not found", null);
        }
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public String encryptedPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
