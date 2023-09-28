package com.dhimantalapatra.demo.service;

import com.dhimantalapatra.demo.record.UserInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Map<String, UserInfo> userMap = new HashMap<>();
    @PostConstruct
    public void init(){
        userMap.put("adminUser",new UserInfo("sdkskds", "adminUser", "Admin User", "ADMIN"));
        userMap.put("normalUser",new UserInfo("e223qwe", "normalUser", "Normal User", "USER"));
    }
    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user =  userMap.get(username);
        if(null == user){
            throw new UsernameNotFoundException(String.format("%s not found!",username));
        }
        return user;
    }
}
