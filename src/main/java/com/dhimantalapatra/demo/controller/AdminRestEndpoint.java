package com.dhimantalapatra.demo.controller;

import com.dhimantalapatra.demo.record.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
public class AdminRestEndpoint {

    private UserDetailsService userDetailsService;
    public AdminRestEndpoint(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    @GetMapping(value = "/current-user", produces = "application/json")
    public ResponseEntity<UserInfo> home(){
        UserInfo userInfo = (UserInfo) userDetailsService.loadUserByUsername("adminUser");
        return new ResponseEntity<UserInfo>(userInfo,HttpStatus.OK);
    }
}
