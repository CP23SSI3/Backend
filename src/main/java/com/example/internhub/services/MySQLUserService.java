package com.example.internhub.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.internhub.dtos.CreateUserDTO;
import com.example.internhub.dtos.EditUserDTO;
import com.example.internhub.dtos.UserGeneralInformationListDTO;
import com.example.internhub.dtos.UserPagination;
import com.example.internhub.entities.*;
import com.example.internhub.exception.*;
import com.example.internhub.repositories.UserRepository;
import com.example.internhub.responses.BadRequestResponseEntity;
import com.example.internhub.responses.ForbiddenResponseEntity;
import com.example.internhub.responses.NotFoundResponseEntity;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MySQLUserService implements UserService {

    @Autowired
    private DecodeBearerTokenService decodeBearerTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressService addressService;
    @Value("${company.logo.link}")
    private String COMPANY_LOGO_LINK;
    @Value("${user.profile.link}")
    private String USER_PROFILE_LINK;
    @Value("${user.profile.bucket.name}")
    private String USER_PROFILE_BUCKET_NAME;
    @Autowired
    private S3Service s3Service;
    private ImageConverterService imageConverterService = new ImageConverterService();
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    private void checkIfUserCanModifyUser(HttpServletRequest req, String userId) throws UserModifyUserException {
        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        DecodedJWT token = decodeBearerTokenService.decodeBearerToken(authorizationHeader);
        if (!token.getClaim("role").asString().equals(Role.ADMIN.toString())) {
            User user = findUserByUserName(token.getSubject());
            if (!user.getUserId().equals(userId)) throw new UserModifyUserException();
        }
    }

    @Override
    public ResponseEntity checkIfUsernameExisted(String username) {
        if (isUsernameExisted(username))
            return new ResponseEntity(new ResponseObject(400, "This username has an existing account.", null),
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
            if (user.getRole() == Role.COMPANY) {
                String logoFile = user.getCompany().getCompLogoKey();
                String id = logoFile.split("\\.")[0];
                user.getCompany().setCompId(id);
                user.getCompany().setCompLogoKey(COMPANY_LOGO_LINK + "/" + logoFile);
            }
            user.setPassword(encryptedPassword(createUserDTO.getRawPassword()));
            userRepository.save(user);
            return new ResponseEntity(new ResponseObject(200, "Create user successfully.", user),
                    null, HttpStatus.OK);
        } catch (EmailExistedException | UsernameExistedException | UserCreateCompanyException ex) {
            return new BadRequestResponseEntity(ex);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public ResponseEntity deleteUser(HttpServletRequest req, String userId) {
        try {
            deleteUserByUserId(userId);
            return new ResponseEntity(new ResponseObject(200, "Delete user id " + userId + " successfully.", null),
                    null, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public void deleteUserByUserId(String userId) throws UserNotFoundException {
        getUserById(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public ResponseEntity editUserGeneralInformation(HttpServletRequest req,
                                                     String userId, EditUserDTO editUserDTO) {
        try {
            checkIfUserCanModifyUser(req, userId);
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
            System.out.println(!user.getUsername().equals(editUser.getUsername()) && isUsernameExisted(editUser.getUsername()));
            if (!user.getUsername().equals(editUser.getUsername()) && isUsernameExisted(editUser.getUsername())) throw new UsernameExistedException();
            if (!user.getEmail().equals(editUser.getEmail()) && isEmailExisted(editUser.getEmail())) throw new EmailExistedException();
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
        } catch (UsernameExistedException | EmailExistedException ex) {
            return new BadRequestResponseEntity(ex);
        } catch (UserModifyUserException e) {
            return new ForbiddenResponseEntity(e);
        } catch (UserNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
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
    public ResponseEntity getAllUserPagination(int pageNumber, int pageSize, String searchText, HttpServletResponse res) {
        Page<User> userPage = userRepository.findByUsernameContainingOrEmailContaining(searchText, searchText, PageRequest.of(pageNumber, pageSize, Sort.by("lastActive").descending()));
        UserPagination userPagination = modelMapper.map(userPage, UserPagination.class);
        return new ResponseEntity(new ResponseObject(200, "The user's list is successfully sent.",
                userPagination),
                null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getResponseUserById(String userId, HttpServletRequest req, HttpServletResponse res) {
        try {
            User user = getUserById(userId);
            UserGeneralInformationListDTO dto = modelMapper.map(user, UserGeneralInformationListDTO.class);
            dto.getEducations().sort(Comparator.comparing(Education::getStartedYear).reversed());
            dto.getLanguages().sort(Comparator.comparing(Language::getLanguageName));
            dto.getExperiences().sort(Comparator.comparing(Experience::getStartedYear).reversed());
            dto.getSkills().sort(Comparator.comparing(Skill::getSkillName));
            return new ResponseEntity(new ResponseObject(200, "The user's data is already sent.", dto),
                    null, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        try{
            User user = userRepository.findById(userId).orElseThrow();
            if (user == null) throw new UserNotFoundException("User id " + userId + " not found.");
            return user;
        } catch (Exception ex) {
            throw new UserNotFoundException("User id " + userId + " not found.");
        }
    }

    private boolean isEmailExisted(String email) {
        return findUserByEmail(email) != null;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }

    @Override
    public ResponseEntity updateUserProfilePicture(String userId, MultipartFile file, HttpServletRequest req) {
        try {
            User user = getUserById(userId);
            checkIfUserCanModifyUser(req, userId);
            String key = s3Service.uploadMultiPartFilePictureWithFilenameToJPGToS3(USER_PROFILE_BUCKET_NAME, userId, file);
            System.out.println(key);
            user.setUserProfileKey(USER_PROFILE_LINK + "/" + key);
            return new ResponseEntity(new ResponseObject(200, "User's profile picture is successfully updated.", null),
                    null, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (UserModifyUserException e) {
            return new ForbiddenResponseEntity(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isUsernameExisted(String username) {
        //If exists -> false
        //If not exist -> true
        return findUserByUserName(username) != null;
    }

    @Override
    public void userActive(User user) {
           user.setLastActive(LocalDateTime.now());
           userRepository.save(user);
    }


}
