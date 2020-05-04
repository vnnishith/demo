package com.example.demo.controller;

import com.example.demo.pojo.PhoneRequest;
import com.example.demo.pojo.Response;
import com.example.demo.pojo.exceptions.AuthenticationException;
import com.example.demo.service.UserService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
@Api(value="User Phone Management", description="Operations pertaining to a particular user's phone")
public class Phone {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Basic user token",
                    required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added a new phone"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized to access the resource")
    })
    @ApiOperation(value = "Add a new phone to a users account", response = Response.class)
    @PostMapping(value = "/user/{userId}/phone")
    public Response addPhone(@Valid @RequestBody PhoneRequest phoneRequest, @PathVariable("userId") UUID userId){
        com.example.demo.model.User authorisedUser = (com.example.demo.model.User)httpServletRequest.getAttribute("user");
        if (!authorisedUser.getUserId().equals(userId)) {
            logger.info("Invalid auth"+userId.toString());
            throw new AuthenticationException("Not allowed access to the resource");
        }
        com.example.demo.model.Phone phone = userService.addPhone(phoneRequest,authorisedUser);
        Response response = new Response(phone);
        return response;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Basic user token",
                    required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed phone"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized to access the resource")
    })
    @ApiOperation(value = "Remove a phone from a user's account", response = Response.class)
    @DeleteMapping(value = "/user/{userId}/phone")
    public Response removePhone(@PathVariable("userId") UUID userId, @RequestParam String phoneNumber) {
        com.example.demo.model.User authorisedUser = (com.example.demo.model.User)httpServletRequest.getAttribute("user");
        if (!authorisedUser.getUserId().equals(userId)) {
            logger.info("Invalid auth"+userId.toString());
            throw new AuthenticationException("Not allowed access to the resource");
        }

        userService.removePhone(authorisedUser,phoneNumber);
        Response response = new Response("Sucessfully Removed");
        return response;
    }

}
