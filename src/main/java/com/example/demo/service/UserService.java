package com.example.demo.service;

import com.example.demo.dao.PhoneDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Phone;
import com.example.demo.model.User;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.pojo.PaginatedResponse;
import com.example.demo.pojo.PhoneRequest;
import com.example.demo.pojo.SignupRequest;
import com.example.demo.pojo.exceptions.AuthenticationException;
import com.example.demo.pojo.exceptions.BadRequestException;
import com.example.demo.utils.CommonUtils;
import com.example.demo.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UserDao userDao;

    @Autowired
    private PhoneDao phoneDao;

    public PaginatedResponse getUsers(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber-1, Constants.PER_PAGE_LIMIT);
        Page<User> users = userDao.findAll(page);
        return new PaginatedResponse(users.getContent(), pageNumber, Constants.PER_PAGE_LIMIT,users.getTotalElements());
    }


    @Transactional
    public User register(SignupRequest signupRequest) {
        validateSignUp(signupRequest);
        User user = new User();
        user.setEmailAddress(signupRequest.getEmailAddress());
        user.setPreferredPhoneNumber(signupRequest.getPreferredPhoneNumber());
        // password stored in encoded form in DB for security purposes
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(signupRequest.getPassword());
        user.setPassword(encodedPassword);
        user.setUsername(signupRequest.getUserName());
        user.setUserId(UUID.randomUUID());
        if (!StringUtils.isEmpty(signupRequest.getPreferredPhoneNumber())) {
            // user can have zero or multiple phones , therefore phone is only is added when it is sent
            Phone phone = new Phone();
            phone.setPhoneId(UUID.randomUUID());
            phone.setPhoneModel(signupRequest.getPhoneModel());
            phone.setPhoneName(signupRequest.getPhoneName());
            phone.setPhoneNumber(signupRequest.getPreferredPhoneNumber());
            phone.setUser(user);
            user.setPhones(Arrays.asList(phone));
        }
        userDao.save(user);
        logger.info("New user registered "+user.toString());
        return user;
    }

    private void validateSignUp(SignupRequest signupRequest) {
        if (signupRequest.getPassword()!=null &&  !signupRequest.getPassword().contentEquals(signupRequest.getConfirmPassword())){
            throw new BadRequestException("password and confirm password doesn't match", Constants.PASSWORD_MISMATCH_EXCEPTION);
        }
        if (!CommonUtils.validateEmailPattern(signupRequest.getEmailAddress())){
            throw new BadRequestException("Not a valid emailId", Constants.USER_INVALID_EMAIL_ID_EXCEPTION);
        }
        if (!StringUtils.isEmpty(signupRequest.getPreferredPhoneNumber())) {
            if (StringUtils.isEmpty(signupRequest.getPhoneName()) ||
                StringUtils.isEmpty(signupRequest.getPhoneModel())) {
                throw new BadRequestException("Phone Model and Name mandatory to add a phone", Constants.MISSING_PHONE_DETAILS_EXCEPTION);
            }
            if (!CommonUtils.validatePhoneNumPattern(signupRequest.getPreferredPhoneNumber())) {
                throw new BadRequestException("Not a valid phoneNumber", Constants.USER_INVALID_PHONE_NUM_EXCEPTION);
            }
        }
        User existingUserWithEmail = userDao.findByEmailAddress(signupRequest.getEmailAddress());
        if (existingUserWithEmail!=null){
            throw new BadRequestException("Email not unique", Constants.USER_EMAIL_ID_NOT_UNIQUE_EXCEPTION);
        }
        Phone phoneExisting = phoneDao.findByPhoneNumber(signupRequest.getPreferredPhoneNumber());
        if (phoneExisting!=null){
            throw new BadRequestException("Phone already registered", Constants.USER_PHONE_NUM_NOT_UNIQUE_EXCEPTION);
        }
        User userByUserName = userDao.findByUserName(signupRequest.getUserName());
        if(userByUserName!=null){
            throw new BadRequestException("Username not unique",Constants.USER_NAME_NOT_UNIQUE_EXCEPTION);
        }
    }

    public void deregister(User user) {
        userDao.delete(user);
        logger.info("User account deactivated ",user.toString());
    }

    public User authenticate(String username, String password) {
        User user = userDao.findByUserName(username);
        if(user==null){
            throw new AuthenticationException("Invalid login credentials");
        }
        // password stored in encoded form in DB for security purposes
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if(!matches){
            throw new AuthenticationException("Invalid login credentials");
        }
        return user;
    }

    @Transactional
    public Phone addPhone(PhoneRequest phoneRequest, User user) {
        if(!CommonUtils.validatePhoneNumPattern(phoneRequest.getPhoneNumber())){
            throw new BadRequestException("Not a valid phoneNumber", Constants.USER_INVALID_PHONE_NUM_EXCEPTION);
        }
        Phone existingPhone = phoneDao.findByPhoneNumber(phoneRequest.getPhoneNumber());
        if (existingPhone!= null) {
            throw new BadRequestException("Phone Number already exists", Constants.USER_PHONE_NUM_NOT_UNIQUE_EXCEPTION);
        }
        Phone phone = new Phone();
        BeanUtils.copyProperties(phoneRequest, phone);
        phone.setPhoneId(UUID.randomUUID());
        phone.setUser(user);
        // if the user has no phone number attached make this the preferred phone number
        if (user.getPreferredPhoneNumber()==null) {
            user.setPreferredPhoneNumber(phoneRequest.getPhoneNumber());
            userDao.save(user);
        }
        phoneDao.save(phone);
        return phone;
    }

    @Transactional
    public void removePhone(User user, String phoneNumber) {
        List<Phone> phones = user.getPhones();
        /* If the user must have atleast one phone -these checks can be added
        if (phones.size() == 1) {
            throw  new BadRequestException("There should be atleast one phone linked to the account",Constants.USER_MINPHONE_COUNT_EXCEPTION);
        }
        if (user.getPreferredPhoneNumber().equals(phoneNumber)) {
            throw  new BadRequestException("The preferred phone number cannot be removed",Constants.USER_PREFERRED_PHONE_DELETION_EXCEPTION);
        } */
        Optional<Phone> existingPhone = phones.stream().filter(x -> x.getPhoneNumber().equals(phoneNumber)).findFirst();
        if (existingPhone.isPresent()) {
            List<Phone> phonesExisting = phones.stream().filter(x -> !x.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList());
            user.setPhones(phonesExisting);
            if (phones.size() == 1) {
                user.setPreferredPhoneNumber(null);
            }
            userDao.save(user);
            phoneDao.deleteById(existingPhone.get().getPhoneId());
        } else {
            throw new BadRequestException("Not a valid phoneNumber", Constants.USER_INVALID_PHONE_NUM_EXCEPTION);
        }
    }

    public User updatePreferredPhoneNumber(User user, String preferredPhoneNumber) {
        List<Phone> phones = user.getPhones();
        Optional<Phone> existingPhone = phones.stream().filter(x -> x.getPhoneNumber().equals(preferredPhoneNumber)).findFirst();
        if (existingPhone.isPresent() && !user.getPreferredPhoneNumber().equals(preferredPhoneNumber)) {
            user.setPreferredPhoneNumber(preferredPhoneNumber);
            userDao.save(user);
        } else {
            throw new BadRequestException("Preferred Phone Number is not linked to the account", Constants.USER_PREFERRED_PHONE_NOT_LINKED_EXCEPTION);
        }
        return user;
    }
}
