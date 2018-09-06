package com.sdkserver.log.controller;

import com.sdkserver.log.Service.UserService;
import com.sdkserver.log.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService  userService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public User add()
        {


        return userService.get(1);
    }
}
