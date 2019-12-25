package com.frame;

import org.springframework.util.AntPathMatcher;

public class Test {

    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        System.out.println(matcher.match("/*/*", "/test/test/test/test"));
        System.out.println(matcher.match("/*/*", "/test/test"));

        System.out.println(matcher.match("/auth/sys/user/*", "/auth/sys/user/add"));
        System.out.println(matcher.match("/auth/sys/user/{userNo:[0-9]*}", "/auth/sys/user/2223333"));
        System.out.println(matcher.match("/auth/sys/user/{userNo:[0-9]*}", "/auth/sys/user/test"));
        System.out.println(matcher.match("/auth/sys/user/{userNo:[0-9]*}", "/auth/sys/user/test/222"));

        System.out.println(matcher.match("/auth/sys/user/*", "/auth/sys/user/find"));

    }
}
