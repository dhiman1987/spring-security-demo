package com.dhimantalapatra.demo.controller;

import com.dhimantalapatra.demo.record.Token;
import com.dhimantalapatra.demo.record.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDetailsService userDetailsService;
    public AuthController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestParam String username, @RequestParam String password) {
        //dummy implementation
        try{
            if (username.equalsIgnoreCase(password)) {
                logger.info("Login successful for {}", username);
                UserInfo userInfo = (UserInfo) userDetailsService.loadUserByUsername(username);
                logger.debug("Fetched UserInfo {}", userInfo);
                String tokenStr = new String(
                        Base64.getEncoder().encode(
                                userInfo.toString().getBytes(StandardCharsets.UTF_8)));
                logger.debug("Generated token {}", tokenStr);
                long expiresOn = System.currentTimeMillis() + 1800000L; // current time + 30 mins
                logger.info("Returning token {}", tokenStr);
                Token token = new Token(userInfo, tokenStr, expiresOn,"Token generated");
                logger.debug("Returning token object {}", token);
                return ResponseEntity.ok(token);
            } else {
                logger.error("Username password does not match");
                return new ResponseEntity<Token>(
                        new Token(null, null, 0L, "Wrong username or password")
                        , HttpStatus.UNAUTHORIZED);
            }
        }catch (UsernameNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<Token>(
                    new Token(null, null, 0L, "Wrong username or password")
                    , HttpStatus.UNAUTHORIZED);
        }


    }

}
