package com.example.demo.ui.controller;

import java.util.Arrays;
import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.model.request.UserRequestModel;
import com.example.demo.model.response.OperationStatusModel;
import com.example.demo.model.response.RequestOperationStatus;
import com.example.demo.model.response.UserRest;
import com.example.demo.service.UserService;
import com.example.demo.shared.RolesEnum;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public UserRest getUser(@PathVariable String id) {
        UserRest returnUserRest = new UserRest();
        BeanUtils.copyProperties(userService.getUserByUserId(id), returnUserRest);
        return returnUserRest;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public UserRest createUser(@RequestBody UserRequestModel userDetail) {

        UserRest returnValue = new UserRest();
//        if (userDetail.getFirstName().isEmpty())
//            throw new UserServiceExeption(ErrorMessage.MISSING_REQUIRED_FIELD.getErrorMessage());
//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userDetail, userDto);
        
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetail, UserDto.class);
        
        userDto.setRoles( new HashSet<String>(Arrays.asList(RolesEnum.ROLE_USER.name())));
        
        UserDto createUser = userService.createUser(userDto);
//        BeanUtils.copyProperties(createUser, returnValue);
        returnValue = modelMapper.map(createUser, UserRest.class);

        return returnValue;
    }

    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public UserRest updateUser(@PathVariable String id, @RequestBody UserRequestModel userDetail) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetail, userDto);
        UserDto createUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(createUser, returnValue);

        return returnValue;
    }

    
    
    @DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        operationStatusModel.setOperationStatus(RequestOperationStatus.SUCCESS.name());
        return operationStatusModel;
    }
}
