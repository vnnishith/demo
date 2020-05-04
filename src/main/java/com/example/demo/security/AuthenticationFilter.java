package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.pojo.exceptions.AuthenticationException;
import com.example.demo.pojo.exceptions.BadRequestException;
import com.example.demo.pojo.exceptions.ExceptionResponse;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

/***
 * Class used to authenticate all incoming requests
 * Authorizations supported :
 * Basic Auth
 * Access Token Based Auth
 */
@Component
public class AuthenticationFilter extends GenericFilterBean {
    private static final Logger logger = LogManager.getLogger();
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList("POST:/v1/user");
    private static final List<String> ADMIN_ENDPOINTS = Arrays.asList("GET:/v1/admin/list-users");

    @Value("${admin.accesstoken}")
    private String adminAccessToken;

    @Autowired
    UserService userService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
       // HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpServletRequest);
        logger.info("Inside Security filter :" + resourcePath);
        try {
            if (resourcePath.contains("/v1") && !PUBLIC_ENDPOINTS.contains(String.format("%s:%s", httpServletRequest.getMethod(), resourcePath))) {
                doAuthentication(httpServletRequest, response, resourcePath);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            if (e instanceof AuthenticationException)
                handleExceptionMsg(401, 401, (HttpServletResponse) response, e);
            else
                handleExceptionMsg(500, 500, (HttpServletResponse) response, e);
        }
    }

    private void doAuthentication(HttpServletRequest request, ServletResponse response, String resourcePath) {


        if (!ADMIN_ENDPOINTS.contains(String.format("%s:%s", request.getMethod(), resourcePath))) {
            logger.info("Doing basic Auth for :" + resourcePath);
            doBasicAuth(request,response);
        } else {
            logger.info("Doing Admin Auth for :" + resourcePath);
            doAdminAuth(request, response);
        }
    }

    private void doBasicAuth(HttpServletRequest request,ServletResponse response) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            logger.info("invalid auth - no authorization headers sent");
            throw new AuthenticationException("Invalid auth");
        } else {
            try {
                String[] authorizationArray = authorization.split(" ");
                String type = authorizationArray[0];
                User user = null;
                if (type.contentEquals("Basic")) {
                    String basic = authorizationArray[1];
                    String userPass = new String(Base64.decodeBase64(basic));
                    String userName = userPass.split(":")[0];
                    String password = userPass.split(":")[1];
                    user = userService.authenticate(userName, password);
                    request.setAttribute("user", user);
                } else {
                   // response
                    throw new AuthenticationException("Invalid auth");
                }
            } catch (Exception ex) {
                throw new AuthenticationException("Invalid auth");
            }
        }
    }

    private void doAdminAuth(HttpServletRequest request,ServletResponse response) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            logger.info("invalid auth");
            throw new AuthenticationException("Invalid auth");
        } else {
            try {
                String[] authorizationArray = authorization.split(" ");
                String type = authorizationArray[0];
                if (!type.contentEquals("Access-Token") || !adminAccessToken.equals(authorizationArray[1])) {
                    throw new AuthenticationException("Invalid auth");
                }
            } catch (Exception ex) {
                throw new AuthenticationException("Invalid auth");
            }
        }
    }

    private void handleExceptionMsg(int status, int errorCode, HttpServletResponse response, Exception e)
            throws IOException {
        String tokenJsonResponse;
        ExceptionResponse er = new ExceptionResponse();
        er.setCode(errorCode);
        er.setMessage(e.getMessage());
        response.setStatus(status);
        tokenJsonResponse = new ObjectMapper().writeValueAsString(er);
        //handle Content type here ....
        response.addHeader("Content-Type", "text/json");
        response.getWriter().print(tokenJsonResponse);
        response.getWriter().flush();
      //  logger.error(tokenJsonResponse, e);
    }

}