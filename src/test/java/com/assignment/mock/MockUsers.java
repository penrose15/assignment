package com.assignment.mock;

import com.assignment.domain.users.domain.Users;

import java.util.ArrayList;
import java.util.List;

public class MockUsers {
    public static Users make() {
        return Users.builder()
                .email("test@gmail.com")
                .password("password")
                .username("test name")
                .role("user")
                .build();
    }

    public static List<Users> makeUsers(int cnt) {
        List<Users> users = new ArrayList<>();
        for (int i = 0; i<cnt; i++) {
            users.add(Users.builder()
                    .email(i + "test@gmail.com")
                    .password("password" + i)
                    .username("test name" + i)
                    .role("user")
                    .build());
        }
        return users;
    }
}
