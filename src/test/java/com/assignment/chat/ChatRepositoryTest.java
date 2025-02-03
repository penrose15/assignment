package com.assignment.chat;

import com.assignment.domain.chat.domain.Chat;
import com.assignment.domain.chat.repository.ChatRepository;
import com.assignment.domain.threads.domain.Threads;
import com.assignment.domain.threads.repository.ThreadsRepository;
import com.assignment.domain.users.domain.Users;
import com.assignment.domain.users.repository.UsersRepository;
import com.assignment.mock.MockChat;
import com.assignment.mock.MockThreads;
import com.assignment.mock.MockUsers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ChatRepositoryTest {
    private final int chatSize = 11;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ThreadsRepository threadsRepository;
    @Autowired
    private ChatRepository chatRepository;
    private Users users;

    @Test
    void findByUsersIdOrderByCreatedAtDescTest() {
        getOneChat();
        Optional<Chat> chat = chatRepository.findByUsersIdOrderByCreatedAtDesc(users.getId());

        assertThat(chat.isEmpty())
                .isFalse();
    }


    @Test
    @DisplayName("findAllByUsersIdOrderByCreatedAt 에서 첫페이지 조회")
    void getPageWhenGetFirstPage() {
        getChats();
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "createdAt");

        Page<Chat> chats = chatRepository.findAllByUsersIdOrderByCreatedAt(users.getId(), pageable);
        List<Chat> chatList = chats.getContent();

        assertThat(chatList.size())
                .isEqualTo(10);
        assertThat(chats.isLast())
                .isFalse();
    }

    @Test
    @DisplayName("findAllByUsersIdOrderByCreatedAt 에서 마지막 페이지 조회")
    void getPageWhenGetLastPage() {
        getChats();
        Pageable pageable = PageRequest.of(1, 10, Sort.Direction.ASC, "createdAt");

        Page<Chat> chats = chatRepository.findAllByUsersIdOrderByCreatedAt(users.getId(), pageable);
        List<Chat> chatList = chats.getContent();

        assertThat(chatList.size())
                .isEqualTo(1);
        assertThat(chats.isLast())
                .isTrue();
    }

    @Test
    @DisplayName("findAllByUsersIdOrderByCreatedAtDesc 에서 첫번째 페이지 조회")
    void getPageDescWhenGetFirstPage() {
        getChats();
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");

        Page<Chat> chats = chatRepository.findAllByUsersIdOrderByCreatedAtDesc(users.getId(), pageable);
        List<Chat> chatList = chats.getContent();

        assertThat(chatList.size())
                .isEqualTo(10);
        assertThat(chats.isLast())
                .isFalse();
    }

    @Test
    @DisplayName("findAllByUsersIdOrderByCreatedAtDesc 에서 마지막 페이지 조회")
    void getPageDescWhenGetLastPage() {
        getChats();
        Pageable pageable = PageRequest.of(1, 10, Sort.Direction.DESC, "createdAt");

        Page<Chat> chats = chatRepository.findAllByUsersIdOrderByCreatedAtDesc(users.getId(), pageable);
        List<Chat> chatList = chats.getContent();

        assertThat(chatList.size())
                .isEqualTo(1);
        assertThat(chats.isLast())
                .isTrue();
    }

    @Test
    void getAllByThreadId() {
        Long threadId = getThreadId();

        List<Chat> chats = chatRepository.findAllByThreadId(threadId);

        assertThat(chats.size())
                .isEqualTo(chatSize);
    }

    void getOneChat() {
        users = usersRepository.save(MockUsers.make());
        Threads threads = threadsRepository.save(MockThreads.make(users));
        chatRepository.save(MockChat.make(users, threads));
    }

    void getChats() {
        users = usersRepository.save(MockUsers.make());
        Threads threads = threadsRepository.save(MockThreads.make(users));
        chatRepository.saveAll(MockChat.makeChats(users, threads, chatSize));
    }

    Long getThreadId() {
        users = usersRepository.save(MockUsers.make());
        Threads threads = threadsRepository.save(MockThreads.make(users));
        chatRepository.saveAll(MockChat.makeChats(users, threads, chatSize));

        return threads.getId();
    }
}
