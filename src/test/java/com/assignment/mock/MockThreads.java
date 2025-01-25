package com.assignment.mock;

import com.assignment.domain.threads.domain.Threads;
import com.assignment.domain.users.domain.Users;

public class MockThreads {
    public static Threads make(Users users) {
        return Threads.builder()
                .users(users)
                .build();
    }
}
