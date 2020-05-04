package com.example.demo.controller;

import com.example.demo.pojo.Response;
import com.example.demo.pojo.SignupRequest;
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
@Api(value="User Management", description="Operations pertaining to a particular user")
public class User {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @PostMapping(value = "/user")
    @ApiOperation(value = "Register a new user", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered user"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public Response registerUser(@Valid @RequestBody SignupRequest signupRequest){
        com.example.demo.model.User user = userService.register(signupRequest);
        Response response = new Response(user);
        return response;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Basic user token",
                    required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed user"),
            @ApiResponse(code = 401, message = "Unauthorized to access the resource")
    })
    @ApiOperation(value = "Remove a user from a system", response = Response.class)
    @DeleteMapping(value = "/user/{userId}")
    public Response deleteUser(@PathVariable UUID userId){
        com.example.demo.model.User authorisedUser = (com.example.demo.model.User)httpServletRequest.getAttribute("user");
        if (!authorisedUser.getUserId().equals(userId)) {
            throw new AuthenticationException("Not allowed access to the resource");
        }
        userService.deregister(authorisedUser);
        Response response = new Response("Successfully deleted User");
        return response;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Basic user token",
                    required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 401, message = "Unauthorized to access the resource")
    })
    @ApiOperation(value = "Get all details of a user", response = Response.class)
    @GetMapping(value = "/user")
    public Response getUserDetails(){
        com.example.demo.model.User authorisedUser = (com.example.demo.model.User)httpServletRequest.getAttribute("user");
        Response response = new Response(authorisedUser);
        return response;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Basic user token",
                    required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user's preferred phone number"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized to access the resource")
    })
    @ApiOperation(value = "Update preferred phone number linked to a user", response = Response.class)
    @PutMapping(value = "/user/{userId}/preferred-phone-number")
    public Response updatePreferredPhoneNumber(@PathVariable UUID userId, @RequestParam String preferredPhoneNumber) {
        com.example.demo.model.User authorisedUser = (com.example.demo.model.User)httpServletRequest.getAttribute("user");
        if (!authorisedUser.getUserId().equals(userId)) {
            throw new AuthenticationException("Not allowed access to the resource");
        }
        com.example.demo.model.User user = userService.updatePreferredPhoneNumber(authorisedUser,preferredPhoneNumber);
        Response response = new Response(user);
        return response;
    }
}
