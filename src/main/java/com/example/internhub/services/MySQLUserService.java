package com.example.internhub.services;

import com.example.internhub.dtos.CreateUserDTO;
import com.example.internhub.dtos.EditUserDTO;
import com.example.internhub.dtos.UserPagination;
import com.example.internhub.entities.Role;
import com.example.internhub.entities.User;
import com.example.internhub.exception.EmailExistedException;
import com.example.internhub.exception.UserCreateCompanyException;
import com.example.internhub.exception.UserNotFoundException;
import com.example.internhub.exception.UsernameExistedException;
import com.example.internhub.repositories.UserRepository;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class MySQLUserService implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressService addressService;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();


    @Override
    public ResponseEntity checkIfUsernameExisted(String username) {
        if (isUsernameExisted(username)) return new ResponseEntity(new ResponseObject(400, "This username has an existing account.", null),
                null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity(new ResponseObject(200, "This username don't has an existing account.", null),
                null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity checkIfUsernameAndEmailExisted(String username, String email) {
        String errorMessage = "";
        if (isUsernameExisted(username)) errorMessage += "This username has an existing account. ";
        if (isEmailExisted(email)) errorMessage += "This email has an existing account. ";
        if (errorMessage != "") return new ResponseEntity(new ResponseObject(400, errorMessage, null), null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity(new ResponseObject(200, "This email and username don't has an existing account.", null), null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity createUser(CreateUserDTO createUserDTO) {
        try {
            User user = modelMapper.map(createUserDTO, User.class);
            if (findUserByEmail(user.getEmail()) != null) throw new EmailExistedException();
            if (findUserByUserName(user.getUsername()) != null) throw new UsernameExistedException();
            if (user.getRole() == Role.USER && user.getCompany() != null) throw new UserCreateCompanyException();
            user.setPassword(encryptedPassword(createUserDTO.getRawPassword()));
            userRepository.save(user);
            return new ResponseEntity(new ResponseObject(200, "Create user successfully.", user),
                    null, HttpStatus.OK);
        } catch (EmailExistedException | UsernameExistedException | UserCreateCompanyException ex) {
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

    @Override
    public ResponseEntity editUserGeneralInformation(String userId, EditUserDTO editUserDTO) {
        try {
            User user = getUserById(userId);
            User editUser = modelMapper.map(editUserDTO, User.class);
            user.setDateOfBirth(editUser.getDateOfBirth());
            user.setFirstname(editUser.getFirstname());
            user.setGender(editUser.getGender());
            user.setLastActive(editUser.getLastActive());
            user.setLastname(editUser.getLastname());
            user.setLastUpdate(editUser.getLastUpdate());
            user.setPhoneNumber(editUser.getPhoneNumber());
            user.setUserDesc(editUser.getUserDesc());
            if (!user.getUsername().equals(editUser.getUsername()) && isUsernameExisted(editUser.getUsername())) throw new UsernameExistedException();
            user.setUsername(editUser.getUsername());
            user.setUserProfileKey(editUser.getUserProfileKey());
            if (user.getAddress() == null) {
                editUser.getAddress().setAddressId(UUID.randomUUID().toString());
                user.setAddress(editUser.getAddress());
            } else {
                addressService.updateAddress(user.getAddress(), editUser.getAddress());
            }
            userRepository.save(user);
            return new ResponseEntity(new ResponseObject(200, "Edit user id " + userId + "successful.", user),
                    null, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity(new ResponseObject(404, ex.getMessage(), null),
                    null, HttpStatus.NOT_FOUND);
        }
//        catch (UsernameExistedException ex) {
//            return new ResponseEntity(new ResponseObject(400, ex.getMessage(), null),
//                    null, HttpStatus.BAD_REQUEST);
//        }
        catch (Exception ex) {
            return new ResponseEntity(new ResponseObject(400, ex.getMessage(), null),
                    null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String encryptedPassword(String rawPassword) {
        return encoder.encode(rawPassword);
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
    public User findUserByUsernameOrEmail(String usernameOrEmail) {
        User user = findUserByUserName(usernameOrEmail);
        if (user == null) user = findUserByEmail(usernameOrEmail);
        return user;
    }

    @Override
    public ResponseEntity getAllUserPagination(int pageNumber, int pageSize,HttpServletResponse res) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("lastActive").descending()));
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

    private boolean isEmailExisted(String email) {
        return findUserByEmail(email) != null;
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
    public boolean isPasswordMatch(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }

    private boolean isUsernameExisted(String username) {
        //If exists -> false
        //If not exist -> true
        return findUserByUserName(username) != null;
    }


}
