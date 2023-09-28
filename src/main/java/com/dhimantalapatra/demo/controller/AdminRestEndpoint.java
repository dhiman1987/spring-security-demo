package com.dhimantalapatra.demo.controller;

import com.dhimantalapatra.demo.record.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminRestEndpoint {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping(value = "/current-user", produces = "application/json")
    public ResponseEntity<UserInfo> home(@AuthenticationPrincipal UserDetails userPrincipal){
        UserInfo userInfo = (UserInfo) userPrincipal;
        return new ResponseEntity<UserInfo>(userInfo,HttpStatus.OK);
    }
}
