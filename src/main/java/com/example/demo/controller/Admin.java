package com.example.demo.controller;

import com.example.demo.pojo.Response;
import com.example.demo.pojo.exceptions.BadRequestException;
import com.example.demo.service.UserService;
import com.example.demo.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
@Api(value="Admin Management", description="Operations which only an admin has access to do")
public class Admin {


    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @GetMapping("/list-users")
    @ApiOperation(value = "View list of users registered with the system", response = Response.class)
    public Response get_users(@RequestParam(defaultValue ="1") String pageNumber){
        if (Integer.parseInt(pageNumber) <1) {
            throw new BadRequestException("Invalid Page Number", Constants.INVALID_PAGENUMBER_EXCEPTION);
        }
        return new Response(userService.getUsers(Integer.parseInt(pageNumber)));
    }


}
