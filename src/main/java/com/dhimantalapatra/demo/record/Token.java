package com.dhimantalapatra.demo.record;

public record Token(UserInfo userInfo, String token, long expiresOn, String description) {
}
