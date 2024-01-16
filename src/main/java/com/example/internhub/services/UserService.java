package com.example.internhub.services;

import com.example.internhub.entities.User;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface UserService {

    public ResponseObject getAllUserPagination(int pageNumber, int pageSize, HttpServletResponse res);
    public ResponseObject getResponseUserById(String userId, HttpServletRequest req, HttpServletResponse res);
    public User getUserById(String userId);

}
