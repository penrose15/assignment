package com.assignment.mock;

import com.assignment.domain.chat.domain.Chat;
import com.assignment.domain.threads.domain.Threads;
import com.assignment.domain.users.domain.Users;

import java.util.ArrayList;
import java.util.List;

public class MockChat {
    public static Chat make(Users users, Threads threads) {
        return Chat.builder()
                .users(users)
                .threads(threads)
                .answer("aaaaaaa")
                .build();
    }

    public static List<Chat> makeChats(Users users, Threads threads, int cnt) {
        List<Chat> chats = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            chats.add(Chat.builder()
                    .users(users)
                    .threads(threads)
                    .answer("aaaaaaa" + i)
                    .build());
        }
        return chats;
    }
}
