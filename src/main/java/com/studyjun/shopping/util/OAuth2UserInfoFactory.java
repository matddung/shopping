package com.studyjun.shopping.util;

import com.studyjun.shopping.entity.oAuth2.Google;
import com.studyjun.shopping.entity.oAuth2.Kakao;
import com.studyjun.shopping.entity.oAuth2.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase("google")) {
            return new Google(attributes);
        } else if (registrationId.equalsIgnoreCase("kakao")) {
            return new Kakao(attributes);
        } else {
            DefaultAssert.isAuthentication(false);
        }
        return null;
    }
}